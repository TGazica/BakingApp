package hr.tgazica.bakingapp;

import android.app.Application;

import hr.tgazica.bakingapp.networking.ApiInteractor;
import hr.tgazica.bakingapp.networking.ApiInteractorImpl;
import hr.tgazica.bakingapp.networking.ApiService;
import hr.tgazica.bakingapp.networking.RetrofitUtil;
import retrofit2.Retrofit;

import static hr.tgazica.bakingapp.util.Constants.BASE_URL;

public class App extends Application {

    private static ApiInteractor apiInteractor;

    @Override
    public void onCreate() {
        super.onCreate();

        final Retrofit retrofit = RetrofitUtil.createRetrofit(this, BASE_URL);
        final ApiService apiService = retrofit.create(ApiService.class);

        apiInteractor = new ApiInteractorImpl(apiService);

    }

    public static ApiInteractor getApiInteractor() {
        return apiInteractor;
    }
}
