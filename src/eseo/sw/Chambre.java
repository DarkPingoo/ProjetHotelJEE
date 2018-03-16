package eseo.sw;

public class Chambre {

	private int idChambre;
	private String typeChambre;
	private int prix;
	private int nbLits;

	public Chambre(int idChambre, String typeChambre, int prix, int nbLits) {
		this.idChambre = idChambre;
		this.typeChambre = typeChambre;
		this.prix = prix;
		this.nbLits = nbLits;
	}
	
	public int getIdChambre() {
		return this.idChambre;
	}
	
	public String getTypeChambre() {
		return this.typeChambre;
	}
	
	public int getPrix() {
		return this.prix;
	}
	
	public int getNbLits() {
		return this.nbLits;
	}
	
	public void setIdChambre(int idChambre) {
		this.idChambre = idChambre;
	}
	
	public void setTypeChambre(String typeChambre) {
		this.typeChambre = typeChambre;
	}
	
	public void setPrix(int prix) {
		this.prix = prix;
	}
	
	public void setNbLits(int nbLits) {
		this.nbLits = nbLits;
	}
}
