package com.example.sitirahzanagusesya.layartancap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    public static String EXTRA_FILM = "extra_film";
    TextView detailTitle;
    TextView detailRating;
    TextView detailTanggal;
    TextView detailOverview;
    ImageView imagePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MovieItem movie = getIntent().getParcelableExtra(EXTRA_FILM);
        detailTitle = findViewById(R.id.detail_title);
        detailRating =  findViewById(R.id.detail_rating);
        detailTanggal = findViewById(R.id.detail_TanggalRilis);
        detailOverview = findViewById(R.id.detail_overview);
        imagePoster = findViewById(R.id.detail_poster);

        detailTitle.setText(movie.getTitle());
        detailRating.setText(String.valueOf(movie.getVote_average()));
        detailTanggal.setText(movie.getRelease_date());
        detailOverview.setText(movie.getOverview());

        String url = "http://image.tmdb.org/t/p/w300" + movie.getPoster_path();
        Glide.with(this)
                .load(url)
                .into(imagePoster);
    }
}
