package in.petsearch.knowthemovies.model;

import android.util.Log;

import in.petsearch.knowthemovies.net.Api;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Movies extends BaseObservable {
    private List<Movie> movieArrayList = new ArrayList<>();
    private MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();

    public void addMovie(Movie bd) {
        movieArrayList.add(bd);
    }

    public MutableLiveData<List<Movie>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void fetchList() {
        Callback<Movies> callback = new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                Movies body = response.body();
                mutableLiveData.setValue(body.movieArrayList);
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Log.e("Test", t.getMessage(), t);
            }
        };
        Api.getApi().getMovies().enqueue(callback);
    }
}
