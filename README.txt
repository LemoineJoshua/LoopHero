Bonjour à tous,
Voici une petite copie du jeu LoopHero réalisée en Java dans le cadre de notre BUT informatique. Pour ceux qui ne connaisse pas LoopHero est un jeux
ou vous incarnez un petit personnage qui parcours une boucle. Le but étant de faire 9 tours de boucle. Afin d'augmenter son score il est possible de poser
des tuiles sur la carte, tuiles qui auront divers effets sur le monde et rendrons donc la boucle plus facile ou bien plus difficile à parcourir.
Ce jeu est entièrement codé en Java et est donc orienté objet. 
Nous sommes très fier du resultat et pensons qu'il est bon, en tout cas j'espère qu'il vous plaira.
Ci dessous vous pouvez voir les controls et la structure du programme pour les plus curieux.

Commandes utiles : 
        Espace : Arret du jeu 
        S : Pause/Passage en mode planification
        D: Reprise du jeu/Sortie du mode planification
        → : Accélération du temps
        ← : Ralentissement du temps
        F: Sauvegarder le jeu
        spacebar : arrêter le jeu

        A: Vitesse des messages en combat : 1sec
        Z: Vitesse des messages en combat : 2sec
        E: Vitesse des messages en combat : 3sec

        Clic gauche : pour sélectionner une tuile/case ou un item. (une fois le jeu en pause)

        Quand vous passez en mode planification, vous pouvez cliquer sur une carte (ce qui fera apparaître un curseur dessus), 
    puis cliquer sur une case pour la placer. Si vous cliquez ailleurs que sur le plateau ou sur une case sur laquelle vous ne pouvez 
    placer la carte sélectionnée. La carte se désélectionne.

Structure :

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
   * AbstractEntitie : possède différentes fonctions qui sont utiles pour toutes les entitées et pour le combats
   * Hero : une classe qui représente le héro et qui hérite de Entities
   * AbstractMonster : classe abstraite qui permet de regrouper une grande partie des fonctions contenues dans chaque monstre
   * Chest : un coffre
   * FieldOfBlade : des Hautes herbes avec des armes (oui ce monstre existe vraiment dans le jeu originel)
   * Ghost : un fantôme de monstre déjà existant 
   * GhostOfGhost : le fantôme, d’un fantôme.
   * PrimalMater : le fantôme, d’un fantôme, d’un fantôme.
   *  Mimic : un faux coffre.
   * RatWolf : un rat loup
   * ScareCrow : un épouvantail
   * ScorchWorm : un vers
   * Skeleton : un squelette.
   * Slime : un slime
   * Spider : une araignée
   * Vampire : un vampire qui n'apparaît que dans le combat.


* Inventories (regroupe les inventaires) :
   * CardInventory : une classe qui représente la main du joueur et le deck
   * RessourcesInventory : une classe qui représente l’ensemble des ressources collectées par un joueur
   * HeroStuff : l’équipement du hero
   * ItemInventory : l’endroit ou se stock les différents items que loot les monstres
   * Item : Une classe qui représente un item

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
   * BattleField : le champ de bataille qui fait apparaître des coffres (ou des mimic) et permet l’apparition de fantômes dans les combats.
   * Beacon: un phare qui augmente la vitesse du héro de 40%
   * Cemetery : un cimetière qui fait apparaître des squelettes.
   * OvergrownWheatField: un champ abandonné qui vous réserve quelques surprises si vous provoquez un combat à l'intérieur.
   * Ruins: des ruines qui font apparaître des vers.
   * SpiderCocoon: un nid d’araigné qui en fait apparaître sur les routes aux alentours
   * VampireMansion: un manoir de vampire qui rejoint tous les combats à proximité
   * Village: un village qui vous soigne et vous donne des quêtes
   * WheatField: un champ qui augmente la capacité de soin des villages proches et qui fait apparaître des épouvantails

*  Fight (qui gère les combats) :
   *  Fight : l’objet dans lequel se passe tout le combat. Avec sa propre boucle et une fonction 
      d’affichage dédiée dans GameView. A la manière d’un jeux dans un jeux.


un  dossier d’image contenant:
* Card (les images des cartes)
* Entities (les entitées)
* HUD (les menus et autres images)
* Tiles (les tuiles)
* Stuff : qui contient toutes les images des différents équipements.

Enfin un dossier fonctionel (les .txt sont les différentes routes possibles):
* save : le dossier de sauvegarde de GameData.
* timeSave : le dossier de sauvegarde de TimeData.
* way.txt 
* way2.txt
* way3.txt
* way4.txt
* way5.txt
* way6.txt
* rectangle.txt
