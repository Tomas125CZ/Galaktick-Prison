package tomas.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Random;

public class Voting {

    public static int votes;
    public static HashMap<String, Integer> rewards = new HashMap<>();

    public Voting() {
        votes = 0;
        if(Main.instance.getConfig().contains("VOTING." + ".VOTES"))
            votes = Main.instance.getConfig().getInt("VOTING." + ".VOTES");
        rewards.put("00", 50);
        rewards.put("01", 25);
        rewards.put("03", 15);
        rewards.put("04", 9);
        rewards.put("05", 1);
    }

    public static void vote(Player player) {
        Main.instance.broadcast(ChatColor.AQUA + "Hráč " + player.getName() + " hlasoval a získal VoteKey a +0.1 k itemům z VoteParty");
        Main.data.get(player).getDataManager().updateStat("votes", Main.data.get(player).getDataManager().getStat("votes") + 1);
        votes++;
        if(votes == 100) {
            Main.instance.broadcast(ChatColor.AQUA + "Začala voteparty!");
            votes = 0;
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                @Override
                public void run() {
                    int rewards = Main.data.get(player).getDataManager().getStat("votes") / 10;
                    if(rewards == 0 && Main.data.get(player).getDataManager().getStat("votes") != 0) rewards = 1;
                    player.sendMessage(rewards + "");
                    for(int i = 0; i < rewards; i++) {
                        String reward = "00";
                        int number = new Random().nextInt(100) + 1;
                        int n1 = 0;
                        for(String s : Voting.rewards.keySet()) {
                            if(number >= n1 && number <= n1 + Voting.rewards.get(s)) {
                                reward = s;
                            }
                            n1 += Voting.rewards.get(s);
                        }
                        addReward(player, reward);
                    }
                }
            }, 40);
        }
    }

    public static void addReward(Player player, String reward) {
        String prefix = ChatColor.GRAY + "[" + ChatColor.BLUE + ChatColor.BOLD + "VOTEPARTY" + ChatColor.GRAY + "] " + ChatColor.GREEN;
        if(reward.equals("00")) {
            player.sendMessage(prefix + "získal jsi 10 tokenů");
            Main.data.get(player).getDataManager().updateStat("tokens", Main.data.get(player).getDataManager().getStat("tokens") + 10);
        }
        else if(reward.equals("01")) {
            player.sendMessage(prefix + "získal jsi 100 tokenů");
            Main.data.get(player).getDataManager().updateStat("tokens", Main.data.get(player).getDataManager().getStat("tokens") + 100);
        }
        else if(reward.equals("02")) {
            player.sendMessage(prefix + "získal jsi 1000 tokenů");
            Main.data.get(player).getDataManager().updateStat("tokens", Main.data.get(player).getDataManager().getStat("tokens") + 1000);
        }
        else if(reward.equals("03")) {
            player.sendMessage(prefix + "získal jsi 10000 tokenů");
            Main.data.get(player).getDataManager().updateStat("tokens", Main.data.get(player).getDataManager().getStat("tokens") + 10000);
        }
        else if(reward.equals("04")) {
            player.sendMessage(prefix + "získal jsi 100000 tokenů");
            Main.data.get(player).getDataManager().updateStat("tokens", Main.data.get(player).getDataManager().getStat("tokens") + 100000);
        }
        else if(reward.equals("05")) {
            player.sendMessage(prefix + "získal jsi 1000000 tokenů");
            Main.data.get(player).getDataManager().updateStat("tokens", Main.data.get(player).getDataManager().getStat("tokens") + 1000000);
            for(Player player1 : Bukkit.getOnlinePlayers()) {
                player1.sendMessage(prefix + " hráč " + player.getName() + " ve VoteParty vyhrál legendární odměnu!");
            }
        }
        else {
            player.sendMessage(prefix + "Stala se chyba! " + " Kód: " + reward);
        }
    }
}
