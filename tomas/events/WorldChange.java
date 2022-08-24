package tomas.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import tomas.main.Main;
import tomas.planets.Planet;

public class WorldChange implements Listener {

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Planet planet = Planet.getPlanetByName(e.getPlayer().getWorld().getName());
        if(planet != null) {
            e.getPlayer().sendMessage(planet.message());
        }
        if(Main.data.get(e.getPlayer()).getMiner().isSpawned()) Main.data.get(e.getPlayer()).getMiner().despawn();
        if(Main.data.get(e.getPlayer()).getQuest() != null) Main.data.get(e.getPlayer()).getQuest().onWorldChange(e.getPlayer().getWorld());
        Main.instance.broadcast(ChatColor.AQUA + e.getPlayer().getName() + " " + e.getFrom().getName() + " -> " + e.getPlayer().getWorld().getName());
    }
}
