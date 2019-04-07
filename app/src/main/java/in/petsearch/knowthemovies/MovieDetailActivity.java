package in.petsearch.knowthemovies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;
import in.petsearch.knowthemovies.model.Genres;
import in.petsearch.knowthemovies.model.MovieDetail;
import in.petsearch.knowthemovies.net.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.petsearch.knowthemovies.common.Utils.getDaySuffix;
import static in.petsearch.knowthemovies.common.Utils.getMonth;

public class MovieDetailActivity extends AppCompatActivity {

    public ProgressDialog pDialog;
    LinearLayout rootLinearLayout;
    ImageView imageView;
    TextView description, runtime, date, rating, genre, language, budget, revenue;

    public String truncateNumber(float floatNumber) {
        long million = 1000000L;
        long billion = 1000000000L;
        long trillion = 1000000000000L;
        long number = Math.round(floatNumber);
        if ((number >= million) && (number < billion)) {
            float fraction = calculateFraction(number, million);
            return Float.toString(fraction) + " Million";
        } else if ((number >= billion) && (number < trillion)) {
            float fraction = calculateFraction(number, billion);
            return Float.toString(fraction) + " Billion";
        }
        return Long.toString(number);
    }

    public float calculateFraction(long number, long divisor) {
        long truncate = (number * 10L + (divisor / 2L)) / divisor;
        float fraction = (float) truncate * 0.10F;
        return fraction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String movieId = null;
        if(intent != null) {
            movieId = intent.getStringExtra("id");
        }
        if(movieId == null) {
            Toast.makeText(this, "No Movie Selected", Toast.LENGTH_SHORT).show();
            finish();
        }
        rootLinearLayout = findViewById(R.id.rootLinearLayout);
        imageView = findViewById(R.id.imageView);
        description = findViewById(R.id.description);
        runtime = findViewById(R.id.runtime);
        date = findViewById(R.id.date);
        rating = findViewById(R.id.rating);
        genre = findViewById(R.id.genre);
        language = findViewById(R.id.language);
        budget = findViewById(R.id.budget);
        revenue = findViewById(R.id.revenue);
        pDialog = new ProgressDialog(MovieDetailActivity.this);
        pDialog.setTitle("Fetching Movie Details");
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Callback<MovieDetail> callback = new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                MovieDetail body = response.body();
                setTitle(body.getTitle());
                Glide.with(imageView).load("https://image.tmdb.org/t/p/w342"+body.getBackdropPath()).into(imageView);
                description.setText(body.getOverview());
                runtime.setText(body.getRuntime()+" minutes");
                String[] releaseDate = body.getReleaseDate().split("-");
                if(releaseDate[2].charAt(0)=='0') {
                    releaseDate[2] = releaseDate[2].substring(1);
                }
                date.setText(releaseDate[2]+getDaySuffix(Integer.parseInt(releaseDate[2]))+" "+getMonth(Integer.parseInt(releaseDate[1]))+" "+releaseDate[0]);
                rating.setText(body.getVoteAverage().toString());
                String genres = "";
                for(Genres gen: body.getGenreIds()) {
                    genres = genres + ", " + gen.name;
                }
                if(genres.length()>0) {
                    genre.setText(genres.substring(2));
                }
                language.setText(body.getOriginalLanguage().toUpperCase());
                budget.setText("$"+truncateNumber(Float.parseFloat(body.getBudget())));
                revenue.setText("$"+truncateNumber(Float.parseFloat(body.getRevenue())));
                pDialog.dismiss();
                rootLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                pDialog.dismiss();
                Log.e("Test", t.getMessage(), t);
                Toast.makeText(MovieDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        Api.moviesDetails().getMovieDetail(movieId).enqueue(callback);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
