package girod_repiquet.sortirametz.Listener;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import girod_repiquet.sortirametz.Fragment.FragmentMap;

/**
 * Created by Zachizac on 22/01/2018.
 */

public class ListenerLocationChange implements LocationListener {

    private FragmentMap fragment;
    //GoogleMap map;

   public ListenerLocationChange(FragmentMap frag){
       this.fragment = frag;
   }

    @Override
    public void onLocationChanged(Location location) {

       fragment.changeCamera();

       fragment.updateLocation(location);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
