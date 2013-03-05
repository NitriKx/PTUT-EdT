package com.iut.ptut.view;


import java.util.Date;

import android.app.Activity;

import com.iut.ptut.R;

public class WeekActivity extends Activity{
	
	Date date = new Date(System.currentTimeMillis());
	
	
	public void setSemaine(){
		
		
		setContentView(R.layout.activity_week);
		
	}
	
	
}
