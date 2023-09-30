package jeu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe Chargement permet de charger un jeu à partir d'un fichier texte
 * conformément à certaines règles et de créer un objet Jeu correspondant.
 */
public class Chargement {

  /**
   * Charge un jeu à partir d'un fichier texte spécifié.
   *
   * @param nomFichier Le nom du fichier contenant le jeu.
   * @return Un objet Jeu représentant le jeu chargé.
   * @throws Exception Si une erreur survient lors du chargement du jeu.
   */
  public static Jeu chargerJeu(String nomFichier) throws Exception {
    // Lecture du fichier
    List<String> fichierLu = lireFichier(nomFichier);

    // Calcul des dimensions du labyrinthe
    int hauteur = fichierLu.size();
    int largeur = calculerLargeur(fichierLu);

    // Initialisation des variables du jeu
    Perso perso = null;
    ListeElements caisses = new ListeElements();
    ListeElements depots = new ListeElements();
    Labyrinthe laby = new Labyrinthe(largeur, hauteur);

    int nombresDeCaisses = 0;
    int nombresDeDepots = 0;
    boolean persoTrouve = false;

    // Création du jeu en parcourant les lignes du fichier
    for (int i = 0; i < hauteur; i++) {
      String ligne = fichierLu.get(i);
      for (int j = 0; j < ligne.length(); j++) {
        char caractereCourant = ligne.charAt(j);

        switch (caractereCourant) {
          case Labyrinthe.PJ:
            if (persoTrouve) {
              throw new FichierIncorrectException("Il y a plus qu'un personnage dans le fichier");
            }
            persoTrouve = true;
            perso = new Perso(j, i);
            break;
          case Labyrinthe.DEPOT:
            nombresDeDepots++;
            depots.getElements().add(new Depot(j, i));
            break;
          case Labyrinthe.CAISSE:
            nombresDeCaisses++;
            caisses.getElements().add(new Caisse(j, i));
            break;
          case Labyrinthe.MUR:
            laby.setMurs(j, i);
            break;
          case Labyrinthe.VIDE:
            break;
          default:
            throw new FichierIncorrectException("Caractere inconnu : " + caractereCourant);
        }
      }
    }

    // Vérification du contenu du jeu
    verifierContenuJeu(perso, persoTrouve, nombresDeCaisses, nombresDeDepots);

    // Création de l'objet Jeu
    return new Jeu(perso, caisses, depots, laby);
  }

  /**
   * Calcule la largeur maximale parmi toutes les lignes du fichier.
   *
   * @param lignes La liste de chaînes représentant les lignes du fichier.
   * @return La largeur maximale.
   */
  private static int calculerLargeur(List<String> lignes) {
    int largeur = 0;
    for (String ligne : lignes) {
      if (largeur < ligne.length()) {
        largeur = ligne.length();
      }
    }
    return largeur;
  }

  /**
   * Vérifie le contenu du jeu, y compris la présence du personnage, des caisses
   * et des dépôts, ainsi que la correspondance entre le nombre de caisses et de dépôts.
   *
   * @param perso            L'objet Perso représentant le personnage.
   * @param persoTrouve      Un indicateur de la présence du personnage.
   * @param nombresDeCaisses Le nombre de caisses dans le jeu.
   * @param nombresDeDepots  Le nombre de dépôts dans le jeu.
   * @throws FichierIncorrectException Si le contenu du jeu est incorrect.
   */
  private static void verifierContenuJeu(Perso perso, boolean persoTrouve, int nombresDeCaisses, int nombresDeDepots)
          throws FichierIncorrectException {
    // Vérification du contenu du jeu
    if (!persoTrouve) {
      throw new FichierIncorrectException("Personnage inconnu");
    }
    if (nombresDeCaisses == 0) {
      throw new FichierIncorrectException("Caisses inconnues");
    }
    if (nombresDeCaisses != nombresDeDepots) {
      throw new FichierIncorrectException("Caisses (" + nombresDeCaisses + ") Depots (" + nombresDeDepots + ")");
    }
  }



  /**
   * Lit un fichier texte et retourne son contenu sous forme d'une liste de chaînes.
   *
   * @param nomFichier Le nom du fichier à lire.
   * @return Une liste de chaînes représentant les lignes du fichier.
   * @throws IOException Si une erreur survient lors de la lecture du fichier.
   */
  private static List<String> lireFichier(String nomFichier) throws IOException {
    // Lecture du fichier et stockage dans une liste de chaînes
    List<String> fichierLu = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
      String ligne;
      while ((ligne = br.readLine()) != null) {
        fichierLu.add(ligne);
      }
    }
    return fichierLu;
  }
}
