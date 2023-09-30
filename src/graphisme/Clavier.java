package graphisme;

import jeu.ActionInconnueException;
import jeu.Jeu;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Clavier extends KeyAdapter {
    /**
     * lien vers le jeu (contenu dans application)
     */
    Application app;

    /**
     * constructeur
     *
     * @param pApp application a controler (pour MVC)
     */
    public Clavier(Application pApp) {
        this.app = pApp;
    }

    /**
     * appui d'une touche
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // mise a jour du jeu (qui appelle la mise a jour graphique)
        try {

            if (e.getKeyChar() == 'z') {
                this.app.deplacerPerso(Jeu.HAUT);
            } else if (e.getKeyChar() == 's') {
                this.app.deplacerPerso(Jeu.BAS);
            } else if (e.getKeyChar() == 'd') {
                this.app.deplacerPerso(Jeu.DROITE);
            } else if (e.getKeyChar() == 'q') {
                this.app.deplacerPerso(Jeu.GAUCHE);
            }


        } catch (ActionInconnueException ex) {
            // erreur action inconnue
            throw new RuntimeException(ex);
        }

    }

}
