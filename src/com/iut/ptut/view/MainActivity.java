package com.iut.ptut.view;

import java.util.Locale;
import java.util.logging.Logger;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.iut.ptut.R;
import com.iut.ptut.ctrl.CRONFetcher;

/**
 * 
 * @author Rémy Bienvenu, Hugo Roman, Benoît Sauvère
 *
 */
public class MainActivity extends Activity {

	// definition de l'id correspondant à la notification
	public static final int ID_NOTIFICATION = 1337;
	
	private final Logger _log = Logger.getLogger(this.getClass().getName());

	public static Context context = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Locale.setDefault(Locale.FRANCE);

		CRONFetcher fetcher = new CRONFetcher();
		fetcher.execute(new String[]{});
		
		// On récupère le conexte pour l'utliser ailleurs
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
