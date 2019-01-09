package calculations;

import interfaces.ODEUpdate;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * Klasa , w ktorej tworzone sa listy z wartosciami t,x i v.
 * Ponadto zawiera metody wyliczajace okres i energie. Nastepnie te  dane zapisywane sa one do pliku txt
 */
public class LandingAnalyzer1 implements ODEUpdate {


    // listy tablicowe zapisujące t,x,v,energię
    private ArrayList<Double> tList = new ArrayList<> ();
    private ArrayList<Double> hList = new ArrayList<> ();
    private ArrayList<Double> vList = new ArrayList<> ();
    private ArrayList<Double> mList = new ArrayList<> ();

    // gettery do tablic


    public ArrayList<Double> gettList() {
        return tList;
    }

    public ArrayList<Double> gethList() {
        return hList;
    }

    public ArrayList<Double> getvList() {
        return vList;
    }

    public ArrayList<Double> getmList() {
        return mList;
    }


    //metody
    @Override // wyrzuca wartości t,x,v do tablic
    public void update(double t, double h, double v, double m) {
        tList.add (t);
        hList.add (h);
        vList.add (v);
        mList.add (m);

    }


    public void saveToFile(String fileName) {
        File file = new File (fileName);


        try (PrintWriter printWriter = new PrintWriter (file)) {

            // dodawanie listy do pliku

            for (int i = 0; i < tList.size (); i++) {
                // czas
                printWriter.print (tList.get (i).toString () + '\t'); //zamienia liczbę do String // '\t' oddziela dane tabem
                // wysokosc
                printWriter.print (hList.get (i).toString () + '\t');
                // prędkość
                printWriter.print (vList.get (i).toString () + '\t');
                // masa
                printWriter.print (mList.get (i).toString () + '\n');// przechodzi do nowej lini


            }


        } catch (IOException e) {
            e.printStackTrace ();
        }

    }


    public String calculateLandingTime() {

        double dt = tList.get (1) - tList.get (0);
        double landingTime = 0;
        double landingSpeed=0;


        for (int i = 0; i < hList.size (); i++) {


            if(hList.get (i) > 0){landingTime = landingTime + dt;landingSpeed=vList.get (i);}

        }


        return"Landing time = " + landingTime + " s, Landing speed = " + landingSpeed + " m/s";
    }





}
