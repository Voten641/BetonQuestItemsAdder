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

	@SuppressWarnings("deprecation")
	public HasItems(Instruction instruction) throws InstructionParseException{
		super(instruction);
		this.item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ")+1);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Boolean execute(String playerID) throws QuestRuntimeException {
		int amount = 1;
		String name = item;
		if(item.contains(" ")) {
			name = item.substring(0, item.indexOf(" "));
			if(isInteger(item.substring(item.indexOf(" ")+1))) {
				amount = Integer.parseInt(item.substring(item.indexOf(" ")+1));
			}
		}
		ItemStack[] inventoryItems = PlayerConverter.getPlayer(playerID).getInventory().getContents();
		for(ItemStack it : inventoryItems) {
			if(ItemsAdder.matchCustomItemName(it, name)) {
				if(it.getAmount() >= amount) {
					return true;
				}
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
