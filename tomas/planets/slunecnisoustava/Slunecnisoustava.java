package tomas.planets.slunecnisoustava;

import tomas.planets.Galaxie;
import tomas.planets.Planet;

import java.util.List;

public class Slunecnisoustava extends Galaxie {
    
    @Override
    public List<Planet> planets() {
        List<Planet> planets = List.of(new Merkur(), new Venuše(), new Země(), new Mars(), new Jupiter(), new Saturn(), new Uran(), new Neptun());
        return planets;
    }

    @Override
    public String getName() {
        return "Slunecnisoustava";
    }
}
