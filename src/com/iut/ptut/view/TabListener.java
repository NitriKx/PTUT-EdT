package com.iut.ptut.view;

import java.util.Date;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

public class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private final Class<T> mClass;
   

    /** Constructor used each time a new tab is created.
      * @param activity  The host Activity, used to instantiate the fragment
      * @param tag  The identifier tag for the fragment
      * @param clz  The fragment's Class, used to instantiate the fragment
      */
    public TabListener(Class<T> clz) {
        mClass = clz;
    }

    // Lorsque l'un des onglet est sélectionné on le réinstancie pour rafraichir les données
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Bundle args = null;
		// Si c'est un TodayFragment on met la date acutelle
		if(mClass.equals(TodayFragment.class)) {
			args = new Bundle();
			args.putLong("date", new Date().getTime());
		}
		
		// On charge un nouveau fragment de mClass
		FragmentManager.chargerFragment(mClass, args, ft);
	}

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    	
    	// On réinstancie pour rafraichir
    	onTabSelected(tab, ft);
    	
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
