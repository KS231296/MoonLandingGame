package calculations;

import interfaces.CalculateAcceleration;
import interfaces.ODEUpdate;

public class Integrator {


    //pola
    private double dt; // krok całkowania

    //konstruktor
    public Integrator(double dt) {
        this.dt = dt;
    }

    //metody
    public void integrate(CalculateAcceleration calculateAcceleration, ODEUpdate odeUpdate, double tStart, double tStop, double h0, double v0, double m0, double u, double g, double k) { // double tStart, double x0, double v0 - warunki początkowe // calculateAcceleration-zaimplementowany interfejs
        //tStart i tStop to granice przedziału czasowego


        // ustawianie wartości początkowych
        double t = tStart;
        double h = h0;
        double v = v0;
        double m = m0;

        double a = calculateAcceleration.a (u, m, g, k);

        //ustawianie
        int nSteps = (int) ((tStop - tStart) / dt); // #castowanie


            odeUpdate.update (t, h, v, m);// wywołanie metody update // wrzuca wartości początkowe do listy

// całkowanie metodą Eulera Cromera

        /*    double vNew;
            if (u == 0) { // gdy skonczy się paliwo
                vNew = v+ g*dt   ;
            } else {
                vNew =  v+g*dt-a*dt   ;  // vNew = v + a * dt
        }
*/
            double vNew;
            vNew= v +   a*dt ;


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


            if(hNew>0){h = hNew;}else{h=0;vNew=0;}
            v = vNew;



            a = aNew;
            m = mNew;

        }


    }

