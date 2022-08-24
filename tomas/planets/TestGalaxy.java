package tomas.planets;

import java.util.List;

public class TestGalaxy extends Galaxie{
    @Override
    public List<Planet> planets() {
        return List.of(new TestPlanet());
    }

    @Override
    public String getName() {
        return "TestGalaxy";
    }
}
