package girod_repiquet.sortirametz.Fragment.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import girod_repiquet.sortirametz.R;


/**
 * Le dialogue de refus de permission
 */
public class PermissionDeniedDialog extends DialogFragment {

    private static final String ARGUMENT_FERME_ACTIVITE = "finish";

    private boolean mFermeActivite = false;

    /**
     * Crée le dialogue de refus de permission
     */
    public static PermissionDeniedDialog newInstance(boolean finishActivity) {
        Bundle arguments = new Bundle();
        arguments.putBoolean(ARGUMENT_FERME_ACTIVITE, finishActivity);

        PermissionDeniedDialog dialog = new PermissionDeniedDialog();
        dialog.setArguments(arguments);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mFermeActivite = getArguments().getBoolean(ARGUMENT_FERME_ACTIVITE);

        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.location_permission_denied)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mFermeActivite) {
            if(getActivity()!=null) {
                Toast.makeText(getActivity(), R.string.permission_toast,
                        Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }
}