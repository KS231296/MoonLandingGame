package sample;

import calculations.Integrator;
import calculations.LandingAcceleration;
import calculations.LandingAnalyzer1;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Time;
import java.time.LocalTime;


public class CalcThread implements Runnable, PropertyChangeListener { // observable i observer z java beans ogarnac
    private boolean isRunning;
    private int interval = 200;
    private PropertyChangeSupport support;


    // obiekty klas implementujących interfejsy , później użyjemy te obiekty do wywołania metod z tych interfejsów
    private LandingAcceleration acceleration;
    private LandingAnalyzer1 analyzer1;
    private Integrator integrator = new Integrator((double) interval / 1000);

    private double t0 = 0;
    private double v0 = -150; // m/s
    private double h0 = 50000; // m
    private double m0 = 2730.14; // kg ( w tym masa statku)
    private double ms = 1000; //kg masa statku
    private double k = 636; // m/s
    private double mi = -16.5; // kg/s
    private double g = 1.63; // przyśpieszenie na Księżycu

    private double u;
    private double thrust;

    public double getThrust() {
        return thrust;
    }

    public CalcThread(double u) {
        this.u = u;
        support = new PropertyChangeSupport(this);
    }

    @Override
    public void run() {
        isRunning = true;
        acceleration = new LandingAcceleration();
        analyzer1 = new LandingAnalyzer1();
        thrust = u;
        int sec = 1;
        while (isRunning) {
            try {
                integrator.integrate(acceleration, analyzer1, t0, h0, v0, m0, thrust, g, k); // wywołanie metody
                t0 = integrator.t;
                setH0(integrator.h);
                setV0(integrator.v);
                setM0(integrator.m);

                if (sec == 5) {
                    thrust = u;
                    sec = 0;
                }
                sec++;
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

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


    public double getH0() {
        return h0;
    }


    public double getM0() {
        return m0;
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


    public void stop() {
        isRunning = false;
    }


    public void addListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener((PropertyChangeListener) pcl);
    }


    public void removeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener((PropertyChangeListener) pcl);
    }

    public void setT0(double value) {
        support.firePropertyChange("t0", this.t0, value);
        this.t0 = value;
    }

    public void setH0(double value) {
        support.firePropertyChange("h0", this.h0, value);
        this.h0 = value;
    }

    public void setV0(double value) {
        support.firePropertyChange("v0", this.v0, value);
        this.v0 = value;
    }

    public void setM0(double value) {
        support.firePropertyChange("m0", this.m0, value);
        this.m0 = value;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }


}
