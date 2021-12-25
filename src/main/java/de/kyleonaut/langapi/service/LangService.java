package de.kyleonaut.langapi.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.langapi.entity.LangEntity;
import de.kyleonaut.langapi.entity.Translation;
import de.kyleonaut.langapi.repository.IpRepository;
import de.kyleonaut.langapi.repository.LangRepository;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LangService {
    private final LangRepository langRepository;
    private final IpRepository ipRepository;

    public String getCountryCode(Player player) {
        return ipRepository.getIpData(Objects.requireNonNull(player.getAddress()).getHostName()).getCountryCode();
    }

    public Translation getTranslation(String key, String language) {
        final Optional<Translation> optionalTranslation = langRepository.findLangEntityByKey(key).getTranslations().stream()
                .filter(translation -> translation.getLanguage().equalsIgnoreCase(language))
                .findFirst();
        return optionalTranslation.orElse(null);
    }

    public List<Translation> getTranslations(String key) {
        return langRepository.findLangEntityByKey(key).getTranslations();
    }

    public void save(LangEntity langEntity) {
        langRepository.save(langEntity);
    }

    public void save(Translation translation) {
        langRepository.save(translation);
    }

    public void remove(String key) {
        langRepository.delete(key);
    }

    public void remove(String key, String lang) {
        langRepository.delete(new Translation(key, null, lang));
    }
}
