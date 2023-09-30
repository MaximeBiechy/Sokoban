package jeu;

import java.util.ArrayList;

// La classe ListeElements contient plusieurs elements qui seront soit des Caisse soit des Depot.
public class ListeElements {

  /**
   * Attribut class ListeElements
   */
  // attribut elements : represente soit une caisse, soit un depot
  private ArrayList<Element> elements;

  /**
   * Constructeur principal de la classe ListeElements
   */
  public ListeElements(){
    elements = new ArrayList<Element>();
  }

  // GETTER
  public ArrayList<Element> getElements(){
    return elements;
  }

  /**
   * Methode getElement qui retourne l’element de la case (x, y)
   * @param x coordonnee de x
   * @param y coordonnee de y
   * @return l'element de la case (x, y) ou null
   */
  public Element getElement(int x, int y){
    Element element = null;
    for (int i = 0; i < this.elements.size(); i++) {
      if (elements.get(i).getX() == x && elements.get(i).getY() == y){
        element = elements.get(i);
        break;
      }
    }
    return element;
  }
}
