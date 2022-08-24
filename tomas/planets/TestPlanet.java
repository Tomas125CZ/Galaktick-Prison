package tomas.planets;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;

public class TestPlanet extends Planet{
    @Override
    public HashMap<Material, Integer> getPrizes() {
        return null;
    }

    @Override
    public void checkForReset() {

    }

    @Override
    public String name() {
        return "TestPlanet";
    }

    @Override
    public String message() {
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public List<Integer> mine() {
        return null;
    }

    @Override
    public Location spawnLocation() {
        return null;
    }
}
