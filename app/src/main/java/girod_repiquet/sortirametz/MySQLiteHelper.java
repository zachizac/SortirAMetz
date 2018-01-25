package girod_repiquet.sortirametz;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import girod_repiquet.sortirametz.Model.Categorie;

/**
 * Created by Zachizac on 22/01/2018.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sortirAMetz.db";

    // Table Names
    public static final String TABLE_SITES = "sites";
    public static final String TABLE_CATEGORIES = "categories";

    //Common column names
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOM = "nom";

    // SITES Table - Column names
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_ADRESSE = "adressePostale";
    public static final String COLUMN_CATEGORIE = "categorie";
    public static final String COLUMN_RESUME = "resume";


    /**
     * Commande sql pour la création de la table SITES
     */
//    private static final String CREATE_TABLE_SITES = "create table "
//            + TABLE_SITES + "(" + COLUMN_ID
//            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NOM + " TEXT, " + COLUMN_LATITUDE
//            + " INTEGER, " + COLUMN_LONGITUDE + " INTEGER, " + COLUMN_ADRESSE
//            + " TEXT, " + COLUMN_CATEGORIE + " INTEGER, " + COLUMN_RESUME + " TEXT);";

    private static final String CREATE_TABLE_SITES = "create table "
            + TABLE_SITES + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NOM + " TEXT, " + COLUMN_LATITUDE
            + " INTEGER, " + COLUMN_LONGITUDE + " INTEGER, " + COLUMN_ADRESSE
            + " TEXT, " + COLUMN_RESUME + " TEXT, " + COLUMN_CATEGORIE + " INTEGER,"
            + " FOREIGN KEY(" + COLUMN_CATEGORIE + ") REFERENCES " + TABLE_CATEGORIES + "(" + COLUMN_ID + "));";

    /**
     *     Commande sql pour la création de la table CATEGORIES
     */
    private static final String CREATE_TABLE_CATEGORIES = "create table "
            + TABLE_CATEGORIES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NOM
            + " TEXT);";


    /**
     * Constructeur de la clase MySQLiteHelper
     * @param context
     */
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {

        //activation des clés étrangères
        database.execSQL("PRAGMA foreign_keys = 1");

        //Creation des tables
        database.execSQL(CREATE_TABLE_CATEGORIES);
        database.execSQL(CREATE_TABLE_SITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        //Supression des tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);

        //On recrée la base
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
