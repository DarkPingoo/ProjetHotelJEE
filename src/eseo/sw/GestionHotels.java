package eseo.sw;

import javax.jws.WebService;

@WebService(targetNamespace = "http://sw.eseo/", endpointInterface = "eseo.sw.GestionHotelsSEI", portName = "GestionHotelsPort", serviceName = "GestionHotelsService")
public class GestionHotels implements GestionHotelsSEI{

	public Chambre[] trouverChambre(Chambre chambre) {
		return null;
	}
	
	public int reserverChambre(ReservationChambre reservationChambre) {
		return 0;
	}
	
	public String payerChambre(int idReservation) {
		return "";
	}
	
	public boolean annulerChambre(int idReservation) {
		return false;
	}
	
	
}
