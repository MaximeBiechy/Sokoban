package graphisme;

import jeu.Jeu;
import jeu.Labyrinthe;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Vue extends JPanel {

    /**
     * taille des cases
     */
    public static final int TAILLE = 80;

    /**
     * le modele a afficher
     */
    Jeu jeu;

    // Tableau stockant les images du personnages
    private ImageIcon[] personnageHautAnimation = new ImageIcon[3];
    private ImageIcon[] personnageBasAnimation = new ImageIcon[3];
    private ImageIcon[] personnageGaucheAnimation = new ImageIcon[3];
    private ImageIcon[] personnageDroiteAnimation = new ImageIcon[3];

    private BufferedImage img;

    /**
     * Icones des elements
     */
    private ImageIcon persoBaseIcon;
    private ImageIcon caisseIcon;
    private ImageIcon murIcon;
    private ImageIcon depotIcon;
    private ImageIcon solIcone;

    /**
     * Gestion des animations
     */
     private Timer animationTimer;

    /**
     * construction d'une vue a partie d'un jeu.
     * construit une vue a la bonne taille a partir du descriptif du jeu.
     *
     * @param jeu le jeu qu'on souhaite dessiner
     */
    public Vue(Jeu jeu) {
        // stocke la reference
        this.jeu = jeu;

        // elements jeu
        this.caisseIcon = new ImageIcon("sprites/crate.png");
        this.murIcon = new ImageIcon("sprites/block.png");
        this.depotIcon = new ImageIcon("sprites/environment.png");
        this.solIcone = new ImageIcon("sprites/ground.png");
        this.persoBaseIcon = new ImageIcon("sprites/player_arret_face.png");

        // personnage
        personnageHautAnimation[0] = new ImageIcon("sprites/player_arret_dos.png");
        personnageHautAnimation[1] = new ImageIcon("sprites/player_avance_haut_01.png");
        personnageHautAnimation[2] = new ImageIcon("sprites/player_avance_haut_02.png");

        personnageBasAnimation[0] = new ImageIcon("sprites/player_arret_face.png");
        personnageBasAnimation[1] = new ImageIcon("sprites/player_avance_bas_01.png");
        personnageBasAnimation[2] = new ImageIcon("sprites/player_avance_bas_02.png");

        personnageDroiteAnimation[0] = new ImageIcon("sprites/player_arret_droite.png");
        personnageDroiteAnimation[1] = new ImageIcon("sprites/player_avance_droite_01.png");
        personnageDroiteAnimation[2] = new ImageIcon("sprites/player_avance_droite_02.png");

        personnageGaucheAnimation[0] = new ImageIcon("sprites/player_arret_gauche.png");
        personnageGaucheAnimation[1] = new ImageIcon("sprites/player_avance_gauche_01.png");
        personnageGaucheAnimation[2] = new ImageIcon("sprites/player_avance_gauche_02.png");

        // initialise la frame
        String descriptif = jeu.jeuToString();

        // recupere les lignes
        String[] lignes = descriptif.split("\n");
        // utilise le descriptif pour avoir la taille
        int width = lignes[0].length() * TAILLE;
        int height = lignes.length * TAILLE;

        // met a jour fenetre
        this.setPreferredSize(new Dimension(width, height));

        // demande mise à jour de l'affichage
        this.repaint();
    }

    /**
     * redefinit la methode d'affoichage
     *
     * @param g le graphics du panel dans lequel dessiner
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // recupere le descriptif
        String desc = this.jeu.jeuToString();
        String[] lignes = desc.split("\n");

        // pour chaque ligne
        for (int numLigne = 0; numLigne < lignes.length; numLigne++) {

            // pour chaque colonne
            String ligneAffiche = lignes[numLigne];

            // parcour chaque colonne de la ligne (equivalent aux lettres de la ligne)
            for (int numCol = 0; numCol < ligneAffiche.length(); numCol++) {

                // traite caractere par caractere
                char charAffiche = ligneAffiche.charAt(numCol);
                dessinerChar(g, numLigne, numCol, charAffiche);
            }
        }
        // fin d'affichage

        // si le jeu est fini, on affiche bravo
        if (this.jeu.etreFini())
        {
            g.setColor(Color.ORANGE);
            int decal = 100;
            g.fillRect(decal, decal,this.getWidth()- 2*decal,this.getHeight()- 2*decal);
            g.setColor(Color.black);
            g.drawString("BRAVO",this.getWidth()/2-50,this.getHeight()/2);
        }
    }

    /**
     * dessine le caractere passe en parametre dans l'image
     *
     * @param g           graphics pour dessiner
     * @param numLigne    numero de ligne
     * @param numCol      numero de colonne
     * @param charAffiche le caractere a trduire dans l'affichage
     */
    private void dessinerChar(Graphics g, int numLigne, int numCol, char charAffiche) {
        // positions (pour etre sur de ne pas faire d'inversion)
        int x = numCol;
        int y = numLigne;

        switch (charAffiche) {

            // si c'est un mur
            case Labyrinthe.MUR:
                this.dessinerMur(g, x, y);
                break;

            // si c'est le personnage
            case Labyrinthe.PJ:
                this.dessinerPersonnage(g, x, y);
                break;

            // si c'est une caisse
            case Labyrinthe.CAISSE:
                this.dessinerCaisse(g, x, y);
                break;

            // si c'est un vide
            case Labyrinthe.VIDE:
                this.dessinerVide(g, x, y);
                break;

            // si c'est un lieu de depot
            case Labyrinthe.DEPOT:
                this.dessinerDepot(g, x, y);
                break;
        }
    }

    /**
     * permet de dessiner un lieu de depot
     * juste un carre dans la case
     *
     * @param g  graphics ou dessiner
     * @param dx numero de ligne
     * @param dy numero de colonne
     */
    private void dessinerDepot(Graphics g, int dx, int dy) {
        Image image = depotIcon.getImage();
        g.drawImage(image, dx * TAILLE, dy * TAILLE, TAILLE, TAILLE, null);
        int x = dx * TAILLE;
        int y = dy * TAILLE;
        g.drawRect(x, y, TAILLE, TAILLE);
    }

    /**
     * dessine une case vide
     *
     * @param g  graphics pour dessiner
     * @param dx numero de ligne
     * @param dy numero de colonne
     */
    private void dessinerVide(Graphics g, int dx, int dy) {
        Image image = solIcone.getImage();
        g.drawImage(image, dx * TAILLE, dy * TAILLE, TAILLE, TAILLE, null);
        int x = dx * TAILLE;
        int y = dy * TAILLE;
        g.drawRect(x, y, TAILLE, TAILLE);
    }

    /**
     * dessine une caisse
     *
     * @param g  graphics ou dessiner
     * @param dx numero de ligne
     * @param dy numero de colonne
     */
    private void dessinerCaisse(Graphics g, int dx, int dy) {
        Image image = caisseIcon.getImage();
        g.drawImage(image, dx * TAILLE, dy * TAILLE, TAILLE, TAILLE, null);
        int x = dx * TAILLE;
        int y = dy * TAILLE;
        g.drawRect(x, y, TAILLE, TAILLE);
    }

    /**
     * dessine un mur
     *
     * @param g  graphics pour dessiner
     * @param dx numero de ligne
     * @param dy numero de colonne
     */
    private void dessinerMur(Graphics g, int dx, int dy) {
        Image image = murIcon.getImage();
        g.drawImage(image, dx * TAILLE, dy * TAILLE, TAILLE, TAILLE, null);
        int x = dx * TAILLE;
        int y = dy * TAILLE;
        g.drawRect(x, y, TAILLE, TAILLE);
    }

    /**
     * dessine le personnage
     *
     * @param g  graphics pour dessiner
     * @param dx numero de ligne
     * @param dy numero de colonne
     */
    private void dessinerPersonnage(Graphics g, int dx, int dy) {
        String direction = jeu.getAction();
        ImageIcon[] animation = null;
        if (direction == null) {
            Image image = persoBaseIcon.getImage();
            g.drawImage(image, dx * TAILLE, dy * TAILLE, TAILLE, TAILLE, null);
            int x = dx * TAILLE;
            int y = dy * TAILLE;
            g.drawRect(x, y, TAILLE, TAILLE);
        } else {
            switch (direction) {
                case Jeu.HAUT:
                    animation = personnageHautAnimation;
                    break;
                case Jeu.BAS:
                    animation = personnageBasAnimation;
                    break;
                case Jeu.GAUCHE:
                    animation = personnageGaucheAnimation;
                    break;
                case Jeu.DROITE:
                    animation = personnageDroiteAnimation;
                    break;
            }

            // Si l'animation n'est pas null, dessinez l'image d'animation du personnage
            if (animation != null) {
                Image image = animation[0].getImage();
                g.drawImage(image, dx * TAILLE, dy * TAILLE, TAILLE, TAILLE, null);
                int x = dx * TAILLE;
                int y = dy * TAILLE;
                g.drawRect(x, y, TAILLE, TAILLE);
            }
        }
    }

}
