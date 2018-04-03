package eseo.sw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationChambre {

	private int idReservation;
	private int idChambre;
	private int idClient;
	private Date dateDebut;
	private Date dateFin;
	private int nbPlaces;
	private boolean paiementEffectue;

	public ReservationChambre(int idReservation, int idChambre, int idClient,Date dateDebut, Date dateFin,int nbPlaces, boolean paiementEffectue) {
		this.idReservation = idReservation;
		this.idChambre = idChambre;
		this.idClient = idClient;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.paiementEffectue = paiementEffectue;
		this.nbPlaces = nbPlaces;
	}
	
	public ReservationChambre() {
		super();
	}
	
	public int getIdChambre() {
		return this.idChambre;
	}
	
	public int getIdClient() {
		return this.idClient;
	}
	
	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public boolean getPaiementEffectue() {
		return this.paiementEffectue;
	}
	
	public int getNbPlaces() {
		return this.nbPlaces;
	}
	
	public void setIdChambre(int idChambre) {
		this.idChambre = idChambre;
	}
	
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	
	public void setPaiementEffectue(boolean paiementEffectue) {
		this.paiementEffectue = paiementEffectue;
	}
	
	public int getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}

	public void setNbPlaces(int nbPlaces) {
		this.nbPlaces = nbPlaces;
	}
	public String ecrire() {
		return ("Reservation("+this.getIdReservation()+","+
							this.getIdChambre()+","+
							this.getIdClient()+","+
							this.getDateDebut()+","+
							this.getDateFin()+","+
							this.getNbPlaces()+","+
							this.getPaiementEffectue()+")");
	}
	
	public static String dateToString(Date date) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	public static Date stringToDate(String string) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(string);
		return date1;
	}
}