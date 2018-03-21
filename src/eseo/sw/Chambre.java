package eseo.sw;

public class Chambre {
	
	private int idChambre;
	private String typeChambre;
	private int prix;
	private int nbLits;
	private int etage;

	public Chambre(int idChambre, String typeChambre, int prix, int nbLits, int etage) {
		this.idChambre = idChambre;
		this.typeChambre = typeChambre;
		this.prix = prix;
		this.nbLits = nbLits;
		this.etage = etage;
	}
	
	public int getEtage() {
		return etage;
	}

	public void setEtage(int etage) {
		this.etage = etage;
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

	public void ecrire() {
		System.out.println("Chambre("+this.getIdChambre()+","+
							this.getTypeChambre()+","+
							this.getNbLits()+","+
							this.getPrix()+","+
							this.getEtage()+")");
	}
}

