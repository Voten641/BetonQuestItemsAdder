package me.voten.betonquestitemsadder.events;

import dev.lone.itemsadder.api.ItemsAdder;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.QuestEvent;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;
import pl.betoncraft.betonquest.utils.PlayerConverter;

public class PlayAnimation extends QuestEvent{

	private final String name;
	
	@SuppressWarnings("deprecation")
	public PlayAnimation(Instruction instruction) throws InstructionParseException {
		super(instruction);
		this.name = instruction.next();
	}

	@Override
	protected Void execute(String playerID) throws QuestRuntimeException {
		ItemsAdder.playTotemAnimation(PlayerConverter.getPlayer(playerID), this.name);
		return null;
	}

}
