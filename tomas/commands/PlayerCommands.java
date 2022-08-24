package tomas.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tomas.data.DataPlayer;
import tomas.main.Main;

public class PlayerCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(label.equalsIgnoreCase("miner")) {
            DataPlayer player = Main.data.get(sender);
            if(player.getMiner().isSpawned()) {
                player.getMiner().despawn();
            }
            else {
                player.getMiner().spawn();
            }
        }
        return false;
    }
}
