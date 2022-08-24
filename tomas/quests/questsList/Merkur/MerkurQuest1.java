package tomas.quests.questsList.Merkur;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import tomas.main.Main;
import tomas.quests.Quest;
import tomas.quests.QuestEntity;

import java.util.List;

public class MerkurQuest1 extends Quest {

    private int sells = 0;
    private int blocks = 0;

    public MerkurQuest1(Player player) {
        super(player);
        questName = "Pomoc minerovi na planetě Merkur";
        dialogs = List.of("Ahoj, já jsem miner na planetě merkur, nacházím se zde už pár let a stále se mi nepodařilo vytěžit dostatečný počet blocků, ale ty vypadáš na někoho kdo by mohl vytěžit tolik blocků!", "Gratuluji ti! Je vidět, že máš talent v tom co děláš, ještě bych ale potřeboval aby jsi ty věci prodal", "Děkuji za pomoc! Pokud by jsi chtěl ještě nějaké questy určitě příjdi!");
        questsStrings = List.of("Vytěž 1000 blocků", "Prodej 5 inventářů", "Quests byl splněn");
        neededBlocks = 1000;
        neededSells = 5;
        worldToGo = Bukkit.getWorld("MERKUR");
        stage = 0;
        load();
        if(stage == 0) {
            sendDialog();
            stage = 1;
        }
    }

    @Override
    public void save() {
        Main.instance.getConfig().set("QUESTS." + player.getUniqueId().toString() + ".NAME", questName);
        Main.instance.getConfig().set("QUESTS." + player.getUniqueId().toString() + ".SELLS", sells);
        Main.instance.getConfig().set("QUESTS." + player.getUniqueId().toString() + ".BLOCKS", blocks);
        Main.instance.getConfig().set("QUESTS." + player.getUniqueId().toString() + ".STAGE", stage);
        Main.instance.saveConfig();
    }

    public void load() {
        if(Main.instance.getConfig().contains("QUESTS." + player.getUniqueId().toString() + ".NAME")) {
            if(Main.instance.getConfig().getString("QUESTS." + player.getUniqueId().toString() + ".NAME") != null) {
                sells = Main.instance.getConfig().getInt("QUESTS." + player.getUniqueId().toString() + ".SELLS");
                blocks = Main.instance.getConfig().getInt("QUESTS." + player.getUniqueId().toString() + ".BLOCKS");
                stage = Main.instance.getConfig().getInt("QUESTS." + player.getUniqueId().toString() + ".STAGE");
            }
        }
    }

    public void delete() {
        Main.instance.getConfig().set("QUESTS." + player.getUniqueId().toString() + ".NAME", null);
        Main.instance.saveConfig();
    }

    @Override
    public void onBlockBreak() {
        if(stage == 1) {
            blocks++;
            if(blocks >= neededBlocks) {
                sendDialog();
                stage++;
            }
        }
    }

    @Override
    public void onEntityClick(QuestEntity entity) {

    }

    @Override
    public void onPlayerFly() {

    }

    @Override
    public void onWorldChange(World world) {

    }

    @Override
    public void onSell() {
        if(stage == 2) {
            sells++;
            if(sells == neededSells) {
                sendDialog();
                finishQuest();
            }
        }
    }
}
