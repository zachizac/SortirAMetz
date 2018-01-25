package girod_repiquet.sortirametz.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import girod_repiquet.sortirametz.Model.Categorie;
import girod_repiquet.sortirametz.Model.Site;
import girod_repiquet.sortirametz.MySQLiteHelper;

/**
 * Created by Zachizac on 22/01/2018.
 * DAO du modèle Site
 */

public class SitesDAO {

    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private CategoriesDAO categoriesDAO;

    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NOM,
            MySQLiteHelper.COLUMN_LATITUDE,
            MySQLiteHelper.COLUMN_LONGITUDE,
            MySQLiteHelper.COLUMN_ADRESSE,
            MySQLiteHelper.COLUMN_CATEGORIE,
            MySQLiteHelper.COLUMN_RESUME
    };

    public SitesDAO(MySQLiteHelper db){
        dbHelper = db;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Fonction de création d'un site dans la base de données
     * @param nom
     * @param latitude
     * @param longitude
     * @param adressePostal
     * @param categorie
     * @param resume
     * @return le site ainsi crée
     */
    public Site createSite(String nom, double latitude, double longitude, String adressePostal, Categorie categorie, String resume) {

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NOM, nom);
        values.put(MySQLiteHelper.COLUMN_LATITUDE, latitude);
        values.put(MySQLiteHelper.COLUMN_LONGITUDE, longitude);
        values.put(MySQLiteHelper.COLUMN_ADRESSE, adressePostal);
        values.put(MySQLiteHelper.COLUMN_CATEGORIE, categorie.getId());
        values.put(MySQLiteHelper.COLUMN_RESUME, resume);

        long insertId = database.insert(MySQLiteHelper.TABLE_SITES, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SITES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Site newSite = cursorToSite(cursor);
        cursor.close();
        return newSite;
    }


    /**
     * Fonction pour supprimer un site de la base de données
     * @param site
     */
    public void deleteSite(Site site) {
        long id = site.getId();
        System.out.println("Site " + id + "a été supprimé !");
        database.delete(MySQLiteHelper.TABLE_SITES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    /**
     * Fonction pour récupérer tous les sites
     * @return la liste des sites de la BDD
     */
    public List<Site> getAllSites() {
        List<Site> sites = new ArrayList<Site>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SITES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Site site = cursorToSite(cursor);
            sites.add(site);
            cursor.moveToNext();
        }
        cursor.close();

        return sites;
    }

    /**
     * Fonction pour convertir un cursor en site
     * @param cursor
     * @return
     */
    private Site cursorToSite(Cursor cursor) {
        Site site = new Site();
        site.setId(cursor.getLong(0));
        site.setNom(cursor.getString(1));
        site.setLatitude(Double.parseDouble(cursor.getString(2)));
        site.setLongitude(Double.parseDouble(cursor.getString(3)));
        site.setAdressePostal(cursor.getString(4));
        site.setCategorie(Long.parseLong(cursor.getString(5)));
        site.setResume(cursor.getString(6));

        return site;
    }

    /**
     * Fonction pour créer les premièrs sites de l'appli
     */
    public void initCat(){

        categoriesDAO = new CategoriesDAO(dbHelper);
        categoriesDAO.open();
        double myLatitude;
        double myLongitude;

        List<Categorie> categories = categoriesDAO.getAllCategories();

        Site site1 = createSite("So Chic Burger", 49.1191925, 6.1723359, "6 Rue Sainte Marie", categories.get(1),"Restaurant de burger");
        Site site2 = createSite("Kyou Sushi", 49.1192651, 6.1710884, "6 Rue du Moyen Pont", categories.get(1),"Restaurant Japonais");
        Site site3 = createSite("Temple", 49.1207535, 6.1721968, "2 Place de la Comédie", categories.get(4),"Temple de Metz");

    }





}
