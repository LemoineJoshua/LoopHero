package theGame.inventories;

import java.util.ArrayList;
import theGame.Cards.Card;

public class CardInventory {
	private final ArrayList<Card> deck;
    private final ArrayList<Card> cardList;

    public CardInventory(){
        this.cardList=new ArrayList<Card>();
        cardList.add(new Card("meadow","Field","pictures/MeadowCard.png"));
        cardList.add(new Card("rock","Field","pictures/RockCard.png"));
        cardList.add(new Card("grove","Road","pictures/GroveCard.png"));
        this.deck = initDeck();
    }

    private ArrayList<Card> initDeck(){
    	ArrayList<Card> deck = new ArrayList<>();
    	for (int i =0; i<3;i++) { // A chaque fois une de moins que dans le deck car il y'en a une dans la main.
    		deck.add(new Card("grove","Road","pictures/GroveCard.png"));
    	}
    	
    	for (int i =0; i<14;i++) {
    		deck.add(new Card("meadow","Field","pictures/MeadowCard.png"));
    	}
    	
    	for (int i =0; i<11;i++) {
    		deck.add(new Card("rock","Field","pictures/RockCard.png"));
    	}
    	return deck;
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
    
    public void addCardInDeck(Card myCard) {
    	deck.add(myCard);
    }
    
    public ArrayList<Card> deck(){
    	return deck;
    }
    
    public void overflowForbidden() {
    	if (cardList.size()>13) {
    		deck.add(cardList.get(0));
    		cardList.remove(0);
    	}
    }
}
