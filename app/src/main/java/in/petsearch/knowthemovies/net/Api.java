package in.petsearch.knowthemovies.net;

import in.petsearch.knowthemovies.model.MovieDetail;
import in.petsearch.knowthemovies.model.Movies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class Api {

    private static ApiInterface api, api2;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static ApiInterface getApi() {
        if (api == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(
                            Movies.class,
                            new JsonMoviesListDeserializer())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            api = retrofit.create(ApiInterface.class);
        }
        return api;
    }

    private static <T> T builder(Class<T> endpoint) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(endpoint);
    }

    public static ApiInterface moviesDetails() {
        return builder(ApiInterface.class);
    }

    public interface ApiInterface {
        @GET("discover/movie?api_key=9a2bdc61f7e9de98ec887d51db0cde00&sort_by=popularity.desc")
        Call<Movies> getMovies();

        @GET("movie/{id}?api_key=9a2bdc61f7e9de98ec887d51db0cde00")
        Call<MovieDetail> getMovieDetail(@Path("id") String movieId);
    }
}