package girod_repiquet.sortirametz.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

import girod_repiquet.sortirametz.R;

/**
 * Created by Zachizac on 24/01/2018.
 * Classe pour créer la popup d'information d'un marker (et ainsi pouvoir afficher
 * des snippets multilignes)
 */
public class PopupAdapter implements InfoWindowAdapter {

    private View popup=null;
    private LayoutInflater inflater;

    public PopupAdapter(LayoutInflater inflater) {
        this.inflater=inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return(null);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getInfoContents(Marker marker) {
        if (popup == null) {
            popup=inflater.inflate(R.layout.popup, null);
        }

        TextView tv=popup.findViewById(R.id.title);

        tv.setText(marker.getTitle());
        tv=popup.findViewById(R.id.snippet);
        tv.setText(marker.getSnippet());

        return(popup);
    }
}
