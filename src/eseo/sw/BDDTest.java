package eseo.sw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BDDTest {

	
	public static void main(String[] args) throws ParseException {
		String s  = "2018-10-15";
		String s2 = "2018-10-20";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date date1 = sdf.parse(s);
		Date date2 = sdf.parse(s2);
		
		GestionHotels hotels = new GestionHotels();
		hotels.trouverChambre("Taudis");
		ReservationChambre newReservation = new ReservationChambre(4,4,3,date1, date2,5,false);
		hotels.reserverChambre(newReservation);
	}
}
