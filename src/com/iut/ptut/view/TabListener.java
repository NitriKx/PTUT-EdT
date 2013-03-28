package com.iut.ptut.view;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import com.iut.ptut.R;

public class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;
   

    /** Constructor used each time a new tab is created.
      * @param activity  The host Activity, used to instantiate the fragment
      * @param tag  The identifier tag for the fragment
      * @param clz  The fragment's Class, used to instantiate the fragment
      */
    public TabListener(Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }

    /* The following are each of the ActionBar.TabListener callbacks */

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // Check if the fragment is already initialized
        if (mFragment == null) {
        	
        	Bundle args = null;
        	// Si c'est un TodayFragment on met la date acutelle
        	if(mClass.equals(TodayFragment.class)) {
        		args = new Bundle();
        		args.putLong("date", 1364347098000L);
        		// args.putLong("date", new Date().getTime());
        	}
        	
            // If not, instantiate and add it to the activity
            mFragment = Fragment.instantiate(mActivity, mClass.getName(), args);
        }
        
        // If it exists, simply attach it in order to show it
        ft.replace(R.id.fragment_contenu, mFragment, mTag);
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {

    	//
    	// Easter Egg
    	//
    	// Si c'est l'onglet Messages
    	if(mClass.equals(MessagesFragment.class)) {
 			i++;
 			if (i >= 3) {
 				Toast.makeText(MainActivity.context, messagesEasterEgg[0], Toast.LENGTH_LONG).show();
 			}
    	}
    }
    
    
    //
    // Pour le Easter Egg
    //
    private int i = 0; 
    private String[] messagesEasterEgg = new String[] { "Éh toi! Appuyer une fois c'est suffisant ! ", "Non mais c'est bon je t'assure.", 
    		"Encore du travail ?", "Tu te rappelles des moutons de Warcraft ?", "Ca va pas tarder à faire pareil sur ton beau smartphone."};

}
