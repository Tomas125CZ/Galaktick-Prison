package tomas.chests;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomas.main.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chest {

    private ItemStack key;
    private String chestName;
    private String keyName;
    private List<Reward> rewards;
    private Location loc;

    private ArmorStand stand;

    public ItemStack getKey() {
        return key;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public boolean canOpenChest(Player player, Block block) {
        if(player.getInventory().getItemInMainHand().getType().equals(key.getType())) {
            if(player.getInventory().getItemInMainHand().getItemMeta().equals(key.getItemMeta())) {
                if(block.getLocation().equals(loc)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeHologram() {
        stand.remove();
    }

    public Chest(String chestName, String keyName, List<Reward> rewards, Location loc) {
        this.chestName = chestName;
        this.keyName = keyName;
        this.rewards = rewards;
        if(this.rewards == null) this.rewards = new ArrayList<>();
        this.loc = loc;

        loc.getBlock().setType(Material.CHEST);
        ArmorStand stand = loc.getWorld().spawn(loc.add(0.5, 0, 0.5), ArmorStand.class);
        loc.add(-0.5, 0, -0.5);
        stand.setVisible(false);
        stand.setCustomNameVisible(true);
        stand.setGravity(false);
        stand.setCustomName(ChatColor.translateAlternateColorCodes('&', chestName));
        this.stand = stand;

        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(keyName);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "S tímto klíčem můžeš otevřít " + ChatColor.translateAlternateColorCodes('&', chestName));
        meta.setLore(lore);
        item.setItemMeta(meta);
        key = item;
    }

    public String getName() {
        return chestName;
    }

    public String getKeyName() {
        return keyName;
    }

    public Location getLocation() {
        return loc;
    }

    public void setName(String name) {
        this.chestName = name;
        keyName = chestName + " " + ChatColor.AQUA + "KEY";
        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', keyName));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "S tímto klíčem můžeš otevřít " + ChatColor.translateAlternateColorCodes('&', chestName));
        meta.setLore(lore);
        item.setItemMeta(meta);
        key = item;
        stand.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
    }

    public ItemStack getRandomGlass() {
        int random = new Random().nextInt(10);
        if(random == 0) return new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        if(random == 1) return new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        if(random == 2) return new ItemStack(Material.BROWN_STAINED_GLASS_PANE);
        if(random == 3) return new ItemStack(Material.CYAN_STAINED_GLASS_PANE);
        if(random == 4) return new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        if(random == 5) return new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        if(random == 6) return new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        if(random == 7) return new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        if(random == 8) return new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        if(random == 9) return new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        return new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
    }

    public Reward getRandomReward() {
        int random = new Random().nextInt(100) + 1;
        int n1 = 0;
        for(Reward reward : rewards) {
            if(random >= n1 && random <= n1 + reward.getSance()) {
                return reward;
            }
            n1 += reward.getSance();
        }
        return rewards.get(0);
    }

    public void openChest(Player player) {
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        ChestCreatorPlayer chestCreatorPlayer = Main.data.get(player).getChestCreatorPlayer();
        int total = -2;
        List<Integer> times = List.of(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 7, 7, 7);
        for(int i = 1; i < 41; i++) {
            total += times.get(i - 1);
            final int ii = i;
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                @Override
                public void run() {
                    player.playSound(loc, Sound.BLOCK_STONE_BREAK, 10, 10);
                    Inventory inv = Bukkit.createInventory(player, 27, ChatColor.translateAlternateColorCodes('&', chestName));
                    if(ii == 1) {
                        for (int i = 0; i < 9; i++) {
                            inv.setItem(i, getRandomGlass());
                        }
                        List<ItemStack> items = new ArrayList<>();
                        for (int i = 0; i < 9; i++) {
                            items.add(i, getRandomReward().getItem());
                        }
                        for(int i = 9; i < 18; i++) {
                            inv.setItem(i, items.get(i - 9));
                        }
                        for (int i = 18; i < 27; i++) {
                            inv.setItem(i, getRandomGlass());
                        }
                        chestCreatorPlayer.setItems(items);
                        player.openInventory(inv);
                    }
                    else {
                        for (int i = 0; i < 9; i++) {
                            inv.setItem(i, getRandomGlass());
                        }
                        List<ItemStack> preItems = chestCreatorPlayer.getItems();
                        List<ItemStack> items = new ArrayList<>();
                        for (int i = 0; i < 9; i++) {
                            items.add(new ItemStack(Material.AIR));
                            if (i < 8) {
                                items.set(i, preItems.get(i + 1));
                            } else {
                                items.set(i, getRandomReward().getItem());
                            }
                        }
                        for(int i = 9; i < 18; i++) {
                            inv.setItem(i, items.get(i - 9));
                        }
                        for (int i = 18; i < 27; i++) {
                            inv.setItem(i, getRandomGlass());
                        }
                        chestCreatorPlayer.setItems(items);
                        player.openInventory(inv);
                        if (ii == 40) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                                @Override
                                public void run() {
                                    player.closeInventory();
                                    for (Reward reward : rewards) {
                                        if (reward.getItem().equals(inv.getItem(13))) {
                                            reward.addReward(player);
                                        }
                                    }
                                }
                            }, 20);
                        }
                    }
                }
            }, times.get(i - 1) + total);
        }
    }
}
