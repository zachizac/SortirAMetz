package girod_repiquet.sortirametz.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import girod_repiquet.sortirametz.FragmentBDDManager;
import girod_repiquet.sortirametz.Listener.ListenerButtonValidDel;
import girod_repiquet.sortirametz.R;

/**
 * Created by Zachizac on 24/01/2018.
 */

public class DelDialog extends DialogFragment{

    FragmentBDDManager fragment = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.delDialog)
                .setPositiveButton(R.string.ok, new ListenerButtonValidDel(this.fragment))
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    public void setFragment(FragmentBDDManager frag){
        this.fragment = frag;
    }
}
