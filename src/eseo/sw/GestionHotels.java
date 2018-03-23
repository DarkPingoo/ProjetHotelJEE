package eseo.sw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		ArrayList<ReservationChambre>  reservations = new ArrayList();
		if(reservationChambre.getDateDebut().after(reservationChambre.getDateFin())) {
			System.out.println("Resevation impossible, il y a une incohérence dans la date !");
			return -1;
		}
		else {
			try {
				Statement stmt = connexion.createStatement();
				stmt.executeQuery("select * from RESERVATION");
				result = stmt.getResultSet();
				while (result.next()) {
					ReservationChambre reservation = new ReservationChambre(result.getInt("idReservation"),
																			result.getInt("idChambre"),
																			result.getInt("idClient"),
																			result.getDate("dateDebut"),
																			result.getDate("dateFin"),
																			result.getInt("nombrePlaces"),
																			result.getBoolean("booleenPaiementEffectue"));
					reservations.add(reservation);
					reservation.ecrire();	
				}
				reservationChambre.ecrire();
				boolean reserver = false;
				for(int y = 0; y < reservations.size(); y++) {
					if(reservations.get(y).getIdChambre() !=(reservationChambre.getIdChambre())) {
						if (reservations.get(y).getDateDebut().after(reservationChambre.getDateFin()) || 
							reservations.get(y).getDateFin().before(reservationChambre.getDateDebut())) {
							System.out.println("c'est okok!");
							}
						else {
							reserver =true;
						}
					}
					else {
						if (reservations.get(y).getDateDebut().after(reservationChambre.getDateFin()) || 
								reservations.get(y).getDateFin().before(reservationChambre.getDateDebut())) {
							System.out.println(reservations.get(y).getDateDebut() +" et "+ reservationChambre.getDateFin());
							System.out.println("c'est okokok");
						}
						else {
							reserver =true;
						}
					}
				}
				if (reserver == false) {
					stmt.executeUpdate("INSERT INTO RESERVATION(idChambre,idClient,dateDebut,dateFin,nombrePlaces,booleenPaiementEffectue) "
							+ "VALUES ("+ reservationChambre.getIdChambre() + "," + reservationChambre.getIdClient() + ",'" + ReservationChambre.dateToString(reservationChambre.getDateDebut()) + "','"
							+ ReservationChambre.dateToString(reservationChambre.getDateFin()) + "'," + reservationChambre.getNbPlaces() + "," + reservationChambre.getPaiementEffectue()+")");
					System.out.println("bravo la réservation à bien été enregistré.");
				}
				else {
					System.out.println("La chambre"+reservationChambre.getIdChambre() +" n'est pas disponible pour la date "+ReservationChambre.dateToString(reservationChambre.getDateDebut())+" à "+ReservationChambre.dateToString(reservationChambre.getDateFin())+" !");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	
