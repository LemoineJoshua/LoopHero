package theGame.inventories;

import java.util.ArrayList;
import theGame.Cards.Card;

public class CardInventory {
	private final ArrayList<Card> deck;
    private final ArrayList<Card> cardList;


    /**
     * Card Inventory constructor
     * Initialize the player's hand, and the cards inside
     * And also the deck and the cards inside
     */
    public CardInventory(){
        this.cardList=new ArrayList<Card>();
        cardList.add(new Card("meadow","Field","pictures/Card/MeadowCard.png"));
        cardList.add(new Card("rock","Field","pictures/Card/RockCard.png"));
        cardList.add(new Card("grove","Road","pictures/Card/GroveCard.png"));
        this.deck = initDeck();
    }

    /**
     * Initialize the deck
     * 
     * @return the initialized deck
     */
    private ArrayList<Card> initDeck(){
    	ArrayList<Card> deck = new ArrayList<>();
    	for (int i =0; i<3;i++) { // A chaque fois une de moins que dans le deck car il y'en a une dans la main.
    		deck.add(new Card("grove","Road","pictures/Card/GroveCard.png"));
    	}
    	
    	for (int i =0; i<14;i++) {
    		deck.add(new Card("meadow","Field","pictures/Card/MeadowCard.png"));
    	}
    	
    	for (int i =0; i<11;i++) {
    		deck.add(new Card("rock","Field","pictures/Card/RockCard.png"));
    	}
    	return deck;
    }
    
    /**
     * Add a card to the player's hand
     * 
     * @param card : the card to had in the player's hand
     */
    public void add(Card card){
        cardList.add(card);
        overflowForbidden();
    }

    /**
     * Remove a card from the player's hand
     * 
     * @param index : The index of the card to remove
     */
    public void remove(int index){
        cardList.remove(index);
    }
    
    /**
     * Put back a card of the player's hand in the deck
     * 
     * @param myCard : the card to put back in the deck
     */
    public void addCardInDeck(Card myCard) {
    	deck.add(myCard);
    }
    
    /**
     * Check if the player's hand is full, and if needed remove the first card of the hand
     */
    public void overflowForbidden() {
    	if (cardList.size()>13) {
    		deck.add(cardList.get(0));
    		cardList.remove(0);
    	}
    }
    
    /**
     * CardList accessor
     * 
     * @return the player's hand
     */
    public ArrayList<Card> cardList(){
    	return cardList;
    }
    
    /**
     * Deck accessor
     * 
     * @return the deck
     */
    public ArrayList<Card> deck(){
    	return deck;
    }
}
