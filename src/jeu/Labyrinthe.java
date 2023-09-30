package jeu;

public class Labyrinthe {

  /**
   * Attribut classe Labyrinthe
   */
  // attribut murs : pour un x et un y donnes, murs[x][y] vaut true si et seulement si la case (x,y) est un mur
  private boolean murs[][];
  private int largeur;
  private int hauteur;

  /**
   * Constantes de la classe Labyrinthe
   */
  // constante mur : represente un mur
  public final static char MUR = '#';

  // constante caisse : represente une caisse
  public final static char CAISSE = '$';

  // constante pj : represente le personnage
  public final static char PJ = '@';

  // constante depot : represente un lieu de depot
  public final static char DEPOT = '.';

  // constante vide : represente une case vide
  public final static char VIDE = ' ';

  /**
   * Constructeur principal de la classe Labyrinthe
   * @param largeur, taille largeur
   * @param hauteur, taille hauteur
   */
  public Labyrinthe(int largeur, int hauteur){
    murs = new boolean[largeur][hauteur];
    this.largeur = largeur;
    this.hauteur = hauteur;
  }

  /**
   * Methode setLaby qui permet d’établir le labyrinthe
   * @param x coordonnee x de la case
   * @param y coordonnee y de la case
   */
  public void setMurs(int x, int y){
    this.murs[x][y] = true;
  }

  /**
   * Methode etreMur : permet de savoir si un mur se trouve sur la case (x,y)
   * @param x, colonne
   * @param y, ligne
   * @return true si la case (x, y) est un mur
   */
  public boolean etreMur(int x, int y){
    return murs[x][y];
  }

  // GETTER
  public boolean[][] getMurs(){
    return murs;
  }

  public int getLargeur(){
    return largeur;
  }

  public int getHauteur(){
    return hauteur;
  }

}
