package me.voten.betonquestitemsadder.conditions;

import dev.lone.itemsadder.api.ItemsAdder;
import me.voten.betonquestitemsadder.util.NumberUtils;
import org.bukkit.inventory.ItemStack;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.Condition;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.utils.PlayerConverter;

public class HasItemInHand extends Condition {

    private final String item;
    private final int amount;

    public HasItemInHand(Instruction instruction) throws InstructionParseException {
        super(instruction, true);
        String item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ") + 1);
        if (item.contains(" ")) {
            this.item = item.substring(0, item.indexOf(" "));
            if (NumberUtils.isInteger(item.substring(item.indexOf(" ") + 1))) {
                this.amount = Integer.parseInt(item.substring(item.indexOf(" ") + 1));
            } else {
                this.amount = 1;
                throw new InstructionParseException("Amount must be a number");
            }
        } else {
            this.item = instruction.getInstruction().substring(instruction.getInstruction().indexOf(" ") + 1);
            this.amount = 1;
        }
    }

    @Override
    protected Boolean execute(String playerID) {
        ItemStack HandItem = PlayerConverter.getPlayer(playerID).getInventory().getItemInMainHand();
        if (ItemsAdder.matchCustomItemName(HandItem, this.item)) {
            return HandItem.getAmount() >= this.amount;
        }
        return false;
    }
}
