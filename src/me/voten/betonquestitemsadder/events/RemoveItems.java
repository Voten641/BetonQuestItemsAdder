package me.voten.betonquestitemsadder.events;

import org.bukkit.inventory.ItemStack;

import dev.lone.itemsadder.api.ItemsAdder;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.QuestEvent;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;
import pl.betoncraft.betonquest.utils.PlayerConverter;

public class RemoveItems extends QuestEvent{
	private final String item;

	@SuppressWarnings("deprecation")
	public RemoveItems(Instruction instruction) throws InstructionParseException {
		super(instruction);
		this.item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ")+1);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Void execute(String playerID) throws QuestRuntimeException {
		int amount = 1;
		String name = item;
		if(item.contains(" ")) {
			name = item.substring(0, item.indexOf(" "));
			if(isInteger(item.substring(item.indexOf(" ")+1))) {
				amount = Integer.parseInt(item.substring(item.indexOf(" ")+1));
			}
		}
		ItemStack it = ItemsAdder.getCustomItem(name);
		if(it == null) {
			System.out.println("§c[BetonQuest -> ItemsAdder] Zla nazwa przedmiotu");
			return null;
		}
		it.setAmount(amount);
		PlayerConverter.getPlayer(playerID).getInventory().removeItem(it);
		return null;
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
