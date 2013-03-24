package com.iut.ptut;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.fortuna.ical4j.data.ParserException;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.iut.ptut.ctrl.CalendarParser;
import com.iut.ptut.model.AssetsManager;
import com.iut.ptut.model.Group;
import com.iut.ptut.model.Lesson;
import com.iut.ptut.model.TimeTable;
import com.iut.ptut.model.database.DatabaseManager;
import com.iut.ptut.view.ActivityNotification;
import com.iut.ptut.view.TableLayoutFragment;
import com.iut.ptut.view.TodayActivity;
import com.iut.ptut.view.WeekActivity;

public class MainActivity extends Activity implements TabListener {

	// definition de l'id correspondant à la notification
	public static final int ID_NOTIFICATION = 1337;

	private final Logger _log = Logger.getLogger(this.getClass().getName());

	public static Context context = null;

	// permettra simplement de recuperer la date l'heure ....
	long theDate;
	Date actual = new Date(theDate);
	WeekActivity wAct;
	Calendar c = Calendar.getInstance();

	private TableLayoutFragment TabToday = new TableLayoutFragment("Aujourd'hui", 2);
	private TableLayoutFragment TabWeek = new TableLayoutFragment("Semaine", 2);
	private TableLayoutFragment TabMsg = new TableLayoutFragment("Messages", 3);

