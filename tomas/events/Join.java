package tomas.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tomas.data.DataPlayer;
import tomas.enchantments.CustomEnchants;
import tomas.main.Main;
import tomas.main.ScoreBoard;

public class Join implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player player = e.getPlayer();
        player.sendMessage(ChatColor.AQUA + "Vítej na serveru!");
        Main.instance.broadcast(ChatColor.AQUA + "Hráč " + player.getName() + " se připojil na server!");
        Main.data.put(player, new DataPlayer(player));
        player.sendMessage(player.hasPermission("perm.test") + "");
        ScoreBoard.createBoard(player);
        enchantments(player);
        Main.data.get(player).TaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
            int counter = 0;
            @Override
            public void run() {
                counter++;
                if(counter == 60) {
                    Main.data.get(player).getDataManager().save();
                    counter = 0;
                }
                ScoreBoard.createBoard(player);
                if(!Bukkit.getOnlinePlayers().contains(player)) {
                    Bukkit.getScheduler().cancelTask(Main.data.get(player).getTaskID());
                    Main.data.get(player).getDataManager().save();
                    if(Main.data.get(player).getMiner().isSpawned()) Main.data.get(player).getMiner().despawn();
                    Main.data.remove(player);
                }
                Main.data.get(player).getDataManager().updateStat("timeplayed", Main.data.get(player).getDataManager().getStat("timeplayed") + 1);
            }
        }, 0, 20);
        if(player.getInventory().isEmpty()) {
            ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemMeta meta = pickaxe.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + "Zacatecni pickaxe [0]");
            pickaxe.setItemMeta(meta);
            pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 100);
            player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
            player.getInventory().addItem(pickaxe);
            player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        }
    }

    public void enchantments(Player player) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                if(player.getInventory().getItemInMainHand() != null)
                    if(player.getInventory().getItemInMainHand().hasItemMeta()) {
                        if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.SPEED)) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3, player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.SPEED)));
                        }
                        if(player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.JUMP)) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 3, player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.JUMP)));
                        }
                    }
            }
        }, 0, 2);
    }
}