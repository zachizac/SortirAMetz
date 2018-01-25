package girod_repiquet.sortirametz.Listener;

import android.Manifest;
import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;

import girod_repiquet.sortirametz.Fragment.Dialog.RationaleDialog;


public class ListenerButtonPermission implements DialogInterface.OnClickListener {

    private static final String ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode";

    private RationaleDialog fragment;

    public ListenerButtonPermission(RationaleDialog frag) {
        this.fragment = frag;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        // Après le clic sur Ok : demander la permission
        final int requestCode = fragment.getArguments().getInt(ARGUMENT_PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(fragment.getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                requestCode);
        // L'application ne ferme pas car le code est demandé
        fragment.setmFermeActivite(false);
    }

}
