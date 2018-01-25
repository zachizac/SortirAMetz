package girod_repiquet.sortirametz;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import girod_repiquet.sortirametz.Listener.ListenerButtonPermission;

/**
 * Created by Zachizac on 21/01/2018.
 */

/**
 * Classe de gestion des permissions de localisation
 */
public abstract class Permission {

    /**
     * Methode de demande de permission nécessaire à l'utilisation de l'application
     */
    public static void demandePermission(FragmentActivity activity, int requestCode, String permissionType, boolean finishActivity){
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionType)){
            Permission.RationaleDialog.newInstance(requestCode, finishActivity).show(activity.getSupportFragmentManager(),"dialog");
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{permissionType}, requestCode);
        }
    }

    /**
     * Methode qui vérifie si la permission est accordée ou non
     * @param grantPermissions
     * @param grantResults
     * @param permissionType
     * @return boolean accordée ou non
     */
    public static boolean permissionValide(String[] grantPermissions, int[] grantResults, String permissionType){
        for (int i = 0; i < grantPermissions.length; i++){
            if(permissionType.equals(grantPermissions[i])){
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }

    /**
     * Fenêtre qui explique la raison de l'utilisation de la permission de localisation
     */
    public static class RationaleDialog extends DialogFragment {

        private static final String ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode";

        private static final String ARGUMENT_FERME_ACTIVITE = "finish";

        private boolean mFermeActivite = false;

        /**
         * Crée un dialogue qui explique la raison de l'utilisation de la permission de localisation
         *
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
         * @param dialog
         */
        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            if (mFermeActivite) {
                if(getActivity()!=null) {
                    Toast.makeText(getActivity(),
                            R.string.permission_toast,
                            Toast.LENGTH_SHORT)
                            .show();
                    getActivity().finish();
                }
            }
        }

        public void setmFermeActivite(boolean b){
            this.mFermeActivite = b;
        }
    }

    /**
     * Le dialogue de refus de permission
     */
    public static class PermissionDeniedDialog extends DialogFragment {

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

}

