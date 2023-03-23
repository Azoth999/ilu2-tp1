package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	private static class Marche {
		private Etal[] etals;

		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		public int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() == false) {
					return i;
				}
			}
			return -1;
		}

		public Etal[] trouverEtals(String produit) {
			int nbrEtals = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbrEtals++;
				}
			}
			Etal[] etalsProd = new Etal[nbrEtals];
			int i = 0;
			for (int j = 0; j < nbrEtals; j++) {
				System.out.println(etalsProd[j]);
				if (etals[j].contientProduit(produit) && etals[j].isEtalOccupe()) {
					etalsProd[i] = etals[j];
					i++;
				}
			}
			System.out.println(etalsProd[0]);
			System.out.println(etalsProd[1]);
			return etalsProd;
		}

		public Etal trouverVendeur(Gaulois gaulois) {
			for (Etal etal : etals) {
				if (etal.getVendeur().equals(gaulois)) {
					return etal;
				}
			}
			return null;
		}

		public String afficherMarche() {
			String texte = "";
			int nbEtalVide = 0;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe()) {
					texte += etal.afficherEtal() + "\n";
				} else {
					nbEtalVide++;
				}
			}
			texte += "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n";
			return texte;
		}

	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}
	
	@SuppressWarnings("serial")
	public class VillageSansChefException extends Exception {
	    public VillageSansChefException(String message) {
	        super(message);
	    }
	}
	
	public String afficherVillageois() throws VillageSansChefException {
		
	    if (chef == null) {
	        throw new VillageSansChefException("Le village n'a pas de chef !");
	    }
	    StringBuilder chaine = new StringBuilder();
	    if (nbVillageois < 1) {
	        chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
	    } else {
	        chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
	        for (int i = 0; i < nbVillageois; i++) {
	            chaine.append("- " + villageois[i].getNom() + "\n");
	        }
	    }
	    return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + "\n");
		int numEtal = marche.trouverEtalLibre();
		if (numEtal == -1) {
			chaine.append("Le vendeur " + vendeur.getNom() + " n'a pas pu trouver d'étal de libre.\n");
		}
		else {
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + numEtal);
			marche.utiliserEtal(numEtal, vendeur, produit, nbProduit);
		}
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsVendeursProduit = marche.trouverEtals(produit);
		if(etalsVendeursProduit.length==0) {
			chaine.append("Aucun vendeurs ne vend de " + produit + ".\n");
			return chaine.toString();
		}
		chaine.append("Les vendeurs qui proposent des " + produit + " sont : \n");
		for (Etal etal : etalsVendeursProduit) {
			if (etal != null && etal.isEtalOccupe()) {
				chaine.append("   - " + etal.getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etalVendeur=rechercherEtal(vendeur);
		chaine.append(etalVendeur.libererEtal());
		return chaine.toString();
		
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		int etalsOccupes = marche.etals.length;
		if (etalsOccupes==0) {
			chaine.append("Il n'y a aucun vendeur au village " + getNom()+".\n");
			return chaine.toString();
		}
		chaine.append("Le marché du village "+ getNom() + " possède plusieurs étals : \n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();

	}

}