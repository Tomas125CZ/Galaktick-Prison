package tomas.quests.entitiesList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import tomas.main.Main;
import tomas.quests.QuestEntity;
import tomas.quests.questsList.MainQuest;

import java.util.List;

public class Pilot extends QuestEntity {

    public Pilot() {
        super("Pilot");
        questsNumber = List.of(0);
        location = new Location(Bukkit.getWorld("world"), -13, 69, -15);
        activate();
    }

    @Override
    public void onClick(Player player) {
        this.player = player;
        checkForQuest();
    }

    @Override
    public void activateQuest(int quest) {
        if(quest == 0) {
            Main.data.get(player).setQuest(new MainQuest(player));
        }
    }
}
