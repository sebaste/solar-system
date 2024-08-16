package solarsystem;

public class CoordinatesSpherical {

    private double rho; // Radial distance.
    private double theta; // Azimuthal angle (radians).
    private double phi; // Polar angle (radians).

    CoordinatesSpherical(double rho, double theta, double phi) {
        this.rho = rho;
        this.theta = theta;
        this.phi = phi;
    }

    public double getRho() {
        return rho;
    }
    public double getTheta() {
        return theta;
    }
    public double getPhi() {
        return phi;
    }

    @Override
    public String toString() {
        return String.format("(rho=%f, theta=%f, phi=%f)", rho, theta, phi);
    }
}
