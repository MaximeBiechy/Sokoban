import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import jeu.*;

import java.io.IOException;


public class TestJeu {


  // test lorsque l’action est inconnu
  @Test
  public void test_01_actionInconnu() throws Exception {

    // preparation des donnees
    String nomFichier = "laby/laby.txt";
    Jeu jeu = Chargement.chargerJeu(nomFichier);

    try {
      // methode testee
      jeu.deplacerPerso("milieu");

      // verification
    }catch (ActionInconnueException e){
      assertEquals("Action inconnu", e.getMessage());
    }
  }


  // test lorsque l’action est connu et que la case suivante est un mur
  @Test
  public void test_02_caseSuivanteMur() throws Exception {

    // preparation des donnees
    String nomFichier = "laby/laby.txt";
    Jeu jeu = Chargement.chargerJeu(nomFichier);
    // coordonnee initial du perso
    int placementPersoInitialeX = jeu.getPerso().getX();
    int placementPersoInitialeY = jeu.getPerso().getY();

    // methode testee
    jeu.deplacerPerso("Droite");

    // verification des coordonnees du personnage
    assertEquals(placementPersoInitialeX, jeu.getPerso().getX());
    assertEquals(placementPersoInitialeY, jeu.getPerso().getY());
  }


  // test lorsque l’action est connu et que la case suivante est une caisse
  // et que celle apres la caisse est vide
  @Test
  public void test_03_caseSuivanteCaisse() throws Exception {

    // preparation des donnees
    String nomFichier = "laby/laby.txt";
    Jeu jeu = Chargement.chargerJeu(nomFichier);

    jeu.deplacerPerso("Haut");

    // coordonnee initial du perso
    int placementPersoInitialeX = jeu.getPerso().getX();
    int placementPersoInitialeY = jeu.getPerso().getY();
    // coordonnee initial de la caisse
    int placementCaisseInitialeX = jeu.getCaisses().getElement(6, 2).getX();
    int placementCaisseInitialeY = jeu.getCaisses().getElement(6, 2).getY();


    // utilisation de la methode lorsque le personnage est à gauche de la caisse
    jeu.deplacerPerso("Droite");


    // verification des coordonnees de la caisse : la coordonnee x doit rester la meme
    //                                             la coordonnee y doit etre celle initiale + 1
    assertEquals(placementCaisseInitialeX + 1, jeu.getCaisses().getElement(7, 2).getX());
    assertEquals(placementCaisseInitialeY, jeu.getCaisses().getElement(7, 2).getY());

    // verification des coordonnees du personnage : la coordonnee x doit rester la meme
    //                                              la coordonnee y doit etre celle initiale + 1
    assertEquals(placementPersoInitialeX + 1, jeu.getPerso().getX());
    assertEquals(placementPersoInitialeY, jeu.getPerso().getY());
  }


  // test lorsque l’action est connu et que la case suivante est une caisse
  // et que celle encore d’après la caisse est une caisse et que celle encore d’après est vide
  @Test
  public void test_04_casesSuivantesCaisses() throws Exception {

    // preparation des donnees
    String nomFichier = "laby/laby_test.txt";
    Jeu jeu = Chargement.chargerJeu(nomFichier);

    jeu.deplacerPerso("Haut");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Gauche");


    // coordonnee initial du perso
    int placementPersoInitialeX = jeu.getPerso().getX();
    int placementPersoInitialeY = jeu.getPerso().getY();
    // coordonnee initial de la caisse 1
    int placementCaisse1InitialeX = jeu.getCaisses().getElement(2, 7).getX();
    int placementCaisse1InitialeY = jeu.getCaisses().getElement(2, 7).getY();

    // coordonnee initial de la caisse 2
    int placementCaisse2InitialeX = jeu.getCaisses().getElement(3, 7).getX();
    int placementCaisse2InitialeY = jeu.getCaisses().getElement(3, 7).getY();


    // utilisation de la methode lorsque le personnage est à droite des deux caisses qui sont collées
    jeu.deplacerPerso("Gauche");


    // verification des coordonnees de la caisse 1 : les coordonnees x et y doivent rester les memes
    assertEquals(placementCaisse1InitialeX, jeu.getCaisses().getElement(2, 7).getX());
    assertEquals(placementCaisse1InitialeY, jeu.getCaisses().getElement(2, 7).getY());

    // verification des coordonnees de la caisse 2 : les coordonnees x et y doivent rester les memes
    assertEquals(placementCaisse2InitialeX, jeu.getCaisses().getElement(3, 7).getX());
    assertEquals(placementCaisse2InitialeY, jeu.getCaisses().getElement(3, 7).getY());

    // verification des coordonnees du personnage : les coordonnees x et y doivent rester les memes
    assertEquals(placementPersoInitialeX, jeu.getPerso().getX());
    assertEquals(placementPersoInitialeY, jeu.getPerso().getY());
  }


  // test lorsque l’action est connu et que la case suivante est une caisse
  // et que celle encore d’après la caisse est un mur
  @Test
  public void test_05_casesSuivantesCaisseMurs() throws Exception {

    // preparation des donnees
    String nomFichier = "laby/laby_test.txt";
    Jeu jeu = Chargement.chargerJeu(nomFichier);

    jeu.deplacerPerso("Haut");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Bas");
    jeu.deplacerPerso("Bas");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Haut");
    jeu.deplacerPerso("Haut");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Gauche");
    jeu.deplacerPerso("Gauche");


    // coordonnee initial du perso
    int placementPersoInitialeX = jeu.getPerso().getX();
    int placementPersoInitialeY = jeu.getPerso().getY();
    // coordonnee initial de la caisse
    int placementCaisse1InitialeX = jeu.getCaisses().getElement(1, 7).getX();
    int placementCaisse1InitialeY = jeu.getCaisses().getElement(1, 7).getY();


    // utilisation de la methode lorsque le personnage est à droite de la caisse et que la caisse est collée au mur
    jeu.deplacerPerso("Gauche");


    // verification des coordonnees de la caisse : les coordonnees x et y doivent rester les memes
    assertEquals(placementCaisse1InitialeX, jeu.getCaisses().getElement(1, 7).getX());
    assertEquals(placementCaisse1InitialeY, jeu.getCaisses().getElement(1, 7).getY());


    // verification des coordonnees du personnage : les coordonnees x et y doivent rester les memes
    assertEquals(placementPersoInitialeX, jeu.getPerso().getX());
    assertEquals(placementPersoInitialeY, jeu.getPerso().getY());
  }





}
