package interfaces;

/**
 * interfejs ,  ktory zawiera metode, ktora zaimplementowana w klasie bedzie dodawala wartosci t,x,v do tablic
 */
public interface ODEUpdate {

    void update(double t, double h, double v,double m);

}
