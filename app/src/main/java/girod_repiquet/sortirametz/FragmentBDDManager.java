package girod_repiquet.sortirametz;

import android.app.Dialog;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import girod_repiquet.sortirametz.DAO.CategoriesDAO;
import girod_repiquet.sortirametz.DAO.SitesDAO;
import girod_repiquet.sortirametz.Listener.ListenerButtonAdd;
import girod_repiquet.sortirametz.Listener.ListenerButtonDel;
import girod_repiquet.sortirametz.Listener.ListenerListViewClick;
import girod_repiquet.sortirametz.Model.Site;

/**
 * Created by Zachizac on 22/01/2018.
 */

public class FragmentBDDManager extends Fragment {

    private MySQLiteHelper dbHelper;
    private CategoriesDAO categoriesDAO;
    private SitesDAO sitesDAO;

    private ListView listBDD;
    private List<Site> listSites;

    private Button addButton;
    private Button delButton;

    private Site selectedSite;

    private Location myLocation;

    public FragmentBDDManager(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate( R.layout.fragment_bddmanager, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        dbHelper = new MySQLiteHelper(this.getActivity());
        //dbHelper.onUpgrade(dbHelper.getWritableDatabase(),1,2);
        sitesDAO = new SitesDAO(dbHelper);
        categoriesDAO = new CategoriesDAO(dbHelper);
        sitesDAO.open();
        categoriesDAO.open();

        addButton = this.getView().findViewById(R.id.addButton);
        delButton = this.getView().findViewById(R.id.delButton);

        addButton.setOnClickListener(new ListenerButtonAdd(this));
        delButton.setOnClickListener(new ListenerButtonDel(this));

        listSites = sitesDAO.getAllSites();

        //on crée une liste des noms de chacun des sites de la BDD pour remplier la listView
        List<String> values = new ArrayList<String>();

        for (int i = 0; i <= listSites.size() - 1; i++) {
            values.add("Catégorie : " + categoriesDAO.getCatWithId(listSites.get(i).getCategorie()).getNom()
                    + "\n" + listSites.get(i).toString() + "\n");
        }

        listBDD = this.getView().findViewById(R.id.ListSites);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                R.layout.listviewbdd_item, values);

        listBDD.setAdapter(adapter);
        listBDD.setOnItemClickListener(new ListenerListViewClick(this));
    }


    /**
     * Récupérer un site à l'aide de sa position dans la listeView
     * @param position
     */
    public void setSelectedSiteWithPosition(int position) {
        this.selectedSite = listSites.get(position);
        System.out.println("position : " + position);
    }

    /**
     * Supprimer un site sélectionner dans la listView
     */
    public void delSelectedSite(){
        this.sitesDAO.deleteSite(selectedSite);
        selectedSite = null;
        this.getDelButton().setEnabled(false);
        this.onStart();
    }

    /**
     * Créer un nouveau site
     */
    public void createSite(String categorie, String nom, String adresse, String resume){

        this.sitesDAO.createSite(nom, myLocation.getLatitude(), myLocation.getLongitude() ,adresse, categoriesDAO.getCatWithNom(categorie), resume);

    }

    public void updateLocation(Location location){
        myLocation = location;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getDelButton() {
        return delButton;
    }
}
