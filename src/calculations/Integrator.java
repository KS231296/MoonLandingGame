package calculations;

import interfaces.CalculateAcceleration;
import interfaces.ODEUpdate;

/**
 * Klasa , w ktorej używając metody Eulera-Richardsona oblicza się kolejne wartości t,h,v,a i m
 */

public class Integrator {





    //pola
    private double dt;
    public double t;
    public double h;
    public double v;
    public double m;
    public double a;


    //konstruktor
    public Integrator(double dt) {
        this.dt = dt;
    }

    //metody
    public void integrate(CalculateAcceleration calculateAcceleration, ODEUpdate odeUpdate, double tStart, double h0, double v0, double m0, double u, double g, double k) { // double tStart, double x0, double v0 - warunki początkowe // calculateAcceleration-zaimplementowany interfejs
        //tStart i tStop to granice przedziału czasowego


        // ustawianie wartości początkowych
        t = tStart;
        h = h0;
        v = v0;
        m = m0;
        a = calculateAcceleration.a (u, m, g, k);

        odeUpdate.update (t, h, v, m);// wywołanie metody update // wrzuca wartości  do listy


        //masa
        double mNew;
        double mHalf;

        if (m > 1000) {// gdy statek ma jeszcze paliwo

            mHalf = m - u * 0.5 * dt;
            mNew = m - u * dt;

        } else {
            u = 0;
            mHalf = 1000;
            mNew = 1000;
        }


        //przyspieszenie
        double aHalf = calculateAcceleration.a (u, mHalf, g, k);
        double aNew = calculateAcceleration.a (u, m, g, k); // u bedzie zmieniane

        //prędkosc
        double vHalf;
        double vNew;

        if (u == 0) { // gdy skonczy się paliwo

            vHalf = v - 0.5 * g * dt;
            vNew = v - g * dt;

        } else {

            vHalf = v + 0.5 * a * dt;
            vNew = v + aHalf * dt;
        }


        //wysokosc
        double hHalf = h + 0.5 * vNew * dt;
        double hNew = h + vHalf * dt;


        //ustawianie wartosci
        t = t + dt;
        v = vNew;
        h = hNew;
        a = aNew;
        m = mNew;




    }

    /**
     * @param dt krok calkowania
     * @param t czas
     * @param h wysokosc
     * @param v predkosc
     * @param m masa statku i paliwa
     * @param a przyspieszenie statku
     * @param mNew chwilowa masa
     * @param vNew chwilowa predkosc
     * @param hNew chwilowa wysokosc
     * @param aNew chwilowe przyspieszenie
     * @param mHalf polowkowa masa
     * @param vHalf cpolowkowe predkosc
     * @param hHalf polowkowa wysokosc
     * @param aHalf polowkowe przyspieszenie
     */


}

