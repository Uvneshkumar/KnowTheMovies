package in.petsearch.knowthemovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import in.petsearch.knowthemovies.databinding.ActivityMoviesListBinding;
import in.petsearch.knowthemovies.model.Movie;
import in.petsearch.knowthemovies.viewmodel.MoviesListViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MoviesListActivity extends AppCompatActivity {
    private MoviesListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        setTitle("Popular Movies");
        setupBindings(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setupBindings(Bundle savedInstanceState) {
        ActivityMoviesListBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_movies_list);
        viewModel = ViewModelProviders.of(this).get(MoviesListViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        activityBinding.setModel(viewModel);
        setupListUpdate();
    }

    private void setupListUpdate() {
        viewModel.loading.set(View.VISIBLE);
        viewModel.fetchList();
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movieList) {
                viewModel.loading.set(View.GONE);
                if (movieList.size() == 0) {
                    viewModel.showEmpty.set(View.VISIBLE);
                } else {
                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setMoviesInAdapter(movieList);
                }
            }
        });
        setupListClick();
    }

    private void setupListClick() {
        viewModel.getSelected().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                if (movie != null) {
                    Intent intent = new Intent(MoviesListActivity.this, MovieDetailActivity.class);
                    intent.putExtra("id", movie.getId());
                    startActivity(intent);
                }
            }
        });
    }
}
