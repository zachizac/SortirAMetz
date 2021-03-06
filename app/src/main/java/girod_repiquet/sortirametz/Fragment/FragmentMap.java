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

import com.google.android.gms.maps.CameraUpdateFactory;
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
    private Location myLoc;

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

        fillCatSpinner();
        fillRaySpinner();

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager locM = (LocationManager) this.getActivity().getSystemService(LOCATION_SERVICE);

        try {
            locM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, new ListenerLocationChange(this));
        }catch(SecurityException e){
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        activerLocalisation();

        mMap.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));
        mMap.setOnInfoWindowClickListener(this);


        myLoc = getMyLocation();
        if(myLoc != null) {
            changeCamera();
            updateLocation(myLoc);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }

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
     * pour créer des sites avec la localisation actuelle de l'utilisateur
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

    /**
     * met à jour la localisation dans le mainActivity
     * @param loc
     */
    public void updateLocation(Location loc){
        myLocationInterface.setLocation(loc);
        myLoc = loc;
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
     * Remplir le spinner de categorie depuis la base de données avec un curseur
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

        catSpin.setOnItemSelectedListener(new ListenerSpinnerCat(this));

        categoriesDAO.close();
        dbHelper.closeDB();

    }

    /**
     * Remplir le spinner de categorie
     */
    public void fillRaySpinner(){

        raySpin = this.getView().findViewById(R.id.rayonSpinner);

        List<String> values = new ArrayList<String>();
        values.add(getString(R.string.rayon));
        values.add(getString(R.string.dist1));
        values.add(getString(R.string.dist2));
        values.add(getString(R.string.dist3));
        values.add(getString(R.string.dist4));
        values.add(getString(R.string.dist5));
        values.add(getString(R.string.dist6));

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), R.layout.spinner_item, values);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        raySpin.setAdapter(adapter);

        raySpin.setOnItemSelectedListener(new ListenerSpinnerRay(this));
    }
 
    /**
     * Methode pour récupérer la localisation de l'appareil
     * @return
     */
    @SuppressLint("MissingPermission")
    public Location getMyLocation() {
        LocationManager lm;
        Location myLocation = null;
        // Get location from GPS if it's available
        if(ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            lm = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
            myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            // Location wasn't found, check the next most accurate place for the current location
            if (myLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                // Finds a provider that matches the criteria
                String provider = lm.getBestProvider(criteria, true);
                // Use the provider to get the last known location
                myLocation = lm.getLastKnownLocation(provider);
            }
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


            if(ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                myLoc = null;
            }

            //on évite de placer des marqueurs si on ne connait pas la localisation de l'appareil (bug par exemple)
            if(myLoc != null && selectedCat != null) {

                for (int i = 0; i <= values.size() - 1; i++) {

                    creerMarker(myLoc, values.get(i));

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
                    .snippet(getString(R.string.resumePop) + " " + site.getResume()
                            + getString(R.string.adressePop) + " " + site.getAdressePostal()
                            + getString(R.string.distancePop) + " " + (int)loc.distanceTo(siteLocation) + " " + getString(R.string.metre)));
        }
    }

    public void setSelectedCat(Categorie selectedCat) {
        this.selectedCat = selectedCat;
    }

    public void setSelectedRayon(int selectedRayon) {
        this.selectedRayon = selectedRayon;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void changeCamera(){
        // Get latitude of the current location
        double latitude = this.myLoc.getLatitude();

        // Get longitude of the current location
        double longitude = this.myLoc.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        afficherSites();
    }
}
