package eseo.sw;

import javax.jws.WebService;

@WebService(name = "GestionHotelsSEI", targetNamespace = "http://sw.eseo/")
public interface GestionHotelsSEI {

	Chambre[] trouverChambre(String typeChambre);

	int reserverChambre(ReservationChambre reservationChambre);

	int payerChambre(int x, int y);

	boolean annulerChambre(int idReservation);

}