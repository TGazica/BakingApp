package hr.tgazica.bakingapp.networking;

import android.content.Context;
import android.net.ConnectivityManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    public static Retrofit createRetrofit(Context context, String URL) {

        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient(context))
                .build();
    }

    private static OkHttpClient okHttpClient(Context context) {
        return new
                OkHttpClient.Builder()
                .addInterceptor(provideLoggingInterceptor())
                .build();
    }


    private static Interceptor provideLoggingInterceptor() {
        return new
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

}

