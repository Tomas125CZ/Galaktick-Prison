package tomas.planets;

import tomas.main.Main;

import java.util.List;
import java.util.Map;

public abstract class Galaxie {

    public abstract List<Planet> planets();

    public static Galaxie getGalaxieByNumber(int number) {
        int i = 1;
        for(Map.Entry<Galaxie, Integer> galaxie : Main.galaxie) {
            if(number == i) return galaxie.getKey();
            i++;
        }
        return null;
    }

    public static Galaxie getGalaxieByName(String name) {
        for(Map.Entry<Galaxie, Integer> galaxie : Main.galaxie) {
            if(name.equals(galaxie.getKey().getName())) return galaxie.getKey();
        }
        return null;
    }

    public static Integer getNumberByName(String name) {
        int i = 1;
        for(Map.Entry<Galaxie, Integer> galaxie : Main.galaxie) {
            if(name.equals(galaxie.getKey().getName())) return i;
            i++;
        }
        return 0;
    }

    public static int getMaxGalaxies() {
        return Main.galaxie.size();
    }

    public abstract String getName();
}
