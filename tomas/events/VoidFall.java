package tomas.events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import tomas.planets.Planet;

public class VoidFall implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if(e.getPlayer().getLocation().getY() <= 50) {
            Planet planet = Planet.getPlanetByName(e.getPlayer().getWorld().getName());
            e.getPlayer().teleport(planet.spawnLocation());
        }
    }
}