	// private TableJavaFragment TabDebug = new TableJavaFragment();
	// private String debug= new String("debug");
	/*
	 * le code ci-dessous permet d'afficher une aute fenetre a partir d'un
	 * bouton, pour cela 1) creer le bouton 2)dans le code xml, rajouter la
	 * ligne android:onClick"" 3) mettre le nom de la methode entre les
	 * parentheses
	 * 
	 * /!\ pour afficher une fenetre c'est setContentView /!\ et vous pouvez
	 * rajouter du code aussi dans la methode
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// On récupère le conexte pour l'utliser ailleurs
		MainActivity.context = this.getApplicationContext();

		setContentView(R.layout.activity_main);
		// wAct.getWeekActivity();

		DatabaseManager bdd = DatabaseManager.getInstance();

		bdd.open();

		try {
			Group grp =  new Group("2B", 4, 2012);
			Date debut = null;
			Date fin = null;
			TimeTable t = CalendarParser.getTimeTableDepuisICS(AssetsManager.ouvrirInputStreamAsset("tests/S4_08.ics"), grp);
			bdd.insererTimeTable(t);
			
			Calendar cal  = Calendar.getInstance();
			
			cal.set(Calendar.YEAR, 2013);
			cal.set(Calendar.MONTH, Calendar.FEBRUARY);
			cal.set(Calendar.DAY_OF_MONTH, 20);
			
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			debut = cal.getTime();
			
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			fin = cal.getTime();
			
			List<Lesson> l = bdd.getListeLessonPourPeriode(debut, fin);
			_log.log(Level.INFO, l.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		bdd.close();

		ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// definition des tabs et de leurs texte
		ActionBar.Tab tabToday = actionbar.newTab().setText("Aujourd'hui");
		ActionBar.Tab tabSemaine = actionbar.newTab().setText("Semaine");
		ActionBar.Tab tabMessage = actionbar.newTab().setText("Messages");

		// definition des fragments qui seronts associés
		// http://developer.android.com/guide/components/fragments.html
		Fragment fragToday = new Fragment();
		Fragment fragSemaine = new Fragment();
		Fragment fragMessage = new Fragment();

		tabToday.setTabListener(new ActionBarTabsListener(fragToday));
		tabSemaine.setTabListener(new ActionBarTabsListener(fragSemaine));
		tabMessage.setTabListener(new ActionBarTabsListener(fragMessage));

		actionbar.addTab(tabToday);
		actionbar.addTab(tabSemaine);
		actionbar.addTab(tabMessage);

		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	private WeekActivity WeekActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	private TodayActivity TodayActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	protected class ActionBarTabsListener implements ActionBar.TabListener {

		private Fragment fragment;
		int i = 0;

		public ActionBarTabsListener(Fragment fragment) {
			this.fragment = fragment;
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			i++;
			if (i >= 7) {
				Toast.makeText(getBaseContext(), "éh toi! Appuyer une fois c'est suffisant ! ", Toast.LENGTH_SHORT).show();
			}
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			if (getActionBar().getSelectedTab().getText() == "Messages")
				setContentView(R.layout.informations);

			if (getActionBar().getSelectedTab().getText() == "Aujourd'hui") {
				Intent intent = new Intent(MainActivity.this, TodayActivity.class);
				startActivity(intent);
			}

			if (getActionBar().getSelectedTab().getText() == "Semaine") {
				System.out.println("dafuq");
				String s = c.DECEMBER + " " + c.DAY_OF_MONTH + " " + c.MONTH;

				// setContentView(R.layout.activity_week);
				// Button b1 = (Button)findViewById(R.id.week_b1);
				// b1.setText(s);
				// intent pour appeler l'autre activity
				Intent intent = new Intent(MainActivity.this, WeekActivity.class);
				startActivity(intent);
			}
			i = 0;
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

	}

	public void launchWeek() {
		System.out.println("voilou");
		// setContentView(R.layout.activity_week);
		// wAct.getSemaine();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_list_item, menu);
		// possibilité d'ajouter des items au menu avec la méthode menu.add(
		// String pS);

		// Méthode java (NE PAS UTILISER). Le XML est beaucoup plus simple
		// menu.add("testMenu");
		// menu.add("testMenu2");

		return true;
	}

	/**
	 * méthode appelée si on clique sur une option du menu
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		/**
		 * // juste un test pour afficher une vue en cliquant if
		 * (item.getItemId() == R.id.menu_settings)
		 * setContentView(R.layout.informations); else if (item.getItemId() ==
		 * R.id.menu_param) setContentView(R.layout.parametres); // On regarde
		 * quel item a été cliqué grâce à son id et on déclenche une // action
		 * return false;
		 */
		return true;
	}

	//
	// NOTIF
	//

	@SuppressWarnings("deprecation")
	public void createNotif() {

		/**
		 * avant tout , il faut bien integerer ces notions, qui sont cruciales
		 * 1) pour les notifications 2) mais surtout pour la prog. android en
		 * general :
		 * http://developer.android.com/reference/android/content/Context.html
		 * http://developer.android.com/reference/android/content/Intent.html
		 * http://developer.android.com/reference/android/app/PendingIntent.html
		 * 
		 * 
		 * voila maintenant de quoi comprendre comment marche une notification :
		 * http
		 * ://developer.android.com/reference/android/app/NotificationManager
		 * .html
		 * http://developer.android.com/reference/android/app/Notification.html
		 * http://developer.android.com/guide/topics/ui/notifiers/notifications.
		 * html
		 */

		// creation d'un gestionnaire de notifications
		final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// creation d'une notification
		Notification notification = new Notification(R.drawable.notification, "notif", System.currentTimeMillis()); // les
																													// parametres
																													// sont
																													// obligatoires
																													// pour
																													// que
																													// cela
																													// fonctionne
		Toast.makeText(getBaseContext(), "debug1 " + notification.toString(), Toast.LENGTH_SHORT).show();

		String titreNotification = "en cours !";// definition du titre de la
												// notif
		String textNotification = "c'est l'heure"; // definition du texte qui
													// caractérise la notif

		/*
		 * definition d'une vibration lors de la notification Ici les chiffres
		 * correspondent à 0sec de pause, 0.2sec de vibration, 0.1sec de pause,
		 * 0.2sec de vibration, 0.1sec de pause, 0.2sec de vibration
		 */
		notification.vibrate = new long[] { 0, 200, 100, 200, 100, 200 };

		Toast.makeText(getBaseContext(), "debug2 " + notification.toString(), Toast.LENGTH_SHORT).show();

		/**
		 * Les intents sont l’une des pierres angulaires de la plateforme (ndlr.
		 * android). Nous pouvons les comparer à des actions qui permettent de
		 * dialoguer à travers le système à partir de canaux qui leurs sont
		 * dédiés.
		 * 
		 * exemple : Quand votre mobile reçoit un appel, la plateforme lance un
		 * Intent signalant l’arrivée de l'appel.
		 */
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, ActivityNotification.class), 0);

		// On configure notre notification avec tous les paramètres que l'on
		// vient de créer
		notification.setLatestEventInfo(getApplicationContext(), titreNotification, textNotification, pendingIntent);

		// Enfin on ajoute notre notification et son ID à notre gestionnaire de
		// notification

		notificationManager.notify(ID_NOTIFICATION, notification);
	}

	public void displayInfos(View v) {

		try {
			createNotif();
			Toast.makeText(getBaseContext(), "createNotif executé ", Toast.LENGTH_SHORT).show();
		} catch (NullPointerException e) {
			// le Toast est un message "notification" qui permet d'afficher
			// quelque chose a l'utilisateur sans changer de fentre
			Toast.makeText(getBaseContext(), "sa marche pas la : " + e.getMessage(), Toast.LENGTH_SHORT).show();
			System.out.println("sa marche pas la : " + e.getMessage());
		}

		setContentView(R.layout.activity_main);

	}

	public void displayMain(View v) {
		setContentView(R.layout.activity_main);
	}

	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		// Toast.makeText(this, tab.getText() + " unselected",
		// Toast.LENGTH_SHORT).show();
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		// Toast.makeText(this, tab.getText() + " selected",
		// Toast.LENGTH_SHORT).show();
		/*
		 * if(tab.getText().equals(TabTooday.getName()))
		 * ft.replace(R.id.fragmentContainer, TabTooday); else if
		 * (tab.getText().equals(TabMsg.getName()))
		 * ft.replace(R.id.fragmentContainer, TabMsg); else if
		 * (tab.getText().equals(this.debug)) {
		 * ft.replace(R.id.fragmentContainer, TabDebug); }
		 */
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		// Toast.makeText(this, tab.getText() + " selected",
		// Toast.LENGTH_SHORT).show();
		if (tab.getText().equals(TabToday.getName()))
			ft.remove(TabToday);
		else if (tab.getText().equals(TabMsg.getName()))
			ft.remove(TabMsg);

	}
}
