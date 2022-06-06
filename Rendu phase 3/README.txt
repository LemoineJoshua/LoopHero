implémentations :


Toutes les cartes demandées ont été implémentées avec leur effet. 
Les équipements de différents niveaux de raretés ainsi que les anneaux  peuvent être obtenus après chaque combat.
La sauvegarde des données est automatique et se fait à chaque tour de boucle et la sauvegarde peut être chargée via le menu de lancement du jeu, 
il est également possible de sauvegarder en appuyant sur F.
La forme de la boucle quant à elle se lit dans un fichier .txt, le joueur peut d’ailleurs choisir entre 7 formes de boucle différentes.


Concernant les bonus, le nombre de monstres par case peut monter jusqu'à 4, et le nombre de monstres en combat peut monter jusqu'à 5 si un manoir 
de vampire est proche ou en cas de combat sur un Overgrown Wheatfield.


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




Structure du code :


En plus de toutes les nouvelles classes concernant les nouvelles cartes implémentées il existe maintenant une classe par monstre. 
Ce qui permet de bien mieux gérer leurs différentes caractéristiques en combat (pour leur apparition en fantôme par exemple). 
Ainsi qu’un dossier “functional” qui regroupe les dossiers utilitaires.
On se retrouve donc avec l’ajout de classes suivantes:


Dans the Game.tiles:
* BattleField : le champ de bataille qui fait apparaître des coffres (ou des mimic) et permet l’apparition de fantômes dans les combats.
* Beacon: un phare qui augmente la vitesse du héro de 40%
* Cemetery : un cimetière qui fait apparaître des squelettes.
* OvergrownWheatField: un champ abandonné qui vous réserve quelques surprises si vous provoquez un combat à l'intérieur.
* Ruins: des ruines qui font apparaître des vers.
* SpiderCocoon: un nid d’araigné qui en fait apparaître sur les routes aux alentours
* VampireMansion: un manoir de vampire qui rejoint tous les combats à proximité
* Village: un village qui vous soigne et vous donne des quêtes
* WheatField: un champ qui augmente la capacité de soin des villages proches et qui fait apparaître des épouvantails


Dans theGame.entities:
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


Enfin dans functional (les .txt sont les différentes routes possibles):
* save : le dossier de sauvegarde de GameData.
* timeSave : le dossier de sauvegarde de TimeData.
* way.txt 
* way2.txt
* way3.txt
* way4.txt
* way5.txt
* way6.txt
* rectangle.txt




Choix techniques :


Premièrement l’implémentation d’une classe par ennemis. Surtout pour gérer l'existence de leurs âmes, s' ils sont “vivants” ou non…, ainsi que le 
fait de savoir s’ils sont “artificiel” ou “objet” pour le vampire mansion. De plus, le Oblivion n’a pas été géré comme les autres tuiles. Cette 
carte n’a pas sa propre classe mais va appeler une méthode située dans chacune des tuiles, si nécessaire, comme pour rock.


Problèmes :


Le seul problème que nous avons rencontré se situe au niveau des statistiques. Les calculer est facile, les calculs sont donnés. Mais leur 
impact dans le jeu était moins clair. Par exemple, nous avons changé deux fois la manière de calculer les dégâts du héros afin que le jeu 
soit un minimum équilibré.