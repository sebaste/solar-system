package solarsystem;

public class App {

    public static void main(String[] args) {
        try {
            SolarSystemImpl solarSystem = new SolarSystemImpl();
            String name1 = "Moon";
            String name2 = "Mars";
            double dist = solarSystem.dist(name1, name2);
            System.out.println(String.format("Distance from %s to %s: %f", name1, name2, dist));
        } catch (Exception e) {
            System.err.println(String.format("Error: %s", e));
        }
    }
}
