package hr.tgazica.bakingapp.networking;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Callback;

public class ApiInteractorImpl implements ApiInteractor {

    private final ApiService apiService;

    public ApiInteractorImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getBakingData(Callback<JsonArray> callback) {
        apiService.getBakingData().enqueue(callback);
    }
}
