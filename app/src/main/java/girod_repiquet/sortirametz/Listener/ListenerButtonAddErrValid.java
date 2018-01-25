package girod_repiquet.sortirametz.Listener;



import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import girod_repiquet.sortirametz.Dialog.AddDialog;
import girod_repiquet.sortirametz.FragmentBDDManager;

public class ListenerButtonAddErrValid implements DialogInterface.OnClickListener {

    FragmentBDDManager fragment;

    public ListenerButtonAddErrValid(FragmentBDDManager frag){
        this.fragment = frag;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        AddDialog addDiag = new AddDialog();
        addDiag.setFragment(this.fragment);
        addDiag.show(fragment.getActivity().getSupportFragmentManager(), "Création d'un site");

    }

}
