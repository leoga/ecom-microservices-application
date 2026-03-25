//package com.leoga.ecom.notification.configuration;
//
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@ConfigurationProperties(prefix = "rabbitmq")
//@Data
//public class RabbitMQData {
//    private Exchange exchange;
//    private Queue queue;
//    private Routing routing;
//
//    @Data
//    public static class Exchange {
//        private String name;
//    }
//
//    @Data
//    public static class Queue {
//        private String name;
//    }
//
//    @Data
//    public static class Routing {
//        private String key;
//    }
//}
