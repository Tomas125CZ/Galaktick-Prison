package tomas.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import tomas.planets.Galaxie;
import tomas.planets.Planet;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    public static void createBoard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("GalacticPrison", "dummy", ChatColor.AQUA + "Galactic Prison");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score players = obj.getScore("" + ChatColor.DARK_BLUE + ChatColor.BOLD + "Hráči" + ChatColor.GRAY + " - " + ChatColor.WHITE + Bukkit.getOnlinePlayers().size());
        players.setScore(16);
        Score name = obj.getScore(ChatColor.AQUA + "  Jméno" + ChatColor.GRAY + " - " + ChatColor.WHITE + player.getName());
        name.setScore(15);
        Score money = obj.getScore(ChatColor.AQUA + "  Peníze" + ChatColor.GRAY + " - " + ChatColor.WHITE + Main.data.get(player).getDataManager().getStat("money") + "$");
        money.setScore(14);
        Score tokens = obj.getScore(ChatColor.AQUA + "  Tokeny" + ChatColor.GRAY + " - " + ChatColor.WHITE + Main.data.get(player).getDataManager().getStat("tokens"));
        tokens.setScore(13);
        Score stats = obj.getScore("" + ChatColor.DARK_BLUE + ChatColor.BOLD + "Statistiky");
        stats.setScore(12);
        Score stanice = obj.getScore(ChatColor.AQUA + "  Stanice" + ChatColor.GRAY + " - " + ChatColor.WHITE + Planet.getNameByNumber(Main.data.get(player).getDataManager().getStat("stanice")));
        stanice.setScore(11);
        Score galaxie = obj.getScore(ChatColor.AQUA + "  Galaxie" + ChatColor.GRAY + " - " + ChatColor.WHITE + Galaxie.getGalaxieByNumber(Main.data.get(player).getDataManager().getStat("galaxie")).getName());
        galaxie.setScore(10);
        Score blocks = obj.getScore(ChatColor.AQUA + "  Blocks" + ChatColor.GRAY + " - " + ChatColor.WHITE + Main.data.get(player).getDataManager().getStat("blocksmined"));
        blocks.setScore(9);
        Score quests = obj.getScore(ChatColor.AQUA + "  Quests" + ChatColor.GRAY + " - " + ChatColor.WHITE + Main.data.get(player).getDataManager().getStat("quest"));
        quests.setScore(8);
        Score timePlayed = obj.getScore(ChatColor.AQUA + "  Odehraný čas " + ChatColor.GRAY + " - " + ChatColor.WHITE + getTimeInMessage(getTime(player)));
        timePlayed.setScore(7);
        Score voting = obj.getScore("" + ChatColor.DARK_BLUE + ChatColor.BOLD + "Hlasování");
        voting.setScore(6);
        Score party = obj.getScore(ChatColor.AQUA + "  Party" + ChatColor.GRAY + " - " + ChatColor.WHITE + Voting.votes + "/100");
        party.setScore(5);
        Score votes = obj.getScore(ChatColor.AQUA + "  Votes" + ChatColor.GRAY + " - " + ChatColor.WHITE + Main.data.get(player).getDataManager().getStat("votes"));
        votes.setScore(4);
        player.setScoreboard(board);
    }

    public static String getTimeInMessage(List<Integer> time) {
        String message = "";
        if(time.get(0) == 0) {
            if(time.get(1) == 0) {
                if(time.get(2) == 0) {
                    message = "" + time.get(3);
                }
                else {
                    message = time.get(2) + ":" + time.get(3);
                }
            }
            else {
                message = time.get(1) + ":" + time.get(2) + ":" + time.get(3);
            }
        }
        else {
            message = time.get(0) + ":" + time.get(1) + ":" + time.get(2) + ":" + time.get(3);
        }
        return message;
    }

    public static java.util.List<Integer> getTime(Player player) {
        List<Integer> time = new ArrayList<>();
        int sec = Main.data.get(player).getDataManager().getStat("timeplayed");
        int min = 0;
        int hour = 0;
        int day = 0;
        while(sec >= 60) {
            sec -= 60;
            min++;
        }
        while(min >= 60) {
            min -= 60;
            hour++;
        }
        while(hour >= 24) {
            hour -= 24;
            day++;
        }
        time = List.of(day, hour, min, sec);
        return time;
    }
}
