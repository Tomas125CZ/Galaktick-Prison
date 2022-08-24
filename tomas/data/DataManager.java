package tomas.data;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import tomas.main.Main;

import java.util.HashMap;

public class DataManager {
    private HashMap<String, Integer> stats = new HashMap<>();
    private Player player;

    public DataManager(Player player) {
        this.player = player;
        if(Main.instance.getConfig().contains("PLAYERDATA." + player.getUniqueId().toString() + ".BLOCKSMINED")) {
            stats.put("blocksmined", Main.instance.getConfig().getInt("PLAYERDATA." + player.getUniqueId().toString() + ".BLOCKSMINED"));
        }
        else {
            stats.put("blocksmined", 0);
        }
        if(Main.instance.getConfig().contains("PLAYERDATA." + player.getUniqueId().toString() + ".TIMEPLAYED")) {
            stats.put("timeplayed", Main.instance.getConfig().getInt("PLAYERDATA." + player.getUniqueId().toString() + ".TIMEPLAYED"));
        }
        else {
            stats.put("timeplayed", 0);
        }
        if(Main.instance.getConfig().contains("PLAYERDATA." + player.getUniqueId().toString() + ".MONEY")) {
            stats.put("money", Main.instance.getConfig().getInt("PLAYERDATA." + player.getUniqueId().toString() + ".MONEY"));
        }
        else {
            stats.put("money", 0);
        }
        if(Main.instance.getConfig().contains("PLAYERDATA." + player.getUniqueId().toString() + ".TOKENS")) {
            stats.put("tokens", Main.instance.getConfig().getInt("PLAYERDATA." + player.getUniqueId().toString() + ".TOKENS"));
        }
        else {
            stats.put("tokens", 0);
        }
        if(Main.instance.getConfig().contains("PLAYERDATA." + player.getUniqueId().toString() + ".STANICE")) {
            stats.put("stanice", Main.instance.getConfig().getInt("PLAYERDATA." + player.getUniqueId().toString() + ".STANICE"));
        }
        else {
            stats.put("stanice", 1);
        }
        if(Main.instance.getConfig().contains("PLAYERDATA." + player.getUniqueId().toString() + ".GALAXIE")) {
            stats.put("galaxie", Main.instance.getConfig().getInt("PLAYERDATA." + player.getUniqueId().toString() + ".GALAXIE"));
        }
        else {
            stats.put("galaxie", 1);
        }
        if(Main.instance.getConfig().contains("PLAYERDATA." + player.getUniqueId().toString() + ".VOTES")) {
            stats.put("votes", Main.instance.getConfig().getInt("PLAYERDATA." + player.getUniqueId().toString() + ".VOTES"));
        }
        else {
            stats.put("votes", 0);
        }
        if(Main.instance.getConfig().contains("PLAYERDATA." + player.getUniqueId().toString() + ".DAILYREWARDS")) {
            stats.put("dailyrewards", Main.instance.getConfig().getInt("PLAYERDATA." + player.getUniqueId().toString() + ".DAILYREWARDS"));
        }
        else {
            stats.put("dailyrewards", 0);
        }
        if(Main.instance.getConfig().contains("PLAYERDATA." + player.getUniqueId().toString() + ".QUEST")) {
            stats.put("quest", Main.instance.getConfig().getInt("PLAYERDATA." + player.getUniqueId().toString() + ".QUEST"));
        }
        else {
            stats.put("quest", 0);
        }
    }
    public void updateStat(String stat, int value) {
        if(value >= Integer.MAX_VALUE || value <= Integer.MIN_VALUE) {
            player.sendMessage(ChatColor.RED + "Stala se chyba při ukládání do databáze!");
            return;
        }
        stats.replace(stat, value);
    }

    public int getStat(String stat) {
        return stats.get(stat);
    }

    public void save() {
        Main.instance.getConfig().set("PLAYERDATA." + player.getUniqueId().toString() + ".BLOCKSMINED", stats.get("blocksmined"));
        Main.instance.getConfig().set("PLAYERDATA." + player.getUniqueId().toString() + ".TIMEPLAYED", stats.get("timeplayed"));
        Main.instance.getConfig().set("PLAYERDATA." + player.getUniqueId().toString() + ".MONEY", stats.get("money"));
        Main.instance.getConfig().set("PLAYERDATA." + player.getUniqueId().toString() + ".TOKENS", stats.get("tokens"));
        Main.instance.getConfig().set("PLAYERDATA." + player.getUniqueId().toString() + ".STANICE", stats.get("stanice"));
        Main.instance.getConfig().set("PLAYERDATA." + player.getUniqueId().toString() + ".GALAXIE", stats.get("galaxie"));
        Main.instance.getConfig().set("PLAYERDATA." + player.getUniqueId().toString() + ".VOTES", stats.get("votes"));
        Main.instance.getConfig().set("PLAYERDATA." + player.getUniqueId().toString() + ".DAILYREWARDS", stats.get("dailyrewards"));
        Main.instance.getConfig().set("PLAYERDATA." + player.getUniqueId().toString() + ".QUEST", stats.get("quest"));
        Main.instance.saveConfig();
    }
}
