package me.voten.betonquestitemsadder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import dev.lone.itemsadder.api.ItemsAdder;
import pl.betoncraft.betonquest.BetonQuest;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.Objective;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.utils.PlayerConverter;

public class SmeltingItem extends Objective implements Listener {

	private final ItemStack item;
	private final int amount;

	@SuppressWarnings("deprecation")
	public SmeltingItem(Instruction instruction) throws InstructionParseException {
		super(instruction);
		this.template = SmeltData.class;
		this.item = ItemsAdder.getCustomItem(instruction.next());
		if (this.item == null) {
			throw new InstructionParseException("Wrong item name");
		}
		String amount = instruction.next();
		if (isInteger(amount)) {
			this.amount = Integer.parseInt(amount);
			if (this.amount <= 0) {
				throw new InstructionParseException("Amount cannot be less than 1");
			}
		} else {
			throw new InstructionParseException("Wrong amount number");
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onItemGet(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;
		if (e.getInventory().getType().equals(InventoryType.FURNACE)
				|| e.getInventory().getType().equals(InventoryType.BLAST_FURNACE)) {
			if (e.getRawSlot() == 2) {
				Player p = (Player) e.getWhoClicked();
				String playerID = PlayerConverter.getID(p);
				if (containsPlayer(playerID)) {
					if (ItemsAdder.matchCustomItemName(e.getCurrentItem(), ItemsAdder.getCustomItemName(this.item))) {
						if (checkConditions(playerID)) {
							SmeltData playerData = (SmeltData) this.dataMap.get(playerID);
							playerData.subtract(e.getCurrentItem().getAmount());
							if (playerData.isZero())
								completeObjective(playerID);
						}
					}
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onShiftSmelting(InventoryClickEvent event) {
		if (event.getInventory().getType().equals(InventoryType.FURNACE) && event.getRawSlot() == 2
				&& event.getClick().equals(ClickType.SHIFT_LEFT) && event.getWhoClicked() instanceof Player) {
			String playerID = PlayerConverter.getID((Player) event.getWhoClicked());
			if (containsPlayer(playerID))
				event.setCancelled(true);
		}
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
			return Integer.toString(this.amount - ((SmeltData) this.dataMap.get(playerID)).getAmount());
		if ("amount".equalsIgnoreCase(name))
			return Integer.toString(((SmeltData) this.dataMap.get(playerID)).getAmount());
		return "";
	}

	public static class SmeltData extends Objective.ObjectiveData {
		private int amount;

		public SmeltData(String instruction, String playerID, String objID) {
			super(instruction, playerID, objID);
			this.amount = Integer.parseInt(instruction);
		}

		private int getAmount() {
			return this.amount;
		}

		private void subtract(int amount) {
			this.amount -= amount;
			update();
		}

		private boolean isZero() {
			return (this.amount <= 0);
		}

		public String toString() {
			return Integer.toString(this.amount);
		}
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}
}
