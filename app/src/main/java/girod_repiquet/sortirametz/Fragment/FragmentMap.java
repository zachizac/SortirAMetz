package girod_repiquet.sortirametz.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;

import java.util.ArrayList;
import java.util.List;

import girod_repiquet.sortirametz.Adapter.PopupAdapter;
import girod_repiquet.sortirametz.BDD.DAO.CategoriesDAO;
import girod_repiquet.sortirametz.BDD.DAO.SitesDAO;
import girod_repiquet.sortirametz.Listener.ListenerSpinnerCat;
import girod_repiquet.sortirametz.Listener.ListenerLocationChange;
import girod_repiquet.sortirametz.Listener.ListenerSpinnerRay;
import girod_repiquet.sortirametz.Model.Categorie;
import girod_repiquet.sortirametz.Model.Site;
import girod_repiquet.sortirametz.Interface.MyLocationInterface;
import girod_repiquet.sortirametz.BDD.MySQLiteHelper;
import girod_repiquet.sortirametz.PermissionUtils;
import girod_repiquet.sortirametz.R;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Zachizac on 22/01/2018.
 * Classe du fragment googleMap, fragment principal de l'application
 */
public class FragmentMap extends Fragment implements
        OnMapReadyCallback, OnInfoWindowClickListener {

    private GoogleMap mMap;

    private MySQLiteHelper dbHelper;
    private CategoriesDAO categoriesDAO;
    private SitesDAO sitesDAO;

    private Spinner catSpin;
    private Spinner raySpin;

    private Categorie selectedCat;
    private int selectedRayon;

    private List<Categorie> categories;

    private MyLocationInterface myLocationInterface;

    private PermissionUtils permission = new PermissionUtils();

    public FragmentMap(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate( R.layout.fragment_maps, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        selectedCat = null;
        selectedRayon = 0;

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager locM = (LocationManager) this.getActivity().getSystemService(LOCATION_SERVICE);

        try {
            locM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, new ListenerLocationChange(this));
        }catch(SecurityException e){
        }

        fillCatSpinner();
        fillRaySpinner();

        catSpin.setOnItemSelectedListener(new ListenerSpinnerCat(this));
        raySpin.setOnItemSelectedListener(new ListenerSpinnerRay(this));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        activerLocalisation();

        mMap.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));
        mMap.setOnInfoWindowClickListener(this);

    }

    /**
     * Fonction pour afficher un toast sur les markers cliqué
     * @param marker
     */
    @Override
    public void onInfoWindowClick(Marker marker){

    }

    /**
     * Gestion de la localisation en dehors du fragment map
      * @param context
     */
    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            myLocationInterface = (MyLocationInterface) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " doit implémenter MyLocationInterface");
        }
    }

    @Override
    public void onDetach(){
        myLocationInterface = null;
        super.onDetach();
    }

    public void updateLocation(Location loc){
        myLocationInterface.setLocation(loc);
    }


    /**
     * Active la localisation (avec le bouton de localisation) si la permission est donnée
     */
    public void activerLocalisation(){
        if(ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permission.demandePermission(this.getActivity(),1, Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if(mMap != null){
            mMap.setMyLocationEnabled(true);
        }
    }

    /**
     * Remplir le spinner de categorie
     */
    public void fillCatSpinner(){

        dbHelper = new MySQLiteHelper(this.getActivity());
        //dbHelper.onUpgrade(dbHelper.getWritableDatabase(),1,2);
        categoriesDAO = new CategoriesDAO(dbHelper);
        categoriesDAO.open();

        categories = categoriesDAO.getAllCategories();
        List<String> values = categoriesDAO.getAllCategoriesNames();

        if(values.size()==0){
            categoriesDAO.initCat();
            values = categoriesDAO.getAllCategoriesNames();
            categories = categoriesDAO.getAllCategories();
        }

        catSpin = this.getView().findViewById(R.id.catSpinner);

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), R.layout.spinner_item, values);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        catSpin.setAdapter(adapter);

        categoriesDAO.close();
        dbHelper.closeDB();

    }

    /**
     * Remplir le spinner de categorie
     */
    public void fillRaySpinner(){

        raySpin = this.getView().findViewById(R.id.rayonSpinner);

        List<String> values = new ArrayList<String>();
        values.add("Rayon");
        values.add("5");
        values.add("200");
        values.add("500");
        values.add("1000");
        values.add("1500");

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), R.layout.spinner_item, values);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        raySpin.setAdapter(adapter);
    }

    /**
     * Methode pour récupérer la localisation de l'appareil
     * @return
     */
    @SuppressLint("MissingPermission")
    public Location getMyLocation() {
        // Get location from GPS if it's available
        LocationManager lm = (LocationManager)this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Location wasn't found, check the next most accurate place for the current location
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            // Finds a provider that matches the criteria
            String provider = lm.getBestProvider(criteria, true);
            // Use the provider to get the last known location
            myLocation = lm.getLastKnownLocation(provider);
        }

        return myLocation;
    }

    /**
     * Affiche les markers des sites en fonction de leur catégories
     */
    public void afficherSites(){

        if(mMap != null) {
            mMap.clear();

            dbHelper = new MySQLiteHelper(this.getActivity());
            sitesDAO = new SitesDAO(dbHelper);
            sitesDAO.open();

            List<Site> values = sitesDAO.getAllSites();

            if (values.size() == 0) {
                sitesDAO.initCat();
                values = sitesDAO.getAllSites();
            }

            Location loc;

            if(ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                loc = null;
            }else{
                loc = getMyLocation();
            }

            updateLocation(loc);

            //on évite de placer des marqueurs si on ne connait pas la localisation de l'appareil (bug par exemple)
            if(loc != null) {

                for (int i = 0; i <= values.size() - 1; i++) {

                    creerMarker(loc, values.get(i));

                }
            }

            sitesDAO.close();
            dbHelper.closeDB();
        }

    }

    /**
     * Methode de création d'un marker pour un site
     * @param loc
     * @param site
     */
    public void creerMarker(Location loc, Site site){

        double siteLatitude;
        double siteLongitude;

        siteLatitude = site.getLatitude();

        siteLongitude = site.getLongitude();

        LatLng latLng = new LatLng(siteLatitude, siteLongitude);

        Location siteLocation = new Location("location");
        siteLocation.setLatitude(siteLatitude);
        siteLocation.setLongitude(siteLongitude);

        if (selectedCat.getId() == site.getCategorie() && loc.distanceTo(siteLocation) <= selectedRayon) {
            mMap.addMarker(new MarkerOptions().position(latLng).title(site.getNom())
                    .snippet("Résumé : " + site.getResume()
                            + "\nAdresse : " + site.getAdressePostal()
                            + "\nDistance : " + (int)loc.distanceTo(siteLocation) + " mètres"));
        }
    }

//    /**
//     * Fonction pour assurer la mise à jour de la localisation dans l'activité principale
//     * @param location
//     */
//    public void updateLocation(Location location){
//        this.getActivity().setLocation(location);
//    }

    public void setSelectedCat(Categorie selectedCat) {
        this.selectedCat = selectedCat;
    }

    public void setSelectedRayon(int selectedRayon) {
        this.selectedRayon = selectedRayon;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public GoogleMap getmMap() {
        return mMap;
    }
}
