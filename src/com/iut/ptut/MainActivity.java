package com.iut.ptut;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    
    /*
     * le code ci-dessous permet d'afficher une aute fenetre a partir d'un bouton, pour cela
     * 1) creer le bouton
     * 2)dans le code xml, rajouter la ligne android:onClick""
     * 3) mettre le nom de la methode entre les parentheses
     * 
     * /!\ pour afficher une fenetre c'est setContentView /!\
     * et vous pouvez rajouter du code aussi dans la methode
     */
    public void displayInfos (View v){
    	setContentView(R.layout.informations);
    }
    
    public void displayMain (View v){
    	setContentView(R.layout.activity_main);
    }
   
    

}