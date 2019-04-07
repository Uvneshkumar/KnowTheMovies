package in.petsearch.knowthemovies.net;

import in.petsearch.knowthemovies.model.Movie;
import in.petsearch.knowthemovies.model.Movies;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;

import static in.petsearch.knowthemovies.common.Utils.getDaySuffix;
import static in.petsearch.knowthemovies.common.Utils.getMonth;

public class JsonMoviesListDeserializer implements JsonDeserializer<Movies> {

    @Override
    public Movies deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Movies movies = new Movies();
        if (json.isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
                if (entry.getKey().equals("results")) {
                    JsonArray jsonArray = entry.getValue().getAsJsonArray();
                    for(JsonElement jsonElement: jsonArray) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        Movie db = new Movie();
                        for (Map.Entry<String, JsonElement> innerJson : jsonObject.entrySet()) {
                            if (innerJson.getKey().equals("id")) {
                                db.setId(innerJson.getValue().getAsString());
                            }
                            if (innerJson.getKey().equals("title")) {
                                db.setTitle(innerJson.getValue().getAsString());
                            }
                            if (innerJson.getKey().equals("poster_path")) {
                                db.setPosterPath(innerJson.getValue().getAsString());
                            }
                            if (innerJson.getKey().equals("overview")) {
                                db.setDescription(innerJson.getValue().getAsString());
                            }
                            if (innerJson.getKey().equals("vote_average")) {
                                db.setRating(innerJson.getValue().getAsString());
                            }
                            if (innerJson.getKey().equals("release_date")) {
                                String[] date = innerJson.getValue().getAsString().split("-");
                                if(date[2].charAt(0)=='0') {
                                    date[2] = date[2].substring(1);
                                }
                                db.setDate(date[2]+ getDaySuffix(Integer.parseInt(date[2]))+" "+getMonth(Integer.parseInt(date[1]))+" "+date[0]);
                            }
                            if (innerJson.getKey().equals("original_language")) {
                                db.setLanguage(innerJson.getValue().getAsString().toUpperCase());
                            }
                        }
                        movies.addMovie(db);
                    }
                }
            }
        }
        return movies;
    }
}
