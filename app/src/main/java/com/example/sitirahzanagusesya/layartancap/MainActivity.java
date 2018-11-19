package com.example.sitirahzanagusesya.layartancap;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.OnMovieItemClicked {

    RecyclerView recyclerView;
    MovieListAdapter adapter;
    ArrayList<MovieItem> daftarFilm = new ArrayList<>();
    ProgressBar progressBar;


    //http://api.themoviedb.org/3/movie/now_playing?api_key=a5914da7e79026cf3d11c42c298d7121
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);

        adapter = new MovieListAdapter(this);
        adapter.setClickHandler(this);
        getNowPlayingMovies();
        //loadDummyData();

        //ini yang penting
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        int mode = pref.getInt("display_status_key", 1);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        //hasil yang disimpan dari display_status_key
        if (mode == 1) {
            getNowPlayingMovies();
        } else {
            getUpComingMovies();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.id_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        //OnQueryTextListener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getBaseContext(), query, Toast.LENGTH_SHORT).show();

                // Get the intent, verify the action and get the query
//                Intent intent = getIntent();
//                if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//                    String query = intent.getStringExtra(SearchManager.QUERY);
//                    doMySearch(query);
//                }

//                adapter.getFilter().filter(query);

                return false;
            }

//            private void doMySearch(String query) {
//            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //Toast.makeText(getBaseContext(), newText, Toast.LENGTH_SHORT).show();
                return false;
            }

        });

        //SearchView.setOnSearchClickListener(searchView);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                Toast.makeText(this, "Refreshing data", Toast.LENGTH_SHORT).show();
                SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
                int mode = pref.getInt("display_status_key", 1);

                //hasil yang disimpan dari display_status_key
                if (mode == 1) {
                    getNowPlayingMovies();
                } else {
                    getUpComingMovies();
                }
                //getNowPlayingMovies();
                break;

            case R.id.now_playing:
                getNowPlayingMovies();
                break;

            case R.id.up_coming:
                getUpComingMovies();
                break;
        }

        //if (id==R.id.menu_refresh){
        //  getNowPlayingMovies();
        //loadDummyData();
        //}
        return super.onOptionsItemSelected(item);
    }

    private void getNowPlayingMovies() {

        //progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        String API_BASE_URL = "https://api.themoviedb.org";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TmdbClient client = retrofit.create(TmdbClient.class);

        Call<MovieList> call = client.getNowPlayingMovies("c78351309f5aaac404328646105322d2");
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                //Disini kode kalau berhasil
                MovieList movieList = response.body();
                List<MovieItem> listMovieItem = movieList.results;
                adapter.setDataFilm(new ArrayList<MovieItem>(listMovieItem));

                getSupportActionBar().setTitle("Now Playing");

                //progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

                //Disini kode kalau error

                //progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }
        });

        //sharedpreferences()
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("display_status_key", 1);
        editor.commit();
    }

    private void getUpComingMovies() {

        //progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        String API_BASE_URL = "https://api.themoviedb.org";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TmdbClient client = retrofit.create(TmdbClient.class);

        Call<MovieList> call = client.getUpComingMovies("c78351309f5aaac404328646105322d2");
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                //Disini kode kalau berhasil
                MovieList movieList = response.body();
                List<MovieItem> listMovieItem = movieList.results;
                adapter.setDataFilm(new ArrayList<MovieItem>(listMovieItem));

                getSupportActionBar().setTitle("Upcoming");

                //progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

                //Disini kode kalau error

                //progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }
        });

        //sharedpreferences()
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("display_status_key", 2);
        editor.commit();



//    private void ambilDataKeServer() {
//
//        AmbilDataTask task = new AmbilDataTask();
//        task.execute();
//    }

        //@Override
        //public void clik (int position){
//        Toast.makeText(this, "hai "+position, Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(MainActivity.this, DetailActivity.class);
//        startActivity(i);
        //}


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

    @Override
    public void clik(int i) {

    }
}