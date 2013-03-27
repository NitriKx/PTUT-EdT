package com.iut.ptut.view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import net.fortuna.ical4j.data.ParserException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import com.iut.ptut.R;
import com.iut.ptut.ctrl.CRONFetcher;
import com.iut.ptut.ctrl.DayAdapter;
import com.iut.ptut.model.database.DatabaseManipulationException;

public class MainActivity extends Activity {

	// definition de l'id correspondant à la notification
	public static final int ID_NOTIFICATION = 1337;
	
	private final Logger _log = Logger.getLogger(this.getClass().getName());

	public static Context context = null;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragment);
	
		// On récupère le conexte pour l'utliser ailleurs
		MainActivity.context = this.getApplicationContext();
		
		// On change l'apparence de l'ActionBar
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		
		ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// definition des tabs et de leurs texte
		ActionBar.Tab tabToday = actionbar.newTab().setText(R.string.tab_today);
		ActionBar.Tab tabSemaine = actionbar.newTab().setText(R.string.tab_week);
		ActionBar.Tab tabMessage = actionbar.newTab().setText(R.string.tab_message);
		
		tabToday.setTabListener(new TabListener<TodayFragment>(this, "today", TodayFragment.class));
		tabSemaine.setTabListener(new TabListener<WeekFragment>(this, "week", WeekFragment.class));
		tabMessage.setTabListener(new TabListener<MessageFragment>(this, "message", MessageFragment.class));
		
		actionbar.addTab(tabToday);
		actionbar.addTab(tabSemaine);
		actionbar.addTab(tabMessage);
		
		
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.liste_menu, menu);*/
	    
		return false;
	}

	
}
