package tomas.quests.entitiesList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import tomas.main.Main;
import tomas.quests.QuestEntity;
import tomas.quests.questsList.MainQuest;
import tomas.quests.questsList.Merkur.MerkurQuest1;

import java.util.List;

public class MerkurMiner extends QuestEntity {

    public MerkurMiner() {
        super("Miner na planetě Merkur");
        questsNumber = List.of(1);
        location = new Location(Bukkit.getWorld("MERKUR"), -3, 65, -2);
        activate();
    }

    @Override
    public void onClick(Player player) {
        this.player = player;
        checkForQuest();
    }

    @Override
    public void activateQuest(int quest) {
        if(quest == 1) {
            Main.data.get(player).setQuest(new MerkurQuest1(player));
        }
    }
}
