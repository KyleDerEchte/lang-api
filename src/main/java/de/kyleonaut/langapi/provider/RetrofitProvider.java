package de.kyleonaut.langapi.provider;

import com.google.inject.Provider;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
public class RetrofitProvider implements Provider<Retrofit> {
    @Override
    public Retrofit get() {
        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl("http://ip-api.com")
                .build();
    }
}
