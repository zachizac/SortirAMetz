package girod_repiquet.sortirametz;

import android.Manifest;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.widget.Button;
import android.os.Bundle;

import girod_repiquet.sortirametz.Fragment.Dialog.PermissionDeniedDialog;
import girod_repiquet.sortirametz.Fragment.FragmentBDDManager;
import girod_repiquet.sortirametz.Fragment.FragmentMap;
import girod_repiquet.sortirametz.Interface.MyLocationInterface;
import girod_repiquet.sortirametz.Listener.ListenerButtonBDD;
import girod_repiquet.sortirametz.Listener.ListenerButtonMap;

public class MainActivity extends FragmentActivity implements MyLocationInterface {

    final FragmentBDDManager db_frag = new FragmentBDDManager();
    final FragmentMap map_frag = new FragmentMap();

    private String fragActif;

    private PermissionUtils permission = new PermissionUtils();

    private boolean permissionValide = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creation des boutons de navigation entre les fragments
        Button mapButton = findViewById(R.id.mapButton);
        Button dbButton = findViewById(R.id.dbButton);

        dbButton.setOnClickListener(new ListenerButtonBDD(this));
        mapButton.setOnClickListener(new ListenerButtonMap(this));

        FragmentManager m = getSupportFragmentManager();
        FragmentTransaction ft = m.beginTransaction();
        ft.add(R.id.contentView, map_frag);
        ft.commit();
        fragActif = "map";

    }

    /**
     * Gestion du retour sur l'application malgrès une permission refusée
     */
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionValide) {
            permissionValide = false;
            // PermissionUtils was not granted, display error dialog.
            showMissingPermissionError();
        }

        if(fragActif.equals("map")) {
        }else {
            FragmentManager m = getSupportFragmentManager();
            FragmentTransaction ft = m.beginTransaction();

            ft.replace(R.id.contentView, db_frag);
            ft.addToBackStack(null);

            ft.commit();

            m = getSupportFragmentManager();
            ft = m.beginTransaction();
            ft.replace(R.id.contentView, map_frag);
            ft.addToBackStack(null);

            ft.commit();
        }
    }

    /**
     * Crée le dialogue de permission manquante
     */
    private void showMissingPermissionError() {
        PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != 1) {
            return;
        }

        if (permission.permissionValide(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // On active alors la localisation en réactivant le fragment map
            FragmentManager m = getSupportFragmentManager();
            FragmentTransaction ft = m.beginTransaction();

            ft.replace(R.id.contentView, db_frag);
            ft.addToBackStack(null);

            ft.commit();

            m = getSupportFragmentManager();
            ft = m.beginTransaction();
            ft.replace(R.id.contentView, map_frag);
            ft.addToBackStack(null);

            ft.commit();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            permissionValide = true;
        }
    }


    @Override
    public void setLocation(Location loc){

        db_frag.updateLocation(loc);
//        FragmentBDDManager frag = (FragmentBDDManager)getSupportFragmentManager().findFragmentById(R.id.contentView);
//        frag.updateLocation(loc);
    }

    public Fragment getDb_frag() {
        return db_frag;
    }

    public Fragment getMap_frag() {
        return map_frag;
    }

    public void setFragActif(String fragActif) {
        this.fragActif = fragActif;
    }
}
