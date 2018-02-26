package miage.m1.pa2017;

import miage.m1.pa2017.jeu.IArene;
import miage.m1.pa2017.jeu.IRobot;
import miage.m1.pa2017.ui.MainFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;



/**
 *
 * @author Mamadou THIAW
 * @version 0.1.0 01/01/2018
 * @Update 04/01/2018
 */

public class App
{
    public static void main( String[] args )
    {

        IArene arene = new Arene();
        // Preparer les robots random : nb roborts, position
        ArrayList<IRobot> robots = new ArrayList<>();
        robots.add(new Robot(1,14, 14, new Arme(2, 10)));
        robots.add(new Robot(2, 5, 10, new Arme(5, 2)));
        robots.add(new Robot(3, 2, 5, new Arme(14, 1)));
        robots.add(new Robot(4, 8, 6, new Arme(1, 100)));

        arene.setRobots(robots);
        MainFrame mainFrame = new MainFrame(arene);
        mainFrame.add(arene);
        mainFrame.setVisible(true);
    }
}
