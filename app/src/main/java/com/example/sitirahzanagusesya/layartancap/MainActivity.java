package com.example.sitirahzanagusesya.layartancap;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sitirahzanagusesya.layartancap.MovieListAdapter;
import com.example.sitirahzanagusesya.layartancap.MovieItem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.OnMovieItemClicked{

    RecyclerView recyclerView;
    MovieListAdapter adapter;
    ArrayList<MovieItem> daftarFilm = new ArrayList<>();


    //http://api.themoviedb.org/3/movie/now_playing?api_key=a5914da7e79026cf3d11c42c298d7121
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.rv_movie_list);

        adapter = new MovieListAdapter(this);
        adapter.setClickHandler(this);
        getNowPlayingMoview();
        //loadDummyData();
        //ini yang penting
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menu_refresh){
            getNowPlayingMoview();
            //loadDummyData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getNowPlayingMoview(){
        String API_BASE_URL = "https://api.themoviedb.org";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TmdbClient client =  retrofit.create(TmdbClient.class);

        Call<MovieList> call = client.getMovies("c78351309f5aaac404328646105322d2");
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response)   {
                //Disini kode kalau berhasil
                MovieList movieList = response.body();
                List<MovieItem> listMovieItem = movieList.results;
                adapter.setDataFilm(new ArrayList<MovieItem>(listMovieItem));
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                //Disini kode kalau error
            }
        });

    }
//    private void ambilDataKeServer() {
//
//        AmbilDataTask task = new AmbilDataTask();
//        task.execute();
//    }

    @Override
    public void clik(int position) {
//        Toast.makeText(this, "hai "+position, Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(MainActivity.this, DetailActivity.class);
//        startActivity(i);
    }


//    @Override
//    public void clik(int position) {
//        Intent i = new Intent(MainActivity.this, DetailActivity.class);
//        startActivity(i);
//    }

//    class AmbilDataTask extends AsyncTask<Void,Void, String>{
//        String result;
//        @Override
//        protected String doInBackground(Void... voids) {
//            //String result;
//            String webUrl="http://api.themoviedb.org/3/movie/now_playing?api_key="+BuildConfig.My_Db_api_key;
//            HttpURLConnection urlConnection;
//
//            try{
//                URL url = new URL(webUrl);
//                urlConnection = (HttpURLConnection)url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                //ambil data ke server
//                InputStream inputStream = urlConnection.getInputStream();
//                if (inputStream == null){
//                    return null;
//                }
//                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//                StringBuffer buffer = new StringBuffer();
//                String line;
//                while((line= reader.readLine()) !=null){
//                    buffer.append(line +"\n");
//                }
//                if (buffer.length()==0){
//                    return null;
//                }
//                result = buffer.toString();
//                //textView.setText(result);
//
//            }catch (Exception e){
//
//            }
//            return result;
//        }
//        @Override
//        protected void onPostExecute(String result){
//            // textView.setText(result);
//            super.onPostExecute(result);
//        }
//    }
}