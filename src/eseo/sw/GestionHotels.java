package eseo.sw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
				Chambre chambre = new Chambre(result.getInt("idChambre"),
				result.getString("typeChambre"),
				result.getInt("nombrePlaceLit"),
				result.getInt("prixJournalier"),
				result.getInt("etage"));
				chambre.ecrire();
				chambres[i] = chambre;
				i++;
			}
			result.close();
			stmt.close();
			connexion.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chambres;
	}
	
	public int reserverChambre(ReservationChambre reservationChambre) {
		return 0;
	}
	
	public String payerChambre(int idReservation) {
		String message = "";
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			String url = "jdbc:mysql://localhost/Hotel?user=jeanseb&password=network";
			connexion = DriverManager.getConnection(url);
			Statement stmt = connexion.createStatement();
			stmt.executeQuery("select booleenPaiementEffectue from RESERVATION where idReservation = '"+idReservation+"'");
			ResultSet result = stmt.getResultSet();
			result.next();
			if(result.getInt("booleenPaiementEffectue")==1) {
				message = "La chambre a déja été payé !";
			} else {
				stmt.executeUpdate("update RESERVATION set booleenPaiementEffectue=1 where idReservation='"+idReservation+"'");
				message = "La chambre a bien été payé !";
			}
			result.close();
			stmt.close();
			connexion.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return message;
	}
	
	public boolean annulerChambre(int idReservation) {
		boolean feedback = false;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			String url = "jdbc:mysql://localhost/Hotel?user=jeanseb&password=network";
			connexion = DriverManager.getConnection(url);
			Statement stmt = connexion.createStatement();
			int suppression = stmt.executeUpdate("delete from RESERVATION where idReservation='"+idReservation+"'");
			if(suppression == 1) {
				System.out.println("La réservation a bien été annulé !");
				feedback = true;
			} else {
				System.out.println("La réservation n'a pas été trouvée !");
				feedback = false;
			}
			stmt.close();
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return feedback;
	}
	
}
	
