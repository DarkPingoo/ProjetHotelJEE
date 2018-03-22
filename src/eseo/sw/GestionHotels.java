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

	private void initConnection() {
		try {
			this.connexion 	= Database.getInstance().createConnection();
			this.stmt 		= this.connexion.createStatement();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public Chambre[] trouverChambre(String typeChambre) {
		initConnection();

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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection();
		return chambres;
	}

	public int reserverChambre(ReservationChambre reservationChambre) {
		initConnection();
		ReservationChambre[] reservations = new ReservationChambre[50];
		int i = 0;
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeConnection();
		return reservationChambre.getIdChambre();
	}

	public String payerChambre(int idReservation) {
		initConnection();

		String message = "erreur";
		try {
			stmt.executeQuery("select booleenPaiementEffectue from RESERVATION where idReservation = '"+idReservation+"'");
			result = stmt.getResultSet();
			result.next();
			if(result.getInt("booleenPaiementEffectue")==1) {
				message = "oui";
			} else {
				stmt.executeUpdate("update RESERVATION set booleenPaiementEffectue=1 where idReservation='"+idReservation+"'");
				message = "non";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection();
		return message;
	}

	public boolean annulerChambre(int idReservation) {
		initConnection();

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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection();
		return feedback;
	}

}
	
