package tomas.miner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import tomas.data.DataManager;
import tomas.main.Main;
import tomas.planets.Planet;

public class Miner {

    private Player player;
    private int coins;
    private int tokens;
    private Planet planet;
    private Entity entity;
    private Location lastPosition;
    private String direction;
    private String direction2;
    private int delay;


    public Miner(Player player) {
        delay = 0;
        direction = "left";
        direction2 = "left";
        this.player = player;
        this.coins = 0;
        this.tokens = 0;
        this.planet = Planet.getPlanetByName(player.getWorld().getName());
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                if(planet != null) {
                    if(entity != null) {
                        entity.teleport(lastPosition);
                        Location copy =  lastPosition;
                        if(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY() - 1, (int) lastPosition.getZ()).getType().equals(Material.AIR)) {
                            entity.teleport(lastPosition.add(0, -1, 0));
                        }
                        else {
                            delay++;
                            if(delay == 5) {
                                delay = 0;
                                player.sendMessage(direction2);
                                if (lastPosition.getY() > planet.mine().get(3)) {
                                    entity.teleport(lastPosition.add(0, -1, 0));
                                    if (planet.getPrizes().containsKey(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()))) {
                                        coins += planet.getPrizes().get(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType());
                                        Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).setType(Material.AIR);
                                    }
                                }
                                if(direction2.equals("left")) {
                                    if (direction.equals("left")) {
                                        if (planet.mine().get(1) == (int) lastPosition.getX()) {
                                            direction = "right";
                                            if (planet.mine().get(5) == (int) lastPosition.getZ()) {
                                                if (lastPosition.getY() == planet.mine().get(2)) {
                                                    despawn();
                                                } else {
                                                    entity.teleport(lastPosition.add(0, -1, 0));
                                                    coins += planet.getPrizes().get(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType());
                                                    Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).setType(Material.AIR);
                                                }
                                                direction2 = "right";
                                            } else {
                                                entity.teleport(lastPosition.add(0, 0, 1));
                                                if (!Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType().equals(Material.AIR)) {
                                                    coins += planet.getPrizes().get(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType());
                                                    Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).setType(Material.AIR);
                                                }
                                            }
                                        } else {
                                            entity.teleport(lastPosition.add(1, 0, 0));
                                            if (!Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType().equals(Material.AIR)) {
                                                coins += planet.getPrizes().get(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType());
                                                Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).setType(Material.AIR);
                                            }
                                        }
                                    } else {
                                        if (planet.mine().get(0) == (int) lastPosition.getX()) {
                                            direction = "left";
                                            if (planet.mine().get(5) == (int) lastPosition.getZ()) {
                                                if (lastPosition.getY() == planet.mine().get(2)) {
                                                    despawn();
                                                } else {
                                                    entity.teleport(lastPosition.add(0, -1, 0));
                                                    coins += planet.getPrizes().get(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType());
                                                    Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).setType(Material.AIR);
                                                }
                                                direction2 = "right";
                                            } else {
                                                entity.teleport(lastPosition.add(0, 0, 1));
                                                if (!Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType().equals(Material.AIR)) {
                                                    coins += planet.getPrizes().get(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType());
                                                    Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).setType(Material.AIR);
                                                }
                                            }
                                        } else {
                                            entity.teleport(lastPosition.add(-1, 0, 0));
                                            if (!Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType().equals(Material.AIR)) {
                                                coins += planet.getPrizes().get(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType());
                                                Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).setType(Material.AIR);
                                            }
                                        }
                                    }
                                }
                                else {
                                    if (direction.equals("left")) {
                                        if (planet.mine().get(1) == (int) lastPosition.getX()) {
                                            direction = "right";
                                            if (planet.mine().get(4) == (int) lastPosition.getZ()) {
                                                if (lastPosition.getY() == planet.mine().get(2)) {
                                                    despawn();
                                                } else {
                                                    entity.teleport(lastPosition.add(0, -1, 0));
                                                    coins += planet.getPrizes().get(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType());
                                                    Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).setType(Material.AIR);
                                                }
                                                direction2 = "left";
                                            } else {
                                                entity.teleport(lastPosition.add(0, 0, -1));
                                                if (!Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType().equals(Material.AIR)) {
                                                    coins += planet.getPrizes().get(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType());
                                                    Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).setType(Material.AIR);
                                                }
                                            }
                                        } else {
                                            entity.teleport(lastPosition.add(1, 0, 0));
                                            if (!Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType().equals(Material.AIR)) {
                                                coins += planet.getPrizes().get(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType());
                                                Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).setType(Material.AIR);
                                            }
                                        }
                                    } else {
                                        if (planet.mine().get(0) == (int) lastPosition.getX()) {
                                            direction = "left";
                                            if (planet.mine().get(4) == (int) lastPosition.getZ()) {
                                                if (lastPosition.getY() == planet.mine().get(2)) {
                                                    despawn();
                                                } else {
                                                    entity.teleport(lastPosition.add(0, -1, 0));
                                                    coins += planet.getPrizes().get(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType());
                                                    Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).setType(Material.AIR);
                                                }
                                                direction2 = "left";
                                            } else {
                                                entity.teleport(lastPosition.add(0, 0, -1));
                                                if (!Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType().equals(Material.AIR)) {
                                                    coins += planet.getPrizes().get(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType());
                                                    Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).setType(Material.AIR);
                                                }
                                            }
                                        } else {
                                            entity.teleport(lastPosition.add(-1, 0, 0));
                                            if (!Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType().equals(Material.AIR)) {
                                                coins += planet.getPrizes().get(Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).getType());
                                                Bukkit.getWorld(planet.name()).getBlockAt((int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()).setType(Material.AIR);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(copy != lastPosition) {
                            boolean inMine = false;
                            if(lastPosition.getX() >= planet.mine().get(0) && lastPosition.getX() <= planet.mine().get(1))
                                if(lastPosition.getZ() >= planet.mine().get(4) && lastPosition.getZ() <= planet.mine().get(5))
                                    inMine = true;
                            if(!inMine) {
                                entity.teleport(player.getLocation());
                                if(!entity.isOnGround()) despawn();
                                lastPosition = player.getLocation();
                            }
                        }
                    }
                }
            }
        }, 0, 4);
    }

    public void spawn() {
        if(!player.isOnGround()) {
            player.sendMessage("Musíš stát na zemi!");
            return;
        }
        this.planet = Planet.getPlanetByName(player.getWorld().getName());
        if(planet != null) {
            entity = player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
            entity.setGravity(false);
            entity.setCustomNameVisible(true);
            entity.setCustomName(ChatColor.BLUE + "" + player.getName() + " miner");
            lastPosition = player.getLocation();
            if(lastPosition.getX() != (int) lastPosition.getX())
                if(lastPosition.getY() != (int) lastPosition.getY())
                    if(lastPosition.getZ() != (int) lastPosition.getZ()) {
                        entity.teleport(new Location(player.getWorld(), (int) lastPosition.getX(), (int) lastPosition.getY(), (int) lastPosition.getZ()));
                    }
            player.sendMessage("Miner byl spawnut!");
            if(player.getLocation().getX() >= planet.mine().get(0) && player.getLocation().getX() <= planet.mine().get(1)) {
                if(player.getLocation().getZ() >= planet.mine().get(4) && player.getLocation().getZ() <= planet.mine().get(5)) {
                    if(!(player.getLocation().getY() >= planet.mine().get(3))) despawn();
                }
            }
            if(planet.mine().get(2) > player.getLocation().getY()) {
                despawn();
            }
            return;
        }
        player.sendMessage("Nejsi ve světě ve kterém se dá spawnout miner!");
    }

    public void despawn() {
        DataManager dataManager = Main.data.get(player).getDataManager();
        dataManager.updateStat("money", dataManager.getStat("money") + coins);
        dataManager.updateStat("tokens", dataManager.getStat("tokens") + tokens);
        entity.remove();
        entity = null;
        player.sendMessage("Miner byl despawnut!");
    }

    public boolean isSpawned() {
        return entity != null;
    }

    public Planet getPlanet() {
        return planet;
    }

    public Entity getEntity() {
        return entity;
    }
}
