package calculations;

import interfaces.ODEUpdate;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * Klasa , w ktorej tworzone sa listy z wartosciami t,h, v i m.
 */
public class LandingAnalyzer1 implements ODEUpdate {


    // listy tablicowe zapisujące t,x,v,m
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

    public void settList(ArrayList<Double> tList) {
        this.tList = tList;
    }

    public void sethList(ArrayList<Double> hList) {
        this.hList = hList;
    }

    public void setvList(ArrayList<Double> vList) {
        this.vList = vList;
    }

    public void setmList(ArrayList<Double> mList) {
        this.mList = mList;
    }

    /**
     * @param t czas
     * @param h wysokosc
     * @param v predkosc
     * @param m masa
     */


    @Override // wyrzuca wartości t,h,v,m do tablic
    public void update(double t, double h, double v, double m) {
        tList.add (t);
        hList.add (h);
        vList.add (v);
        mList.add (m);

    }


}
