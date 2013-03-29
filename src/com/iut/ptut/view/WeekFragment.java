package com.iut.ptut.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.iut.ptut.R;
import com.iut.ptut.ctrl.SemaineCtrl;

/**
 * 
 * @author Rémy Bienvenu, Benoît Sauvère
 *
 */
public class WeekFragment extends Fragment {
	
	private Logger _log = Logger.getLogger(this.getClass().getName());
	
	private View vue;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		vue = inflater.inflate(R.layout.activity_week, container, false);
		creerBoutons();
		return vue;
	}
	
	/**
	 * Cette méthode créer les boutons en se basant sur les donées disponible sur algec.
	 */
	public void creerBoutons() {
		
		SimpleDateFormat formatDateBouton = new SimpleDateFormat("EEEE dd/MM/yyyy", Locale.getDefault());
		
		try {
			// On récupère le layout qui va héberger les boutons
			LinearLayout parent = (LinearLayout) vue.findViewById(R.id.layout_affichage_boutons);
			// On reset la vue
			parent.removeAllViews();
			
			List<Date> listeJour = SemaineCtrl.getListeJourDisponible();
			
			for(Date d : listeJour) {
				Button but = new Button(vue.getContext());
				but.setGravity(Gravity.CENTER);
				but.setText(formatDateBouton.format(d));
				but.setTag(d.getTime());
				// On créer un listener pour faire une instance pour afficher le jour
				but.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						
						// On lance un intent dans l'hébergeur de fragment
						Bundle args = new Bundle();
						args.putLong("date", ((Long) v.getTag()));
						args.putBoolean("needBack", true);
						
						FragmentTransaction ft = getFragmentManager().beginTransaction(); 
						FragmentManager.chargerFragment(TodayFragment.class, args, ft);
						ft.commit();
					}
					
				});
				
				// On ajoute le bouton dans la vue
				parent.addView(but);
			}
			
		} catch (ParseException e) {
			_log.log(Level.SEVERE, "Erreur lors de la création des boutons. Raison = [" + e.getMessage() + "]");
		}
		
		
	}
	
}
