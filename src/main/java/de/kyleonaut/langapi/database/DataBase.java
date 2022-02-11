package de.kyleonaut.langapi.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.langapi.LangAPIPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class DataBase implements IDataBase {
    private final LangAPIPlugin plugin;
    private Connection connection;

    @Override
    public void connect() {
        final String url = plugin.getConfig().getString("mysql.url");
        final String user = plugin.getConfig().getString("mysql.user");
        final String password = plugin.getConfig().getString("mysql.password");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
            Bukkit.getLogger().log(Level.INFO, "[LangApI] Established SQL connection.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close() {
        if (this.connection == null) {
            return;
        }
        try {
            if (this.connection.isClosed()) {
                return;
            }
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection connection() {
        return this.connection;
    }
}
