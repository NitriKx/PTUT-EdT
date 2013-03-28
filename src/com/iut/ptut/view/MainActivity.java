package com.iut.ptut.view;

import java.util.Locale;
import java.util.logging.Logger;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.iut.ptut.R;
import com.iut.ptut.ctrl.cron.CRONFetcher;

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
	public static Activity activity = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Locale.setDefault(Locale.FRANCE);

		// On récupère le conexte pour l'utliser ailleurs
		MainActivity.context = this.getApplicationContext();
		MainActivity.activity = this;
		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_contextuel, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.menu_refresh:
	            launchRefresh();
	            return true;
	        case R.id.menu_parametres:
	        	
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	/**
	 * Lance le rafraichissement de la base dans une tâche parallèle.
	 */
	public void launchRefresh() {
		
		Toast toastConfirmation = Toast.makeText(MainActivity.context, "Récupération en cours...", Toast.LENGTH_LONG);
		toastConfirmation.show();
		
		CRONFetcher fetcher = new CRONFetcher();
		fetcher.execute(new String[]{});
		
	}
}
