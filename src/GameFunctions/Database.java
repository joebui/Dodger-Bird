package GameFunctions;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    private Connection c;
    private Statement stmt;

    public Database() {
        // Check connection to sqlite database file.
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:achievement.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void addToDatabase(String name, int t) {
        try {
            stmt = c.createStatement();
            StringBuilder builder = new StringBuilder();
            builder.append(t / 60); builder.append(":"); builder.append(t % 60);

            String sql = "INSERT INTO achievement (name, time, exactTime) VALUES ( \""
                    + name + "\",\""
                    + builder.toString() + "\"," +
                    + t + ");";
            stmt.execute(sql);
            System.out.println("success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void listTopTenResults(ArrayList<JLabel> names, ArrayList<JLabel> times) {
        try {
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM achievement ORDER BY exactTime DESC LIMIT 10;");
            while (resultSet.next()) {
                JLabel name = new JLabel(String.valueOf(resultSet.getString(2)));
                JLabel time = new JLabel(String.valueOf(resultSet.getString(3)));
                name.setForeground(Color.WHITE); name.setFont(new Font("Arial", Font.PLAIN, 14));
                time.setForeground(Color.WHITE); time.setFont(new Font("Arial", Font.PLAIN, 14));
                names.add(name);
                times.add(time);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
