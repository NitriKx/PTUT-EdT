package com.iut.ptut.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iut.ptut.R;

public class MessagesFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View vueFragment = inflater.inflate(R.layout.activity_messages, container, false);
		return vueFragment;
	}
	
}
