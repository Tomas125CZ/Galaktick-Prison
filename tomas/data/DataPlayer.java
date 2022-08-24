package tomas.data;

import org.bukkit.entity.Player;
import tomas.chests.ChestCreatorPlayer;
import tomas.miner.Miner;
import tomas.quests.Quest;

public class DataPlayer {

    private ChestCreatorPlayer chestCreatorPlayer;
    private DataManager dataManager;
    private Miner miner;

    public Miner getMiner() {
        return miner;
    }

    public static int TaskID;

    private Quest quest;

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public void saveQuest() {
        if(quest != null) quest.save();
    }

    public int getTaskID() {
        return TaskID;
    }

    public DataPlayer(Player player) {
        chestCreatorPlayer = new ChestCreatorPlayer();
        dataManager = new DataManager(player);
        miner = new Miner(player);
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public ChestCreatorPlayer getChestCreatorPlayer() {
        return chestCreatorPlayer;
    }
}
