package girod_repiquet.sortirametz.Listener;

import android.view.View;
import android.widget.Button;

import girod_repiquet.sortirametz.Dialog.AddDialog;
import girod_repiquet.sortirametz.FragmentBDDManager;

/**
 * Created by Zachizac on 24/01/2018.
 */

public class ListenerButtonAdd implements Button.OnClickListener{

    FragmentBDDManager fragment;

    public ListenerButtonAdd(FragmentBDDManager frag){
        this.fragment = frag;
    }

    @Override
    public void onClick(View view) {

        AddDialog addDiag = new AddDialog();
        addDiag.setFragment(this.fragment);
        addDiag.show(fragment.getActivity().getSupportFragmentManager(), "Création d'un site");

    }
}
