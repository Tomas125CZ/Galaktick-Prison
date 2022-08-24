package tomas.quests;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import tomas.main.Main;

import java.util.ArrayList;
import java.util.List;

public abstract class QuestEntity {

    public Location location;
    public ArmorStand stand;

    public List<Integer> questsNumber = new ArrayList<>();
    public String name;
    public Player player;

    public QuestEntity(String name) {
        this.name = name;
    }

    public void activate() {
        stand = location.getWorld().spawn(location, ArmorStand.class);
        stand.setVisible(true);
        stand.setCustomName(name);
        stand.setCustomNameVisible(true);
        stand.setGravity(false);
    }

    public abstract void onClick(Player player);
    public abstract void activateQuest(int quest);

    public void checkForQuest() {
        if(Main.data.get(player).getQuest() == null) {
            if(questsNumber.contains(Main.data.get(player).getDataManager().getStat("quest"))) {
                activateQuest(Main.data.get(player).getDataManager().getStat("quest"));
            }
            else {
                player.sendMessage(ChatColor.RED + "Tato postava pro tebe momentálně nemá žádný quest!");
            }
        }
        else {
            player.sendMessage(ChatColor.RED + "Již máš aktivovaný quest!");
        }
    }
}
