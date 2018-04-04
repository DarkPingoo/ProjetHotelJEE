package eseo.sw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.jws.WebService;


@WebService(targetNamespace = "http://sw.eseo/", endpointInterface = "eseo.sw.GestionHotelsSEI", portName = "GestionHotelsPort", serviceName = "GestionHotelsService")
public class GestionHotels implements GestionHotelsSEI {

	private Connection connexion = null;
	private Statement stmt = null;
	private ResultSet result;
	
	public void closeConnection() {
		try {
			result.close();
			stmt.close();
			connexion.close();
		} catch (SQLException e) {
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

	public ArrayList<Chambre> trouverChambre(Chambre chambreC) {
		initConnection();
		ArrayList<Chambre> chambres = new ArrayList<Chambre>();
		System.out.println("TEST");

		try {
			stmt.executeQuery("select * from CHAMBRE where typeChambre = '"+chambreC.getTypeChambre() +"' AND nombrePlaceLit >= " + chambreC.getNbLits() + " ORDER BY nombrePlaceLit ASC");
			result = stmt.getResultSet();
			while (result.next()) {
				System.out.println("Result");

				Chambre chambre = new Chambre(result.getInt("idChambre"),
						result.getString("typeChambre"),
						result.getInt("nombrePlaceLit"),
						result.getInt("prixJournalier"),
						result.getInt("etage"));
				chambre.ecrire();
				chambres.add(chambre);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(chambres.size() == 0) {
			System.out.println("Aucune chambre de ce type n'a été trouvée !");
		}
		closeConnection();
		return chambres;
	}

	/**
	 * Reserve une chambre
	 * @param reservationChambre reservation
	 * @return Identifiant de reservation si >0, sinon code d'erreur
	 * -1 : Incohérence de date
	 * -2 : Chambre inexistante
	 * -3 : Chambre déjà réservée
	 */
	public int reserverChambre(ReservationChambre reservationChambre){
		initConnection();
		Chambre chambre = null;
		int idReservation = -100;
		ArrayList<ReservationChambre> reservations = new ArrayList<ReservationChambre>();
		if(reservationChambre.getDateDebut().after(reservationChambre.getDateFin())) {
			System.out.println("Reservation impossible, il y a une incohérence dans la date !");
			return -1;
		}
		try {
			stmt.executeQuery("select * from CHAMBRE where idChambre = "+ reservationChambre.getIdChambre());
			result = stmt.getResultSet();
			while (result.next()) {
				chambre = new Chambre(result.getInt("idChambre"),
						result.getString("typeChambre"),
						result.getInt("nombrePlaceLit"),
						result.getInt("prixJournalier"),
						result.getInt("etage"));
			}
			if(chambre == null) {
				System.out.println("La chambre n'existe pas");
				return -2;
			} else {
				System.out.println("la chambre existe !");
				stmt.executeQuery("select * from RESERVATION where idChambre="+reservationChambre.getIdChambre());
				result = stmt.getResultSet();
				while(result.next()) {
					ReservationChambre reservation = new ReservationChambre(result.getInt("idReservation"),
																			result.getInt("idChambre"),
																			result.getInt("idClient"),
																			result.getDate("dateDebut"),
																			result.getDate("dateFin"),
																			result.getInt("nombrePlaces"),
																			result.getBoolean("booleenPaiementEffectue"));
					reservations.add(reservation);
				}
			}
			boolean reserver = false;
			for(int y = 0; y < reservations.size(); y++) {
				if (reservations.get(y).getDateDebut().after(reservationChambre.getDateFin()) || 
						reservations.get(y).getDateFin().before(reservationChambre.getDateDebut())) {
				}
				else {
					System.out.println("la chambre "+chambre.getIdChambre()+" n'est pas disponible pour ces dates !");
					reserver =true;
				}
			}
			if (reserver == false) {
				stmt.executeUpdate("INSERT INTO RESERVATION(idChambre,idClient,dateDebut,dateFin,nombrePlaces,booleenPaiementEffectue) "
						+ "VALUES ("+ reservationChambre.getIdChambre() + "," + reservationChambre.getIdClient() + ",'" + ReservationChambre.dateToString(reservationChambre.getDateDebut()) + "','"
						+ ReservationChambre.dateToString(reservationChambre.getDateFin()) + "'," + reservationChambre.getNbPlaces() + "," + reservationChambre.getPaiementEffectue()+")",
                        Statement.RETURN_GENERATED_KEYS);
				System.out.println("bravo la réservation à bien été enregistré.");
				ResultSet rs = stmt.getGeneratedKeys();
				if(rs.next()) {
					idReservation = rs.getBigDecimal(1).intValue();
				}
			}
			else {
				System.out.println("la réservation n'a pas pu etre effectuée !");
				return -3;
			}	
		
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		closeConnection();
		return idReservation;
	}
	
	private boolean reservationExiste(int idReservation) {
		initConnection();
		ArrayList<Integer> idReservations = new ArrayList<Integer>();
		boolean idExiste = false;
		try {
			stmt.executeQuery("select idReservation from RESERVATION");
			result = stmt.getResultSet();
			while(result.next()) {
				idReservations.add(result.getInt("idReservation"));
			}
			for(int i=0; i<idReservations.size();i++) {
				if(idReservations.get(i) == idReservation) {
					idExiste = true;
					System.out.println("La réservation a été trouvée !");
				}
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return idExiste;
	}


	public String payerChambre(int idReservation) {
		initConnection();
		String message = "";
		try {
			
			if(reservationExiste(idReservation)) {
				stmt.executeQuery("select booleenPaiementEffectue from RESERVATION where idReservation = '"+idReservation+"'");
				result = stmt.getResultSet();
				result.next();
				if(result.getInt("booleenPaiementEffectue")==1) {
					System.out.println("La chambre a déja été payée !");
					message = "non";
				} else {
					stmt.executeUpdate("update RESERVATION set booleenPaiementEffectue=1 where idReservation='"+idReservation+"'");
					System.out.println("La chambre a bien été payée !");
					message = "oui";
				}
			} else {
				System.out.println("La réservation n'a pas été trouvée !");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection();
		return message;
	}

	public boolean annulerChambre(int idReservation) {
		initConnection();
		Date actuelle = new Date();
		boolean feedback = false;
		try {
			if(reservationExiste(idReservation)) {
				stmt.executeQuery("select dateDebut, dateFin from RESERVATION where idReservation="+idReservation);
				result = stmt.getResultSet();
				result.next();
				if(result.getDate("dateDebut").before(actuelle) && result.getDate("dateFin").after(actuelle)) {
					System.out.println("Impossble d'annuler la réservation !");
				} else {
					stmt.executeUpdate("delete from RESERVATION where idReservation='"+idReservation+"'");
					System.out.println("La réservation a bien été annulée ");
					feedback = true;
				}
			} else {
				System.out.println("la réservation n'existe pas !");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection();
		return feedback;
	}

}
	
