package jeu;

// La classe Element represente un element de jeu.
public class Element {

  /**
   * Attributs class Element
   */
  // attribut x/y : correspond au numero de la colonne/ligne
  private int x, y;

  /**
   * Constructeur principal de la classe Element
   * @param x, attribut x : correspond au numero de la colonne
   * @param y  attribut y : correspond au numero de la ligne
   */
  public Element(int x, int y){
    this.x = x;
    this.y = y;
  }

  // GETTER
  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  // SETTER
  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }
}
