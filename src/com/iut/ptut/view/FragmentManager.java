package com.iut.ptut.view;

import com.iut.ptut.R;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Cette classe sert à charger / décharges les fragments
 * @author Benoît Sauvère
 *
 */
public class FragmentManager {

	private static FragmentManager _instance = null;
	
	private static Fragment enCours = null;
	
	private FragmentManager() {
		
	}
	
	public static FragmentManager getInstance() {
		if(_instance == null){
			_instance = new FragmentManager();
		}
		return _instance;
	}
	
	
	public static Fragment chargerFragment(Class<?> clz, Bundle args, FragmentTransaction ft) {
		
		Fragment mFragment = Fragment.instantiate(MainActivity.context, clz.getName(), args);
		FragmentManager.enCours = mFragment;
		ft.replace(R.id.fragment_contenu, mFragment);
		return mFragment;
	}
	
	public Fragment getEnCours() {
		return enCours;
	}
	
}
