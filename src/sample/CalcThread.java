package sample;

import calculations.Integrator;
import calculations.LandingAcceleration;
import calculations.LandingAnalyzer1;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class CalcThread extends Observable implements Runnable, Observer {
    private boolean isRunning;
    private int interval;


    // obiekty klas implementujących interfejsy , później użyjemy te obiekty do wywołania metod z tych interfejsów
    private LandingAcceleration acceleration;
    private LandingAnalyzer1 analyzer1;
    private Integrator integrator = new Integrator(interval / 1000);

    private double t0 = 0;
    private double v0 = -150; // m/s
    private double h0 = 50000; // m
    private double m0 = 2730.14; // kg ( w tym masa statku)
    private double ms = 1000; //kg masa statku
    private double k = 636; // m/s
    private double mi = -16.5; // kg/s
    private double g = 1.63; // przyśpieszenie na Księżycu

    private double u;


    public CalcThread(int interval, double u, LandingAcceleration a, LandingAnalyzer1 odeUpdate) {
        this.interval = interval;
        this.u = u;
        this.acceleration = a;
        this.analyzer1 = odeUpdate;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }


    public double getV0() {
        return v0;
    }

    public void setV0(double v0) {
        this.v0 = v0;
    }

    public double getH0() {
        return h0;
    }

    public void setH0(double h0) {
        this.h0 = h0;
    }

    public double getM0() {
        return m0;
    }

    public void setM0(double m0) {
        this.m0 = m0;
    }

    public double getMs() {
        return ms;
    }

    public void setMs(double ms) {
        this.ms = ms;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getMi() {
        return mi;
    }

    public void setMi(double mi) {
        this.mi = mi;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getU() {
        return u;
    }

    public void setU(double u) {
        this.u = u;
    }

    @Override
    public void run() {
        isRunning = true;
        acceleration = new LandingAcceleration();
        analyzer1 = new LandingAnalyzer1();


        analyzer1.update(t0, h0, v0, m0);// wywołanie metody update // wrzuca wartości początkowe do listy

        while (isRunning) {
            try {
                System.out.println("before: u: " + u + " h: " + h0 + " v: " + v0 + " t: " + t0);
                //integrator.integrateVoid(acceleration, analyzer1, t0, 300, h0, v0, m0, u, g, k);
                  integrator.integrate(acceleration, analyzer1, t0,  h0, v0, m0, u, g, k); // wywołanie metody

              /*  t0 = integrator.getT();
                h0 = integrator.getH();
                v0 = integrator.getV();
                m0 = integrator.getM();*/

                ArrayList tList = analyzer1.gettList();
                ArrayList hList = analyzer1.gethList();
                ArrayList vList = analyzer1.getvList();
                ArrayList mList = analyzer1.getmList();


                t0 = (double) tList.get(tList.size() - 1);
                h0 = (double) tList.get(hList.size() - 1);
                v0 = (double) tList.get(vList.size() - 1);
                m0 = (double) tList.get(mList.size() - 1);

                System.out.println("u: " + u + " h: " + h0 + " v: " + v0 + " t: " + t0);
               // isRunning = false;
               /* if (h0 == 0) {
                    isRunning = false;
                }*/
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

    }

    public void stop() {
        isRunning = false;
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
