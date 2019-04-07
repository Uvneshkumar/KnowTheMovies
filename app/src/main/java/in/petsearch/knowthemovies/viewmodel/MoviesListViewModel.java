package in.petsearch.knowthemovies.viewmodel;

import android.util.Log;
import android.view.View;

import in.petsearch.knowthemovies.R;
import in.petsearch.knowthemovies.adapter.MoviesAdapter;
import in.petsearch.knowthemovies.model.Movie;
import in.petsearch.knowthemovies.model.Movies;

import java.util.List;

import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MoviesListViewModel extends ViewModel {

    private Movies movies;
    private MoviesAdapter adapter;
    public MutableLiveData<Movie> selected;
    public ObservableArrayMap<String, String> images;
    public ObservableInt loading;
    public ObservableInt showEmpty;

    public void init() {
        movies = new Movies();
        selected = new MutableLiveData<>();
        adapter = new MoviesAdapter(R.layout.view_movie, this);
        images = new ObservableArrayMap<>();
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }

    public void fetchList() {
        movies.fetchList();
    }

    public MutableLiveData<List<Movie>> getMovies() {
        return movies.getMutableLiveData();
    }

    public MoviesAdapter getAdapter() {
        return adapter;
    }

    public void setMoviesInAdapter(List<Movie> movies) {
        this.adapter.setMoviesList(movies);
        this.adapter.notifyDataSetChanged();
    }

    public MutableLiveData<Movie> getSelected() {
        return selected;
    }

    public void onItemClick(Integer index) {
        Movie db = getMovieAt(index);
        selected.setValue(db);
    }

    public Movie getMovieAt(Integer index) {
        if (movies.getMutableLiveData().getValue() != null &&
                index != null &&
                movies.getMutableLiveData().getValue().size() > index) {
            return movies.getMutableLiveData().getValue().get(index);
        }
        return null;
    }

    public void fetchMovieImagesAt(Integer index) {
        Movie movie = getMovieAt(index);
        if (movie != null && !images.containsKey(movie.getTitle())) {
            images.put(movie.getTitle(), movie.getPosterPath());
            Log.d("Image URL", movie.getPosterPath());
        }
    }


}
