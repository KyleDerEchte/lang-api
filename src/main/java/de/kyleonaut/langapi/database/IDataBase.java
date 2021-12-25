package de.kyleonaut.langapi.database;

import java.sql.Connection;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
public interface IDataBase {
    void connect();

    void close();

    Connection connection();
}
