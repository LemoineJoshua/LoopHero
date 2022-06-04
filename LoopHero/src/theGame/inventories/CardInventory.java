package theGame.inventories;

import java.io.Serializable;
import java.util.ArrayList;
import theGame.Cards.Card;

public class CardInventory implements Serializable {
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
        cardList.add(new Card("cemetery","Road","pictures/Card/CemeteryCard.png"));
        cardList.add(new Card("ruins","Road","pictures/Card/RuinsCard.png"));
        cardList.add(new Card("oblivion","Oblivion","pictures/Card/OblivionCard.png"));
        cardList.add(new Card("oblivion","Oblivion","pictures/Card/OblivionCard.png"));
        cardList.add(new Card("oblivion","Oblivion","pictures/Card/OblivionCard.png"));
        cardList.add(new Card("oblivion","Oblivion","pictures/Card/OblivionCard.png"));
        cardList.add(new Card("spiderCocoon","RoadSide","pictures/Card/SpiderCocoonCard.png"));
        cardList.add(new Card("vampireMansion","RoadSide","pictures/Card/VampireMansionCard.png"));
        cardList.add(new Card("battleField","RoadSide","pictures/Card/battleFieldCard.png"));
        cardList.add(new Card("village","Road","pictures/Card/villageCard.png"));
        cardList.add(new Card("wheatFields","WheatField","pictures/Card/wheatFieldsCard.png"));
        cardList.add(new Card("beacon","Field","pictures/Card/BeaconCard.png"));
       
        this.deck = initDeck();
    }

    /**
     * Initialize the deck
     * 
     * @return the initialized deck
     */
    private ArrayList<Card> initDeck(){
    	ArrayList<Card> deck = new ArrayList<>();
    	for (int i =0; i<3;i++) { // Each time, one less in the deck because there is already one in the player hand.
    		deck.add(new Card("grove","Road","pictures/Card/GroveCard.png"));
    	}
    	
    	for (int i =0; i<14;i++) {
    		deck.add(new Card("meadow","Field","pictures/Card/MeadowCard.png"));
    	}
    	
    	for (int i =0; i<11;i++) {
    		deck.add(new Card("rock","Field","pictures/Card/RockCard.png"));
    	}
    	
    	for (int i =0; i<2; i++) {
    		deck.add(new Card("cemetery","Road","pictures/Card/CemeteryCard.png"));
    	}
    	
    	for (int i =0; i<2; i++) {
    		deck.add(new Card("ruins","Road","pictures/Card/RuinsCard.png"));
    	}
    	for (int i =0; i<1; i++) {
    		deck.add(new Card("oblivion","Oblivion","pictures/Card/OblivionCard.png"));
    	}
    	
    	for (int i =0;i<2; i++) {
    		deck.add(new Card("vampireMansion","RoadSide","pictures/Card/VampireMansionCard.png"));
    	}
    	
    	for (int i =0;i<5; i++) {
    		deck.add(new Card("spiderCocoon","RoadSide","pictures/Card/SpiderCocoonCard.png"));
    	}
    	
    	for (int i =0;i<1; i++) {
            deck.add(new Card("battleField","RoadSide","pictures/Card/battleFieldCard.png"));
    	}
    	
    	for (int i =0;i<2; i++) {
    		deck.add(new Card("village","Road","pictures/Card/villageCard.png"));
    	}
    	
    	for (int i =0;i<5; i++) {
    		deck.add(new Card("wheatFields","WheatField","pictures/Card/wheatFieldsCard.png"));
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
