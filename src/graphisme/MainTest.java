package graphisme;

import jeu.Chargement;
import jeu.FichierIncorrectException;
import jeu.Jeu;

import java.io.IOException;

public class MainTest {
    public static void main(String[] args) throws Exception, FichierIncorrectException, IOException {
        // charge un jeu
        Jeu j = Chargement.chargerJeu("laby/laby_test.txt");
        Application appli = new Application(j);
    }
}
