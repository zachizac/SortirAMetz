package girod_repiquet.sortirametz.Listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import girod_repiquet.sortirametz.FragmentBDDManager;
import girod_repiquet.sortirametz.FragmentMap;

/**
 * Created by Zachizac on 24/01/2018.
 */

public class ListenerListViewClick implements OnItemClickListener {

    FragmentBDDManager fragment;

    public ListenerListViewClick(FragmentBDDManager fragment){
        this.fragment = fragment;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
        view.setSelected(true);
        fragment.setSelectedSiteWithPosition(position);

        fragment.getDelButton().setEnabled(true);
    }
}
