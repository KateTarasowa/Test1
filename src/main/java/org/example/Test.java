package org.example;

import java.sql.*;

public class Test {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myApp?serverTimezone=Europe/Moscow&useSSL=false", "mysql", "mysql");
            if (args[0].equals("1")) {
                createTable(conn);
            }
            if (args[0].equals("2")) {
                createRecord(conn, args[1] + ' ' + args[2] + ' ' + args[3], args[4], args[5]);
            }
            if (args[0].equals("3")) {
                selectRecords(conn);
            }
            if (args[0].equals("4")) {
                selectRecordsMale(conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Hello World!");
    }

    public static void createTable(Connection connection) {
        String myTableName = "CREATE TABLE People ("
                + "FIO TEXT,"
                + "birthday DATE,"
                + "sex ENUM('male', 'female'))";
        //String myTableDrop = "DROP TABLE people";
        try {
            //connection.createStatement().executeUpdate(myTableDrop);
            connection.createStatement().executeUpdate(myTableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createRecord(Connection connection, String fio, String birthday, String gender) {
        String myRecordName = "INSERT INTO people (FIO, birthday, sex) VALUES ('" +
                fio + "','" + birthday + "','" + gender + "')";
        try {
            connection.createStatement().executeUpdate(myRecordName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void selectRecords(Connection connection) {
        String myRecordName = "SELECT DISTINCT FIO, birthday FROM people ORDER BY FIO";
        try {
            ResultSet rs = connection.createStatement().executeQuery(myRecordName);
            while (rs.next()) {
                String fio = rs.getString(1);
                Date birthday = rs.getDate(2);
                int age = getCurrentYear() - Integer.parseInt(birthday.toString().substring(0, 4));
                System.out.println("FIO: " + fio + ", birthday: " + birthday + ", age: " + age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void selectRecordsMale(Connection connection) {
        String myRecordName = "SELECT FIO, birthday FROM people WHERE FIO LIKE 'F%' AND sex='male' ";
        try {
            ResultSet rs = connection.createStatement().executeQuery(myRecordName);
            while (rs.next()) {
                String fio = rs.getString(1);
                Date birthday = rs.getDate(2);
                System.out.println("FIO: " + fio + ", birthday: " + birthday);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getCurrentYear() {
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        return calendar.get(java.util.Calendar.YEAR);
    }
}
