package com.iut.ptut.view;

import java.util.logging.Logger;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.iut.ptut.R;

/**
 * 
 * @author R�my Bienvenu, Hugo Roman, Beno�t Sauv�re
 *
 */
public class MainActivity extends Activity {

	// definition de l'id correspondant � la notification
	public static final int ID_NOTIFICATION = 1337;
	
	private final Logger _log = Logger.getLogger(this.getClass().getName());

	public static Context context = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// On r�cup�re le conexte pour l'utliser ailleurs
		MainActivity.context = this.getApplicationContext();

		setContentView(R.layout.activity_main);
		
		ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// definition des tabs et de leurs texte
		ActionBar.Tab tabToday = actionbar.newTab().setText(R.string.tab_today);
		ActionBar.Tab tabSemaine = actionbar.newTab().setText(R.string.tab_week);
		ActionBar.Tab tabMessage = actionbar.newTab().setText(R.string.tab_message);

		tabToday.setTabListener(new TabListener<TodayFragment>(this, "today", TodayFragment.class));
		tabSemaine.setTabListener(new TabListener<WeekFragment>(this, "week", WeekFragment.class));
		tabMessage.setTabListener(new TabListener<MessagesFragment>(this, "messages", MessagesFragment.class));

		actionbar.addTab(tabToday);
		actionbar.addTab(tabSemaine);
		actionbar.addTab(tabMessage);

		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		
	}

	
}
