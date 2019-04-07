package in.petsearch.knowthemovies.model;

import androidx.databinding.BaseObservable;

public class Movie extends BaseObservable {
    private String title;
    private MovieDetail movieDetail;
    private String description;
    private String rating;
    private String date;
    private String language;
    private String posterPath;
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public MovieDetail getMovieDetail() {
        return movieDetail;
    }
    public void setMovieDetail(MovieDetail movieDetail) {
        this.movieDetail = movieDetail;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getPosterPath() {
        return posterPath;
    }
    public void setPosterPath(String posterPath) {
        this.posterPath = "https://image.tmdb.org/t/p/w342"+posterPath;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
