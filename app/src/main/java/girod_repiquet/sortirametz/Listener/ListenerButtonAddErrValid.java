package girod_repiquet.sortirametz.Listener;



import android.content.DialogInterface;

import girod_repiquet.sortirametz.Fragment.Dialog.AddDialog;
import girod_repiquet.sortirametz.Fragment.FragmentBDDManager;

public class ListenerButtonAddErrValid implements DialogInterface.OnClickListener {

    private FragmentBDDManager fragment;

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
