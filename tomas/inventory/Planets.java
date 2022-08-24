package tomas.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomas.main.Main;
import tomas.planets.Galaxie;
import tomas.planets.Planet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Planets implements CommandExecutor, Listener {

    private String title = "" + ChatColor.AQUA + ChatColor.BOLD + "PLANETS";

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getView().getTitle().startsWith(title)) {
            e.setCancelled(true);
            if(e.getCurrentItem() != null) {
                if(e.getCurrentItem().getType().equals(Material.GREEN_CONCRETE)) {
                    if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasLore()) {
                        Planet planet = null;
                        for(Map.Entry<Planet, Integer> planet2 : Main.planets) {
                            String name = "" + ChatColor.AQUA + ChatColor.BOLD + "" + planet2.getKey().name();
                            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(name)) planet = planet2.getKey();
                        }
                        if(planet != null) {
                            e.getWhoClicked().teleport(planet.spawnLocation());
                        }
                    }
                }
                else if(e.getCurrentItem().getType().equals(Material.RED_CONCRETE)) {
                    if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasLore()) {
                        for(int i = 0; i < 54; i++) {
                            if(e.getCurrentItem().equals(e.getInventory().getItem(i))) {
                                if(Main.data.get(e.getWhoClicked()).getDataManager().getStat("stanice") == i) {
                                    for(Map.Entry<Planet, Integer> planet : Main.planets) {
                                        String name = "" + ChatColor.AQUA + ChatColor.BOLD + "" + planet.getKey().name();
                                        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(name)) {
                                            if(planet.getValue() <= Main.data.get(e.getWhoClicked()).getDataManager().getStat("money")) {
                                                Main.data.get(e.getWhoClicked()).getDataManager().updateStat("money", Main.data.get(e.getWhoClicked()).getDataManager().getStat("money") - planet.getValue());
                                                Main.data.get(e.getWhoClicked()).getDataManager().updateStat("stanice", Main.data.get(e.getWhoClicked()).getDataManager().getStat("stanice") + 1);
                                                Main.instance.broadcast(ChatColor.AQUA + "Hráč " + e.getWhoClicked().getName() + " se dostal na planetu " + Planet.getNameByNumber(Main.data.get(e.getWhoClicked()).getDataManager().getStat("stanice")));
                                                openInventory((Player) e.getWhoClicked(), Galaxie.getGalaxieByName(e.getView().getTitle().replace(title, "").replace(" - ", "").replaceAll(" ", "")));
                                                return;
                                            }
                                            e.getWhoClicked().sendMessage(ChatColor.RED + "Nemáš dostatek peněz!");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else if (e.getCurrentItem().getType().equals(Material.YELLOW_CONCRETE)) {
                    for(int i = 0; i < 54; i++) {
                        if(e.getCurrentItem().equals(e.getInventory().getItem(i))) {
                            if(i == 48) {
                                openInventory((Player) e.getWhoClicked(), Galaxie.getGalaxieByNumber(Galaxie.getNumberByName(e.getView().getTitle().replace(title, "").replace(" - ", "").replaceAll(" ", "")) - 1));
                            }
                            else if (i == 50) {
                                if(Main.data.get(e.getWhoClicked()).getDataManager().getStat("galaxie") >= Galaxie.getNumberByName(e.getView().getTitle().replace(title, "").replace(" - ", "").replaceAll(" ", "")) + 1) {
                                    openInventory((Player) e.getWhoClicked(), Galaxie.getGalaxieByNumber(Galaxie.getNumberByName(e.getView().getTitle().replace(title, "").replace(" - ", "").replaceAll(" ", "")) + 1));
                                }
                                else {
                                    for(Map.Entry<Galaxie, Integer>galaxie : Main.galaxie) {
                                        if(galaxie.getKey().equals(Galaxie.getGalaxieByName(e.getView().getTitle().replace(title, "").replace(" - ", "").replaceAll(" ", "")))) {
                                            if(Main.data.get(e.getWhoClicked()).getDataManager().getStat("money") >= galaxie.getValue()) {
                                                Main.data.get(e.getWhoClicked()).getDataManager().updateStat("money", Main.data.get(e.getWhoClicked()).getDataManager().getStat("money") - galaxie.getValue());
                                                Main.data.get(e.getWhoClicked()).getDataManager().updateStat("galaxie", Main.data.get(e.getWhoClicked()).getDataManager().getStat("galaxie") + 1);
                                                openInventory((Player) e.getWhoClicked(), Galaxie.getGalaxieByNumber(Galaxie.getNumberByName(e.getView().getTitle().replace(title, "").replace(" - ", "").replaceAll(" ", "")) + 1));
                                                Main.instance.broadcast(ChatColor.AQUA + "Hráč " + e.getWhoClicked().getName() + " se dostal na galaxii " + Galaxie.getGalaxieByNumber(Main.data.get(e.getWhoClicked()).getDataManager().getStat("galaxie")).getName());
                                            }
                                            else {
                                                e.getWhoClicked().sendMessage(ChatColor.RED + "Nemáš dostatek peněz!");
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                e.getWhoClicked().sendMessage(ChatColor.RED + "Stala se chyba! Zopakuj tuto akci za chvíli");
                            }
                        }
                    }
                }
            }
        }
    }

    public void openInventory(Player player, Galaxie galaxie) {
        if(!Main.data.containsKey(player)) {
            player.sendMessage(ChatColor.RED + "Stala se chyba! Zopakuj tuto akci za chvíli");
            return;
        }
        Inventory inv = Bukkit.createInventory(player, 54, title + " - " + galaxie.getName());
        int i = 0;
        for(Map.Entry<Planet, Integer> planet : Main.planets) {
            for(Planet planet1 : galaxie.planets()) {
                if(planet1.name().equals(planet.getKey().name())) {
                    ItemStack itemStack = new ItemStack(Material.RED_CONCRETE);
                    if (Main.data.get(player).getDataManager().getStat("stanice") >= Planet.getNumberByName(planet.getKey().name()))
                        itemStack = new ItemStack(Material.GREEN_CONCRETE);
                    ItemMeta meta = itemStack.getItemMeta();
                    meta.setDisplayName("" + ChatColor.AQUA + ChatColor.BOLD + "" + planet.getKey().name());
                    List<String> lore = new ArrayList<>();
                    if (itemStack.getType().equals(Material.RED_CONCRETE)) {
                        if (Main.data.get(player).getDataManager().getStat("stanice") == i) {
                            lore.add(ChatColor.GREEN + "Tuto planetu si můžeš zakoupit za " + planet.getValue());
                        } else {
                            lore.add(ChatColor.RED + "Tuto planetu si ještě zakoupit nemůžeš!");
                        }
                    } else lore.add(ChatColor.GREEN + "Tuto planetu již máš zakoupenou");
                    meta.setLore(lore);
                    itemStack.setItemMeta(meta);
                    inv.setItem(i, itemStack);
                    i++;
                }
            }
        }
        ItemStack zpet = new ItemStack(Material.YELLOW_CONCRETE);
        ItemStack vpred = new ItemStack(Material.YELLOW_CONCRETE);
        ItemMeta zpetmeta = zpet.getItemMeta();
        ItemMeta vpredmeta = vpred.getItemMeta();
        zpetmeta.setDisplayName("" + ChatColor.AQUA + ChatColor.BOLD + "MINULÁ STRÁNKA");
        if(Main.data.get(player).getDataManager().getStat("galaxie") <= Galaxie.getNumberByName(galaxie.getName())) {
            int prize = 0;
            for(Map.Entry<Galaxie, Integer> galaxie1 : Main.galaxie) {
                if(galaxie1.getKey().equals(Galaxie.getGalaxieByNumber(Galaxie.getNumberByName(galaxie.getName()) + 1))) prize = galaxie1.getValue();
            }
            if (Galaxie.getNumberByName(galaxie.getName()) != Galaxie.getMaxGalaxies()) vpredmeta.setDisplayName("" + ChatColor.AQUA + ChatColor.BOLD + "ZAKUP SI TUTO GALAXII " + Galaxie.getGalaxieByNumber(Galaxie.getNumberByName(galaxie.getName()) + 1).getName());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Cena: " + prize);
            vpredmeta.setLore(lore);
        }
        else vpredmeta.setDisplayName("" + ChatColor.AQUA + ChatColor.BOLD + "DALŠÍ STRÁNKA");
        zpet.setItemMeta(zpetmeta);
        vpred.setItemMeta(vpredmeta);
        if (Galaxie.getNumberByName(galaxie.getName()) != 1) inv.setItem(48, zpet);
        if (Galaxie.getNumberByName(galaxie.getName()) != Galaxie.getMaxGalaxies()) inv.setItem(50, vpred);
        player.openInventory(inv);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("planets")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                openInventory(player, Galaxie.getGalaxieByNumber(1));
             }
        }
        return false;
    }
}
