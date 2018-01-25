package girod_repiquet.sortirametz.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import girod_repiquet.sortirametz.FragmentBDDManager;
import girod_repiquet.sortirametz.Listener.ListenerButtonAddErrValid;
import girod_repiquet.sortirametz.R;

/**
 * Created by Zachizac on 24/01/2018.
 */

public class ErrDialog extends DialogFragment {

    FragmentBDDManager fragment = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.errDialog)
                .setPositiveButton(R.string.ok2, new ListenerButtonAddErrValid(this.fragment))
                .create();
    }

    public void setFragment(FragmentBDDManager frag){
        this.fragment = frag;
    }
}

