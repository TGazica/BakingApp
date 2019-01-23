package hr.tgazica.bakingapp.networking;

import com.google.gson.JsonArray;

import retrofit2.Callback;


public interface ApiInteractor {

    void getBakingData(Callback<JsonArray> callback);

}
