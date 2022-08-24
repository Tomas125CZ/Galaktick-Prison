package tomas.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tomas.main.Voting;

public class ConsoleCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("vote")) {
            if(!(sender instanceof Player)) {
                if(args.length >= 1) {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        if(player.getName().equals(args[0])) {
                            Voting.vote(player);
                            return true;
                        }
                    }
                    System.out.println(ChatColor.RED + "Zadaný uživatel nebyl nalezen!");
                }
                else {
                    System.out.println(ChatColor.RED + "Nebyl zadán žádný uživatel!");
                }
            }
        }
        return false;
    }
}
