package eseo.sw;

import java.util.ArrayList;

import javax.jws.WebService;

@WebService(name = "GestionHotelsSEI", targetNamespace = "http://sw.eseo/")
public interface GestionHotelsSEI {

	ArrayList<Chambre> trouverChambre(Chambre chambre);

	int reserverChambre(ReservationChambre reservationChambre);

	String payerChambre(int idReservation);

	boolean annulerChambre(int idReservation);

}