package me.voten.betonquestitemsadder.conditions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import dev.lone.itemsadder.api.ItemsAdder;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.Condition;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;

public class IsBlock extends Condition{
	private final String item;

	@SuppressWarnings("deprecation")
	public IsBlock(Instruction instruction) throws InstructionParseException {
		super(instruction);
		this.item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ")+1);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Boolean execute(String playerID) throws QuestRuntimeException {
		String name = item;
		String Location;
		World world = null;
		int x = 0,y = 0,z = 0;
		if(item.contains(" ")) {
			name = item.substring(0, item.indexOf(" "));
			Location = item.substring(item.indexOf(" ")+1);
			for(int i = 0; i < 3; i++) {
				if(Location.contains(";")) {
					if(isInteger(Location.substring(0,Location.indexOf(";")))) {
						switch(i) {
						case 0:
							x = Integer.parseInt(Location.substring(0,Location.indexOf(";")));
							break;
						case 1:
							y = Integer.parseInt(Location.substring(0,Location.indexOf(";")));
							break;
						case 2:
							z = Integer.parseInt(Location.substring(0,Location.indexOf(";")));
							break;
						}
					}
					Location = Location.substring(Location.indexOf(";")+1);
				}
			}
			world = Bukkit.getWorld(Location);
			if(world == null) {System.out.println("WORLD cannot be null");return null;}
			Location loc = new Location(world, x, y, z);
			if(ItemsAdder.isCustomBlock(loc.getBlock())) {
				ItemStack it = ItemsAdder.getCustomBlock(loc.getBlock());
				if(ItemsAdder.matchCustomItemName(it, name)) {
					return true;
				}
			}
		}
		return false;
	}

	public Integer getInt(int i) {
		return i;
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
