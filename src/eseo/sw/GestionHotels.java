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
	private Statement stmt = null;
	private ResultSet result;
	
	public void init() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			String url = "jdbc:mysql://localhost/Hotel?user=quentin&password=network";
			connexion = DriverManager.getConnection(url);
			stmt = connexion.createStatement();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			result.close();
			stmt.close();
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Chambre[] trouverChambre(String typeChambre) {
		Chambre[] chambres = new Chambre[50];
		int i = 0;
		try {
			stmt.executeQuery("select * from CHAMBRE where typeChambre = '"+typeChambre +"'");
			result = stmt.getResultSet();
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
			/*result.close();
			stmt.close();
			connexion.close();*/
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chambres;
	}
	
	public int reserverChambre(ReservationChambre reservationChambre) {
		ReservationChambre[] reservations = new ReservationChambre[50];
		int i = 0;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			String url = "jdbc:mysql://localhost/Hotel?user=quentin&password=network";
			connexion = DriverManager.getConnection(url);
			Statement stmt = connexion.createStatement();
			stmt.executeQuery("select * from RESERVATION");
			ResultSet result = stmt.getResultSet();
			while (result.next()) {
				ReservationChambre reservation = new ReservationChambre(result.getInt("idReservation"),
																		result.getInt("idChambre"),
																		result.getInt("idClient"),
																		result.getDate("dateDebut"),
																		result.getDate("dateFin"),
																		result.getInt("nombrePlaces"),
																		result.getBoolean("booleenPaiementEffectue"));
				reservations[i] = reservation;
				i++;
				reservation.ecrire();
				
			}
			reservationChambre.ecrire();
			for(int y = 0; y < reservations.length; y++) {
				if(reservations[y].getIdChambre() !=(reservationChambre.getIdChambre())) {
					if (reservations[y].getDateDebut().before(reservationChambre.getDateFin()) && 
						reservations[y].getDateFin().after(reservationChambre.getDateDebut())) {
						stmt.executeQuery("INSERT INTO RESERVATION(idChambre,idClient,dateDebut,dateFin,nombrePlaces,booleenPaiementEffectue) "
								+ "VALUES ("+ reservationChambre.getIdChambre() + "," + reservationChambre.getIdClient() + "," + ReservationChambre.dateToString(reservationChambre.getDateDebut()) + ","
								+ ReservationChambre.dateToString(reservationChambre.getDateFin()) + "," + reservationChambre.getNbPlaces() + "," + reservationChambre.getPaiementEffectue());
					}
					else {
						System.out.println("Cette chambre n'est pas disponible pour cette date!");
					}
				}
				else {
					System.out.println("Cette chambre n'est pas disponible");
				}
			}
			result.close();
			stmt.close();
			connexion.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reservationChambre.getIdChambre();
	}
	
	public String payerChambre(int idReservation) {
		String message = "";
		try {
			stmt.executeQuery("select booleenPaiementEffectue from RESERVATION where idReservation = '"+idReservation+"'");
			result = stmt.getResultSet();
			result.next();
			if(result.getInt("booleenPaiementEffectue")==1) {
				message = "La chambre a déja été payé !";
			} else {
				stmt.executeUpdate("update RESERVATION set booleenPaiementEffectue=1 where idReservation='"+idReservation+"'");
				message = "La chambre a bien été payé !";
			}
			/*result.close();
			stmt.close();
			connexion.close();*/
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return message;
	}
	
	public boolean annulerChambre(int idReservation) {
		boolean feedback = false;
		try {
			int suppression = stmt.executeUpdate("delete from RESERVATION where idReservation='"+idReservation+"'");
			if(suppression == 1) {
				System.out.println("La réservation a bien été annulé !");
				feedback = true;
			} else {
				System.out.println("La réservation n'a pas été trouvée !");
				feedback = false;
			}
			/*stmt.close();
			connexion.close();*/
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return feedback;
	}
	
}
	
