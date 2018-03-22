package eseo.sw;

public class BDDTest {

	public static void main(String[] args) {
		
		GestionHotels hotels = new GestionHotels();
		hotels.init();
		hotels.trouverChambre("Taudis");
		System.out.println(hotels.payerChambre(4));
		hotels.annulerChambre(6);
		hotels.closeConnection();
	} 
}
