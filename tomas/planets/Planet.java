package tomas.planets;

import org.bukkit.Location;
import org.bukkit.Material;
import tomas.main.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Planet {
     public abstract HashMap<Material, Integer> getPrizes();

     public abstract void checkForReset();

     public static Planet getPlanetByName(String name) {
          for(Map.Entry<Planet, Integer> planet : Main.planets) {
               if(planet.getKey().name().equalsIgnoreCase(name)) return planet.getKey();
          }
          return null;
     }

     public static int getNumberByName(String name) {
          int i = 1;
          for(Map.Entry<Planet, Integer> planet : Main.planets) {
               if(planet.getKey().name().equals(name)) return i;
               i++;
          }
          return 1;
     }

     public static String getNameByNumber(int number) {
          int i = 1;
          for(Map.Entry<Planet, Integer> planet: Main.planets) {
               if(i == number) return planet.getKey().name();
               i++;
          }
          return "CHYBA";
     }

     public abstract String name();

     public abstract String message();

     public abstract void reset();

     public abstract List<Integer> mine();

     public abstract Location spawnLocation();
}
