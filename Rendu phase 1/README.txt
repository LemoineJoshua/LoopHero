Implémentation :

        Tout ce qui à été demandé à été implémenté. Cependant l'apparition des camps de Gobelin tous les 10 rocks ainsi que la création 
	de la montagne quand un carré 3x3 de rock est posé n’a pas été géré par manque de temps/d’idée.


Structure du code : 


Différents packages:
* game (regroupe les données du jeux) :
   * Game : une classe qui possède la méthode d’actualisations de l’écran
   * GameData : une classe qui regroupe toutes les données du jeux
   * GameView : une classe qui regroupe les fonctions d’affichage
   * TimeData : une classe qui regroupe les fonctione/données temporelles
   * Main : le main du jeux


* boardGame (regroupe ce qui est relatif au plateau) :
   * Board : une classe qui représente le plateau
   * Coord : un record qui représente un couple de coordonnées


* Cards (regroupe ce qui est relatif aux cartes) :
   * Card : une classe qui représente toutes les cartes


* Entities (regroupe les différentes entitées) :
   * Entities : Une classe abstraite qui représentes toutes les entitées
   * Hero : une classe qui représente le héro et qui hérite de Entities
   * Monster : une classe qui représente tout les monstres et qui hérite de Entities


* Inventories (regroupe les inventaires) :
   * CardInventory : une classe qui représente la main du joueur et le deck
   * RessourcesInventory : une classe qui représente l’ensemble des ressources collectées par un joueur


* Tiles (regroupe les tuiles du jeu) :
   * AbstractTile : classe abstraite représentant toute les tuiles de jeux
   * AbtractRoad : classe abstraite représentant les tuiles de route et qui hérite de AbstractTile
   * CampFire : classe représentant le feu de camp et qui hérite de AbstractRoad
   * Grove : classe représentant une grove, hérite de AbstractRoad
   * Wastelands : classe représentant le chemin vide, hérite de AbstractRoad
   * EmptyField  : classe représentant une case extérieur vide, hérite de AbstractTile
   * EmptyRoadSide : classe représentant un bord de route vide, hérite de AbstractTile
   * Meadow : classe représentant un meadow/blooming meadow, hérite de AbstractTile
   * Rock : classe représentant un rock, hérite de AbstractTile


Ainsi qu’un  dossier d’image contenant:
* Card (les images des cartes)
* Entities (les entitées)
* HUD (les menus et autres images)
* Tiles (les tuiles)


Choix techniques :


        Nous avons choisi de répartir les données de jeux en quatre catégories en suivant le Modèle-Vue-Contrôleur :  gameView pour l’affichage, 
	gameData pour les données, TimeData pour les données relatives au temps qui passe et game où se trouve les méthodes liées aux actions de 
	l’utilisateur. Cette répartition  est issue de celle que nous avons observé dans SimpleGamePictures, et ce que vous nous aviez dit lors de 
	la première SAE. Nous avons également choisi de représenter les tuiles et leur arrangement de manière spécifique, comme expliqué plus haut 
	dans la partie “Structure de code” Pour gérer le graphisme et l’apparition de certaines images qui doivent être affichées très souvent. 
	Nous avons choisi de stocker les images dans leur classe correspondante, pour éviter de devoir la rebuffer à chaque fois.


Commandes utiles :
        Espace : Arret du jeu 
        S : Passage en mode planification
        D: Sortie du mode planification
        → : Accélération du temps
        ← : Ralentissement du temps

        Clic gauche : pour sélectionner une tuile/case

        Quand vous passez en mode planification, vous pouvez cliquer sur une carte (ce qui fera apparaître un curseur dessus), 
	puis cliquer sur une case pour la placer. Si vous cliquez ailleurs que sur le plateau ou sur une case sur laquelle vous ne pouvez 
	placer la carte sélectionnée. La carte se désélectionne.


Problème :


	Nous avons rencontré deux problèmes principaux. Le premier est la compréhension de l’interface graphique. Le deuxième est 
	le fait que nous avons recommencé 2 fois l’architecture des tuiles afin d’avoir quelque chose de propre et qui permet l’ajout 
	facile de nouvelles cartes.