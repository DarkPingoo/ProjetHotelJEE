package eseo.sw;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

public class GestionHotelsTest {

	
	private static GestionHotels hotels;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		hotels = new GestionHotels();
	}

	@Test
	public void testTrouverChambre1() {
		ArrayList<Chambre> chambres = new ArrayList<Chambre>();
		Chambre chambre1 = new Chambre(3,"Affaire",2,80,3);
		chambres = hotels.trouverChambre(chambre1);
		assertEquals("Bonne id Chambre : ",3, chambres.get(0).getIdChambre());
		assertEquals("Bon type Chambre : ", "Affaire",chambres.get(0).getTypeChambre());
		assertEquals("Bon nombre place lits :",2, chambres.get(0).getNbLits());
		assertEquals("Bon prix :",80, chambres.get(0).getPrix());
		assertEquals("Bon étage :",3, chambres.get(0).getEtage());
	}
	
	@Test
	public void testTrouverChambre2() {
		ArrayList<Chambre> chambres = new ArrayList<Chambre>();
		Chambre chambre2 = new Chambre(4,"FAMILIALE",3,79,1);
		chambres = hotels.trouverChambre(chambre2);
		assertEquals("Bonne id Chambre : ",4, chambres.get(0).getIdChambre());
		assertEquals("Bon type Chambre : ", "Familiale",chambres.get(0).getTypeChambre());
		assertEquals("Bon nombre place lits :",3, chambres.get(0).getNbLits());
		assertEquals("Bon prix :",79, chambres.get(0).getPrix());
		assertEquals("Bon étage :",1, chambres.get(0).getEtage());
	}
	
	@Test
	public void testTrouverChambre3() {
		ArrayList<Chambre> chambres = new ArrayList<Chambre>();
		Chambre chambre3 = new Chambre(1,"Taudis",1,20,2);
		chambres = hotels.trouverChambre(chambre3);
		assertEquals("Bonne nombre de chambres : ",2, chambres.size());
	}
	
	@Test
	public void testTrouverChambre4() {
		ArrayList<Chambre> chambres = new ArrayList<Chambre>();
		Chambre chambre2 = new Chambre(6,"unknow",3,79,1);
		chambres = hotels.trouverChambre(chambre2);
		assertEquals("La chambre n'existe pas :",new ArrayList<Chambre>(), chambres);
	}
	
	@Test
	public void testConstructeurReserverChambre() throws ParseException {
		String s  = "2018-10-10";
		String s2 = "2018-10-20";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(s);
		Date date2 = sdf.parse(s2);
		ReservationChambre newReservation = new ReservationChambre(4,3,3,date1, date2,5,false);
		assertEquals("bon id de réservation",newReservation.getIdReservation(),4);
		assertEquals("bon id de la chambre",newReservation.getIdChambre(),3);
		assertEquals("bon id de client",newReservation.getIdClient(),3);
		assertEquals("bon ne date de début",newReservation.dateToString(newReservation.getDateDebut()),"2018-10-10");
		assertEquals("bon ne date de fin",newReservation.dateToString(newReservation.getDateFin()),"2018-10-20");
		assertEquals("bon nombre de lit disponnible pour la chambre",newReservation.getNbPlaces(),5);
		assertEquals("bon id de réservation",newReservation.getPaiementEffectue(),false);

	}
	@Test
	public void testConstructeurReserverChambre2() throws ParseException {
		String s  = "2018-10-15";
		String s2 = "2018-10-22";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(s);
		Date date2 = sdf.parse(s2);
		ReservationChambre newReservation = new ReservationChambre(-1,3,3,date1, date2,5,false);
		assertEquals("bon id de réservation",newReservation.getIdReservation(),-1);
		assertEquals("bon id de la chambre",newReservation.getIdChambre(),3);
		assertEquals("bon id de client",newReservation.getIdClient(),3);
		assertEquals("bon ne date de début",newReservation.dateToString(newReservation.getDateDebut()),"2018-10-15");
		assertEquals("bon ne date de fin",newReservation.dateToString(newReservation.getDateFin()),"2018-10-22");
		assertEquals("bon nombre de lit disponnible pour la chambre",newReservation.getNbPlaces(),5);
		assertEquals("bon id de réservation",newReservation.getPaiementEffectue(),false);

	}
	
	
	
	
	@Test
	public void testReserverChambre1() throws ParseException {
		ReservationChambre reservation = new ReservationChambre(1,1,2,ReservationChambre.stringToDate("2018-11-03"),ReservationChambre.stringToDate("2018-11-07"),2,false);
		int reserverChambre = hotels.reserverChambre(reservation);
		assertEquals("La chambre n'est pas disponible :",-3, reserverChambre);
	}
	
	@Test
	public void testReserverChambre2() throws ParseException {
		ReservationChambre reservation = new ReservationChambre(1,45,2,ReservationChambre.stringToDate("2018-11-03"),ReservationChambre.stringToDate("2018-11-07"),2,false);
		int reserverChambre = hotels.reserverChambre(reservation);
		assertEquals("La chambre n'existe pas :",-2, reserverChambre);
	}
	
	@Test
	public void testReserverChambre3() throws ParseException {
		ReservationChambre reservation = new ReservationChambre(1,1,2,ReservationChambre.stringToDate("2018-01-07"),ReservationChambre.stringToDate("2018-01-03"),2,false);
		int reserverChambre = hotels.reserverChambre(reservation);
		assertEquals("Incohérence dans la date :",-1, reserverChambre);
	}
	

	@Test
	public void testPayerChambre1() {
		String payer = hotels.payerChambre(1);
		assertEquals("La chambre est déja payé :","non", payer);
	}
	
	@Test
	public void testPayerChambre2() {
		String payer = hotels.payerChambre(22);
		assertEquals("La chambre n'existe pas :",null, payer);
	}
	
	@Test
	public void testPayerChambre3(){
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection connexion = Database.getInstance().createConnection();
			Statement stmt = connexion.createStatement();
			stmt.executeUpdate("update RESERVATION set booleenPaiementEffectue=0 where idReservation=2");
			String payer = hotels.payerChambre(2);
			assertEquals("La chambre est a bien été payée :","oui",payer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAnnulerChambre1() {
		boolean feedback = hotels.annulerChambre(233);
		assertFalse("la réservation n'existe pas",feedback);
	}
	
	@Test
	public void testAnnulerChambre2() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection connexion = Database.getInstance().createConnection();
			Statement stmt = connexion.createStatement();
			stmt.executeQuery("INSERT INTO RESERVATION VALUES(1,1,2018-06-03,2018-06-05,2,0");
			ResultSet result = stmt.getResultSet();
			result.next();
			int idReservation = result.getInt("idReservation");
			boolean feedback = hotels.annulerChambre(idReservation);
			assertTrue("la chambre a bien été annulé",feedback);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAnnulerChambre3() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection connexion = Database.getInstance().createConnection();
			Statement stmt = connexion.createStatement();
			stmt.executeUpdate("INSERT INTO RESERVATION VALUES(1,1,2018-01-01,2018-07-05,2,0");
			ResultSet result = stmt.getResultSet();
			result.next();
			int idReservation = result.getInt("idReservation");
			boolean feedback = hotels.annulerChambre(idReservation);
			assertFalse("La chambre ne peut pas etre réservé :",feedback);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}