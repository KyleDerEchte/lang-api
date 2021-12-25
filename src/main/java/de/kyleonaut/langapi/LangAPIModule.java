package de.kyleonaut.langapi;

import com.google.inject.AbstractModule;
import de.kyleonaut.langapi.provider.RetrofitProvider;
import retrofit2.Retrofit;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
public class LangAPIModule extends AbstractModule {
    private final LangAPIPlugin plugin;

    public LangAPIModule(LangAPIPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(LangAPIPlugin.class).toInstance(plugin);
        bind(Retrofit.class).toProvider(RetrofitProvider.class);
    }
}
