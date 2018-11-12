package com.example.sitirahzanagusesya.layartancap;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sitirahzanagusesya.layartancap.R;
import com.example.sitirahzanagusesya.layartancap.MovieItem;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolder> {
    ArrayList<MovieItem> dataFilm;
    OnMovieItemClicked Handler;
    Context context;

    public MovieListAdapter(Context context){
        this.context = context;
    }
    public void setDataFilm(ArrayList<MovieItem> films){
        this.dataFilm = films;
        notifyDataSetChanged();
    }

    public void setClickHandler(OnMovieItemClicked clickHandler) {
        this.Handler = clickHandler;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        MovieHolder holder = new MovieHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, final int position) {
        MovieItem movie = dataFilm.get(position);
        holder.textTitle.setText(movie.getTitle());
        holder.textRating.setText(String.valueOf(movie.getVote_average()));
        holder.textTanggalRilis.setText(movie.getRelease_date());
        String url = "http://image.tmdb.org/t/p/w300" + movie.getPoster_path();
        Glide.with(holder.itemView)
                .load(url)
                .into(holder.imagePoster);
        holder.view_layar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_FILM, dataFilm.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataFilm !=null){
            return dataFilm.size();
        }
        return 0;
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        ImageView imagePoster;
        TextView textTitle;
        TextView textRating;
        TextView textTanggalRilis;
        LinearLayout view_layar;
        public MovieHolder(View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.img_poster);
            textTitle = itemView.findViewById(R.id.textTitle);
            textRating = itemView.findViewById(R.id.textRating);
            textTanggalRilis = itemView.findViewById(R.id.textTanggal);
            view_layar = (LinearLayout)itemView.findViewById(R.id.container);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Handler.clik(getAdapterPosition());
//
//                }
//            });
        }

    }
    public interface OnMovieItemClicked{
        void clik(int i);

    }
}