package miage.m1.pa2017.ui;

import java.awt.Component;
import java.lang.annotation.Annotation;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import miage.m1.pa2017.plugin.Plugin;

/***
 *
 *
 * @Author Mamadou THIAW
 * @Version static du 02/01/2018
 */

public class JlistRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        Object plugin = value;
        Annotation annotation = plugin.getClass().getAnnotation(Plugin.class);
        Plugin pluginAnn = (Plugin) annotation;
        setText(pluginAnn.nom());

        return this;
    }
}
