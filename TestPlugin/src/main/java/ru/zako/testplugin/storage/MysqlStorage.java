package ru.zako.testplugin.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.zako.testplugin.Config;
import ru.zako.testplugin.User;

import java.sql.*;

public class MysqlStorage implements Storage {
    private final HikariDataSource dataSource;
    private String url = "jdbc:mysql://"+ Config.MySQL.HOST+":"+Config.MySQL.PORT+"/"+Config.MySQL.DATABASE;


    public MysqlStorage() {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(Config.MySQL.USER);
        config.setPassword(Config.MySQL.PASSWORD);
        config.setMaximumPoolSize(3);
        dataSource = new HikariDataSource(config);
    }

    public void unload() {
        dataSource.close();
    }

    @Override
    public User getUser(String name) {
        try (Connection conn = dataSource.getConnection()) {
            try (final PreparedStatement ps = conn.prepareStatement("""
                SELECT * FROM users
                WHERE `name` = ?
            """)) {
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    final int age = rs.getInt("age");

                    return new User(name, age);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void setUserAge(String name, int age) {
        try (Connection conn = dataSource.getConnection()) {
            try (final PreparedStatement ps = conn.prepareStatement("""
                UPDATE users SET `age` = ?
                WHERE `name` = ?
            """)) {
                ps.setInt(1, age);
                ps.setString(2, name);

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
