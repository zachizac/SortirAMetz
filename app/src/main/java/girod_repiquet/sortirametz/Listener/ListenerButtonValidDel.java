package girod_repiquet.sortirametz.Listener;

import android.content.DialogInterface;
import android.view.View;


import girod_repiquet.sortirametz.FragmentBDDManager;

/**
 * Created by Zachizac on 24/01/2018.
 */

public class ListenerButtonValidDel implements DialogInterface.OnClickListener {

    private FragmentBDDManager fragment;

    public ListenerButtonValidDel(FragmentBDDManager frag){
        this.fragment = frag;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        this.fragment.delSelectedSite();
    }
}
