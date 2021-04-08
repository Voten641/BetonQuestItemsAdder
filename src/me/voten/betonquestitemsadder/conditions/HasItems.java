package me.voten.betonquestitemsadder.conditions;

import org.bukkit.inventory.ItemStack;

import dev.lone.itemsadder.api.ItemsAdder;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.Condition;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;
import pl.betoncraft.betonquest.utils.PlayerConverter;

public class HasItems extends Condition{
	private final String item;
	private int amount;

	@SuppressWarnings("deprecation")
	public HasItems(Instruction instruction) throws InstructionParseException{
		super(instruction);
		String item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ")+1);
		if(item.contains(" ")) {
			this.item = item.substring(0, item.indexOf(" "));
			if(isInteger(item.substring(item.indexOf(" ")+1))) {
				this.amount = Integer.parseInt(item.substring(item.indexOf(" ")+1));
			}
			else {
				this.amount = 1;
			    throw new InstructionParseException("Amount must be a number"); 
			}
		}
		else {
			this.item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ")+1);
			this.amount = 1;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Boolean execute(String playerID) throws QuestRuntimeException {
		ItemStack[] inventoryItems = PlayerConverter.getPlayer(playerID).getInventory().getContents();
		int am = 0;
		for(ItemStack it : inventoryItems) {
			if(ItemsAdder.matchCustomItemName(it, this.item)) {
				if(it.getAmount() >= this.amount) {
					return true;
				}
				else {
					am += it.getAmount();
				}
			}
			if(am >= this.amount) {
				return true;
			}
		}
		return false;
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
