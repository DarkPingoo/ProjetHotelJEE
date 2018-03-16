package eseo.sw;

import java.util.Date;

public class ReservationChambre {

	private int idReservation;
	private int idChambre;
	private int idClient;
	private Date dateDebut;
	private Date dateFin;
	private int nbPlaces;
	private boolean paiementEffectue;

	public ReservationChambre(int idReservation, int idChambre, int idClient,Date dateDebut, Date dateFin, boolean paiementEffectue, int nbPlaces) {
		this.idReservation = idReservation;
		this.idChambre = idChambre;
		this.idClient = idClient;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.paiementEffectue = paiementEffectue;
		this.nbPlaces = nbPlaces;
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
}