package theGame.inventories;

import java.util.ArrayList;
import theGame.Cards.Card;

public class CardInventory {
    private final ArrayList<Card> cardList;

    public CardInventory(){
        this.cardList=new ArrayList<Card>();
        cardList.add(new Card("meadow","Field","pictures/MeadowCard.png"));
        cardList.add(new Card("rock","Field","pictures/RockCard.png"));
        cardList.add(new Card("grove","Road","pictures/GroveCard.png"));
        
    }

    public void add(Card carte){
        cardList.add(carte);
        tooMuchCard();
    }

    public void remove(int index){
        // Utilise pour les cartes jouees
        cardList.remove(index);
    }
    
    private void tooMuchCard(){
        if(cardList.size()>13){
            cardList.remove(0);
        }
    }

    public ArrayList<Card> cardList(){
    	return cardList;
    }
    
}
