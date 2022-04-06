package me.voten.betonquestitemsadder.conditions;

import dev.lone.itemsadder.api.ItemsAdder;
import me.voten.betonquestitemsadder.util.NumberUtils;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.Condition;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class IsBlock extends Condition {

    private final String item;

    public IsBlock(Instruction instruction) {
        super(instruction, true);
        this.item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ") + 1);
    }

    @Override
    protected Boolean execute(String playerID) {
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
            if (ItemsAdder.isCustomBlock(loc.getBlock())) {
                ItemStack it = ItemsAdder.getCustomBlock(loc.getBlock());
                return ItemsAdder.matchCustomItemName(it, name);
            }
        }
        return false;
    }
}
