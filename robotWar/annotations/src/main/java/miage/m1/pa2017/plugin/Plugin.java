package miage.m1.pa2017.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import miage.m1.pa2017.Util.Type;

/**
 *
 * @author Mamadou THIAW
 * @version 0.1.0 01/01/2018
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Plugin {
    String nom() default "plugin de base";
    Type type() default Type.BASE;
    boolean activer() default false;
}