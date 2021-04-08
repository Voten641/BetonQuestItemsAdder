package me.voten.betonquestitemsadder.events;

import org.bukkit.inventory.ItemStack;

import dev.lone.itemsadder.api.ItemsAdder;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.QuestEvent;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;
import pl.betoncraft.betonquest.utils.PlayerConverter;

public class RemoveItems extends QuestEvent{
	private ItemStack item;
	private int amount;

	@SuppressWarnings("deprecation")
	public RemoveItems(Instruction instruction) throws InstructionParseException {
		super(instruction);
		String item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ")+1);
		if(item.contains(" ")) {
			this.item = ItemsAdder.getCustomItem(item.substring(0, item.indexOf(" ")));
			if(isInteger(item.substring(item.indexOf(" ")+1))) {
				this.amount = Integer.parseInt(item.substring(item.indexOf(" ")+1));
			}
			else {
				this.amount = 1;
			    throw new InstructionParseException("Amount must be a number"); 
			}
		}
		else {
			this.item = ItemsAdder.getCustomItem(instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ")+1));
			this.amount = 1;
		}
	}

	@Override
	protected Void execute(String playerID) throws QuestRuntimeException {
		if(this.item == null) {
			System.out.println("§c[BetonQuest -> ItemsAdder] Wrong item name("+item+")");
			return null;
		}
		this.item.setAmount(this.amount);
		PlayerConverter.getPlayer(playerID).getInventory().removeItem(this.item);
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
