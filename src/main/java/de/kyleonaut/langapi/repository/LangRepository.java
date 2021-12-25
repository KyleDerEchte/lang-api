package de.kyleonaut.langapi.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.langapi.database.DataBase;
import de.kyleonaut.langapi.entity.LangEntity;
import de.kyleonaut.langapi.entity.Translation;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LangRepository implements ILangRepository {
    private final DataBase dataBase;

    @Override
    public LangEntity findLangEntityByKey(String key) {
        try {
            @Cleanup final PreparedStatement ps = dataBase.connection().prepareStatement(
                    "SELECT * FROM translations WHERE langkeys.langkey = ?;"
            );
            ps.setString(1, key);
            @Cleanup final ResultSet rs = ps.executeQuery();
            final List<Translation> translations = new ArrayList<>();
            while (rs.next()) {
                final Translation translation = new Translation();
                translation.setKey(key);
                translation.setLanguage(rs.getString("lang"));
                translation.setContent(rs.getString("content"));
                translations.add(translation);
            }
            return new LangEntity(key, translations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new LangEntity(key, new ArrayList<>());
    }

    @Override
    public void save(LangEntity langEntity) {
        try {
            @Cleanup final PreparedStatement ps = dataBase.connection().prepareStatement(
                    "INSERT IGNORE INTO langkeys VALUES(?);"
            );
            ps.setString(1, langEntity.getKey());
            ps.execute();
            for (Translation translation : langEntity.getTranslations()) {
                @Cleanup final PreparedStatement ps1 = dataBase.connection().prepareStatement(
                        "REPLACE INTO translations VALUES(?,?,?);"
                );
                ps1.setString(1, translation.getKey());
                ps1.setString(2, translation.getContent());
                ps1.setString(3, translation.getLanguage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Translation translation) {
        try {
            @Cleanup final PreparedStatement ps = dataBase.connection().prepareStatement(
                    "REPLACE INTO translations VALUES(?,?,?);"
            );
            ps.setString(1, translation.getKey());
            ps.setString(2, translation.getContent());
            ps.setString(3, translation.getLanguage());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String key) {
        try {
            @Cleanup final PreparedStatement ps = dataBase.connection().prepareStatement(
                    "DELETE FROM translations WHERE langkey = ?; DELETE FROM langkeys WHERE langkey = ?;"
            );
            ps.setString(1, key);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Translation translation) {
        try {
            @Cleanup final PreparedStatement ps = dataBase.connection().prepareStatement(
                    "DELETE FROM translations WHERE langkey = ? AND lang = ?;"
            );
            ps.setString(1, translation.getKey());
            ps.setString(1, translation.getLanguage());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        try {
            @Cleanup final PreparedStatement ps = dataBase.connection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS langkeys(langkey VARCHAR(255) PRIMARY KEY NOT NULL);"
            );
            ps.execute();
            @Cleanup final PreparedStatement ps1 = dataBase.connection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS translations(langkey VARCHAR(255),content TEXT,lang VARCHAR(10), FOREIGN KEY (langkey) REFERENCES langkeys(langkey),PRIMARY KEY (langkey,lang));"
            );
            ps1.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
