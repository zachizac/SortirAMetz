package girod_repiquet.sortirametz.Listener;

import android.view.View;
import android.widget.Button;

import girod_repiquet.sortirametz.Dialog.DelDialog;
import girod_repiquet.sortirametz.FragmentBDDManager;

/**
 * Created by Zachizac on 24/01/2018.
 */

public class ListenerButtonDel implements Button.OnClickListener{

    FragmentBDDManager fragment;

    public ListenerButtonDel(FragmentBDDManager frag){
        this.fragment = frag;
    }

    @Override
    public void onClick(View view) {
        DelDialog delDiag = new DelDialog();
        delDiag.setFragment(this.fragment);
        delDiag.show(fragment.getActivity().getSupportFragmentManager(), "Attention");
    }
}