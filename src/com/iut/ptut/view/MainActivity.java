package com.iut.ptut.view;

import java.util.Locale;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.iut.ptut.R;
import com.iut.ptut.ctrl.cron.CRONFetcher;

/**
 * 
 * @author R�my Bienvenu, Hugo Roman, Beno�t Sauv�re
 *
 */
public class MainActivity extends Activity {

	// definition de l'id correspondant � la notification
	public static final int ID_NOTIFICATION = 1337;
	
	public static Context context = null;
	public static MainActivity activity = null;
	
	
	private ActionBar.Tab tabToday;
	private ActionBar.Tab tabSemaine;
	private ActionBar.Tab tabMessage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Locale.setDefault(Locale.FRANCE);

		// On r�cup�re le conexte pour l'utliser ailleurs
		MainActivity.context = this.getApplicationContext();
		MainActivity.activity = this;
		
		// Si on a relanc� l'Activity sans red�marrer l'application, on r�cup�re le fragment pr�c�demment charg�
		Fragment fragmentPrecedent = FragmentManager.getEnCours();
		Bundle fragmentPrecedenArgs = FragmentManager.getEnCoursArgs();
		Tab ongletPrecedent = TabListener.enCours;
		
		setContentView(R.layout.activity_main);
		
		ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// definition des tabs et de leurs texte
		tabToday = actionbar.newTab().setText(R.string.tab_today);
		tabSemaine = actionbar.newTab().setText(R.string.tab_week);
		tabMessage = actionbar.newTab().setText(R.string.tab_message);

		tabToday.setTabListener(new TabListener<TodayFragment>(TodayFragment.class));
		tabSemaine.setTabListener(new TabListener<WeekFragment>(WeekFragment.class));
		tabMessage.setTabListener(new TabListener<MessagesFragment>(MessagesFragment.class));

		tabToday.setTag(TodayFragment.class.getName());
		tabSemaine.setTag(WeekFragment.class.getName());
		tabMessage.setTag(MessagesFragment.class.getName());
		
		actionbar.addTab(tabToday);
		actionbar.addTab(tabSemaine);
		actionbar.addTab(tabMessage);

		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		// Si on a relanc� l'Activity sans red�marrer l'application
		// - On r�cup�re remet d'abort l'onglet avant le red�marrage de l'Activity
		//   du fait qu'� la res�lection le fragment sera recharg�.
		if(ongletPrecedent != null) {
			
			// Si c'�tait l'onglet message
			if(MessagesFragment.class.getName().equals(ongletPrecedent.getTag())) {
				actionbar.selectTab(tabMessage);
			} else if (WeekFragment.class.getName().equals(ongletPrecedent.getTag())) {
				actionbar.selectTab(tabSemaine);
			} else {
				actionbar.selectTab(tabToday);
			}
		}
		// - On r�cup�re le framgent pr�c�demment charg�.
		if(fragmentPrecedent != null) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			FragmentManager.chargerFragment(fragmentPrecedent.getClass(), fragmentPrecedenArgs, ft);
			ft.commit();
		}
		
		// Lancement de la r�cup�ration pour le groupe actuel
		launchRefresh();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_contextuel, menu);
	    return true;
	}
	
	// M�htode appell�e lorsque l'utilisateur presse un des onglets
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = null;
		
	    switch (item.getItemId()) {
	        case R.id.menu_refresh:
	        	// On lance le rafraichissement des donn�es
	            launchRefresh();
	            return true;
	        case R.id.menu_parametres:
	        	// On lance l'activit� des param�tres
	        	i = new Intent(this.getApplicationContext(), SettingsActivity.class);
	        	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        	MainActivity.context.startActivity(i);
	        	return true;
	        case R.id.menu_about:
	        	// On charge le fragment "A propos" dans le conteneur de fragments
	        	FragmentTransaction ft = getFragmentManager().beginTransaction(); 
				FragmentManager.chargerFragment(AboutFragment.class, null, ft);
				ft.commit();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	/**
	 * Lance le rafraichissement de la base dans une t�che parall�le.
	 */
	public void launchRefresh() {
		
		Toast toastConfirmation = Toast.makeText(MainActivity.context, "R�cup�ration en cours...", Toast.LENGTH_LONG);
		toastConfirmation.show();
		
		CRONFetcher fetcher = new CRONFetcher();
		fetcher.execute(new String[]{});
		
	}
	
	// M�htode appell�e lorsque le bouton "Retour" est press�
	@Override
	public void onBackPressed() {
		
		Fragment enCours = FragmentManager.getEnCours();
		
		// Si le fragment est de type TodayFragment
		if(enCours instanceof TodayFragment) {
			
			// et qu'on doit revenir au fragment Week lors d'un retour
			TodayFragment f = (TodayFragment) enCours;
			if(f.isRetourListeJours()) {
				forceAffichageOngletWeek();
				return;
			}
		}
		
		// Si c'est un AboutActivity
		if(enCours instanceof AboutFragment) {
			// On revient au framgment Today
			this.getActionBar().selectTab(this.tabToday);
			return;
		}
		
		// Si ce n'�tait pas un des cas pr�c�dent on fait l'action par d�faut (fermeture de l'application)
		super.onBackPressed();
		
	}
	
	/**
	 * Permet de rafraichir le tab actuellement affich�.
	 */
	public void rafraichirFragment() {
		
		// On res�lectionne le tab courrant pour le rafraichir
		this.getActionBar().selectTab(this.getActionBar().getSelectedTab());
	}
	
	/**
	 * Force l'affichage de la semaine. (utili� pour un retour � partir d'un jour ouvert dans l'onglet semaine)
	 */
	public void forceAffichageOngletWeek() {
		this.getActionBar().selectTab(this.tabSemaine);
	}
	
}
