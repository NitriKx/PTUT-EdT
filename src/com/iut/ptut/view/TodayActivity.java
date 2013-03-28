package com.iut.ptut.view;

import java.util.List;
import java.util.logging.Logger;

import android.app.Activity;
import android.os.Bundle;

import com.iut.ptut.R;
import com.iut.ptut.model.Lesson;
/**
 * 
 * @author Hugz2 & modified by Remy
 *
 */
public class TodayActivity extends Activity {

	private List<Lesson> MyListLesson;
	
	private Logger _log = Logger.getLogger(this.getClass().getName());

	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_today);
		
	}
	
}
