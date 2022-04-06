package me.voten.betonquestitemsadder.objectives;

import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import dev.lone.itemsadder.api.ItemsAdder;
import pl.betoncraft.betonquest.BetonQuest;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.Objective;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.utils.PlayerConverter;

public class EnchantItem extends Objective implements Listener {

	private final ItemStack item;
	private final List<EnchantmentData> enchantments;

	public EnchantItem(Instruction instruction) throws InstructionParseException {
		super(instruction);
		this.template = Objective.ObjectiveData.class;
		this.item = ItemsAdder.getCustomItem(instruction.next());
		if (this.item == null) {
			throw new InstructionParseException("Wrong item name");
		}
		this.enchantments = instruction.getList(EnchantmentData::convert);
		if (this.enchantments.isEmpty())
			throw new InstructionParseException("Not enough arguments 2");
	}

	@EventHandler(ignoreCancelled = true)
	public void onEnchant(EnchantItemEvent event) {
		String playerID = PlayerConverter.getID(event.getEnchanter());
		if (!containsPlayer(playerID))
			return;
		if (!ItemsAdder.matchCustomItemName(event.getItem(), ItemsAdder.getCustomItemName(this.item))) {
			return;
		}
		for (EnchantmentData enchant : this.enchantments) {
			if (!event.getEnchantsToAdd().keySet().contains(enchant.getEnchantment())
					|| event.getEnchantsToAdd().get(enchant.getEnchantment()) < enchant
							.getLevel())
				return;
		}
		if (checkConditions(playerID))
			completeObjective(playerID);
	}

	@Override
	public void start() {
		Bukkit.getPluginManager().registerEvents(this, BetonQuest.getInstance());
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this);
	}

	@Override
	public String getDefaultDataInstruction() {
		return "";
	}

	@Override
	public String getProperty(String name, String playerID) {
		return "";
	}

	public static class EnchantmentData {
		private final Enchantment enchantment;

		private final int level;

		public EnchantmentData(Enchantment enchantment, int level) {
			this.enchantment = enchantment;
			this.level = level;
		}

		public static EnchantmentData convert(String string) throws InstructionParseException {
			int level;
			String[] parts = string.split(":");
			if (parts.length != 2)
				throw new InstructionParseException("Could not parse enchantment: " + string);

			Enchantment enchantment = Enchantment.getByName(parts[0].toUpperCase(Locale.ROOT));
			if (enchantment == null)
				throw new InstructionParseException("Enchantment type '" + parts[0] + "' does not exist");
			try {
				level = Integer.parseInt(parts[1]);
			} catch (NumberFormatException e) {
				throw new InstructionParseException("Could not parse enchantment level: " + string, e);
			}
			return new EnchantmentData(enchantment, level);
		}

		public Enchantment getEnchantment() {
			return this.enchantment;
		}

		public int getLevel() {
			return this.level;
		}
	}
}
