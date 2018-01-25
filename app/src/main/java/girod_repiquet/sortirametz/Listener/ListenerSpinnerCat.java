package girod_repiquet.sortirametz.Listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import girod_repiquet.sortirametz.Fragment.FragmentMap;

/**
 * Created by Zachizac on 24/01/2018.
 */

public class ListenerSpinnerCat implements OnItemSelectedListener {

    private FragmentMap fragment;

    public ListenerSpinnerCat(FragmentMap frag){
        this.fragment = frag;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        fragment.setSelectedCat(fragment.getCategories().get(position));
        fragment.afficherSites();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
