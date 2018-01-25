package girod_repiquet.sortirametz.Fragment.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import girod_repiquet.sortirametz.Listener.ListenerButtonPermission;
import girod_repiquet.sortirametz.R;

/**
 * Fenêtre qui explique la raison de l'utilisation de la permission de localisation
 */
public class RationaleDialog extends DialogFragment {

    private static final String ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode";

    private static final String ARGUMENT_FERME_ACTIVITE = "finish";

    private boolean mFermeActivite = false;

    /**
     * Crée un dialogue qui explique la raison de l'utilisation de la permission de localisation
     * <p>
     * La permission est demandée après avoir validé le dialogue
     *
     * @param requestCode
     * @param finishActivity Whether the calling Activity should be finished if the dialog is
     *                       cancelled.
     */
    public static RationaleDialog newInstance(int requestCode, boolean finishActivity) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode);
        arguments.putBoolean(ARGUMENT_FERME_ACTIVITE, finishActivity);
        RationaleDialog dialog = new RationaleDialog();
        dialog.setArguments(arguments);
        return dialog;
    }

    /**
     * lors de la création du dialogue
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        final int requestCode = arguments.getInt(ARGUMENT_PERMISSION_REQUEST_CODE);
        mFermeActivite = arguments.getBoolean(ARGUMENT_FERME_ACTIVITE);

        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.permission_motif)
                .setPositiveButton(android.R.string.ok, new ListenerButtonPermission(this))
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    /**
     * lors du rejet du dialogue
     *
     * @param dialog
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mFermeActivite) {
            if (getActivity() != null) {
                Toast.makeText(getActivity(),
                        R.string.permission_toast,
                        Toast.LENGTH_SHORT)
                        .show();
                getActivity().finish();
            }
        }
    }

    public void setmFermeActivite(boolean b) {
        this.mFermeActivite = b;
    }
}
