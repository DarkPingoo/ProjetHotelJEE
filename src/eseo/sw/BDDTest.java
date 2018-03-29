package eseo.sw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BDDTest {

	
	public static void main(String[] args) throws ParseException {
		String s  = "2018-10-19";
		String s2 = "2018-10-25";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(s);
		Date date2 = sdf.parse(s2);
		
		GestionHotels hotels = new GestionHotels();
		//ReservationChambre newReservation = new ReservationChambre(4,2,3,date2, date1,5,false);
		//hotels.reserverChambre(newReservation);
	}
}
