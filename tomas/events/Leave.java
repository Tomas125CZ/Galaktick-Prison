package tomas.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import tomas.main.Main;

public class Leave implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Player player = e.getPlayer();
        for(Player player1 : Bukkit.getOnlinePlayers()) {
            player1.sendMessage(ChatColor.AQUA + "Hráč " + player.getName() + " se odpojil ze serveru!");
        }
    }
}