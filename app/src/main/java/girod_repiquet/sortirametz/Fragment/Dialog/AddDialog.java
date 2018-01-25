package girod_repiquet.sortirametz.Fragment.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import girod_repiquet.sortirametz.BDD.DAO.CategoriesDAO;
import girod_repiquet.sortirametz.Fragment.FragmentBDDManager;
import girod_repiquet.sortirametz.Listener.ListenerButtonValidAdd;
import girod_repiquet.sortirametz.BDD.MySQLiteHelper;
import girod_repiquet.sortirametz.R;

/**
 * Created by Zachizac on 24/01/2018.
 */

public class AddDialog extends DialogFragment {

    private MySQLiteHelper dbHelper;
    private CategoriesDAO categoriesDAO;
    private FragmentBDDManager fragment = null;

    private Spinner addSpinner;
    private EditText nom;
    private EditText adresse;
    private EditText resume;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_newsite, null))
                .setPositiveButton(R.string.ok, new ListenerButtonValidAdd(this.fragment, this))
                .setNegativeButton(R.string.cancel, null);

        return builder.create();
    }

    @Override
    public void onStart(){
        super.onStart();

        fillAddSpinner();

        nom = this.getDialog().findViewById(R.id.nom);
        adresse = this.getDialog().findViewById(R.id.adresse);
        resume = this.getDialog().findViewById(R.id.resume);

    }

    /**
     * Remplir le spinner de categorie
     */
    public void fillAddSpinner(){

        dbHelper = new MySQLiteHelper(this.getActivity());
        categoriesDAO = new CategoriesDAO(dbHelper);
        categoriesDAO.open();

        List<String> values = categoriesDAO.getAllCategoriesNames();

        if(values.size()==0){
            categoriesDAO.initCat();
            values = categoriesDAO.getAllCategoriesNames();
        }

        //System.out.println("View = " + this.getView());
        addSpinner = this.getDialog().findViewById(R.id.addSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.fragment.getActivity().getApplicationContext(), R.layout.spinner_item, values);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        addSpinner.setAdapter(adapter);

        dbHelper.closeDB();

    }

    public void setFragment(FragmentBDDManager frag){
        this.fragment = frag;
    }

    public EditText getNom() {
        return nom;
    }

    public EditText getAdresse() {
        return adresse;
    }

    public EditText getResume() {
        return resume;
    }

    public Spinner getAddSpinner() {
        return addSpinner;
    }
}
