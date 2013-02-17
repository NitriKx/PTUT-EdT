package com.iut.ptut.view;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Cette classe est utilisée pour afficher les deux tableaux qui sont 
 * intièrement gérés dans les layouts
 * @author ehret_g
 *
 */
public class TableLayoutFragment extends Fragment {

    private String name;
    private int id;

    

    @TargetApi(11)
	public TableLayoutFragment(String name, int id) {
	super();
	this.name = name;
	this.id = id;
    }

    @TargetApi(11)
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	// Inflate the layout for this fragment
	return inflater.inflate(
		getResources().getIdentifier("fragment" + id, "layout", container.getContext().getPackageName()), container, false);
    }

    public String getName() {
	return name;
    }
}
