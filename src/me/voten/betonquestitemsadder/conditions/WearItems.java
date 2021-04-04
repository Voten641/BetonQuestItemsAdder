package me.voten.betonquestitemsadder.conditions;

import org.bukkit.inventory.ItemStack;

import dev.lone.itemsadder.api.ItemsAdder;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.Condition;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;
import pl.betoncraft.betonquest.utils.PlayerConverter;

public class WearItems extends Condition{
	private final String item;

	@SuppressWarnings("deprecation")
	public WearItems(Instruction instruction) throws InstructionParseException{
		super(instruction);
		this.item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ")+1);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Boolean execute(String playerID) throws QuestRuntimeException {
		ItemStack[] inventoryItems = PlayerConverter.getPlayer(playerID).getInventory().getArmorContents();
		for(ItemStack it : inventoryItems) {
			if(ItemsAdder.matchCustomItemName(it, item)) {
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
