package girod_repiquet.sortirametz;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import girod_repiquet.sortirametz.Fragment.Dialog.RationaleDialog;

/**
 * Created by Zachizac on 21/01/2018.
 */

/**
 * Classe de gestion des permissions de localisation
 */
public class PermissionUtils {

    /**
     * Methode de demande de permission nécessaire à l'utilisation de l'application
     */
    public void demandePermission(FragmentActivity activity, int requestCode, String permissionType, boolean finishActivity){
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionType)){
            RationaleDialog rationalDiag = RationaleDialog.newInstance(requestCode, finishActivity);
            rationalDiag.show(activity.getSupportFragmentManager(),"dialog");
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
    public boolean permissionValide(String[] grantPermissions, int[] grantResults, String permissionType){
        for (int i = 0; i < grantPermissions.length; i++){
            if(permissionType.equals(grantPermissions[i])){
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }

}

