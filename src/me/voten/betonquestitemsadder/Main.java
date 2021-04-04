package me.voten.betonquestitemsadder;

import org.bukkit.plugin.java.JavaPlugin;

import me.voten.betonquestitemsadder.conditions.HasItemInHand;
import me.voten.betonquestitemsadder.conditions.HasItems;
import me.voten.betonquestitemsadder.conditions.IsBlock;
import me.voten.betonquestitemsadder.conditions.WearItems;
import me.voten.betonquestitemsadder.events.GiveItems;
import me.voten.betonquestitemsadder.events.RemoveItems;
import me.voten.betonquestitemsadder.events.SetBlockAt;
import pl.betoncraft.betonquest.BetonQuest;

public class Main extends JavaPlugin{

	private static Main inst;
	
	public void onEnable() {
		System.out.println("[BetonQuestItemsAdder] Successful Enabled");
		//conditions
		BetonQuest.getInstance().registerConditions("hasitems", HasItems.class);
		BetonQuest.getInstance().registerConditions("wearitems", WearItems.class);
		BetonQuest.getInstance().registerConditions("hasiteminhand", HasItemInHand.class);
		BetonQuest.getInstance().registerConditions("isblock", IsBlock.class);
		//events
		BetonQuest.getInstance().registerEvents("removeitems", RemoveItems.class);
		BetonQuest.getInstance().registerEvents("giveitems", GiveItems.class);
		BetonQuest.getInstance().registerEvents("setblockat", SetBlockAt.class);
	}
	
	public void onDisable() {

		System.out.println("[BetonQuestItemsAdder] Disabled");
	}
	
    public Main() {
        inst = this;
    }

    public static Main getInst() {
        if (inst == null) {
            return new Main();
        }
        return inst;
    }
}
