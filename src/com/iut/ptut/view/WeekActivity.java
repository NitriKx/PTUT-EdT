package com.iut.ptut.view;


import java.util.Calendar;
import java.util.Date;

import android.R.string;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.iut.ptut.MainActivity;
import com.iut.ptut.R;
import com.iut.ptut.ctrl.DateTools;

public class WeekActivity extends Activity{
	
	Calendar cal;
	Date d;
	DateTools dt;
	private Button b1;
	private Button b2;
	private Button b3;
	private Button b4;
	private Button b5;
	
	private Button b6;
	private Button b7;
	private Button b8;
	private Button b9;
	private Button b10;
	
	

	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_week);
		
		cal = Calendar.getInstance();
		d= new Date();
		d= cal.getTime();
		
		b1 = (Button)findViewById(R.id.week_b1);
		b2 = (Button)findViewById(R.id.week_b2);
		b3 = (Button)findViewById(R.id.week_b3);
		b4 = (Button)findViewById(R.id.week_b4);
		b5 = (Button)findViewById(R.id.week_b5);
		
		b6 = (Button) findViewById(R.id.week2_b1);
		b7 = (Button) findViewById(R.id.week2_b2);
		b8 = (Button) findViewById(R.id.week2_b3);
		b9 = (Button) findViewById(R.id.week2_b4);
		b10 = (Button) findViewById(R.id.week2_b5);
		
		ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		
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
		
		actionbar.addTab(tabSemaine);
		actionbar.addTab(tabToday);
		actionbar.addTab(tabMessage);

		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.liste_menu, menu);
	  
		return true;
	}
	/**
	 * méthode appelée si on clique sur une option du menu
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.menu_about:
			setContentView(R.layout.informations);
			return true;
		case R.id.menu_param:
			return true;
		case R.id.menu_actualiser:
			return true;
		default:
			break;
		}
		return false;
		
	}
	
	 protected void onStart(){
		 super.onStart();
		 getSemaine();
		 
		
	 }
	
	 
     protected void onRestart(){
    	 super.onRestart();
     }

     protected void onResume(){
    	 super.onResume();
     }

     protected void onPause(){
    	 super.onPause();
     }

     protected void onStop(){
    	 super.onStop();
     }

     protected void onDestroy(){
    	 super.onDestroy();
     }
     
     
     public int getFirstMondayOfMonth (){
    	 int act= Calendar.DATE, day= Calendar.DAY_OF_MONTH,bla=Calendar.DAY_OF_WEEK;
    	 
    	 for( act = Calendar.DAY_OF_MONTH ; act > 7 ;act--){
    		 bla=Calendar.DAY_OF_WEEK;
    		 day --;
    	 }
    	 
    	 for (int j = bla; j!=1;j--){
    		 day --;
    	 }
    	 
		return day;
    	 
     }
     
     
	 /*
	public WeekActivity(){
		cal = Calendar.getInstance();
		b1 = (Button)findViewById(R.id.week_b1);
		b2 = (Button)findViewById(R.id.week_b2);
		b3 = (Button)findViewById(R.id.week_b3);
		b4 = (Button)findViewById(R.id.week_b4);
		b5 = (Button)findViewById(R.id.week_b5);
	}
	*/
	public WeekActivity getWeekActivity(){
		if (this == null){
			return (new WeekActivity());
		}
		
		return this;
		
	}
	
	
	public void getSemaine(){
		
		String lun = "lundi " + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+2 )  +" " + getMois(cal.get(Calendar.MONTH));
		String mar = "mardi " + String.valueOf(cal.get(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_WEEK) +3) +" " + getMois(cal.get(Calendar.MONTH));
		String mer = "mercredi " + String.valueOf(cal.get(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_WEEK)+4) +" " + getMois(cal.get(Calendar.MONTH));
		String jeu = "jeudi " + String.valueOf(cal.get(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_WEEK)+5)+" " + getMois(cal.get(Calendar.MONTH));
		String ven = "vendredi  " + String.valueOf(cal.get(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_WEEK)+6) +" " + getMois(cal.get(Calendar.MONTH));
		
		
		
		
		b1.setText(getlibJour(1));
		b2.setText(getlibJour(2));
		b3.setText(getlibJour(3));
		b4.setText(getlibJour(4));
		b5.setText(getlibJour(5));
		
		b6.setClickable(false);
		b7.setClickable(false);
		b8.setClickable(false);
		b9.setClickable(false);
		b10.setClickable(false);
		
	}
	
	
	
	
	public String getJour(int idJour){
		switch(idJour){
		case 1 : return "Lundi";
		case 2 : return "Mardi";
		case 3 : return "mercredi";
		case 4 : return "jeudi";
		case 5 : return "vendredi";
		default : return "erreur jour";
		}
	}
	
	public String getMois(int idMois){
		switch(idMois){
		case 0 : return "Janvier";
		case 1 : return "Fevrier";
		case 2 : return "Mars";
		case 3 : return "Avril";
		case 4 : return "Mai";
		case 5 : return "Juin";
		case 6 : return "Juillet";
		case 7 : return "Aout";
		case 8 : return "Septembre";
		case 9 : return "Octobre";
		case 10 : return "Novembre";
		case 11 : return "Decembre";
		default : return "erreur mois";
		}	
	}
	
	public String verifMois(int jour){
		int m=cal.get(Calendar.MONTH) ; // m sert a recuperer le mois pour les verifications.
		System.out.println(m);
		if ( (cal.get(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1)<1 && m==1 || m==3 || m==5 || m==7 || m==8 || m==10 || m==12){
			System.out.println("debug 0");
			return(String.valueOf(31 - cal.get( Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1 ) + " " + getMois(cal.get(Calendar.MONTH)-1));
		}else if ((cal.get(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1)<1 && m==2 && !IsLeapYear(cal.get(Calendar.YEAR))){
			System.out.println("debug 2");
			return(String.valueOf(29 -cal.get( Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+2 ) + " " + getMois(cal.get(Calendar.MONTH)-1));
		}else if ((cal.get(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1)<1 && m==2 && IsLeapYear(cal.get(Calendar.YEAR))){
			System.out.println("debug 3");
			return(String.valueOf(29 -cal.get( Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1 ) + " " + getMois(cal.get(Calendar.MONTH)-1));
		}else if ( (cal.get(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1)<1 && m==4 || m==6 || m==9 || m==11){
			System.out.println("debug 4");
			return(String.valueOf(31 - (cal.get( Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1 )) + " " + getMois(cal.get(Calendar.MONTH)-1));
		}else if ( (cal.get(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1)>31 && m==1 || m==3 || m==5 || m==7 || m==8 || m==10 || m==12){
			System.out.println("debug 5");
			return(String.valueOf(31 - cal.get(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1 ) + " " + getMois(cal.get(Calendar.MONTH)+1));
		}else if ( (cal.get(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1)>28 && m==2 && !IsLeapYear(cal.get(Calendar.YEAR))){
			System.out.println("debug 6");
			return(String.valueOf(28 -cal.get( Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1 ) + " " + getMois(cal.get(Calendar.MONTH)+1));
		}else if ( (cal.get(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1)>29 && m==2 && IsLeapYear(cal.get(Calendar.YEAR))){
			System.out.println("debug 7");
			return(String.valueOf(29 - cal.get(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1 ) + " " + getMois(cal.get(Calendar.MONTH)+1));
		}else if ( (cal.get(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1)>30 && m==4 || m==6 || m==9 || m==11){
			System.out.println("debug 8");
			 return(String.valueOf(32 - cal.get(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+jour+1  ) + " " + getMois(cal.get(Calendar.MONTH)+1));
		}else {
			System.out.println("debug ultime");
			return(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)- cal.get(Calendar.DAY_OF_WEEK)+1+jour ) + " " + getMois(cal.get(Calendar.MONTH)));
		}
	}
	
	public String getlibJour(int jourSem){
		String str = null;
		
		
		switch (jourSem){
		case 1:
			str= "lundi "+verifMois(1);
			break;
		case 2:
			str= "mardi "+verifMois(2);
			break;	
		case 3:
			str= "mercredi "+verifMois(3);
			break;	
		case 4:
			str= "jeudi "+verifMois(4);
			break;
		case 5:
			str= "vendredi "+verifMois(5);
		break;
		default : 
			str = "soucis";
			break;
		}
		return str;
	}
	
	 public static boolean IsLeapYear(int Year) {
	       return (((Year & 3) == 0) && ((Year % 100 != 0) || (Year % 400 == 0)));
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
			if (getActionBar().getSelectedTab().getText() == "Messages"){
				setContentView(R.layout.informations);
				Intent intent = new Intent(WeekActivity.this, MessagesActivity.class);
				startActivity(intent);
			}

			if (getActionBar().getSelectedTab().getText() == "Aujourd'hui") {
				Intent intent = new Intent(WeekActivity.this, TodayActivity.class);
				startActivity(intent);
			}

			if (getActionBar().getSelectedTab().getText() == "Semaine") {
				
			}
			i = 0;
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

	}
	
	
	
}
