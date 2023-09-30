import jeu.Chargement;
import jeu.FichierIncorrectException;
import jeu.Jeu;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestChargement {

  // test lorsque tout est ok
  @Test
  public void test_01_OK() throws IOException, FichierIncorrectException {

    // preparation des donnees
    String nomFichier = "laby/laby.txt";
    Jeu jeu = null;

    // methode testee
    try {
      jeu = Chargement.chargerJeu(nomFichier);
    } catch (Exception e) {
      e.getMessage();
    }

    // verification que le jeu n’est pas null
    assertNotNull(jeu);

    // verification que le contenu du jeu correspond à celui du fichier
    assertEquals(2, jeu.getCaisses().getElements().size());
    assertEquals(2, jeu.getDepots().getElements().size());
    assertEquals(5, jeu.getPerso().getX());
    assertEquals(3, jeu.getPerso().getY());
    assertEquals(9, jeu.getLaby().getLargeur());
    assertEquals(7, jeu.getLaby().getHauteur());
  }


  // test avec un fichier inexistant
  @Test
  public void test_02_fichierInexistant() throws IOException, FichierIncorrectException  {

    // preparation des donnees
    String nomFichier = "fichierInexistant.txt";

    // methode testee
    try {
      Jeu jeu = Chargement.chargerJeu(nomFichier);
    } catch (Exception e) {
      // verification
      assertEquals(nomFichier + " (Le fichier spécifié est introuvable)", e.getMessage());
    }
  }


  // test avec un fichier contenant un caractere non attendu
  @Test
  public void test_03_caracNonAttendu() throws IOException{

    // preparation des donnees
    String nomFichier = "laby/labyTestChargement/caracNonAttendu.txt";

    // methode testee
    try{
      Jeu jeu = Chargement.chargerJeu(nomFichier);
    }catch (Exception e){
      assertEquals("Caractere inconnu : (", e.getMessage());
    }
  }


  // test avec un fichier où il manque le personnage
  @Test
  public void test_04_pasDePerso() throws IOException {

    // preparation des donnees
    String nomFichier = "laby/labyTestChargement/pasDePerso.txt";

    // methode testee
    try{
      Jeu jeu = Chargement.chargerJeu(nomFichier);
    } catch (Exception e){
      assertEquals("Personnage inconnu", e.getMessage());
    }
  }


  // test avec un fichier où le nombre de caisses et de depots n’est pas le meme
  @Test
  public void test_05_pasMemeNbCaissesEtDepots() throws IOException, FichierIncorrectException {

    // preparation des donnees
    String nomFichier = "laby/labyTestChargement/pasMemeNbCaissesEtDepots.txt";

    try {
      // methode testee
      Jeu jeu = Chargement.chargerJeu(nomFichier);
    }catch (Exception e){
      // verification
      assertEquals("Caisses (3) Depots (2)", e.getMessage());
    }
  }


  // test avec un fichier où le nombre de caisses et de depots n’est pas le meme
  @Test
  public void test_06_pasdeCaisse() throws IOException, FichierIncorrectException {

    // preparation des donnees
    String nomFichier = "laby/labyTestChargement/pasDeCaisse.txt";

    try {
      // methode testee
      Jeu jeu = Chargement.chargerJeu(nomFichier);
    }catch (Exception e){
      // verification
      assertEquals("Caisses inconnues", e.getMessage());
    }
  }


}
