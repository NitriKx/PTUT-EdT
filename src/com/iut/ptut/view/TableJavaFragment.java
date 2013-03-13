package com.iut.ptut.view;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.iut.ptut.R;

/**
 * Ce fragment permet d'illustrer comment gérer un tableau à partir du code de
 * votre application
 * 
 * @author ehret_g
 * 
 */
@TargetApi(11)
public class TableJavaFragment extends Fragment {

    private TableLayout containerTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	// Inflate the layout for this fragment
	return inflater.inflate(R.layout.activity_today, container, false);
    }

    /*public String getName() {
	return "Fragment 3";
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);

	containerTable = (TableLayout) getActivity().findViewById(R.id.containerTable);

	// Recuperation du table layout sur lequel nous allons agir
	String[] players = getResources().getStringArray(R.array.locations);

	// On va calculer la largeur des colonnes en fonction de la marge de 10
	// On affiche l'enreg dans une ligne
	TableRow tableRow = new TableRow(getActivity());
	containerTable.addView(tableRow, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	// bordure
	containerTable.setBackgroundColor(getResources().getColor(R.color.black));

	// On crée une ligne de x players colonnes
	tableRow.setLayoutParams(new LayoutParams(players.length));

	// On va commencer par renseigner une ligne de titre par joueur
	int i = 0;
	for (String player : players) {
	    TextView text = createTextView(false , i == players.length - 1);
	    text.setText(player);
	    text.setGravity(Gravity.CENTER);
	    text.setBackgroundColor(getResources().getColor(R.color.blue));
	    tableRow.addView(text, i++);
	}

	for (int j = 0; j < 10; j++) {
	    tableRow = new TableRow(getActivity());
	    containerTable.addView(tableRow, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	    i = 0;
	    for (String player : players) {
			TextView text = createTextView(j==9, i == players.length - 1);
			text.setText("123");
			tableRow.addView(text, i++);
			text.setGravity(Gravity.RIGHT);
	    }
	}
	

    }
    
    private TextView createTextView(boolean endline, boolean endcolumn){
		TextView text = new TextView(getActivity(), null, R.style.frag3HeaderCol);
		int bottom = endline ? 1 : 0;
		int right = endcolumn ? 1 :0;
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0.3f);
		params.setMargins(1, 1, right, bottom);
		text.setLayoutParams(params);
		text.setPadding(4, 4, 10, 4);
		text.setBackgroundColor(getResources().getColor(R.color.white));
		return text;
    }
}
