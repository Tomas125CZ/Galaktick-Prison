package tomas.main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomas.planets.Planet;

import java.util.HashMap;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("sell")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(!Main.data.containsKey(player)) {
                    player.sendMessage(ChatColor.RED + "Stala se chyba! Zopakuj tuto akci za chvíli");
                    return true;
                }
                Planet planet = Planet.getPlanetByName(player.getWorld().getName());
                if(planet != null) {
                    HashMap<Material, Integer> blocks = new HashMap<>();
                    for(Material material : planet.getPrizes().keySet()) {
                        if(player.getInventory().contains(material)) {
                            while (player.getInventory().contains(material)) {
                                player.getInventory().removeItem(new ItemStack(material));
                                if (blocks.containsKey(material)) blocks.replace(material, blocks.get(material) + 1);
                                else blocks.put(material, 1);
                                Main.data.get(player).getDataManager().updateStat("money", Main.data.get(player).getDataManager().getStat("money") + planet.getPrizes().get(material));
                            }
                        }
                    }
                    if(!blocks.isEmpty()) {
                        if(Main.data.get(player).getQuest() != null) Main.data.get(player).getQuest().onSell();
                        player.sendMessage(ChatColor.GREEN + "Úspěšně jsi prodal věci v obchodě " + player.getWorld().getName());
                        player.sendMessage(ChatColor.GREEN + "Prodané itemy:");
                        int money = 0;
                        for(Material material : blocks.keySet()) {
                            player.sendMessage(ChatColor.GREEN + material.name() + " - " + blocks.get(material) + " - " + (blocks.get(material) * planet.getPrizes().get(material)));
                            money += blocks.get(material) * planet.getPrizes().get(material);
                        }
                        player.sendMessage(ChatColor.GREEN + "Dohromady jsi prodal itemy za " + money);
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "Nejsi na žádné planetě!");
                }
            }
        }
        return false;
    }
}
