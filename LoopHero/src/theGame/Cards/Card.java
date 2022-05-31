package theGame.Cards;

import java.io.Serializable;

public class Card implements Serializable {
	private final String name;
	private final String type;
	private final String img;
	
	/**
	 * Card Constructor
	 * 
	 * @param name : card Name
	 * @param type : card type
	 * @param path : the path to the card picture
	 */
	public Card(String name,String type,String path) {
		this.name=name;
		this.type=type;
		this.img=path;
	}
	
	/**
	 * Type Accessor
	 * 
	 * @return the type of the card
	 */
	public String type() {
		return type;
	}
	
	/**
	 * Name Accessor
	 * 
	 * @return the name of the card
	 */
	public String name() {
		return name;
	}
	
	/**
	 * Picture Accessor
	 * 
	 * @return the buffered image of the card
	 */
	public String img(){
		return img;
	}
}
