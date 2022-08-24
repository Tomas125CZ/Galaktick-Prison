package tomas.miner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MinerEvents implements Listener {

    public static String title = ChatColor.AQUA + "MINER";

    public static void openInventory(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9, title);
    }

    public void onInventoryClick(InventoryClickEvent e) {
        
    }
}
