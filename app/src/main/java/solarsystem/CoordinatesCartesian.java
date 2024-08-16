package solarsystem;

public class CoordinatesCartesian {

    private double x;
    private double y;
    private double z;

    CoordinatesCartesian(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double getZ() {
        return this.z;
    }

    CoordinatesCartesian(CoordinatesSpherical pos) {
        // x = ρ sin φ cos θ, y = ρ sin φ sin θ, z = ρ cos φ.
        this.x = pos.getRho() * Math.sin(pos.getPhi()) * Math.cos(pos.getTheta());
        this.y = pos.getRho() * Math.sin(pos.getPhi()) * Math.sin(pos.getTheta());
        this.z = pos.getRho() * Math.cos(pos.getPhi());
    }

    public void add(CoordinatesCartesian c) {
        this.x += c.x;
        this.y += c.y;
        this.z += c.z;
    }

    public double dist(CoordinatesCartesian c) {
        return Math.pow(
            Math.pow(c.x - this.x, 2) + Math.pow(c.y - this.y, 2) + Math.pow(c.z - this.z, 2),
            0.5
            );
    }

    @Override
    public String toString() {
        return String.format("(x=%f, y=%f, z=%f)", x, y, z);
    }
}
