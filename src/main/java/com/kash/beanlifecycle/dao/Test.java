package com.kash.beanlifecycle.dao;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        StudentDAO studentDAO = context.getBean("studentDAO", StudentDAO.class);
        studentDAO.selectAllRows();
    }
}

