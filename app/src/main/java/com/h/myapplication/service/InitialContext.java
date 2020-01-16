package com.h.myapplication.service;

/**
 * 服务定位模式(Service Locator Design Pattern)
 * 服务定位模式主要针对JNDI(Java Naming and Directory Interface， Java命名和目录接口)。利用缓存技术使得用户在访问统一服务时，可以直接从缓存中取出对象，不用重新创建。
 */
public class InitialContext {
 
    public Object lookup(String jndiName) {
        if(jndiName.equalsIgnoreCase(ServiceOne.class.getSimpleName())) {
            System.out.println("Looking up and creating a new Service1 object");
            return new ServiceOne();
        }
        return null;
    }
    
}