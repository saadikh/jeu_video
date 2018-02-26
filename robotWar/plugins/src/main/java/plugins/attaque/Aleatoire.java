package plugins.attaque;

import miage.m1.pa2017.Util;
import miage.m1.pa2017.jeu.IArene;
import miage.m1.pa2017.jeu.IRobot;
import miage.m1.pa2017.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Flavien BOSQUES
 * @Update by par Mamadou THIAW le 02/01/2018
 *
 */
@Plugin(nom = "Attaque aleatoire", type = Util.Type.ATTAQUE)
public class Aleatoire {

    public void action(Object obj, ArrayList<Object> list) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getX = obj.getClass().getDeclaredMethod("getX");
        Method getY = obj.getClass().getDeclaredMethod("getY");
        Method getDegats = obj.getClass().getDeclaredMethod("getDegats");
        Method getPortee = obj.getClass().getDeclaredMethod("getPortee");

        int dommages = (int)getDegats.invoke(obj);
        for(Object robot : list){
            Method getXList = robot.getClass().getDeclaredMethod("getX");
            Method getYList = robot.getClass().getDeclaredMethod("getY");
            Method getVie   = robot.getClass().getDeclaredMethod("getVie");
            Method setVie   = robot.getClass().getDeclaredMethod("setVie", int.class);
            Method setEnergie = robot.getClass().getDeclaredMethod("setEnergie", int.class);
            Method getEnergie = robot.getClass().getDeclaredMethod("getEnergie");

            if((int)getX.invoke(obj) != (int)getXList.invoke(robot) && (int)getY.invoke(obj) != (int)getYList.invoke(robot)){

                if ((int)getX.invoke(obj) >= (int)getXList.invoke(robot)) {
                    if (((int)getX.invoke(obj) - (int)getPortee.invoke(obj))<= (int)getXList.invoke(robot) && (int)getEnergie.invoke(robot) > 0) {
                        // if energie est suffisante
                        setVie.invoke(robot, (int)getVie.invoke(robot) - dommages);
                        setEnergie.invoke(robot, (int) getEnergie.invoke(robot) - 10);
                        // on set energie (diminution)
                    }
                } else if ((int)getX.invoke(obj) <= (int)getXList.invoke(robot)) {
                    if (((int)getX.invoke(obj) + (int)getPortee.invoke(obj)) >= (int)getXList.invoke(robot)) {
                        setVie.invoke(robot, (int)getVie.invoke(robot) - dommages);
                    }
                } else if ((int)getY.invoke(obj) >= (int)getYList.invoke(robot)) {
                    if (((int)getY.invoke(obj) - (int)getPortee.invoke(obj)) <= (int)getYList.invoke(robot)) {
                        setVie.invoke(robot, (int)getVie.invoke(robot) - dommages);
                    }
                } else if ((int)getY.invoke(obj) <= (int)getYList.invoke(robot)) {
                    if (((int)getY.invoke(obj) - (int)getPortee.invoke(obj)) >= (int)getYList.invoke(robot)) {
                        setVie.invoke(robot, (int)getVie.invoke(robot) - dommages);
                    }
                }
            }
        }
        System.out.println("robot " + obj.getClass().getDeclaredMethod("getId").invoke(obj)+ " attaque !");
    }
}
