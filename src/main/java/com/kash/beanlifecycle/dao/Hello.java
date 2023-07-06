package com.kash.beanlifecycle.dao;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Hello implements InitializingBean, DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("==>Inside after the property set method--> init() method");
    }

    public void sample(){
        System.out.println("==>My coding starts from here and end here!");
    }
    @Override
    public void afterPropertiesSet() throws Exception {

        System.out.println("==>Inside destroy method--> destroy() method");
    }
}
