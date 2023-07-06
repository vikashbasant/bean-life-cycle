package com.kash.beanlifecycle.dao;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
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

    // Connection Object:
    Connection con;


    //======================================Setter Method===================================================//
    public void setUserName(String userName) {
        System.out.println("-->Setter Method for Name is Called!<--");
        this.userName = userName;
    }

    public void setPassword(String password) {
        System.out.println("-->Setter Method for Password is Called!<--");
        this.password = password;
    }

    public void setUrl(String url) {
        System.out.println("-->Setter Method for URL is Called!<--");
        this.url = url;
    }

    public void setDriver(String driver) {
        System.out.println("-->Setter Method for Driver is Called!<--");
        this.driver = driver;
    }


    //======================================Init Method===================================================//

    /**
     *
     * getConnection() is the init method for us. annotate a method with @PostConstruct to use it as a init method:
     * <p/>
     * We don't need to call init method, Our spring framework will call it for us.
     * <p/>
     * We can give our init method name as anything.
     */
    @PostConstruct
    public void init() {
        System.out.println("=>>Inside Custom Init Method<<=");
        getConnection();
    }


    public void getConnection() {
        System.out.println("-->Inside Get connection Method Called<--");
        try {

            // load driver:
            Class.forName(this.driver);

            // get connection:
            con = DriverManager.getConnection(this.url, this.userName, this.password);

            if (con != null) {
                System.out.println("Relax Bro, Connection With Database is Successful");
            } else {
                System.out.println("No Barry!, Try again something has gone wrong");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }



    //======================================Utility Method===================================================//

    public void selectAllRows() throws SQLException {

        System.out.println("-->selectAllRows Method Called<--");

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


    }

    public void deleteStudentRecord(int studentId) throws SQLException {

        System.out.println("-->deleteStudentRecord Method Called<--");

        // execute query:
        Statement stmt = con.createStatement();

        stmt.executeQuery("delete from hoststudentinfo where student_id = " + studentId);

        System.out.println("Successfully Delete the Record with studentId: " + studentId);


    }


    //======================================Destroy Method===================================================//

    /**
     * @PreDestroy() annotation over the method which has to be called before the container is destroyed.
     * <p/>
     * Before your container object will destroy, spring will call your custom destroy method.
     * <p/>
     * Remember that the destroy method will only be called once your container object got destroyed/close.
     *
     */
    @PreDestroy
    public void destroy() throws SQLException {
        System.out.println("Inside Bean destroy method");

        // => cleanup job
        closeConnection();
    }


    public void closeConnection() throws SQLException {

        System.out.println("-->Inside Close Connection Method Called<--");
        // connection close:
        con.close();
    }
}
