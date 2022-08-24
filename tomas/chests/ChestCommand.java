package tomas.chests;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tomas.main.Main;

import java.util.ArrayList;

public class ChestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(label.equalsIgnoreCase("chests")) {
            Player player = (Player) sender;
            if(sender.hasPermission("chests.use")) {
                if(args.length > 0) {
                    if(args[0].equalsIgnoreCase("getKeys")) {
                        for(Chest chest : Main.chests) {
                            player.getInventory().addItem(chest.getKey());
                        }
                    }
                    if(args.length > 1) {
                        if(args[0].equalsIgnoreCase("zalozit")) {
                            ChestCreatorPlayer chestCreatorPlayer = Main.data.get(player).getChestCreatorPlayer();
                            if(chestCreatorPlayer.isSelected()) {
                                for(Chest chest : Main.chests) {
                                    if(chest.getLocation().equals(new Location(chestCreatorPlayer.getWorld(), chestCreatorPlayer.getX(), chestCreatorPlayer.getY(), chestCreatorPlayer.getZ()))) {
                                        player.sendMessage(ChatColor.RED + " Na tomto místě se již nachází chestka!");
                                        return true;
                                    }
                                }
                                String name = args[1];
                                for (int i = 2; i < args.length; i++) {
                                    name += " " + args[i];
                                }
                                String chests = "";
                                for(Chest chest : Main.chests) {
                                    chests += ChatColor.RESET + "" + chest.getName() + " ";
                                    if(chest.getName().equals(name)) {
                                        player.sendMessage(ChatColor.RED + " Chestka s tímto názvem již existuje!");
                                        return true;
                                    }
                                }
                                chests += name;
                                player.sendMessage(chests);
                                Chest chest = new Chest(name, name, new ArrayList<>(), new Location(chestCreatorPlayer.getWorld(), chestCreatorPlayer.getX(), chestCreatorPlayer.getY(), chestCreatorPlayer.getZ()));
                                Main.chests.add(chest);
                            }
                            else {
                                sendHelp(player);
                            }
                        }
                        if(args[0].equalsIgnoreCase("remove")) {
                            String name = args[1];
                            for (int i = 2; i < args.length; i++) {
                                name += " " + args[i];
                            }
                            for(Chest chest : Main.chests) {
                                if(chest.getName().equals(name)) {
                                    chest.getLocation().getBlock().setType(Material.AIR);
                                    chest.removeHologram();
                                    Main.chests.remove(chest);
                                    player.sendMessage(ChatColor.GREEN + "Chest " + ChatColor.translateAlternateColorCodes('&', name) + ChatColor.GREEN + " byla úspěšně odstraněna!");
                                    for(Player player1 : Bukkit.getOnlinePlayers()) {
                                        if(Main.data.get(player1).getChestCreatorPlayer().getFocusedChest() != null) {
                                            Main.data.get(player1).getChestCreatorPlayer().setFocusedChest(null);
                                            player.sendMessage(ChatColor.RED + "Chestka na které jsi měl focus byla odstraněna!");
                                        }
                                    }
                                    return true;
                                }
                            }
                            player.sendMessage(ChatColor.RED + "Chest s názvem " + ChatColor.translateAlternateColorCodes('&', name) + ChatColor.RED + " neexistuje!");
                        }
                        if(args[0].equalsIgnoreCase("focus")) {
                            String name = args[1];
                            for (int i = 2; i < args.length; i++) {
                                name += " " + args[i];
                            }
                            for(Chest chest : Main.chests) {
                                if(chest.getName().equals(name)) {
                                    player.sendMessage(ChatColor.GREEN + "Chest " + ChatColor.translateAlternateColorCodes('&', chest.getName()) + ChatColor.GREEN + " je nyní focusovaná");
                                    ChestCreatorPlayer chestCreatorPlayer = Main.data.get(player).getChestCreatorPlayer();
                                    chestCreatorPlayer.setFocusedChest(chest);
                                    return true;
                                }
                            }
                            player.sendMessage(ChatColor.RED + "Chest s názvem " + ChatColor.translateAlternateColorCodes('&', name) + ChatColor.RED + " neexistuje!");
                        }
                    }
                    if(args[0].equalsIgnoreCase("update")) {
                        ChestCreatorPlayer chestCreatorPlayer = Main.data.get(player).getChestCreatorPlayer();
                        if(chestCreatorPlayer.getFocusedChest() != null) {
                            if (args.length > 2) {
                                if (args[1].equalsIgnoreCase("nazev")) {
                                    String name = args[2];
                                    for (int i = 3; i < args.length; i++) {
                                        name += " " + args[i];
                                    }
                                    player.sendMessage(ChatColor.GREEN + "Název byl změnen z " + ChatColor.translateAlternateColorCodes('&', chestCreatorPlayer.getFocusedChest().getName()) + ChatColor.GREEN + " na " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', name));
                                    chestCreatorPlayer.getFocusedChest().setName(name);
                                }
                            }
                            if(args.length > 3) {
                                if(args[1].equalsIgnoreCase("rewards")) {
                                    if(args[2].equalsIgnoreCase("removeMoney")) {
                                        for(Reward reward : chestCreatorPlayer.getFocusedChest().getRewards()) {
                                            if(reward.getMoneyAmount() == Integer.parseInt(args[3])) {
                                                player.sendMessage(ChatColor.GREEN + args[3] + " bylo odebráno z rewards");
                                                chestCreatorPlayer.getFocusedChest().getRewards().remove(reward);
                                                return true;
                                            }
                                        }
                                        player.sendMessage(ChatColor.RED + "Chestka neobsahuje tento počet peněz!");
                                    }
                                    if(args[2].equalsIgnoreCase("removeTokens")) {
                                        for(Reward reward : chestCreatorPlayer.getFocusedChest().getRewards()) {
                                            if(reward.getTokensAmount() == Integer.parseInt(args[3])) {
                                                player.sendMessage(ChatColor.GREEN + args[3] + " tokenů bylo odebráno z rewards");
                                                chestCreatorPlayer.getFocusedChest().getRewards().remove(reward);
                                                return true;
                                            }
                                        }
                                        player.sendMessage(ChatColor.RED + "Chestka neobsahuje tento počet tokenů!");
                                    }
                                }
                            }
                            if(args.length > 4) {
                                if(args[1].equalsIgnoreCase("rewards")) {
                                    if(args[2].equalsIgnoreCase("addMoney")) {
                                        chestCreatorPlayer.getFocusedChest().getRewards().add(new Reward(Integer.parseInt(args[4]), Integer.parseInt(args[3]), 0, "", RewardType.MONEY));
                                        player.sendMessage(ChatColor.GREEN + args[3] + " bylo přidáno do rewards");
                                    }
                                }
                                if(args[1].equalsIgnoreCase("rewards")) {
                                    if(args[2].equalsIgnoreCase("addTokens")) {
                                        chestCreatorPlayer.getFocusedChest().getRewards().add(new Reward(Integer.parseInt(args[4]), 0, Integer.parseInt(args[3]), "", RewardType.TOKENS));
                                        player.sendMessage(ChatColor.GREEN + args[3] + " tokenů bylo přidáno do rewards");
                                    }
                                }
                            }
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Žádná chesta není focusovaná!");
                        }
                    }
                    if(args[0].equalsIgnoreCase("help")) {
                        sendHelp(player);
                    }
                }
                else sendHelp(player);
            }
        }
        return false;
    }

    public void sendHelp(Player player) {
        player.sendMessage(ChatColor.AQUA + "/chests zalozit <nazev> !!! před tímto příkazem musíš pomocí diamond axe označit místo");
        player.sendMessage(ChatColor.AQUA + "/chests remove <nazev>");
        player.sendMessage(ChatColor.AQUA + "/chests focus <nazev>");
        player.sendMessage(ChatColor.AQUA + "/chests update nazev <nazev>");
        player.sendMessage(ChatColor.AQUA + "/chests update rewards addMoney <pocet> <sance>");
        player.sendMessage(ChatColor.AQUA + "/chests update rewards removeMoney <pocet>");
        player.sendMessage(ChatColor.AQUA + "/chests update rewards addTokens <pocet> <sance>");
        player.sendMessage(ChatColor.AQUA + "/chests update rewards removeTokens <pocet>");
    }
}
