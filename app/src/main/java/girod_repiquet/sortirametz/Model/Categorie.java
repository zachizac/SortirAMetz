package girod_repiquet.sortirametz.Model;

/**
 * Created by Zachizac on 22/01/2018.
 * Modèle des catégories pour trier les sites à afficher
 */

public class Categorie {

    private long id;

    private String nom;

    /**
     * Constructeur d'une catégorie (depuis la BDD)
     * @param nom
     */
    public Categorie(long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Categorie(String nom){
        this.nom = nom;
    }

    public Categorie(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
