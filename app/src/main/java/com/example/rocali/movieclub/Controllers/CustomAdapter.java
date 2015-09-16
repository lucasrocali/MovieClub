package com.example.rocali.movieclub.Controllers;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.rocali.movieclub.Models.Model;
import com.example.rocali.movieclub.R;

public class CustomAdapter extends BaseAdapter{

    //model
    public Model model = Model.getInstance();

    //array of img resources
    int [] movieImgSrcs;
    Context context;    //To be sent to get the img resource


    private static LayoutInflater inflater=null;
    public CustomAdapter(MovieList movieList) {
        // TODO Auto-generated constructor stub

        //Get context to be sent to model to get img resources
        context = movieList;
        movieImgSrcs = model.getImgResources(context);

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return model.movies.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    //Class to hold the elements of the row
    public class RowInfo
    {
        ImageView poster;
        TextView title;
        TextView year;
        TextView shortPlot;
        TextView dateAndTime;
        TextView location;
        TextView nInvitees;
        RatingBar rating;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //Creating row info
        RowInfo rowInfo = new RowInfo();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_movie_row, null);

        //Finding elements of the row and keeping on the rowInfo class
        rowInfo.poster = (ImageView) rowView.findViewById(R.id.imgPosterList);
        rowInfo.title = (TextView) rowView.findViewById(R.id.txtTitle);
        rowInfo.year = (TextView) rowView.findViewById(R.id.txtYear);
        rowInfo.shortPlot = (TextView) rowView.findViewById(R.id.txtShortPlot);
        rowInfo.dateAndTime = (TextView) rowView.findViewById(R.id.txtDateTime);
        rowInfo.location = (TextView) rowView.findViewById(R.id.txtLocation);
        rowInfo.nInvitees = (TextView) rowView.findViewById(R.id.txtNInvitees);
        rowInfo.rating = (RatingBar) rowView.findViewById(R.id.rtbListMovie);

        //Seting information of the movie (never change)
        rowInfo.poster.setImageResource(movieImgSrcs[position]);
        rowInfo.title.setText(model.movies[position].title);
        rowInfo.year.setText(model.movies[position].year);
        rowInfo.shortPlot.setText(model.movies[position].shortPlot);

        //Set ratting in case of the user had ratted the movie before
        if (model.movies[position].rating != 0) {
            rowInfo.rating.setRating(model.movies[position].rating);
        }
        //Setting the information of the party schedulled
        if (model.movies[position].scheduled == true) {
            rowInfo.dateAndTime.setText(model.movies[position].date + " - " + model.movies[position].time);
            rowInfo.location.setText("At " + model.movies[position].location);
            int nInv = model.movies[position].invitees.size();
            rowInfo.nInvitees.setText("Invitees: " + String.valueOf(nInv));

        } else {
            //In case of there is no schedulled party for the movie
            rowInfo.dateAndTime.setText("Movie not scheduled yet, be the first!");
            rowInfo.location.setText(" ");
            rowInfo.nInvitees.setText(" ");

        }

        //If the user click on the row, its called the new view with the movie details
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(context.getApplicationContext(), MovieSelected.class);
                i.putExtra("movieId", position + "");
                context.startActivity(i);
            }
        });

        //set action to rating bar
        rowInfo.rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                model.movies[position].rating = rating;


            }
        });
        return rowView;
    }

}