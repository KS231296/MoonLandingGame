package calculations;

import interfaces.CalculateAcceleration;

/**
 * Klasa implementujaca interfejs CalculateAcceleration , wylicza przyspieszenie
 */
public class LandingAcceleration implements CalculateAcceleration {

    @Override
    public double a(double u, double m, double g, double k) {
        return -g - (k * -u) / m;
    }



}
