package me.voten.betonquestitemsadder.conditions;

import dev.lone.itemsadder.api.ItemsAdder;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.Condition;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;
import pl.betoncraft.betonquest.utils.PlayerConverter;
import org.bukkit.inventory.ItemStack;

public class WearItems extends Condition {
    private final String item;

    public WearItems(Instruction instruction) {
        super(instruction, true);
        this.item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ") + 1);
    }

    @Override
    protected Boolean execute(String playerID) {
        ItemStack[] inventoryItems = PlayerConverter.getPlayer(playerID).getInventory().getArmorContents();
        for (ItemStack it : inventoryItems) {
            if (ItemsAdder.matchCustomItemName(it, item)) {
                return true;
            }
        }
        return false;
    }
}
