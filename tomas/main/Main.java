package tomas.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tomas.chests.*;
import tomas.commands.ConsoleCommands;
import tomas.commands.PlayerCommands;
import tomas.data.DataPlayer;
import tomas.enchantments.CustomEnchants;
import tomas.events.*;
import tomas.inventory.Planets;
import tomas.inventory.Upgrades;
import tomas.planets.Galaxie;
import tomas.planets.TestGalaxy;
import tomas.planets.TestPlanet;
import tomas.planets.slunecnisoustava.*;
import tomas.planets.Planet;
import tomas.quests.QuestEntity;
import tomas.quests.entitiesList.MerkurMiner;
import tomas.quests.entitiesList.Pilot;

import java.util.*;

public final class Main extends JavaPlugin {

    public static HashMap<Player, DataPlayer> data = new HashMap<>();
    public static List<Map.Entry<Planet,Integer>> planets;
    public static List<QuestEntity> questEntities;
    public static List<Chest> chests;
    public static List<Map.Entry<Galaxie, Integer>> galaxie;
    public static Main instance;
    private int counter;

    @Override
    public void onEnable() {
        questEntities = List.of(new Pilot(), new MerkurMiner());
        chests = new ArrayList<>();
        instance = this;
        CustomEnchants.register();
        counter = 0;
        HashMap<Planet, Integer> prePlanets = new HashMap<>();
        prePlanets.put(new Merkur(), 0);
        prePlanets.put(new Venuše(), 1500);
        prePlanets.put(new Země(), 5500);
        prePlanets.put(new Mars(), 13000);
        prePlanets.put(new Jupiter(), 22000);
        prePlanets.put(new Saturn(), 35000);
        prePlanets.put(new Uran(), 50000);
        prePlanets.put(new Neptun(), 70000);
        prePlanets.put(new TestPlanet(), 100000);
        HashMap<Galaxie, Integer> preGalaxie = new HashMap<>();
        preGalaxie.put(new Slunecnisoustava(), 0);
        preGalaxie.put(new TestGalaxy(), 10000);
        new Voting();

        planets = new ArrayList<>(prePlanets.entrySet());

        Collections.sort(planets, new Comparator<Map.Entry<Planet, Integer>>() {
            @Override
            public int compare(Map.Entry<Planet, Integer> o1, Map.Entry<Planet, Integer> o2) {
                return o1.getValue()-o2.getValue();
            }
        });

        galaxie = new ArrayList<>(preGalaxie.entrySet());

        Collections.sort(galaxie, new Comparator<Map.Entry<Galaxie, Integer>>() {
            @Override
            public int compare(Map.Entry<Galaxie, Integer> o1, Map.Entry<Galaxie, Integer> o2) {
                return o1.getValue()-o2.getValue();
            }
        });


        getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(new Join(), this);
        getServer().getPluginManager().registerEvents(new Leave(), this);
        getServer().getPluginManager().registerEvents(new WorldChange(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new Planets(), this);
        getServer().getPluginManager().registerEvents(new Upgrades(), this);
        getServer().getPluginManager().registerEvents(new onDamage(), this);
        getServer().getPluginManager().registerEvents(new VoidFall(), this);
        getServer().getPluginManager().registerEvents(new Shift(), this);
        getServer().getPluginManager().registerEvents(new ChestEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityClick(), this);
        getCommand("planets").setExecutor(new Planets());
        getCommand("sell").setExecutor(new Commands());
        getCommand("vote").setExecutor(new ConsoleCommands());
        getCommand("miner").setExecutor(new PlayerCommands());
        getCommand("chests").setExecutor(new ChestCommand());

        for(Map.Entry<Planet, Integer> planet : planets) {
            planet.getKey().reset();
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                int i  = 0;
                for(Map.Entry<Planet, Integer> planet : planets) {
                    if(i == counter) planet.getKey().reset();
                    i++;
                }
                counter++;
                if(counter == planets.size()) counter = 0;
            }
        }, 0, 6000);
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                if(getConfig().contains("CHESTS." + ".LIST")) {
                    List<String> chests = (List<String>) getConfig().getList("CHESTS." + ".LIST");
                    for(String s : chests) {
                        String keyName = getConfig().getString("CHESTS." + s + ".KEYNAME");
                        int x = getConfig().getInt("CHESTS." + s + ".X");
                        int y = getConfig().getInt("CHESTS." + s + ".Y");
                        int z = getConfig().getInt("CHESTS." + s + ".Z");
                        World world = Bukkit.getWorld(getConfig().getString("CHESTS." + s + ".WORLD"));
                        int rewardsnumber = getConfig().getInt("CHESTS." + s + ".REWARDSINT");
                        List<Reward> rewards = new ArrayList<>();
                        for(int i = 0; i < rewardsnumber; i++) {
                            int sance = getConfig().getInt("CHESTS." + s + ".REWARDS" + i + ".SANCE");
                            int moneyAmount = getConfig().getInt("CHESTS." + s + ".REWARDS" + i + ".MONEYAMOUNT");
                            int tokensAmount = getConfig().getInt("CHESTS." + s + ".REWARDS" + i + ".TOKENSAMOUNT");
                            String keyNamet = getConfig().getString("CHESTS." + s + ".REWARDS" + i + ".KEYNAME");
                            RewardType type = Reward.getRewardTypeByName(getConfig().getString("CHESTS." + s + ".REWARDS" + i + "TYPE"));
                            rewards.add(new Reward(sance, moneyAmount, tokensAmount, keyNamet, type));
                        }
                        Main.chests.add(new Chest(s, keyName, rewards, new Location(world, x, y, z)));
                        System.out.println("Chest " + s + " byla založena! " + s + " " + keyName + " " + rewards.size() + " " + world.getName() + " " + x + " " + y + " " + z);
                    }
                    saveConfig();
                }
            }
        }, 40);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        List<String> chestsS = new ArrayList<>();
        if(chests != null) {
            for (Chest chest : chests) {
                System.out.println(chest.getName());
                chestsS.add(chest.getName());
                chest.removeHologram();
                getConfig().set("CHESTS." + chest.getName() + ".KEYNAME", chest.getKeyName());
                getConfig().set("CHESTS." + chest.getName() + ".X", chest.getLocation().getX());
                getConfig().set("CHESTS." + chest.getName() + ".Y", chest.getLocation().getY());
                getConfig().set("CHESTS." + chest.getName() + ".Z", chest.getLocation().getZ());
                getConfig().set("CHESTS." + chest.getName() + ".WORLD", chest.getLocation().getWorld().getName());
                int number = 0;
                for (Reward reward : chest.getRewards()) {
                    getConfig().set("CHESTS." + chest.getName() + ".REWARDS" + number + "SANCE", reward.getSance());
                    getConfig().set("CHESTS." + chest.getName() + ".REWARDS" + number + "MONEYAMOUNT", reward.getMoneyAmount());
                    getConfig().set("CHESTS." + chest.getName() + ".REWARDS" + number + "TOKENSAMOUNT", reward.getTokensAmount());
                    getConfig().set("CHESTS." + chest.getName() + ".REWARDS" + number + "KEYNAME", reward.getKeyName());
                    getConfig().set("CHESTS." + chest.getName() + ".REWARDS" + number + "TYPE", String.valueOf(reward.getType()));
                    number++;
                }
                getConfig().set("CHESTS." + chest.getName() + ".REWARDSINT", number);
            }
        }
        System.out.println(chestsS);
        getConfig().set("CHESTS." + ".LIST", chestsS);
        getConfig().set("VOTING." + ".VOTES", Voting.votes);
        saveConfig();
        for(Player player : Bukkit.getOnlinePlayers()) {
            data.get(player).getDataManager().save();
            if(data.get(player).getMiner().isSpawned()) data.get(player).getMiner().despawn();
            player.kickPlayer(ChatColor.RED + "Server byl vypnut!");
        }
    }

    public void broadcast(String message) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + ChatColor.BOLD + "BROADCAST" + ChatColor.GRAY + "] " + ChatColor.RESET + message);
        }
    }
}
