package eseo.sw;

import javax.jws.WebService;

@WebService(name = "GestionHotelsSEI", targetNamespace = "http://sw.eseo/")
public interface GestionHotelsSEI {

	Chambre[] trouverChambre(Chambre chambre);

	int reserverChambre(ReservationChambre reservationChambre);

	String payerChambre(int idReservation);

	boolean annulerChambre(int idReservation);

}