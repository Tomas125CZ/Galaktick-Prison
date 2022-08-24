package tomas.chests;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import tomas.main.Main;

public class ChestEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_AXE)) {
                ChestCreatorPlayer chestCreatorPlayer = Main.data.get(e.getPlayer()).getChestCreatorPlayer();
                chestCreatorPlayer.setSelected(true);
                chestCreatorPlayer.setX(e.getClickedBlock().getX());
                chestCreatorPlayer.setY(e.getClickedBlock().getY());
                chestCreatorPlayer.setZ(e.getClickedBlock().getZ());
                chestCreatorPlayer.setWorld(e.getClickedBlock().getWorld());
                e.setCancelled(true);
            }
            if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TRIPWIRE_HOOK)) {
                for(Chest chest : Main.chests) {
                    if(chest.getLocation().equals(e.getClickedBlock().getLocation())) {
                        if(chest.canOpenChest(e.getPlayer(), e.getClickedBlock())) {
                            chest.openChest(e.getPlayer());
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if(e.getClickedBlock().getType().equals(Material.CHEST)) {
                for(Chest chest : Main.chests) {
                    if(chest.getLocation().equals(e.getClickedBlock().getLocation())) {
                        Inventory inv = Bukkit.createInventory(e.getPlayer(), 54, chest.getName() + " " + ChatColor.AQUA + "rewards");
                        for(Reward reward : chest.getRewards()) {
                            inv.addItem(reward.getItem());
                        }
                        e.getPlayer().openInventory(inv);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
