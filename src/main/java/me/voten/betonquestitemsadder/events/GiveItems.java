package me.voten.betonquestitemsadder.events;

import dev.lone.itemsadder.api.ItemsAdder;
import me.voten.betonquestitemsadder.util.NumberUtils;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.QuestEvent;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;
import pl.betoncraft.betonquest.utils.PlayerConverter;
import org.bukkit.inventory.ItemStack;

public class GiveItems extends QuestEvent {

    private final ItemStack item;
    private final int amount;

    public GiveItems(Instruction instruction) throws InstructionParseException {
        super(instruction, true);
        String item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ") + 1);
        if (item.contains(" ")) {
            this.item = ItemsAdder.getCustomItem(item.substring(0, item.indexOf(" ")));
            if (NumberUtils.isInteger(item.substring(item.indexOf(" ") + 1))) {
                this.amount = Integer.parseInt(item.substring(item.indexOf(" ") + 1));
            } else {
                this.amount = 1;
                throw new InstructionParseException("Amount must be a number");
            }
        } else {
            this.item = ItemsAdder.getCustomItem(instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ") + 1));
            this.amount = 1;
        }
    }

    @Override
    protected Void execute(String playerID) {
        if (this.item == null) {
            System.out.println("§c[BetonQuest -> ItemsAdder] Wrong item name ");
            return null;
        }
        this.item.setAmount(this.amount);
        PlayerConverter.getPlayer(playerID).getInventory().addItem(this.item);
        return null;
    }
}
