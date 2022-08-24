package tomas.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomas.enchantments.CustomEnchants;
import tomas.main.Main;
import tomas.planets.Planet;

import java.util.Random;

public class BlockBreak implements Listener {

    private boolean checkInventoryFull(Player player, Material material) {
        for(int i = 0; i < 36; i++) {
            if(player.getInventory().getItem(i) != null) {
                if(player.getInventory().getItem(i).getType().equals(material)) {
                    if(player.getInventory().getItem(i).getAmount() < 64) {
                        return false;
                    }
                }
            }
            else return false;
        }
        return true;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            e.setCancelled(true);
            Planet planet = Planet.getPlanetByName(e.getPlayer().getWorld().getName());
            if (planet != null) {
                if (e.getPlayer().getInventory().getItemInMainHand() != null) {
                    if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)) {
                        if (e.getBlock().getX() >= planet.mine().get(0) && e.getBlock().getX() <= planet.mine().get(1)) {
                            if (e.getBlock().getY() >= planet.mine().get(2) && e.getBlock().getY() <= planet.mine().get(3)) {
                                if (e.getBlock().getZ() >= planet.mine().get(4) && e.getBlock().getZ() <= planet.mine().get(5)) {
                                    if (!checkInventoryFull(e.getPlayer(), e.getBlock().getType())) {
                                        e.getPlayer().getInventory().addItem(new ItemStack(e.getBlock().getType()));
                                        planet.checkForReset();
                                        if (Main.data.containsKey(e.getPlayer())) {
                                            Main.data.get(e.getPlayer()).getDataManager().updateStat("blocksmined", Main.data.get(e.getPlayer()).getDataManager().getStat("blocksmined") + 1);
                                            if(Main.data.get(e.getPlayer()).getQuest() != null) Main.data.get(e.getPlayer()).getQuest().onBlockBreak();
                                            if (Main.data.get(e.getPlayer()).getDataManager().getStat("blocksmined") % 100 == 0) {
                                                e.getPlayer().sendMessage(ChatColor.GREEN + "Získal jsi 10 tokenů za vytěžení 100 blocků");
                                                Main.data.get(e.getPlayer()).getDataManager().updateStat("tokens", Main.data.get(e.getPlayer()).getDataManager().getStat("tokens") + 10);
                                            }
                                            if (Main.data.get(e.getPlayer()).getDataManager().getStat("blocksmined") % 10000 == 0) {
                                                e.getPlayer().sendMessage(ChatColor.GREEN + "Získal jsi 100 tokenů za vytěžneí 10000 blocků");
                                                Main.data.get(e.getPlayer()).getDataManager().updateStat("tokens", Main.data.get(e.getPlayer()).getDataManager().getStat("tokens") + 100);
                                            }
                                            if (Main.data.get(e.getPlayer()).getDataManager().getStat("blocksmined") % 1000000 == 0) {
                                                e.getPlayer().sendMessage(ChatColor.GREEN + "Získal jsi 1000 tokenů za vytěžneí 1000000 blocků");
                                                Main.data.get(e.getPlayer()).getDataManager().updateStat("tokens", Main.data.get(e.getPlayer()).getDataManager().getStat("tokens") + 1000);
                                            }
                                            int blocks = Integer.parseInt(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().replace(ChatColor.AQUA + "Zacatecni pickaxe", "").replaceAll(" ", "").replace("[", "").replace("]", ""));
                                            blocks++;
                                            ItemMeta meta = e.getPlayer().getInventory().getItemInMainHand().getItemMeta();
                                            meta.setDisplayName(ChatColor.AQUA + "Zacatecni pickaxe [" + blocks + "]");
                                            e.getPlayer().getInventory().getItemInMainHand().setItemMeta(meta);
                                        }
                                        Bukkit.getWorld(e.getPlayer().getWorld().getName()).getBlockAt(e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ()).setType(Material.AIR);
                                    } else {
                                        e.getPlayer().sendTitle("" + ChatColor.RED + ChatColor.BOLD + "Máš plný inventář", ChatColor.GREEN + "Prodej ho pomocí příkazu /sell");
                                    }
                                }
                            }
                        }
                        if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.JACKHAMMER)) {
                            int level = e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.JACKHAMMER);
                            int random = new Random().nextInt(100) + 1;
                            if(level * 4 >= random) {
                                int y = e.getBlock().getY();
                                if(y >= planet.mine().get(2) && y <= planet.mine().get(3)) {
                                    for(int x = planet.mine().get(0); x <= planet.mine().get(1); x++) {
                                        for(int z = planet.mine().get(4); z <= planet.mine().get(5); z++) {
                                            if(!(e.getPlayer().getWorld().getBlockAt(x, y, z).getType().equals(Material.AIR))) {
                                                if(!checkInventoryFull(e.getPlayer(), e.getPlayer().getWorld().getBlockAt(x, y, z).getType())) {
                                                    e.getPlayer().getInventory().addItem(new ItemStack(e.getPlayer().getWorld().getBlockAt(x, y, z).getType()));
                                                    if (Main.data.containsKey(e.getPlayer())) {
                                                        Main.data.get(e.getPlayer()).getDataManager().updateStat("blocksmined", Main.data.get(e.getPlayer()).getDataManager().getStat("blocksmined") + 1);
                                                        if(Main.data.get(e.getPlayer()).getQuest() != null) Main.data.get(e.getPlayer()).getQuest().onBlockBreak();
                                                        if (Main.data.get(e.getPlayer()).getDataManager().getStat("blocksmined") % 100 == 0) {
                                                            e.getPlayer().sendMessage(ChatColor.GREEN + "Získal jsi 10 tokenů za vytěžení 100 blocků");
                                                            Main.data.get(e.getPlayer()).getDataManager().updateStat("tokens", Main.data.get(e.getPlayer()).getDataManager().getStat("tokens") + 10);
                                                        }
                                                        if (Main.data.get(e.getPlayer()).getDataManager().getStat("blocksmined") % 10000 == 0) {
                                                            e.getPlayer().sendMessage(ChatColor.GREEN + "Získal jsi 100 tokenů za vytěžneí 10000 blocků");
                                                            Main.data.get(e.getPlayer()).getDataManager().updateStat("tokens", Main.data.get(e.getPlayer()).getDataManager().getStat("tokens") + 100);
                                                        }
                                                        if (Main.data.get(e.getPlayer()).getDataManager().getStat("blocksmined") % 1000000 == 0) {
                                                            e.getPlayer().sendMessage(ChatColor.GREEN + "Získal jsi 1000 tokenů za vytěžneí 1000000 blocků");
                                                            Main.data.get(e.getPlayer()).getDataManager().updateStat("tokens", Main.data.get(e.getPlayer()).getDataManager().getStat("tokens") + 1000);
                                                        }
                                                        int blocks = Integer.parseInt(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().replace(ChatColor.AQUA + "Zacatecni pickaxe", "").replaceAll(" ", "").replace("[", "").replace("]", ""));
                                                        blocks++;
                                                        ItemMeta meta = e.getPlayer().getInventory().getItemInMainHand().getItemMeta();
                                                        meta.setDisplayName(ChatColor.AQUA + "Zacatecni pickaxe [" + blocks + "]");
                                                        e.getPlayer().getInventory().getItemInMainHand().setItemMeta(meta);
                                                    }
                                                    Bukkit.getWorld(e.getPlayer().getWorld().getName()).getBlockAt(x, y, z).setType(Material.AIR);
                                                } else {
                                                    e.getPlayer().sendTitle("" + ChatColor.RED + ChatColor.BOLD + "Máš plný inventář", ChatColor.GREEN + "Prodej ho pomocí příkazu /sell");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.EXPLOSIVE)) {
                            int level = e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.EXPLOSIVE);
                            int random = new Random().nextInt(100) + 1;
                            if(level >= random)  {
                                int range = level / 33;
                                if(range == 0) range = 1;
                                for(int x = e.getBlock().getX() - range; x < e.getBlock().getX() + range; x++) {
                                    for(int y = e.getBlock().getY() - range; y < e.getBlock().getY() + range; y++) {
                                        for(int z = e.getBlock().getZ() - range; z < e.getBlock().getZ() + range; z++) {
                                            if (x >= planet.mine().get(0) && x <= planet.mine().get(1)) {
                                                if (y >= planet.mine().get(2) && y <= planet.mine().get(3)) {
                                                    if (z >= planet.mine().get(4) && z <= planet.mine().get(5)) {
                                                        if (!e.getPlayer().getWorld().getBlockAt(x, y, z).getType().equals(Material.AIR)) {
                                                            if (!checkInventoryFull(e.getPlayer(), e.getPlayer().getWorld().getBlockAt(x, y, z).getType())) {
                                                                e.getPlayer().getInventory().addItem(new ItemStack(e.getPlayer().getWorld().getBlockAt(x, y, z).getType()));
                                                                if (Main.data.containsKey(e.getPlayer())) {
                                                                    Main.data.get(e.getPlayer()).getDataManager().updateStat("blocksmined", Main.data.get(e.getPlayer()).getDataManager().getStat("blocksmined") + 1);
                                                                    if(Main.data.get(e.getPlayer()).getQuest() != null) Main.data.get(e.getPlayer()).getQuest().onBlockBreak();
                                                                    if (Main.data.get(e.getPlayer()).getDataManager().getStat("blocksmined") % 100 == 0) {
                                                                        e.getPlayer().sendMessage(ChatColor.GREEN + "Získal jsi 10 tokenů za vytěžení 100 blocků");
                                                                        Main.data.get(e.getPlayer()).getDataManager().updateStat("tokens", Main.data.get(e.getPlayer()).getDataManager().getStat("tokens") + 10);
                                                                    }
                                                                    if (Main.data.get(e.getPlayer()).getDataManager().getStat("blocksmined") % 10000 == 0) {
                                                                        e.getPlayer().sendMessage(ChatColor.GREEN + "Získal jsi 100 tokenů za vytěžneí 10000 blocků");
                                                                        Main.data.get(e.getPlayer()).getDataManager().updateStat("tokens", Main.data.get(e.getPlayer()).getDataManager().getStat("tokens") + 100);
                                                                    }
                                                                    if (Main.data.get(e.getPlayer()).getDataManager().getStat("blocksmined") % 1000000 == 0) {
                                                                        e.getPlayer().sendMessage(ChatColor.GREEN + "Získal jsi 1000 tokenů za vytěžneí 1000000 blocků");
                                                                        Main.data.get(e.getPlayer()).getDataManager().updateStat("tokens", Main.data.get(e.getPlayer()).getDataManager().getStat("tokens") + 1000);
                                                                    }
                                                                    int blocks = Integer.parseInt(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().replace(ChatColor.AQUA + "Zacatecni pickaxe", "").replaceAll(" ", "").replace("[", "").replace("]", ""));
                                                                    blocks++;
                                                                    ItemMeta meta = e.getPlayer().getInventory().getItemInMainHand().getItemMeta();
                                                                    meta.setDisplayName(ChatColor.AQUA + "Zacatecni pickaxe [" + blocks + "]");
                                                                    e.getPlayer().getInventory().getItemInMainHand().setItemMeta(meta);
                                                                }
                                                                Bukkit.getWorld(e.getPlayer().getWorld().getName()).getBlockAt(x, y, z).setType(Material.AIR);
                                                            } else {
                                                                e.getPlayer().sendTitle("" + ChatColor.RED + ChatColor.BOLD + "Máš plný inventář", ChatColor.GREEN + "Prodej ho pomocí příkazu /sell");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                planet.checkForReset();
                                e.getPlayer().getWorld().createExplosion(e.getBlock().getLocation(), 0);
                            }
                        }
                    }
                }
            }
        }
    }
}
