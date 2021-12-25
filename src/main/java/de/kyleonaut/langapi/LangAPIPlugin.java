package de.kyleonaut.langapi;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import de.kyleonaut.langapi.database.DataBase;
import de.kyleonaut.langapi.repository.LangRepository;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
public class LangAPIPlugin extends JavaPlugin {

    @Getter
    private Injector injector;

    @Inject
    private DataBase dataBase;

    @Inject
    private LangRepository langRepository;

    @Override
    public void onEnable() {
        final LangAPIModule module = new LangAPIModule(this);
        this.injector = Guice.createInjector(module);
        this.injector.injectMembers(this);
        saveDefaultConfig();

        dataBase.connect();

        langRepository.init();
    }

    @Override
    public void onDisable() {
        dataBase.close();
    }
}
