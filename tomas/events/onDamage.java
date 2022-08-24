package tomas.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class onDamage implements Listener {

    @EventHandler
    public void onDamageEvent(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            e.setCancelled(true);
            e.getEntity().sendMessage(ChatColor.GREEN + "Byl zablokován damage");
        }
    }
}
