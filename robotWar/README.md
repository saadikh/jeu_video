#Sujet# 

Le but de ce projet est de construire un jeu de combat virtuel de type RobotWar

Dans une arêne de combat en 2D, vue de dessus, des robots s'affrontent, gérés par une IA relativement basique. 
Le comportement ainsi que le graphisme des robots est décidé par des plugins. 
Un robot est actif tant que sa vie n'a pas atteint 0 et le gagnant est le dernier robot actif. 
À chaque robot est associé une quantité d'énergie et chaque action consomme une partie de celle-ci. 
L'énergie remonte régulièrement tant qu'elle n'a pas atteint la valeur maximale. 

Le mécanisme de plugins devra utiliser les annotations comme vu dans le TP4. 


#Grandes lignes du projet#
	- Interfaces(Terrain, Robotes, GestionnairePlugin..),  Annotations et Plugins: 2personnes
	- La sérialisation (persistance): 1/2personne
	- l'IA(depl aleat, attaque alea,..): 1personne
	- squelette du jeu: 1/2 personne; (thiaw a commence)
=======
#Ligne de conduite
Il est necessaire de recompiler tout le projet (modules et sous-module), afin de voir ou corriger les eventuelles erreurs.
c'est à dire, avoir [SUCCESS] sur les modules et sous-modules.

#Rappel de dépendances 
    - Annotations: ne dependent d'aucun sous-module.
    - Interfaces: ne dependent d'aucun sous-module.
    - Moteur: depend de Annotations et Interfaces.
    - Plugins: depend de Annotations et Interfaces.

#Procédure pour creer un nouveau plugin
Pour écrire un plugin compatible avec notre jeu RobotWar, il faut impérativement suivre les étapes suivantes:
    - Creer une nouvelle classe dans le package plugins.[type_de_plugin], avec type_de_plugin  prend une des valeurs (attaque, graphique, deplacement)
    - Annoter le plugin avec l'annotation Plugin en lui donnant les parametres: nom, type
    - définir les méthodes suivantes:
	    - boolean activer();
        - les autres methodes pour un traitement du plugin en question
il fauy aussi avoir un constructeur par defaut et un contstructeur avec un parametre de type ITerrain pour avoir accès à tous les éléments du jeux notamment les Robots.

## Exemple
```
@Plugin(nom = "nomPlugin", type = TypePlugin)
public class MonPlugin {
    private ITerrain terrain;
 
    public MonPlugin() {
    	// doit rester vide
	}

    public MonPlugin(ITerrain terrain) {
        this.terrain = terrain;
        ...
    }
   
    public boolean activer() {
        // TODO 
		// ici les traitements
        return true;
    }



#Tester le jeu (version 04/01/2018) #
  - Lancer le projet avec Maven (classe principal: App (dans sous-module:  moteur))
  - Vous voyer des robots qui sont généres (aleat. maxi=10)
  - cliqiez sur gestionnairePlugin -> Parcourir -> plugin -> target -> selectionner le jar puis ouvrir
  - les plugin sont ajoutés dans l'interface
  - selectionner le plugin graphique puis activer --> tous les robots changent de couleurs
  ....
#--> fin teste version 04/01#
}

