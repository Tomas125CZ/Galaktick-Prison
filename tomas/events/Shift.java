package tomas.events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;
import tomas.main.Main;

public class Shift implements Listener {

    @EventHandler
    public void onShift(PlayerToggleSneakEvent e) {
        if(e.isSneaking()) {
            Location l = e.getPlayer().getLocation();
            Vector v =  l.getDirection();
            v.multiply(1.5f);
            e.getPlayer().setVelocity(v);
            if(Main.data.get(e.getPlayer()).getQuest() != null) Main.data.get(e.getPlayer()).getQuest().onPlayerFly();
        }
    }
}
