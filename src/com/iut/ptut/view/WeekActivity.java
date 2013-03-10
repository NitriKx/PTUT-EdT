package com.iut.ptut.view;


import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.iut.ptut.R;

public class WeekActivity extends Activity{
	
	Calendar cal;
	Date d;
	private Button b1;
	private Button b2;
	private Button b3;
	private Button b4;
	private Button b5;
	
	

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
		
	}
	
	
	 protected void onStart(){
		 super.onStart();
		 getSemaine();
		 System.out.println("test");
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
		
		String lun = getJour(5-cal.get(Calendar.DAY_OF_WEEK)) + " " + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_WEEK) )  +" " + getMois(cal.get(Calendar.MONTH));
		String mar = getJour(6-cal.get(Calendar.DAY_OF_WEEK)) + " " + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_WEEK)+1) +" " + getMois(cal.get(Calendar.MONTH));
		String mer = getJour(7-cal.get(Calendar.DAY_OF_WEEK)) + " " + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_WEEK)+2) +" " + getMois(cal.get(Calendar.MONTH));
		String jeu = getJour(8-cal.get(Calendar.DAY_OF_WEEK)) + " " + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_WEEK)+3) +" " + getMois(cal.get(Calendar.MONTH));
		String ven = getJour(9-cal.get(Calendar.DAY_OF_WEEK)) + " " + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_WEEK)+4) +" " + getMois(cal.get(Calendar.MONTH));
		System.out.println(cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH));
		
		
		;
		b1.setText(lun);
		b2.setText(mar);
		b3.setText(mer);
		b4.setText(jeu);
		b5.setText(ven);
		
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
}
