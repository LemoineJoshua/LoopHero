package leJeu;

import java.util.ArrayList;

public class InventaireRessources {
    private final ArrayList<String> inventaireRessourceNom;
	private final ArrayList<Integer> inventaireRessourceQuantite;
	
	
	public InventaireRessources() {
		this.inventaireRessourceNom = new ArrayList<String>();
        initNom();
		this.inventaireRessourceQuantite = new ArrayList<Integer>();
        initQuantite();
        }
	

    public void initNom(){        
		inventaireRessourceNom.add("Branche");
        inventaireRessourceNom.add("Bois");
        inventaireRessourceNom.add("Caillou");
        inventaireRessourceNom.add("Rocher");
        inventaireRessourceNom.add("MetalScrap");
        inventaireRessourceNom.add("Metal");
        inventaireRessourceNom.add("Ration");
        inventaireRessourceNom.add("Repas");
        inventaireRessourceNom.add("MemoryFragment");
        inventaireRessourceNom.add("MemoryBook");
        inventaireRessourceNom.add("NoticableChange");
        inventaireRessourceNom.add("Metamorphose");
    }

    public void initQuantite(){
        for (int i=0; i<12; i++){
            inventaireRessourceQuantite.add(0);
        }
    }

    public void addRessources(String nom, int quantite){
        int indexRessource=inventaireRessourceNom.indexOf(nom);
        inventaireRessourceQuantite.set(indexRessource,inventaireRessourceQuantite.get(indexRessource)+quantite);   
    }
}
