package eseo.sw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.jws.WebService;

@WebService(targetNamespace = "http://sw.eseo/", endpointInterface = "eseo.sw.GestionHotelsSEI", portName = "GestionHotelsPort", serviceName = "GestionHotelsService")
public class GestionHotels implements GestionHotelsSEI{
	
	private Connection connexion = null;
	
	public Chambre[] trouverChambre(String typeChambre) {
		Chambre[] chambres = new Chambre[50];
		int i = 0;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			String url = "jdbc:mysql://localhost/Hotel?user=jeanseb&password=network";
			connexion = DriverManager.getConnection(url);
			Statement stmt = connexion.createStatement();
			stmt.executeQuery("select * from CHAMBRE where typeChambre = '"+typeChambre +"'");
			ResultSet result = stmt.getResultSet();
			while (result.next()) {
				Chambre chambre = new Chambre(Integer.parseInt(result.getString("idChambre")),
				result.getString("typeChambre"),
				Integer.parseInt(result.getString("nombrePlaceLit")),
				Integer.parseInt(result.getString("prixJournalier")),
				Integer.parseInt(result.getString("etage")));
				chambre.ecrire();
				chambres[i] = chambre;
				i++;
			}
			result.close();
			stmt.close();
			connexion.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chambres;
	}
	
	public int reserverChambre(ReservationChambre reservationChambre) {
		return 0;
	}
	
	public int payerChambre(int x, int y) {
		return x+y;
	}
	
	public boolean annulerChambre(int idReservation) {
		return false;
	}
	
}
	
