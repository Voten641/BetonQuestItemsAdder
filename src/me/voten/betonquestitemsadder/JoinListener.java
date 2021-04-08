package me.voten.betonquestitemsadder;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    
    @EventHandler
    public void Join(PlayerJoinEvent e) {
    	if(Main.getInst().UpdateAvaible) {
        	if(e.getPlayer().hasPermission("bqia.update")) {
        		Main.getInst().UpdateCheck(1);
        		e.getPlayer().sendMessage("§7[BetonQuestItemsAdder]  §cThere is a new update avaible");
        		e.getPlayer().sendMessage("§aNew Version: " + Main.getInst().newVersion);
        		e.getPlayer().sendMessage("§cYour Version: " + Main.getInst().getDescription().getVersion());
        	}
    	}
    }
}
