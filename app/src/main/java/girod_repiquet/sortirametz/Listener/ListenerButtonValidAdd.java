package girod_repiquet.sortirametz.Listener;

import android.content.DialogInterface;

import girod_repiquet.sortirametz.Dialog.AddDialog;
import girod_repiquet.sortirametz.Dialog.ErrDialog;
import girod_repiquet.sortirametz.FragmentBDDManager;

/**
 * Created by Zachizac on 24/01/2018.
 */

public class ListenerButtonValidAdd implements DialogInterface.OnClickListener{

    FragmentBDDManager fragment;
    AddDialog dialog;

    String categorie;
    String nom;
    String adresse;
    String resume;


    public ListenerButtonValidAdd(FragmentBDDManager frag, AddDialog dial){
        this.fragment = frag;
        this.dialog = dial;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i){
        this.categorie = this.dialog.getAddSpinner().getSelectedItem().toString();
        this.nom = this.dialog.getNom().getText().toString();
        this.adresse = this.dialog.getAdresse().getText().toString();
        this.resume = this.dialog.getResume().getText().toString();

        if(this.categorie.equals("Catégorie")){
            ErrDialog errDialog = new ErrDialog();
            errDialog.show(dialog.getFragmentManager(), "Erreur");
        }else{
            fragment.createSite(this.categorie,this.nom,this.adresse,this.resume);
            fragment.onStart();
        }
    }

}
