package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    ArrayList<Genre> favList;

    Spinner spinner;
    ArrayAdapter adpater;

    FlexboxLayout pillContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        pillContainer = findViewById(R.id.genre_pill_container);
        spinner = findViewById(R.id.spinner);
        favList = new ArrayList<>();
        favList.add(Genre.NonFiction);
        favList.add(Genre.Manga);
        updateSpinner();

        for( Genre g : favList)
            addPillContainer(g);

    }


    //Code to populate spinner
    //use R.layout.simple_list_item item verbatim
    void updateSpinner(){
        adpater = new ArrayAdapter(this,R.layout.simple_list_item,R.id.list_item_text,getFilteredList());
        spinner.setAdapter(adpater);
    }

    //list of genres that are not favourited
    public ArrayList<Genre> getFilteredList(){
        ArrayList<Genre> filteredList = new ArrayList<>();

        for(Genre g : Genre.getAllGenreList())
            if(!favList.contains(g))
                filteredList.add(g);

        return filteredList;
    }



    //Everything below is very specific to how views for genre pill is created and populated into the flexbox layout
    public void addGenre(View view) {
        Spinner spinner = findViewById(R.id.spinner);

        if(spinner.getSelectedItem() instanceof  Genre && !spinner.getSelectedItem().equals(Genre.Select)){
            favList.add((Genre)spinner.getSelectedItem());
            addPillContainer((Genre)spinner.getSelectedItem());
            updateSpinner();
            Log.d("Pill count:",""+pillContainer.getChildCount());
        }
    }

    public void addPillContainer(Genre g){
        View genrePill = getLayoutInflater().inflate(R.layout.genre_pill, pillContainer,false);

        ( (TextView)genrePill.findViewById(R.id.genre_pill_tex) ).setText(g.toString());
        ImageButton pillRemove = (ImageButton) genrePill.findViewById(R.id.genre_pill_close_button);

        pillRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pillContainer.removeView(genrePill);
                favList.remove(g);
                updateSpinner();
            }
        });


        pillContainer.addView(genrePill);
    }




}






enum Genre {
    Select,
    Fiction,
    NonFiction,
    SciFi,
    Romance,
    Fantasy,
    Child,
    YoungAdult,
    Adult,
    Comedy,
    Horror,
    GraphicNovel,
    Action,
    Adventure,
    Biography,
    Historical,
    Mystery,
    Survival,
    Manga;

    //creating a nicer default output format for certain genres
    @NonNull
    @Override
    public String toString() {
        String formattedName;

        if(this == NonFiction){
            formattedName = "Non-Fiction";
        }else if(this == SciFi){
            formattedName = "Sci-Fi";
        }else if(this == YoungAdult){
            formattedName = "Young Adult";
        }else if(this == GraphicNovel) {
            formattedName = "Graphic Novel";
        }else if(this == Select){
            formattedName = "Select a genre";
        }else{
            formattedName = this.name();
        }

        return formattedName;
    }

    public static ArrayList<Genre> getAllGenreList(){
        ArrayList<Genre> gList = new ArrayList<>();
        for(Genre g : Genre.values())
            gList.add(g);

        return  gList;
    }
}