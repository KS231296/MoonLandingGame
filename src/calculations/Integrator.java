package calculations;

import interfaces.CalculateAcceleration;
import interfaces.ODEUpdate;

public class Integrator {


    //pola
    private double dt; // krok całkowania [s]

    private double t; // [s]
    private double h;
    private double v;
    private double m;
    private double a;

    //konstruktor
    public Integrator(double dt) {
        this.dt = dt;
    }

    //metody
    public void integrateVoid(CalculateAcceleration calculateAcceleration, ODEUpdate odeUpdate, double tStart, double tStop, double h0, double v0, double m0, double u, double g, double k) { // double tStart, double x0, double v0 - warunki początkowe // calculateAcceleration-zaimplementowany interfejs
        //tStart i tStop to granice przedziału czasowego


        // ustawianie wartości początkowych
        double t = tStart;
        double h = h0;
        double v = v0;
        double m = m0;

        double a = calculateAcceleration.a (u, m, g, k);

        //ustawianie
        int nSteps = (int) ((tStop - tStart) / dt); // #castowanie


        // tu będą równania 1a,1b,1c
        for (int i = 0; i < nSteps; i++) {
            odeUpdate.update (t, h, v, m);// wywołanie metody update // wrzuca wartości początkowe do listy
            System.out.println("pocz petli: u- " + u + " t- " + t + " h- " + h);
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
            System.out.println("end petli: u- " + u + " t- " + t + " h- " + h);

        }


    }


    public double[] integrate(CalculateAcceleration calculateAcceleration, ODEUpdate odeUpdate, double tStart, double h0, double v0, double m0, double u, double g, double k) { // double tStart, double x0, double v0 - warunki początkowe // calculateAcceleration-zaimplementowany interfejs
        //tStart i tStop to granice przedziału czasowego


        // ustawianie wartości początkowych
        t = tStart;
        h = h0;
        v = v0;
        m = m0;

        a = calculateAcceleration.a(u, m, g, k);

        //ustawianie
        //  int nSteps = (int) ((tStop - tStart) / dt); // #castowanie



// całkowanie metodą Eulera Cromera

        /*    double vNew;
            if (u == 0) { // gdy skonczy się paliwo
                vNew = v+ g*dt   ;
            } else {
                vNew =  v+g*dt-a*dt   ;  // vNew = v + a * dt
        }
*/
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


        double aNew = calculateAcceleration.a(u, m, g, k); // u bedzie zmieniane
        this.a = aNew;

        t = t + dt;// czas się zwiększa z każdym przejściem pętli
        System.out.println("int T:" + t);

        if (hNew <= 0) {

            hNew = 0;
            vNew = 0;
        }

        this.t = t;
        this.h = hNew;
        this.m = mNew;
        this.v = vNew;
        System.out.println("integrator - u: " + u + " h: " + h + " t: " + t);
        odeUpdate.update(t, h, v, m);// wywołanie metody update // wrzuca wartości końcowe do listy


        double[] result = {t, hNew, vNew, mNew};
        return result;
    }


    public double getDt() {
        return dt;
    }

    public double getT() {
        return t;
    }

    public double getH() {
        return h;
    }

    public double getV() {
        return v;
    }

    public double getM() {
        return m;
    }
}

