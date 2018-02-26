package miage.m1.pa2017.ui;

import miage.m1.pa2017.jeu.IArene;
import miage.m1.pa2017.jeu.IRobot;
import miage.m1.pa2017.plugin.Plugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author Mamadou THIAW
 * @version 0.1.0 01/01/2018
 * @Update 04/01/2018
 */

public class MainFrame extends JFrame implements ActionListener {

    public static IArene areneJeu;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem exitAppMenuItem;
    private JMenu pluginMenu;
    private JMenuItem pluginsManagement;
    private JMenu partieMenu;
    private JMenuItem partieManagement;

    public MainFrame(IArene arene) {
        MainFrame.areneJeu = arene;
        EventQueue.invokeLater(new Runnable() {
                                   @Override
                                   public void run() {
                                       try {
                                           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                                       } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                                       }
                                       MainFrame.this.setTitle("Robot War 1.0");
                                       MainFrame.this.setSize(800, 500);
                                       MainFrame.this.setLocationRelativeTo(null);
                                       MainFrame.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                       setMenu();
                                       MainFrame.this.setJMenuBar(menuBar);

                                   }
                               }
        );

    }

    private void setMenu() {
        // BARRE DE MENU
        menuBar = new JMenuBar();
        // MENU ONGLET FICHIER
        fileMenu = new JMenu();
        pluginMenu = new JMenu();
        exitAppMenuItem = new JMenuItem();

        fileMenu.setText("Fichier");
        exitAppMenuItem.setText("Sortir");
        fileMenu.add(exitAppMenuItem);

        // MENU PLUGIN
        pluginsManagement = new JMenuItem();
        pluginMenu.setText("Les plugins");
        pluginsManagement.setText("Gestion des plugins");

        // MENU PARTIE
        partieMenu = new JMenu();
        partieManagement = new JMenuItem();
        partieMenu.setText("Partie");
        partieManagement.setText("Lancer partie");
        pluginMenu.add(pluginsManagement);
        partieMenu.add(partieManagement);


        menuBar.add(fileMenu);
        menuBar.add(pluginMenu);
        menuBar.add(partieMenu);


        // AJOUT DUN ECOUTEUR DE CLIC SUR CHAQUE MENU
        exitAppMenuItem.addActionListener(this);
        pluginsManagement.addActionListener(this);
        partieManagement.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.exitAppMenuItem) {
            this.setVisible(false);
            System.exit(0);
        } else if (e.getSource() == this.pluginsManagement) {
            GestionnaireFrame gestionnaireFrame = null;
            gestionnaireFrame.getInstance();
        } else if (e.getSource() == this.partieManagement) {
            Thread trd = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        lancementPartie();
                    } catch (InvocationTargetException e1) {
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchMethodException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            trd.start();
            // TODO TRAITEMENT
            System.out.println("Start partie");
        }
    }

    public void lancementPartie() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InterruptedException {
        while (!partieTerminee()) {
            for (IRobot robot : MainFrame.areneJeu.getRobots()) {
                if (robot.isAlive()) {
                    // On déplace le robot si un plugin de déplacement est activé
                    if (GestionnaireFrame.activatedPlugins[0] != null) {
                        Method deplacement = GestionnaireFrame.activatedPlugins[0].getClass().getDeclaredMethod("action", Object.class, int.class, int.class);
                        deplacement.invoke(GestionnaireFrame.activatedPlugins[0], robot, MainFrame.areneJeu.getmaxAbscisse(), MainFrame.areneJeu.getMaxOrdonnee());
                    }
                    // ON fait attaquer le robot si une cible est a portée
                    if (GestionnaireFrame.activatedPlugins[1] != null) {
                        Method attack = GestionnaireFrame.activatedPlugins[1].getClass().getDeclaredMethod("action", Object.class, ArrayList.class);
                        attack.invoke(GestionnaireFrame.activatedPlugins[1], robot, MainFrame.areneJeu.getRobots());
                    }
                    // On reset la couleur ou l'image si il n'y en a pas déjà d'instancier
                    if (GestionnaireFrame.activatedPlugins[2] != null) {
                        Annotation annotation = GestionnaireFrame.activatedPlugins[2].getClass().getAnnotation(Plugin.class);
                        Plugin plugAnnot = (Plugin) annotation;
                        if (plugAnnot.nom() == "Graphique aleatoire") {
                            Method robotGraphics = GestionnaireFrame.activatedPlugins[2].getClass().getDeclaredMethod("action", Object.class);
                            robotGraphics.invoke(GestionnaireFrame.activatedPlugins[2], robot);
                        } else if (plugAnnot.nom() == "Graphique imageAleatoire") {
                            Method robotGraphics = GestionnaireFrame.activatedPlugins[2].getClass().getDeclaredMethod("action", Object.class, ArrayList.class);
                            robotGraphics.invoke(GestionnaireFrame.activatedPlugins[2], robot, MainFrame.areneJeu.getAllImages());
                        } else if (plugAnnot.nom() != "Graphique aleatoire" && plugAnnot.nom() == "Graphique imageAleatoire" && plugAnnot.nom() == null) {
                            Method robotGraphics = GestionnaireFrame.activatedPlugins[2].getClass().getDeclaredMethod("action", Object.class);
                            robotGraphics.invoke(GestionnaireFrame.activatedPlugins[2], robot);
                        }

                    }
                    robot.repaintRobot();
                } else {
                    robot.setVisible(false);
                    MainFrame.areneJeu.remove(robot.getId());
                }
                MainFrame.areneJeu.refresh();
                Thread.sleep(100);

            }
        }
    }

    public boolean partieTerminee() {
        int cpt = 0;
        IRobot vainqueur;
        for (IRobot rob : MainFrame.areneJeu.getRobots()) {
            if (rob.isAlive()) {
                ++cpt;
            }
        }
        if (cpt > 1) {
            return false;
        } else {
            for (IRobot rob : MainFrame.areneJeu.getRobots()) {
                if (rob.getVie()>0) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Robot " + rob.getId() + " a gagné !");
                }
            }
            return true;
        }
    }
}
