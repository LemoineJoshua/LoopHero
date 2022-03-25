package theGame.inventories;

import java.util.ArrayList;

public class CardInventory {
    private final ArrayList<Carte> listeCarte;

    public CardInventory(){
        this.listeCarte=new ArrayList<Carte>();
    }

    public void add(Carte carte){
        listeCarte.add(carte);
        tooMuchCard();
    }

    public void remove(int index){
        // Utilise pour les cartes jouees
        listeCarte.remove(index);
    }
    
    private void tooMuchCard(){
        if(listeCarte.size()>13){
            listeCarte.remove(0);
        }
    }

    
}