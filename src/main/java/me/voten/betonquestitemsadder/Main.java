package me.voten.betonquestitemsadder;

import me.voten.betonquestitemsadder.conditions.HasItemInHand;
import me.voten.betonquestitemsadder.conditions.HasItems;
import me.voten.betonquestitemsadder.conditions.IsBlock;
import me.voten.betonquestitemsadder.conditions.WearItems;
import me.voten.betonquestitemsadder.events.GiveItems;
import me.voten.betonquestitemsadder.events.PlayAnimation;
import me.voten.betonquestitemsadder.events.RemoveItems;
import me.voten.betonquestitemsadder.events.SetBlockAt;
import me.voten.betonquestitemsadder.objectives.*;
import pl.betoncraft.betonquest.BetonQuest;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main INSTANCE;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        //conditions
        BetonQuest.getInstance().registerConditions("hasitems", HasItems.class);
        BetonQuest.getInstance().registerConditions("wearitems", WearItems.class);
        BetonQuest.getInstance().registerConditions("hasiteminhand", HasItemInHand.class);
        BetonQuest.getInstance().registerConditions("isblock", IsBlock.class);
        //events
        BetonQuest.getInstance().registerEvents("removeitems", RemoveItems.class);
        BetonQuest.getInstance().registerEvents("giveitems", GiveItems.class);
        BetonQuest.getInstance().registerEvents("setblockat", SetBlockAt.class);
        BetonQuest.getInstance().registerEvents("playanimation", PlayAnimation.class);
        //objectives
        BetonQuest.getInstance().registerObjectives("craftitems", CraftingItem.class);
        BetonQuest.getInstance().registerObjectives("pickupitems", PickupItem.class);
        BetonQuest.getInstance().registerObjectives("blockbreak", BlockBreak.class);
        BetonQuest.getInstance().registerObjectives("blockplace", BlockPlace.class);
        BetonQuest.getInstance().registerObjectives("enchantitem", EnchantItem.class);
        BetonQuest.getInstance().registerObjectives("smeltingitems", SmeltingItem.class);
    }

    @Override
    public void onDisable() {

    }
}
