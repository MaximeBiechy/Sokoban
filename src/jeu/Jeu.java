package jeu;

public class Jeu {

  /**
   * Attributs de la classe Jeu
   */
  // attribut Perso : represente le personnage controle par le joueur et sa position
  private Perso perso;

  // attribut caisses : contient tous les objets Caisse du jeu
  private ListeElements caisses;

  // attribut depots : contient tous les objets Depot du jeu
  private ListeElements depots;

  // attribut laby : decrit les murs de l’environnement
  private Labyrinthe laby;

  // action du perso
  private String action;

  /**
   * Constantes de la classe Jeu
   */
  // constante haut
  public static final String HAUT = "Haut";

  // constante bas
  public static final String BAS = "Bas";

  // constante gauche
  public static final String GAUCHE = "Gauche";

  // constante droite
  public static final String DROITE = "Droite";

  /**
   * Constructeur principal de la classe Jeu
   * @param perso personnage
   * @param caisses liste contenant les caisses du jeu
   * @param depots liste contenant les depots du jeu
   * @param laby labyrinthe
   */
  public Jeu(Perso perso, ListeElements caisses, ListeElements depots, Labyrinthe laby){
    this.perso = perso;
    this.caisses = caisses;
    this.depots = depots;
    this.laby = laby;
  }

  /**
   * Retourne le caractere correspondant a la case (x,y)
   * @param x, colonne
   * @param y, ligne
   * @return le caractere correspondant a la case (x,y)
   */
  public char getChar(int x, int y){
    char carac = Labyrinthe.VIDE;

    // Cherche le caractere correspondant a la case (x, y)
    if (laby.getMurs()[x][y]){
      carac = Labyrinthe.MUR;
    } else if (perso.getX() == x && perso.getY() == y){
      carac = Labyrinthe.PJ;
    } else if (caisses.getElement(x, y) != null) {
      carac = Labyrinthe.CAISSE;
    } else if (depots.getElement(x, y) != null){
      carac = Labyrinthe.DEPOT;
    }

    return carac;
  }

  /**
   * Genere le descriptif de l’etat du jeu conformement a la maniere dont les caracteres sont representes
   * @return le descriptif de l'etat du jeu
   */
  public String jeuToString(){
    String res = "";
    for (int i = 0; i < laby.getHauteur(); i++){
      for (int j = 0; j < laby.getLargeur(); j++){
        res += getChar(j, i);
      }
      res += "\n";
    }

    return res;
  }

  /**
   * Methode getSuivant : permet de calculer les coordonnees de la case suivante
   * en fonction de l’action
   * @param x, colonne
   * @param y, ligne
   * @param action, action que le personnage veut effectuer
   * @return les coordonnees de la case suivante selon l'action
   */
  public static int[] getSuivant(int x, int y, String action) throws ActionInconnueException {
    int[] case_suivante =new int[2];
    switch (action){
      case Jeu.HAUT:
        case_suivante[0] = x;
        case_suivante[1] = y - 1;
        break;
      case Jeu.BAS:
        case_suivante[0] = x;
        case_suivante[1] = y + 1;
        break;
      case Jeu.GAUCHE:
        case_suivante[0] = x - 1;
        case_suivante[1] = y;
        break;
      case Jeu.DROITE:
        case_suivante[0] = x + 1;
        case_suivante[1] = y;
        break;
      default:
        throw new ActionInconnueException();
    }
    return case_suivante;
  }

  /**
   * Deplace le personnage dans la direction choisie par l’utilisateur tout en
   * respectant les regles du jeu
   * @param action, action que le personnage doit effectuer
   */
  public void deplacerPerso(String action) throws ActionInconnueException{
    this.action = action;
    boolean deplacer_perso = false;
    // case suivante
    int[] case_suivante = getSuivant(perso.getX(), perso.getY(), action);
    // element de la case suivante
    char element_case_suivante = getChar(case_suivante[0], case_suivante[1]);

    // Verifie si le personnage peut se deplacer selon les regles du jeu
    if(element_case_suivante == Labyrinthe.MUR) {
      System.out.println("Action impossible. Il y a un mur.");
    } else if (element_case_suivante == Labyrinthe.CAISSE){
      // case derriere la caisse
      int[] case_derriere_caisse = getSuivant(case_suivante[0], case_suivante[1], action);
      // element derriere la caisse
      char element_derriere_caisse = getChar(case_derriere_caisse[0], case_derriere_caisse[1]);

      // Si l'element derriere la caisse est une caisse ou un mur alors il est impossible de la deplacer
      if (element_derriere_caisse == Labyrinthe.CAISSE || element_derriere_caisse == Labyrinthe.MUR){
        System.out.println("Action impossible. La caisse ne peut pas bouger dans ce sens.");

        // Sinon si l'element derriere la caisse est une case vide ou une zone de depots alors on deplace la caisse
      } else if (element_derriere_caisse == Labyrinthe.VIDE || element_derriere_caisse == Labyrinthe.DEPOT){
        // Parcourt des caisses pour deplacer la bonne
        for (int i = 0; i < caisses.getElements().size(); i++){
          if (caisses.getElements().get(i).getX() == case_suivante[0] && caisses.getElements().get(i).getY() == case_suivante[1]) {
            deplacer_perso = true;

            // deplacement de la caisse
            caisses.getElements().get(i).setX(case_derriere_caisse[0]);
            caisses.getElements().get(i).setY(case_derriere_caisse[1]);
            break;
          }
        }
      }
    } else deplacer_perso = true;
    if (deplacer_perso){
      // Deplacement du personnage
      perso.setX(case_suivante[0]);
      perso.setY(case_suivante[1]);
    }
  }

  /**
   * Verifie si le jeu est fini, c’est-a-dire si le joueur a reussi a pousser les caisses sur les lieux de stockage
   * @return true si le jeu est fini, sinon false
   */
  public boolean etreFini(){
    for(int i = 0; i < caisses.getElements().size(); i++){
      boolean fini = false;
      for (int j = 0; j < depots.getElements().size(); j++){
        if((caisses.getElements().get(i).getX() == depots.getElements().get(j).getX()) && (caisses.getElements().get(i).getY() == depots.getElements().get(j).getY())){
          fini = true;
          break;
        }
      }
      if(!fini){
        return false;
      }
    }
    return true;
  }

  // GETTER
  public Perso getPerso() {
    return perso;
  }

  public ListeElements getCaisses() {
    return caisses;
  }

  public ListeElements getDepots() {
    return depots;
  }

  public Labyrinthe getLaby() {
    return laby;
  }

  public String getAction() {
    return action;
  }
}
