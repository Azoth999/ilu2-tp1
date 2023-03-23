package histoire;


import villagegaulois.*;
import villagegaulois.Village.VillageSansChefException;
import personnages.*;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		Village village = new Village("ElVillage", 10, 10);
		
		//Test de la méthode afficherVillageois avec de paramètres non valides
		try {
			String villageois = village.afficherVillageois();
			System.out.println(villageois);
		} catch (VillageSansChefException e) {
			System.err.println("Erreur : " + e.getMessage());
		}
		
		// Test de la méthode acheterProduit avec des paramètres non valides
		Gaulois acheteur = null;
		try {
			etal.acheterProduit(0, acheteur);
		} catch (IllegalArgumentException e) {
			System.err.println("Erreur : " + e.getMessage());
		}

		try {
			etal.acheterProduit(-1, new Gaulois("Obélix", 5));
		} catch (IllegalArgumentException e) {
			System.err.println("Erreur : " + e.getMessage());
		}

		try {
			etal.acheterProduit(1, new Gaulois("Obélix", 5));
		} catch (IllegalStateException e) {
			System.err.println("Erreur : " + e.getMessage());
		}

		System.out.println("Fin du test");
	}

}
