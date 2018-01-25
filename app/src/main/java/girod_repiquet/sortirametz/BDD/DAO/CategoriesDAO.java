package girod_repiquet.sortirametz.BDD.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import girod_repiquet.sortirametz.Model.Categorie;
import girod_repiquet.sortirametz.BDD.MySQLiteHelper;

/**
 * Created by Zachizac on 22/01/2018.
 * DAO du modèle Categorie
 */

public class CategoriesDAO {

    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NOM
    };

    public CategoriesDAO(MySQLiteHelper db){
        dbHelper = db;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Fonction de création d'une catégorie dans la base de données
     * @param nom de la nouvelle catégorie
     * @return la catégorie crée
     */
    private Categorie createCategorie(String nom) {

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NOM, nom);

        long insertId = database.insert(MySQLiteHelper.TABLE_CATEGORIES, null, values);

        System.out.println(MySQLiteHelper.COLUMN_ID + " = " + insertId);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORIES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Categorie newCategorie = cursorToCategorie(cursor);
        cursor.close();
        return newCategorie;
    }

    /**
     * Fonction pour supprimer une catégorie de la base de données
     * @param categorie a supprimer
     */
    private void deleteCategorie(Categorie categorie) {
        long id = categorie.getId();
        System.out.println("Categorie " + id + "a été supprimée !");
        database.delete(MySQLiteHelper.TABLE_CATEGORIES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    /**
     * Fonction pour récupérer le nom de toutes les catégories
     * @return la liste des catégories de la BDD
     */
    public List<String> getAllCategoriesNames() {

        List<String> categories = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORIES,
                allColumns, null, null, null, null, null);

        System.out.println(cursor);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Categorie categorie = cursorToCategorie(cursor);
            categories.add(categorie.getNom());
            cursor.moveToNext();
        }
        cursor.close();
        return categories;
    }

    /**
     * Fonction pour récupérer toutes les catégories
     * @return la liste des catégories de la BDD
     */
    public List<Categorie> getAllCategories() {

        List<Categorie> categories = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORIES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Categorie categorie = cursorToCategorie(cursor);
            categories.add(categorie);
            cursor.moveToNext();
        }
        cursor.close();
        return categories;
    }

    /**
     * Fonction pour récuperer une catégorie avec son id
     * @param id de la catégorie recherchée
     * @return la catégorie correspondante
     */
    public Categorie getCatWithId(Long id){

        Categorie cat;

        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_CATEGORIES + " WHERE " + MySQLiteHelper.COLUMN_ID + " = " + id, new String[] {});

        cursor.moveToFirst();
        cat = cursorToCategorie(cursor);

        cursor.close();

        return cat;
    }

    /**
     * Fonction pour récuperer une catégorie avec son id
     * @param nom de la catégorie recherchée
     * @return la catégorie correspondante
     */
    public Categorie getCatWithNom(String nom){

        Categorie cat;

        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_CATEGORIES + " WHERE " + MySQLiteHelper.COLUMN_NOM + " = '" + nom + "'", new String[] {});

        cursor.moveToFirst();
        cat = cursorToCategorie(cursor);

        cursor.close();

        return cat;
    }

    /**
     * Fonction pour convertir un cursor en categorie
     * @param cursor de navigation dans la BDD
     * @return la catégorie
     */
    private Categorie cursorToCategorie(Cursor cursor) {
        Categorie categorie = new Categorie();
        categorie.setId(cursor.getLong(0));
        categorie.setNom(cursor.getString(1));

        return categorie;
    }

    /**
     * Fonction pour créer les premières catégories de l'appli
     */
    public void initCat(){

        createCategorie("Catégorie");
        createCategorie("Restaurant");
        createCategorie("Bar");
        createCategorie("Hotel");
        createCategorie("Eglise");

    }

}
