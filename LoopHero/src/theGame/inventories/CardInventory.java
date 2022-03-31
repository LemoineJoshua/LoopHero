package theGame.inventories;

import java.util.ArrayList;
import theGame.Cards.Card;

public class CardInventory {
	private final ArrayList<Card> deck;
    private final ArrayList<Card> cardList;

    /**
     * Constructeur de Card Inventory,
     * Initialise la main du joeur et son contenu
     * Ainsi que le deck et son contenu
     */
    public CardInventory(){
        this.cardList=new ArrayList<Card>();
        cardList.add(new Card("meadow","Field","pictures/MeadowCard.png"));
        cardList.add(new Card("rock","Field","pictures/RockCard.png"));
        cardList.add(new Card("grove","Road","pictures/GroveCard.png"));
        this.deck = initDeck();
    }

    /**
     * @return un deck initialisée
     */
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
    
    /**
     * Ajoute une carte à la main du joueur
     * 
     * @param carte carte ajoutée à la main du joueur
     */
    public void add(Card carte){
        cardList.add(carte);
        overflowForbidden();
    }

    /**
     * Enleve une carte de la main du joueur
     * 
     * @param index index de la carte à retirer
     */
    public void remove(int index){
        cardList.remove(index);
    }
    
    /**
     * Remet une carte de la main au deck
     * 
     * @param myCard La carte à remettre dans le deck
     */
    public void addCardInDeck(Card myCard) {
    	deck.add(myCard);
    }
    
    /**
     * Verifie si la main est pleine et enlève une carte dans ce cas
     */
    public void overflowForbidden() {
    	if (cardList.size()>13) {
    		deck.add(cardList.get(0));
    		cardList.remove(0);
    	}
    }
    
    /**
     * @return la main du joueur
     */
    public ArrayList<Card> cardList(){
    	return cardList;
    }
    
    /**
     * @return le deck
     */
    public ArrayList<Card> deck(){
    	return deck;
    }
}
