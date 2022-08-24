package tomas.events;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import tomas.main.Main;
import tomas.quests.QuestEntity;

public class EntityClick implements Listener {

    @EventHandler
    public void onEntityClick(PlayerInteractAtEntityEvent e) {
        if(e.getRightClicked() instanceof ArmorStand) {
            for(QuestEntity questEntity : Main.questEntities) {
                if(e.getRightClicked().getCustomName().equals(questEntity.stand.getCustomName())) {
                    if(Main.data.get(e.getPlayer()).getQuest() != null) Main.data.get(e.getPlayer()).getQuest().onEntityClick(questEntity);
                    questEntity.onClick(e.getPlayer());
                    return;
                }
            }
        }
    }
}
