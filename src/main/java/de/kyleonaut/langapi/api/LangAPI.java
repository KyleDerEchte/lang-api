package de.kyleonaut.langapi.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.langapi.LangAPIPlugin;
import de.kyleonaut.langapi.entity.LangEntity;
import de.kyleonaut.langapi.entity.Translation;
import de.kyleonaut.langapi.service.LangService;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LangAPI {
    private final LangService langService;
    private final Map<Player, String> countryCodes = new ConcurrentHashMap<>();

    /**
     * Get an Instance of this class to access the API functionalities
     *
     * @return Returns a LangAPI Obejct
     */
    public static LangAPI instance() {
        return LangAPIPlugin.getPlugin(LangAPIPlugin.class).getInjector().getInstance(LangAPI.class);
    }

    /**
     * Find the correct Translation based on the players ip address
     *
     * @param key    the language key
     * @param player the player
     * @return Returns a CompletableFuture with the correct Translation or null if the Translation couldn't be found.
     */
    public CompletableFuture<Translation> getTranslation(Player player, String key) {
        return CompletableFuture.supplyAsync(() -> {
            if (!this.countryCodes.containsKey(player)) {
                final String countryCode = langService.getCountryCode(player);
                this.countryCodes.put(player, countryCode);
                return langService.getTranslation(key, countryCode);
            }
            return langService.getTranslation(key, this.countryCodes.get(player));
        });
    }

    /**
     * Find all Translations for the given language key
     *
     * @param key the language key
     * @return Returns a CompletableFuture with all Translations or null if no Translations could be found.
     */
    public CompletableFuture<LangEntity> getLangEntity(String key) {
        return CompletableFuture.supplyAsync(() -> new LangEntity(key, langService.getTranslations(key)));
    }

    /**
     * Save a Translation Object
     */
    public void saveTranslation(Translation translation) {
        CompletableFuture.runAsync(() -> {
            langService.save(translation);
        });
    }

    /**
     * Save all Translations with the given LangEntity
     */
    public void saveLangEntity(LangEntity langEntity) {
        CompletableFuture.runAsync(() -> {
            langService.save(langEntity);
        });
    }

    /**
     * Save a Translation by the given params
     *
     * @param key      the language key
     * @param content  the content of the translation
     * @param language the language
     */
    public void saveTranslation(String key, String content, String language) {
        CompletableFuture.runAsync(() -> langService.save(new Translation(key, content, language)));
    }

    /**
     * Remove a Translation by the given Object
     */
    public void remove(Translation translation) {
        CompletableFuture.runAsync(() -> langService.remove(translation.getKey(), translation.getLanguage()));
    }

    /**
     * Remove all Translations by the given key
     */
    public void remove(String key) {
        CompletableFuture.runAsync(() -> langService.remove(key));
    }

    /**
     * Remove a Translation by the given params
     */
    public void remove(String key, String language) {
        CompletableFuture.runAsync(() -> langService.remove(key, language));
    }
}
