package calculations;

import interfaces.CalculateAcceleration;
import interfaces.ODEUpdate;

public class Integrator {




    //pola
    private double dt; // krok całkowania
    // ustawianie wartości początkowych
    public double t ;
    public double h ;
    public double v ;
    public double m ;

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

        //ustawianie
        //     int nSteps = (int) ((tStop - tStart) / dt); // #castowanie


        // tu będą równania 1a,1b,1c
        //   for (int i = 0; i < nSteps; i++) {
        odeUpdate.update (t, h, v, m);// wywołanie metody update // wrzuca wartości początkowe do listy

// całkowanie metodą Eulera Cromera



        double vNew;
        vNew = v + a * dt;


        double hNew = h + vNew * dt;


        double mNew;
        if (m > 1000) {// gdy statek ma jeszcze paliwo
            mNew = m - u * dt;
        } else {
            u = 0;
            mNew = 1000;
        }


        double aNew = calculateAcceleration.a (u, m, g, k); // u bedzie zmieniane


        t = t + dt;// czas się zwiększa z każdym przejściem pętli





        if (hNew > 0) {
            h = hNew;
        } else {
            h = 0;
            vNew = 0;
        }
        v = vNew;


        a = aNew;
        m = mNew;


        System.out.println ("h = "+  h);
        System.out.println ("v = "+ v);


        // }





    }


}//koniec klasy Integrator



/*

            double vNew = v+a*dt;
            double hNew = h+vNew*dt;
            double aNew = calculateAcceleration.a (u,m,g,k); // u bedzie zmieniane
            double mNew = u;


 */