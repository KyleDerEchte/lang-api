package de.kyleonaut.langapi.repository;

import com.google.inject.ImplementedBy;
import de.kyleonaut.langapi.entity.LangEntity;
import de.kyleonaut.langapi.entity.Translation;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
@ImplementedBy(LangRepository.class)
public interface ILangRepository {

    LangEntity findLangEntityByKey(String key);

    void save(LangEntity langEntity);

    void save(Translation translation);

    void delete(String key);

    void delete(Translation translation);

    void init();
}
