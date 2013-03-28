package com.iut.ptut.view;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.iut.ptut.R;
import com.iut.ptut.ctrl.CRONFetcher;

public class MainActivity extends Activity {

	// definition de l'id correspondant à la notification
	public static final int ID_NOTIFICATION = 1337;
	
	private final Logger _log = Logger.getLogger(this.getClass().getName());

	public static Context context = null;
	
	private String menuAbout;
	private String menuActualiser;
	
	// permettra simplement de recuperer la date l'heure ....
	long theDate;
	Date actual = new Date(theDate);
	WeekActivity wAct;
	Calendar c = Calendar.getInstance();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// On récupère le conexte pour l'utliser ailleurs
		MainActivity.context = this.getApplicationContext();

		setContentView(R.layout.activity_main);
		
		ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// definition des tabs et de leurs texte
		ActionBar.Tab tabToday = actionbar.newTab().setText(R.string.tab_today);
		ActionBar.Tab tabSemaine = actionbar.newTab().setText(R.string.tab_week);
		ActionBar.Tab tabMessage = actionbar.newTab().setText(R.string.tab_message);

		// definition des fragments qui seronts associés
		// http://developer.android.com/guide/components/fragments.html
		Fragment fragToday = new Fragment();
		Fragment fragSemaine = new Fragment();
		Fragment fragMessage = new Fragment();

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
