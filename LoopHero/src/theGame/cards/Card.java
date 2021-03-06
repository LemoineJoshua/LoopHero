package theGame.cards;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Objects;

import theGame.GameView;

public class Card implements Serializable {
	private static final long serialVersionUID = 7633905939019663426L;
	private final String name;
	private final String type;
	private final String path;
	private transient BufferedImage img;
	
	/**
	 * Card Constructor
	 * 
	 * @param name : card Name
	 * @param type : card type
	 * @param path : the path to the card picture
	 */
	public Card(String name,String type,String path) {
		this.name=Objects.requireNonNull(name);
		this.type=Objects.requireNonNull(type);
		this.path=Objects.requireNonNull(path);
		this.img=GameView.stringToImage(path);
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
	public BufferedImage img(){
		if(img==null) {
			this.img=GameView.stringToImage(path);
		}
		return img;
	}
}
