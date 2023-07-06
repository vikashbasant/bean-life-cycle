package com.kash.beanlifecycle.dao;

import org.springframework.beans.factory.annotation.Value;

import java.sql.*;


public class StudentDAO {

    @Value("${mySql.userName}")
    private String userName;

    @Value("${mySql.password}")
    private String password;

    @Value("${mySql.url}")
    private String url;
    @Value("${mySql.driver}")
    private String driver;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {

            // load driver:
            Class.forName(this.driver);

            // get connection:
            connection = DriverManager.getConnection(this.url, this.userName, this.password);

            if (connection != null) {
                System.out.println("Relax Bro, Connection With Database is Successful");
            } else {
                System.out.println("No Barry!, Try again something has gone wrong");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection(Connection con) throws SQLException {
        // connection close:
        con.close();
    }

    public void selectAllRows() throws SQLException {
        // get connection:
        Connection con = getConnection();

        // execute query:
        Statement stmt = con.createStatement();

        // get resultSet:
        ResultSet resultSet = stmt.executeQuery("select * from hoststudentinfo");

        // iterate over resultSet:
        while(resultSet.next()) {
            int studentId = resultSet.getInt(1);
            String studentName = resultSet.getString(2);
            String hostelFee = resultSet.getString(3);
            String foodType = resultSet.getString(4);

            System.out.println("Student id: " + studentId + ", name: " + studentName + ", hostelFee: " + hostelFee + ", foodType: " + foodType);

        }

        // connection close:
        closeConnection(con);
    }

    public void deleteStudentRecord(int studentId) throws SQLException {

        // get connection:
        Connection con = getConnection();

        // execute query:
        Statement stmt = con.createStatement();

        stmt.executeQuery("delete from hoststudentinfo where student_id = " + studentId);

        System.out.println("Successfully Delete the Record with studentId: " + studentId);

        // connection close:
        closeConnection(con);
    }
}
