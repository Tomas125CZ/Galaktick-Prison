package tomas.chests;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomas.main.Main;

public class Reward {

    private ItemStack item;
    private int sance;
    private RewardType type;
    private int moneyAmount;
    private int tokensAmount;
    private String keyName;

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public int getTokensAmount() {
        return tokensAmount;
    }

    public String getKeyName() {
        return keyName;
    }

    public RewardType getType() {
        return type;
    }

    public static String getStringByReward(RewardType type) {
        if(type.equals(RewardType.TOKENS)) return "TOKENS";
        if(type.equals(RewardType.KEY)) return "KEY";
        if(type.equals(RewardType.MONEY)) return "MONEY";
        return "";
    }

    public static RewardType getRewardTypeByName(String name) {
        if(name.equalsIgnoreCase("MONEY")) return RewardType.MONEY;
        if(name.equalsIgnoreCase("KEY")) return RewardType.KEY;
        if(name.equalsIgnoreCase("TOKENS")) return RewardType.TOKENS;
        return null;
    }

    public Reward(int sance, int moneyAmount, int tokensAmount, String keyName, RewardType type) {
        this.sance = sance;
        this.moneyAmount = moneyAmount;
        this.tokensAmount = tokensAmount;
        this.keyName = keyName;
        this.type = type;
        if(type.equals(RewardType.KEY)) {
            for(Chest chest : Main.chests) {
                if(chest.getKeyName().equals(keyName)) {
                    item = chest.getKey();
                }
            }
        }
        if(type.equals(RewardType.MONEY)) {
            ItemStack item = new ItemStack(Material.GREEN_DYE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "" + moneyAmount + "$");
            item.setItemMeta(meta);
            this.item = item;
        }
        if(type.equals(RewardType.TOKENS)) {
            ItemStack item = new ItemStack(Material.SUNFLOWER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + "" + tokensAmount + " tokens");
            item.setItemMeta(meta);
            this.item = item;
        }
    }

    public ItemStack getItem() {
        return item;
    }

    public void addReward(Player player) {
        if(type.equals(RewardType.KEY)) {
            for(Chest chest : Main.chests) {
                if(chest.getKeyName().equals(keyName)) {
                    player.getInventory().addItem(chest.getKey());
                    player.sendMessage(ChatColor.GREEN + "Získal jsi " + chest.getKeyName());
                }
            }
        }
        if(type.equals(RewardType.MONEY)) {
            Main.data.get(player).getDataManager().updateStat("money", Main.data.get(player).getDataManager().getStat("money") + moneyAmount);
            player.sendMessage(ChatColor.GREEN + "Získal jsi " + moneyAmount + "$");
        }
        if(type.equals(RewardType.TOKENS)) {
            Main.data.get(player).getDataManager().updateStat("tokens", Main.data.get(player).getDataManager().getStat("tokens") + tokensAmount);
            player.sendMessage(ChatColor.GREEN + "Získal jsi " + tokensAmount + " tokenů");
        }
    }

    public int getSance() {
        return sance;
    }
}
