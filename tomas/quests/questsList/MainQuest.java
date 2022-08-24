package tomas.quests.questsList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import tomas.main.Main;
import tomas.quests.Quest;
import tomas.quests.QuestEntity;

import java.util.List;

public class MainQuest extends Quest {

    private int flies = 0;
    private int blocks = 0;

    public MainQuest(Player player) {
        super(player);
        questName = "Začátek";
        dialogs = List.of("Vítej na serveru! Na začátek se musíš naučit základní věci, pro teleportaci napiš příkaz /planets a klikni na planetu na kterou chceš cestovat.", "Vítej na planetě MERKUR, aby jsi se ale nejdříve k planetě dostal musíš lítat, zmáčknutím tlačítka shift vyletíš, vyzkoušej si to!", "Výborně! Teď doleť na planetu a vytěž nějaké blocky.", "Tolik blocků stačí, nyní je prodej pomocí příkazu /sell", "Nyní již můžeš začít hrát! Ještě abych nezapomněl, na každé planetě se nachází postava která ti dá questy jako jsem ti teď dával já, pokud chceš nějaké bonusové věci určitě si s ní promluv!");
        questsStrings = List.of("Teleportuj se na planetu MERKUR", "Vyleť 10x do vzduchu", "Vytěž 10 blocků", "Prodej svůj inventář", "Quests byl splněn");
        neededFlies = 10;
        neededBlocks = 10;
        neededSells = 1;
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
        Main.instance.getConfig().set("QUESTS." + player.getUniqueId().toString() + ".FLIES", flies);
        Main.instance.getConfig().set("QUESTS." + player.getUniqueId().toString() + ".BLOCKS", blocks);
        Main.instance.getConfig().set("QUESTS." + player.getUniqueId().toString() + ".STAGE", stage);
        Main.instance.saveConfig();
    }

    public void load() {
        if(Main.instance.getConfig().contains("QUESTS." + player.getUniqueId().toString() + ".NAME")) {
            if(Main.instance.getConfig().getString("QUESTS." + player.getUniqueId().toString() + ".NAME") != null) {
                flies = Main.instance.getConfig().getInt("QUESTS." + player.getUniqueId().toString() + ".FLIES");
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
        if(stage == 3) {
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
        if(stage == 2) {
            flies++;
            if (flies == neededFlies) {
                sendDialog();
                stage++;
            }
        }
    }

    @Override
    public void onWorldChange(World world) {
        if(stage == 1)
            if(world.equals(worldToGo)) {
                sendDialog();
                stage++;
            }

    }

    @Override
    public void onSell() {
        if(stage == 4) {
            sendDialog();
            finishQuest();
        }
    }
}
