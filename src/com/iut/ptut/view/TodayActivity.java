package com.iut.ptut.view;



import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import net.fortuna.ical4j.data.ParserException;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.iut.ptut.R;
import com.iut.ptut.ctrl.DateTools;
import com.iut.ptut.ctrl.DayAdapter;
import com.iut.ptut.model.Lesson;
import com.iut.ptut.model.database.DatabaseManipulationException;
/**
 * 
 * @author Hugz2 & modified by Remy
 *
 */
public class TodayActivity extends Activity {

	//h0800 pour créneaux 8h00
	//h0930 pour créneaux 09h30
	private Button h0800;
	private Button h0930;
	private Button h1105;
	private Button h1415;
	private Button h1540;
	private Button h1715;
	
	private List<Lesson> MyListLesson;

	public void onCreate (Bundle savedInstanceState){
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_today);
		//code de remy pour l'acionBar et les fragment
		//Extract Méthode by Hugz2
		setEnFonctionActionBar();
		
		try{
			
			this.MyListLesson = DayAdapter.getLesson();
			
			//association entre les boutons java et les boutons du XML
			this.h0800 = (Button)findViewById(R.id.h0800);
			this.h0930 = (Button)findViewById(R.id.h0930);
			this.h1105 = (Button)findViewById(R.id.h1105);
			this.h1415 = (Button)findViewById(R.id.h1415);
			this.h1540 = (Button)findViewById(R.id.h1540);
			this.h1715 = (Button)findViewById(R.id.h1715);
			
			//Permet de remplire tous les boutons avec la liste de lesson du jour courant.
			setValuesButtons();
			
		} catch (MalformedURLException e) {
			 e = new MalformedURLException("URL pas malformé, MalformedURLException");
			 System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ParserException e) {
			System.out.println(e.getMessage());
		}catch(DatabaseManipulationException e)
		{
			e = new DatabaseManipulationException("Erreur dans mon test");
		}
		
	}
	
	
	/**
	 * permet de charger toutes les valeur des lessons d'un jour dans les bouttons.
	 * @author Hugz2
	 * @param
	 */
	public void setValuesButtons(){
			
		
			// Si la liste est vide n'est pas vide on continue
			if(!MyListLesson.isEmpty())
			{				
				//boucle pour parcourir les 6 créneaux de la journée.
				//pour chaque créneaux je change le text du bouton qui correspond à ce créneaux
				String b1;
				for (int i = 0; i <this.MyListLesson.size() ; i++) {
					b1 = getValueButton(i);
					
					switch (i) {
					case 0:
						this.h0800.setText(b1);
						break;
					case 1:
						this.h0930.setText(b1);
						break;
					case 2:
						this.h1105.setText(b1);
						break;
					case 3:
						this.h1415.setText(b1);
						break;
					case 4:
						this.h1540.setText(b1);		
						break;
					case 5:
						this.h1715.setText(b1);
						break;

					default:
						break;
					}
				}
				
			}else{
				//on gére le cas ou la liste est vide, on remplis tous les champs à "Tu n'as pas cours"
				this.h0800.setText("Tu n'as pas cours");
				this.h0930.setText("Tu n'as pas cours");
				this.h1105.setText("Tu n'as pas cours");
				this.h1415.setText("Tu n'as pas cours");
				this.h1540.setText("Tu n'as pas cours");
				this.h1715.setText("Tu n'as pas cours");
			}	
			
		
		
	}
	
	/**
	 * permet de créer une chaine de caractere formaté : "08:00-09:30 - ACSI - 102" en fonction d'un indice pour une Liste de lesson.
	 * @author Hugz2
	 * @param  
	 */
	private String getValueButton(int pind)  {
		String b1;
		// je concatenne tous les champs pour faire une chaine affichable dans un boutton.
		try{
			
			b1 = "--- "+DateTools.recupererHeureFormatte(this.MyListLesson.get(pind).getDateDebut());
			b1 += "-"+DateTools.recupererHeureFormatte(this.MyListLesson.get(pind).getDateFin());
			b1 += " -- "+this.MyListLesson.get(pind).getLibelle();//cours
			b1 += " -- "+this.MyListLesson.get(pind).getEmplacement()+" --- ";//emplacement
		
			return b1;
			
		}catch (Exception e/*test pour nullPointerExep*/) 
		{
			return "Tu n'as pas cours";
		}
	}
	
	/**
	 * permet d'allerger le code
	 * @author Hugz2& remy
	 */
	private void setEnFonctionActionBar() {
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

		actionbar.addTab(tabToday);
		actionbar.addTab(tabSemaine);
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
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.menu_about:
			setContentView(R.layout.informations);
			return true;
		case R.id.menu_param:
			return true;
			
		default:
			break;
		}
		return false;
		
	}
	
	protected void onStart(){
		 super.onStart();
		
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
	
	public TodayActivity getTodayActivity(){
		if (this == null){
			return (new TodayActivity());
		}
		
		return this;
	}
	
	
	 protected class ActionBarTabsListener implements ActionBar.TabListener {

	 		@SuppressWarnings("unused")
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
	 				Intent intent = new Intent(TodayActivity.this, MessagesActivity.class);
	 				startActivity(intent);
	 			}

	 			if (getActionBar().getSelectedTab().getText() == "Aujourd'hui") {
	 				
	 			}

	 			if (getActionBar().getSelectedTab().getText() == "Semaine") {
	 				Intent intent = new Intent(TodayActivity.this, WeekActivity.class);
	 				startActivity(intent);
	 			}
	 			i = 0;
	 		}

	 		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	 			// TODO Auto-generated method stub

	 		}

	 	}
}
