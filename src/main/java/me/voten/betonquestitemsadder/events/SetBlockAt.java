package me.voten.betonquestitemsadder.events;

import dev.lone.itemsadder.api.ItemsAdder;
import me.voten.betonquestitemsadder.util.NumberUtils;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.QuestEvent;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class SetBlockAt extends QuestEvent {

    private final String item;

    public SetBlockAt(Instruction instruction) throws InstructionParseException {
        super(instruction, true);
        this.item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ") + 1);
    }

    @Override
    protected Void execute(String playerID) {
        String name;
        String Location;
        World world;
        int x = 0, y = 0, z = 0;
        if (item.contains(" ")) {
            name = item.substring(0, item.indexOf(" "));
            Location = item.substring(item.indexOf(" ") + 1);
            for (int i = 0; i < 3; i++) {
                if (Location.contains(";")) {
                    if (NumberUtils.isInteger(Location.substring(0, Location.indexOf(";")))) {
                        switch (i) {
                            case 0 -> x = Integer.parseInt(Location.substring(0, Location.indexOf(";")));
                            case 1 -> y = Integer.parseInt(Location.substring(0, Location.indexOf(";")));
                            case 2 -> z = Integer.parseInt(Location.substring(0, Location.indexOf(";")));
                        }
                    }
                    Location = Location.substring(Location.indexOf(";") + 1);
                }
            }
            world = Bukkit.getWorld(Location);
            if (world == null) {
                System.out.println("WORLD cannot be null");
                return null;
            }
            Location loc = new Location(world, x, y, z);
            ItemStack block = ItemsAdder.getCustomItem(name);
            if (block.getType().isBlock()) {
                ItemsAdder.placeCustomBlock(loc, block);
			} else {
                System.out.println("Item must be a block");
			}
			return null;
		}
        return null;
    }
}
