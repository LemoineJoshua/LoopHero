Implémentation :


        Tout ce qui à été demandé à été implémenté. Les combats sont fonctionnels, avec l’affichage des statistiques du héro et des différents Mobs 
(tout le temps pour le héro, le temps du combat pour les mobs). Les combats sont d’ailleurs fonctionnels et peuvent accueillir jusqu'à 5 monstres.
        Le drop des équipements gris est aussi fonctionnel. Les différents  inventaires nécessaires pour l’équipement sont aussi gérés. 


Structure du code : 


En plus de la structure de la dernière fois il y a de nouvelles classes.


* Entities (regroupe les entitées):
   * AbstractEntitie : La classe entitée a changé de nom et possède maintenant différentes fonctions qui sont utiles pour le combats

* Inventories (regroupe les inventaires) :
   * HeroStuff : l’équipement du hero
   * ItemInventory : l’endroit ou se stock les différents items que loot les monstres
   * Item : Une classe qui représente un item


*  Fight (qui gère les combats:
   *  Fight : l’objet dans lequel se passe tout le combat. Avec sa propre boucle et une fonction 
d’affichage dédiée dans GameView. A la manière d’un jeux dans un jeux.


Ainsi qu’un  dossier d’image contenant:
* Stuff : qui contient toutes les images des différents équipements.




Choix techniques :


L’inventaire d’objet  et l’équipement se gère de la même manière que les inventaires précédents À ceci près que l’équipement modifie les 
statistiques du héro a chaque changement d’équipement.
        Le combat est géré à la manière d’un jeu dans le jeu. C'est-à-dire qu’on lui donne toute les informations nécessaires, 
puis il lance le combat dans sa propre boucle en mettant le jeu principal en pause. A la fin, s' il renvoie true le héro est vivant, 
sinon il est mort. Tout ce qui est drop, dégâts… est géré à l'intérieur de l’objet combat.


     
Problème :

        A un moment, d’après github desktop, la bibliothèque Zen5 s’est modifiée et le
 Le programme est devenu complètement inutilisable. Nous avons donc remis les fichiers de Zen5 
comme ils étaient avant cette modification impromptue en fouillant directement dans les fichiers. Ce
qui a fonctionné.