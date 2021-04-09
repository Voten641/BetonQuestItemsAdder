package me.voten.betonquestitemsadder;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.voten.betonquestitemsadder.conditions.HasItemInHand;
import me.voten.betonquestitemsadder.conditions.HasItems;
import me.voten.betonquestitemsadder.conditions.IsBlock;
import me.voten.betonquestitemsadder.conditions.WearItems;
import me.voten.betonquestitemsadder.events.GiveItems;
import me.voten.betonquestitemsadder.events.PlayAnimation;
import me.voten.betonquestitemsadder.events.RemoveItems;
import me.voten.betonquestitemsadder.events.SetBlockAt;
import me.voten.betonquestitemsadder.objectives.BlockBreak;
import me.voten.betonquestitemsadder.objectives.BlockPlace;
import me.voten.betonquestitemsadder.objectives.CraftingItem;
import me.voten.betonquestitemsadder.objectives.EnchantItem;
import me.voten.betonquestitemsadder.objectives.PickupItem;
import pl.betoncraft.betonquest.BetonQuest;

public class Main extends JavaPlugin{

	private static Main inst;
	public boolean UpdateAvaible = false;
	public String newVersion;
	
	public void onEnable() {
		System.out.println("[BetonQuestItemsAdder] Successful Enabled");
		UpdateCheck();
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        int pluginID = 10973;
		new Metrics(this,pluginID);
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
	
	public void UpdateCheck(int i) {
        new UpdateChecker(this, 90933).getVersion(version -> {
            if (!this.getDescription().getVersion().equalsIgnoreCase(version)) {
                UpdateAvaible = true;
                newVersion = version;
            } else {
            	UpdateAvaible = false;
            }
        });
	}
	public void UpdateCheck() {
		Logger logger = this.getLogger();
        new UpdateChecker(this, 90933).getVersion(version -> {
            if (!this.getDescription().getVersion().equalsIgnoreCase(version)) {
                UpdateAvaible = true;
                newVersion = version;
                logger.info("§cThere is a new update available.");
                logger.info("§aNew Version: " + newVersion);
                logger.info("§cYour Version: " + this.getDescription().getVersion());
                for(Player p : Bukkit.getOnlinePlayers()) {
                	if(p.hasPermission("bqia.update")) {
                		p.sendMessage("§7[BetonQuestItemsAdder]  §cThere is a new update avaible");
                		p.sendMessage("§aNew Version: " + Main.getInst().newVersion);
                		p.sendMessage("§cYour Version: " + Main.getInst().getDescription().getVersion());
                	}
                }
            } else {
            	UpdateAvaible = false;
            	logger.info(" There is not a new update available.");
            }
        });
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
