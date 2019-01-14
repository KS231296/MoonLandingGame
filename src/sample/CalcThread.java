package sample;

import calculations.Integrator;
import calculations.LandingAcceleration;
import calculations.LandingAnalyzer1;

import java.util.Observable;
import java.util.Observer;

public class CalcThread implements Runnable, Observer {
    private boolean isRunning;
    private int interval;


    // obiekty klas implementujących interfejsy , później użyjemy te obiekty do wywołania metod z tych interfejsów
    LandingAcceleration landingAcceleration = new LandingAcceleration();
    LandingAnalyzer1 landingAnalyzer1 = new LandingAnalyzer1();


    double v0 = -150; // m/s
    double h0 = 50000; // m
    double m0 = 2730.14; // kg ( w tym masa statku)
    double ms = 1000; //kg masa statku
    double k = 636; // m/s
    double μ = -16.5; // kg/s
    double g = 1.63; // przyśpieszenie na Księżycu

    double u = 16.5;


    public CalcThread(int interval) {
        this.interval = interval;
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {

            try {
                Integrator integrator = new Integrator(1);

                integrator.integrate(landingAcceleration, landingAnalyzer1, 0, 700, h0, v0, m0, u, g, k); // wywołanie metody


                //   landingAnalyzer1.saveToFile ("C:\\Users\\gendasai\\Desktop\\LandingChartsData.txt"); //używane do wyrysowania wykresu w MatLabie


                System.out.println(landingAnalyzer1.calculateLandingTime());


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
