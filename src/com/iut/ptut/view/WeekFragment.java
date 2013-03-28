package com.iut.ptut.view;

import com.iut.ptut.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WeekFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View vueFragment = inflater.inflate(R.layout.activity_week, container, false);
		
		
		
		return vueFragment;
	}
	
}
