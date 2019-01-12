package interfaces;

/**
 * interfejs , ktory zawiera metode, ktora zaimplementowana w klasie bedzie wyliczala przyspieszenie
 */
public interface CalculateAcceleration {

    double a(double u,double m,double g,double k);

    //a=−g−k*u(t)/m(t)
//ut wprowadza użytkownik

}
