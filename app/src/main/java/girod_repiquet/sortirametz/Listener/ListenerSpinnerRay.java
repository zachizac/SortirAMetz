package girod_repiquet.sortirametz.Listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import girod_repiquet.sortirametz.FragmentMap;

/**
 * Created by Zachizac on 24/01/2018.
 */

public class ListenerSpinnerRay implements OnItemSelectedListener {

    FragmentMap fragment;

    public ListenerSpinnerRay(FragmentMap frag){
        this.fragment = frag;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(isInteger(parent.getItemAtPosition(position).toString())){
            fragment.setSelectedRayon(Integer.parseInt(parent.getItemAtPosition(position).toString()));
        }else fragment.setSelectedRayon(0);

        fragment.afficherSites();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * test si le string est un entier
     * @param s
     * @return
     */
    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e){
            return false;
        }

        return true;
    }

}
