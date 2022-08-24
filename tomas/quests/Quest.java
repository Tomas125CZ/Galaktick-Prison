package tomas.quests;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import tomas.main.Main;

import java.util.List;

public abstract class Quest {

    public Player player;
    public String questName;
    public int neededBlocks;
    public int neededFlies;
    public int neededSells;
    public int stage;
    public World worldToGo;
    public List<String> dialogs;
    public List<String> questsStrings;

    public Quest(Player player) {
        this.player = player;
    }

    public abstract void save();
    public abstract void load();
    public abstract void onBlockBreak();
    public abstract void onEntityClick(QuestEntity entity);
    public abstract void onPlayerFly();
    public abstract void onWorldChange(World world);
    public abstract void onSell();
    public abstract void delete();
    public void sendDialog() {
        if(stage == 0) player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "QUESTS", ChatColor.WHITE + "Zahájil jsi quest " + questName);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(questsStrings.get(stage)));
        player.sendMessage(dialogs.get(stage));
    }

    public void finishQuest() {
        player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "QUESTS", ChatColor.WHITE + "Dokončil jsi quest " + questName);
        Main.data.get(player).setQuest(null);
        Main.data.get(player).getDataManager().updateStat("quest", Main.data.get(player).getDataManager().getStat("quest") + 1);
        delete();
    }
}
