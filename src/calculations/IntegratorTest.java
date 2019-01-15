package calculations;

public class IntegratorTest {

    public static void main(String[] args) {

        double t = 0.2;
        double s = 1;

        double r = s%t;

        System.out.println(r);



      /*  //stałe

        double v0 = -150; // m/s
        double h0 = 50000; // m
        double m0 = 2730.14; // kg ( w tym masa statku)
        double ms = 1000; //kg masa statku
        double k = 636; // m/s
        double μ = -16.5; // kg/s
        double g=1.63; // przyśpieszenie na Księżycu

        double u=16.5 ;
                ; // wprowadzane przez uzytkownika

        //  nowy obiekt klasy
        Integrator test1 = new Integrator (1);

        // obiekty klas implementujących interfejsy , później użyjemy te obiekty do wywołania metod z tych interfejsów
        LandingAcceleration landingAcceleration = new LandingAcceleration();
        LandingAnalyzer1 landingAnalyzer1 = new LandingAnalyzer1 ();

        test1.integrate (landingAcceleration, landingAnalyzer1, 0, 700, h0, v0,m0, u ,g,k); // wywołanie metody



        landingAnalyzer1.saveToFile ("C:\\Users\\gendasai\\Desktop\\LandingChartsData.txt"); //używane do wyrysowania wykresu w MatLabie


        System.out.println (landingAnalyzer1.calculateLandingTime ());*/


    }


}
