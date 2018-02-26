package miage.m1.pa2017.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import miage.m1.pa2017.Util;
import miage.m1.pa2017.jeu.IArene;
import miage.m1.pa2017.jeu.IRobot;
import miage.m1.pa2017.loader.PluginLoader;
import miage.m1.pa2017.plugin.Plugin;

public class GestionnaireFrame extends JFrame implements ActionListener {

    public static Object [] activatedPlugins = new Object[3];
    private static GestionnaireFrame instance;
    private PluginLoader pluginLoader;
    private JButton browseBtn;
    private JPanel panel;
    private JFileChooser fileChooser;
    private String filePath;
    private JTextField tf;
    private ArrayList<Object> pluginList;
    private DefaultListModel graphiqueLstModel;
    private DefaultListModel attaqueLstModel;
    private DefaultListModel deplacementLstModel;

    private JList combo1;
    private JList combo2;
    private JList combo3;
    private JButton activerPGraph;
    private JButton activerPAttaq;
    private JButton activerPDepl;
    private JButton cancelBtn;

    public GestionnaireFrame() {
        this.setTitle("Gestionnaire de plugins");
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        panel = new JPanel();
        initComponents(panel);

        this.add(panel);
        this.setVisible(true);
    }

    public static GestionnaireFrame getInstance() {
        if (instance == null) {
            instance = new GestionnaireFrame();
        }
        instance.setVisible(true);
        return instance;
    }
    // =========================================================================================================================================================
    // Lorsque l'on clique sur le bouton Activer au chargement des plugins cette méthode appelle la méthode du plugin correspondant pour lancer l'action /*TODO*/
    // =========================================================================================================================================================
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == this.browseBtn) {
                try {
                    lancerChooser();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    //JOptionPane.showMessageDialog(GestionnaireFrame.this, "Echec du chargement de plugin", "Erreur", ERROR_MESSAGE);
                }
                /* Si on a sélectionner un plugin à activer on rentre dans le else if */
            } else if (e.getSource() == this.activerPGraph && combo1.getSelectedIndex() != -1) {
                activerPlugin(combo1.getModel().getElementAt(combo1.getSelectedIndex()));
                System.out.println("graphique activé");
            } else if (e.getSource() == this.activerPAttaq && combo2.getSelectedIndex() != -1) {
                activerPlugin(combo2.getModel().getElementAt(combo2.getSelectedIndex()));
                System.out.println("attaque activé");
            } else if (e.getSource() == this.activerPDepl && combo3.getSelectedIndex() != -1) {
                activerPlugin(combo3.getModel().getElementAt(combo3.getSelectedIndex()));
                System.out.println("déplacement activé");
            } else if (e.getSource() == this.cancelBtn) {
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(GestionnaireFrame.this, "Veuillez sélectionner un plugin!");
            }
        } catch (Exception ex) {
            StackTraceElement [] ligne=null;
            ligne=ex.getStackTrace();

            for (int i=ligne.length-1;i>=0; i--)
            {
                System.out.println("class name: "+ligne[i].getClassName()+" File name: "+ligne[i].getFileName());
                System.out.println("ligne :"+ligne[i].getLineNumber() +" Method name:" +ligne[i].getMethodName() );
            }
            JOptionPane.showMessageDialog(GestionnaireFrame.this, "Echec d'activation du plugin see the stackTrace");
        }
    }

    public void lancerChooser() throws HeadlessException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        fileChooser = new JFileChooser();
        FileNameExtensionFilter extfilter = new FileNameExtensionFilter("Compressed files", "zip", "jar");
        fileChooser.setFileFilter(extfilter);
        fileChooser.setCurrentDirectory(new File("."));
        if (fileChooser.showOpenDialog(GestionnaireFrame.this) == JFileChooser.APPROVE_OPTION) {
            filePath = fileChooser.getSelectedFile().getAbsolutePath();
            tf.setText(filePath);
            // =============================================
            // Appelle de la méthode pour charger les plugin
            // =============================================
            chargerPlugins();

            for (Object p : pluginList) {

                Annotation annotation = p.getClass().getAnnotation(Plugin.class);
                Plugin plugin = (Plugin) annotation;
                if (plugin.type() == Util.Type.ATTAQUE) {
                    attaqueLstModel.addElement(p);
                } else if (plugin.type() == Util.Type.DEPLACEMENT) {
                    deplacementLstModel.addElement(p);
                } else if (plugin.type() == Util.Type.GRAPHIQUE) {
                    graphiqueLstModel.addElement(p);
                }
            }

        }
    }

    void activerPlugin(Object plugin) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        Annotation annotation = plugin.getClass().getAnnotation(Plugin.class);
        Plugin plugAnnot      = (Plugin) annotation;
        if(plugAnnot.type() == Util.Type.DEPLACEMENT){
            GestionnaireFrame.activatedPlugins[0] = plugin;
        } else if(plugAnnot.type() == Util.Type.ATTAQUE){
            GestionnaireFrame.activatedPlugins[1] = plugin;
        } else if(plugAnnot.type() == Util.Type.GRAPHIQUE){
            GestionnaireFrame.activatedPlugins[2] = plugin;
        }


        /*Method method = plugin.getClass().getMethod("activer", null);
        System.out.print("ça récupère la méthode l 143 de GestionnaireFrame");
        method.invoke(plugin, null);*/
    }
    // ================================================================
    // Méthode qui charge les plugins dans une variable du gestionnaire
    // ================================================================
    public void chargerPlugins() {
        try {
            pluginLoader = new PluginLoader(filePath);
            System.out.println(filePath);
            pluginList = pluginLoader.initializePluginLoader();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(GestionnaireFrame.this, "Echec du chargement de plugin", "Erreur", ERROR_MESSAGE);
        }
    }

    void initComponents(JPanel panel) {
        JPanel detailsPane = new JPanel(new GridBagLayout());
        detailsPane.setBorder(new CompoundBorder(null, new EmptyBorder(12, 12, 12, 12)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        detailsPane.add(new JLabel("Chemin vers le jar: "), gbc);

        gbc.gridx++;
        gbc.weightx = 1;

        gbc.fill = GridBagConstraints.BOTH;
        tf = new JTextField();
        tf.setEnabled(false);
        detailsPane.add(tf, gbc);
        gbc.gridx++;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        browseBtn = new JButton("Parcourir");
        browseBtn.addActionListener(this);
        detailsPane.add(browseBtn, gbc);
        gbc.weightx = 1;
        gbc.gridy++;
        gbc.gridx = 0;

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        JPanel pluginCateg = new JPanel(new GridBagLayout());
        pluginCateg.setBorder(new CompoundBorder(new TitledBorder("Les catégories"), new EmptyBorder(4, 4, 4, 4)));

        graphiqueLstModel = new DefaultListModel();
        attaqueLstModel = new DefaultListModel();
        deplacementLstModel = new DefaultListModel();

        /*
         * On instancie la liste d'affichage des plugins chargés, on modifie le comportement de la JList pour ne pouvoir sélectionner que un seul item de chacune
         */
        combo1 = new JList(graphiqueLstModel);
        combo1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        combo1.setCellRenderer(new JlistRenderer());
        combo2 = new JList(attaqueLstModel);
        combo2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        combo2.setCellRenderer(new JlistRenderer());
        combo3 = new JList(deplacementLstModel);
        combo3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        combo3.setCellRenderer(new JlistRenderer());
        JLabel lb1 = new JLabel("Graphique");
        JLabel lb2 = new JLabel("Attaque");
        JLabel lb3 = new JLabel("Déplacement");

        gbc.gridx = 0;
        gbc.gridy++;
        pluginCateg.add(lb1, gbc);
        gbc.gridx++;
        pluginCateg.add(lb2, gbc);
        gbc.gridx++;
        pluginCateg.add(lb3, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weighty = 1;
        System.out.println(combo1);
        pluginCateg.add(new JScrollPane().add(combo1), gbc);
        gbc.gridx++;
        pluginCateg.add(new JScrollPane().add(combo2), gbc);
        gbc.gridx++;
        pluginCateg.add(new JScrollPane().add(combo3), gbc);
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy++;
        activerPGraph = new JButton("Activer");
        activerPGraph.addActionListener(this);
        pluginCateg.add(activerPGraph, gbc);
        gbc.gridx++;
        activerPAttaq = new JButton("Activer");
        activerPAttaq.addActionListener(this);
        pluginCateg.add(activerPAttaq, gbc);
        gbc.gridx++;
        activerPDepl = new JButton("Activer");
        activerPDepl.addActionListener(this);
        pluginCateg.add(activerPDepl, gbc);

        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 1;
        detailsPane.add(pluginCateg, gbc);

        gbc.gridy++;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.EAST;
        cancelBtn = new JButton("Fermer");
        cancelBtn.addActionListener(this);
        detailsPane.add(cancelBtn, gbc);

        panel.setLayout(new BorderLayout());

        panel.add(detailsPane);
    }
}
