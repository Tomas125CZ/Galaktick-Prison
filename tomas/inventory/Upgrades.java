package tomas.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomas.enchantments.CustomEnchants;
import tomas.main.Main;

import java.util.*;

public class Upgrades implements Listener {

    private String title = "" + ChatColor.AQUA + ChatColor.BOLD + "Pickaxe Upgrades";

    private List<Map.Entry<Enchantment, Integer>> enchantments;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getView().getTitle().equals(title)) {
            e.setCancelled(true);
            if(e.getCurrentItem() != null) {
                if(e.getCurrentItem().getType().equals(Material.GREEN_STAINED_GLASS)) {
                    for(Map.Entry<Enchantment, Integer> enchantment : enchantments) {
                        String name = "" + ChatColor.AQUA + ChatColor.BOLD + enchantment.getKey().getName();
                        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(name)) {
                            if (e.getCurrentItem().getItemMeta().getEnchantLevel(enchantment.getKey()) < enchantment.getKey().getMaxLevel()) {
                                if (Main.data.get(e.getWhoClicked()).getDataManager().getStat("tokens") >= (int) (enchantment.getValue() * (e.getWhoClicked().getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(enchantment.getKey()) + 1) * 1.7)) {
                                    if(e.getWhoClicked().getInventory().getItemInMainHand().getEnchantmentLevel(enchantment.getKey()) + 1 > enchantment.getKey().getMaxLevel())  return;
                                    ItemStack item = e.getWhoClicked().getInventory().getItemInMainHand();
                                    e.getWhoClicked().sendMessage(enchantment.getKey().getMaxLevel() + "");
                                    Main.data.get(e.getWhoClicked()).getDataManager().updateStat("tokens", Main.data.get(e.getWhoClicked()).getDataManager().getStat("tokens") - (int) (enchantment.getValue() * (e.getWhoClicked().getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(enchantment.getKey()) + 1) * 1.7));
                                    e.getWhoClicked().sendMessage(ChatColor.GREEN + "Koupil sis enchantment " + enchantment.getKey().getName() + " " + (e.getWhoClicked().getInventory().getItemInMainHand().getEnchantmentLevel(enchantment.getKey()) + 1) + " za " + (int) (enchantment.getValue() * (e.getWhoClicked().getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(enchantment.getKey()) + 1) * 1.7) + " tokenů");
                                    int nextLvl = e.getWhoClicked().getInventory().getItemInMainHand().getEnchantmentLevel(enchantment.getKey()) + 1;
                                    List<String> lore;
                                    HashMap<Enchantment, Integer> enchantments = new HashMap<>();
                                    for(Map.Entry<Enchantment, Integer>  enchantment1 : e.getWhoClicked().getInventory().getItemInMainHand().getEnchantments().entrySet()) {
                                        enchantments.put(enchantment1.getKey(), enchantment1.getValue());
                                    }
                                    if(enchantments.containsKey(enchantment.getKey())) {
                                        enchantments.remove(enchantment.getKey());
                                        enchantments.put(enchantment.getKey(), nextLvl);
                                    }
                                    else enchantments.put(enchantment.getKey(), nextLvl);
                                    lore = new ArrayList<>();
                                    for(Map.Entry<Enchantment, Integer> enchantment1: enchantments.entrySet()) {
                                        lore.add("" + ChatColor.GRAY + enchantment1.getKey().getName() + " " + enchantment1.getValue());
                                    }
                                    ItemMeta meta = item.getItemMeta();
                                    meta.setLore(lore);
                                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                    item.setItemMeta(meta);
                                    item.addUnsafeEnchantments(enchantments);
                                    e.getWhoClicked().getInventory().setItemInMainHand(item);
                                    System.out.println(e.getWhoClicked().getInventory().getItemInMainHand().getEnchantments());
                                    openInventory((Player) e.getWhoClicked());
                                }
                                else {
                                    e.getWhoClicked().sendMessage(ChatColor.RED + "Nemáš dostatek tokenů!");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void openInventory(Player player) {
        Inventory inv = Bukkit.createInventory(player, 54, title);
        int i = 0;
        for(Map.Entry<Enchantment, Integer> enchantment : enchantments) {
            ItemStack item = new ItemStack(Material.GREEN_STAINED_GLASS);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("" + ChatColor.AQUA + ChatColor.BOLD + enchantment.getKey().getName());
            List<String> lore = new ArrayList<>();
            if(player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(enchantment.getKey())) {
                if(player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(enchantment.getKey()) < enchantment.getKey().getMaxLevel()) {
                    lore.add("" + ChatColor.GREEN + player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(enchantment.getKey()) + "/" + enchantment.getKey().getMaxLevel());
                }
                else {
                    lore.add("" + ChatColor.RED + ChatColor.BOLD + "MAX LVL");
                }
            }
            else lore.add(ChatColor.GREEN + "0/" + enchantment.getKey().getMaxLevel());
            if(!lore.contains("" + ChatColor.RED + ChatColor.BOLD + "MAX LVL")) {
                if(player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(enchantment.getKey())) {
                    lore.add(ChatColor.GREEN + "Cena: " + (int) (enchantment.getValue() * (player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(enchantment.getKey()) + 1) * 1.7) + " tokenů");
                }
                else {
                    lore.add(ChatColor.GREEN + "Cena: " + enchantment.getValue() + " tokenů");
                }
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(i, item);
            i++;
        }
        player.openInventory(inv);
    }

    public Upgrades() {
        HashMap<Enchantment, Integer> preEnchantments = new HashMap<>();
        preEnchantments.put(CustomEnchants.EXPLOSIVE, 250);
        preEnchantments.put(CustomEnchants.SPEED, 100);
        preEnchantments.put(CustomEnchants.JUMP, 100);
        preEnchantments.put(CustomEnchants.JACKHAMMER, 150);

        enchantments = new ArrayList<>(preEnchantments.entrySet());

        Collections.sort(enchantments, new Comparator<Map.Entry<Enchantment, Integer>>() {
            @Override
            public int compare(Map.Entry<Enchantment, Integer> o1, Map.Entry<Enchantment, Integer> o2) {
                return o1.getValue()-o2.getValue();
            }
        });
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)) {
                openInventory(e.getPlayer());
            }
        }
    }
}
