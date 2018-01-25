package girod_repiquet.sortirametz.Model;

import girod_repiquet.sortirametz.DAO.CategoriesDAO;

/**
 * Created by Zachizac on 22/01/2018.
 * Modèle des sites à afficher sur la carte
 */

public class Site {

    private long id;

    private String nom;

    private double latitude;

    private double longitude;

    private String adressePostal;

    private Long categorie;

    private String resume;


    /**
     * Constructeur de site (données récupérées depuis la BDD
     * @param nom
     * @param latitude
     * @param longitude
     * @param adressePostal
     * @param categorie
     * @param resume
     */
    public Site(long id, String nom, double latitude, double longitude, String adressePostal, Long categorie, String resume) {
        this.id = id;
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adressePostal = adressePostal;
        this.categorie = categorie;
        this.resume = resume;
    }

    public Site(){

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAdressePostal() {
        return adressePostal;
    }

    public void setAdressePostal(String adressePostal) {
        this.adressePostal = adressePostal;
    }

    public Long getCategorie() {
        return categorie;
    }

    public void setCategorie(Long categorie) {
        this.categorie = categorie;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String toString(){
        return "Nom : " + this.nom + "\nAdresse : " + this.adressePostal + "\nRésumé : " + this.resume;
    }
}
