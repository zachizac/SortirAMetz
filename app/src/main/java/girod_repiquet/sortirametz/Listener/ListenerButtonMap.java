package girod_repiquet.sortirametz.Listener;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import girod_repiquet.sortirametz.MainActivity;
import girod_repiquet.sortirametz.R;

/**
 * Created by Zachizac on 22/01/2018.
 */

public class ListenerButtonMap implements Button.OnClickListener{

    private MainActivity activity;

    public ListenerButtonMap(MainActivity act){
        this.activity = act;
    }

    @Override
    public void onClick(View view) {
        FragmentManager m = activity.getSupportFragmentManager();
        FragmentTransaction ft = m.beginTransaction();

        ft.replace(R.id.contentView, activity.getMap_frag());
        ft.addToBackStack(null);

        ft.commit();

        activity.setFragActif("Map");
    }

}
