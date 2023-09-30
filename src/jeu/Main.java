package jeu;

import graphisme.Application;

import java.io.IOException;

public class Main {

  public static void main(String args []) throws Exception {

  Jeu jeu = Chargement.chargerJeu("laby/laby_simple.txt");
  Application app = new Application(jeu);

  }
}
