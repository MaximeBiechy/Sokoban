package jeu;

import java.util.Scanner;

public class MainJeu {
  public static void main(String[] args) throws Exception {

    Jeu jeu = Chargement.chargerJeu("laby/laby.txt");
    System.out.println(jeu.jeuToString());

    Scanner sc = new Scanner(System.in);
    int nbAction = 0;
    int deplacementInconnu = 0;
    int nbDeplacement;

    while (!jeu.etreFini()) {
      System.out.println("Faites une action ! (z : haut) (s : bas) (q : gauche) (d : droite)");
      switch (sc.nextLine().toUpperCase()) {

        // action : haut
        case "Z":
          jeu.deplacerPerso(jeu.HAUT);
          break;

        // action : bas
        case "S":
          jeu.deplacerPerso(jeu.BAS);
          break;

        // action : gauche
        case "Q":
          jeu.deplacerPerso(jeu.GAUCHE);
          break;

        // action : droite
        case "D":
          jeu.deplacerPerso(jeu.DROITE);
          break;

        default:
          System.out.println("Action inconnue");
          deplacementInconnu++;
          break;
      }
      nbAction++;

      nbDeplacement = nbAction - deplacementInconnu;
      System.out.println("Nombre de deplacement : " + nbDeplacement + "\n\n" + jeu.jeuToString());
    }
    System.out.println("C'est gagné !");
  }
}
