package me.voten.betonquestitemsadder.objectives;

import dev.lone.itemsadder.api.ItemsAdder;
import me.voten.betonquestitemsadder.util.NumberUtils;
import pl.betoncraft.betonquest.BetonQuest;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.Objective;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.utils.PlayerConverter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CraftingItem extends Objective implements Listener {

    private final ItemStack item;
    private final int amount;

    public CraftingItem(Instruction instruction) throws InstructionParseException {
        super(instruction);
        this.template = CraftData.class;
        String item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ") + 1);
        if (item.contains(" ")) {
            String am = item.substring(item.indexOf(" ") + 1);
            this.item = ItemsAdder.getCustomItem(item.substring(0, item.indexOf(" ")));
            if (am.contains(" ")) {
                am = am.substring(0, am.indexOf(" "));
            }
            if (NumberUtils.isInteger(am)) {
                this.amount = Integer.parseInt(am);
            } else {
                this.amount = 1;
                throw new InstructionParseException("Amount must be a number");
            }
        } else {
            this.item = ItemsAdder.getCustomItem(instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ") + 1));
            this.amount = 1;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCrafting(CraftItemEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        String playerID = PlayerConverter.getID(player);
        CraftData playerData = (CraftData) this.dataMap.get(playerID);
        if (containsPlayer(playerID)) {
            if (ItemsAdder.matchCustomItemName(event.getRecipe().getResult(), ItemsAdder.getCustomItemName(this.item))) {
                if (checkConditions(playerID)) {
                    int absoluteCreations = countPossibleCrafts(event);
                    int remainingSpace = countRemainingSpace(player);
                    playerData.subtract(Math.min(remainingSpace, absoluteCreations));
                    if (playerData.isZero()) completeObjective(playerID);
                }
            }
        }
    }

    private int countPossibleCrafts(CraftItemEvent event) {
        int possibleCreations = 1;
        if (event.isShiftClick()) {
            possibleCreations = Integer.MAX_VALUE;
            for (ItemStack item : event.getInventory().getMatrix()) {
                if (item != null && !item.getType().equals(Material.AIR))
                    possibleCreations = Math.min(possibleCreations, item.getAmount());
            }
        }
        return possibleCreations * event.getRecipe().getResult().getAmount();
    }

    private int countRemainingSpace(Player player) {
        int remainingSpace = 0;
        for (ItemStack i : player.getInventory().getStorageContents()) {
            if (i == null || i.getType().equals(Material.AIR)) {
                remainingSpace += this.item.getMaxStackSize();
            } else if (i.equals(this.item)) {
                remainingSpace += this.item.getMaxStackSize() - i.getAmount();
            }
        }
        return remainingSpace;
    }

    @Override
    public void start() {
        Bukkit.getPluginManager().registerEvents(this, (Plugin) BetonQuest.getInstance());
    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public String getDefaultDataInstruction() {
        return Integer.toString(this.amount);
    }

    @Override
    public String getProperty(String name, String playerID) {
        if ("left".equalsIgnoreCase(name))
            return Integer.toString(this.amount - ((CraftData) this.dataMap.get(playerID)).getAmount());
        if ("amount".equalsIgnoreCase(name))
            return Integer.toString(((CraftData) this.dataMap.get(playerID)).getAmount());
        return "";
    }

    public static class CraftData extends Objective.ObjectiveData {
        private int amount;

        public CraftData(String instruction, String playerID, String objID) {
            super(instruction, playerID, objID);
            this.amount = Integer.parseInt(instruction);
        }

        private void subtract(int amount) {
            this.amount -= amount;
            update();
        }

        private boolean isZero() {
            return (this.amount <= 0);
        }

        private int getAmount() {
            return this.amount;
        }

        public String toString() {
            return String.valueOf(this.amount);
        }
    }
}
