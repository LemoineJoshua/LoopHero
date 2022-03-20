package leJeu.inventories;

import java.util.ArrayList;

import leJeu.boardGame.Coord;

public class Carte {
    private final String image;
    private final float chanceDrop;
    private final ArrayList<Coord> casesImpactes;

    public Carte(String image,float chanceDrop){
        this.image=image;
        this.chanceDrop=chanceDrop;
        this.casesImpactes=new ArrayList<Coord>();
        casesImpactes.add(new Coord(0,0));
    }

    public void addCaseImpacte(Coord coord){
        casesImpactes.add(coord);
    }




    public boolean isDropped();
    // A faire avec la reponse de Carine Pivoteau

    
}
