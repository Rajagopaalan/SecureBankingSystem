package com.websystique.springmvc.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@EnableTransactionManagement
@ComponentScan({ "com.websystique.springmvc.configuration" })
@PropertySource(value = { "classpath:application.properties" }) 
public class MailConfiguration {

//    @Value("${email.host}")
//    private String host;
//
//    @Value("${email.from}")
//    private String from;
//
//    @Value("${email.subject}")
//    private String subject;

    @Autowired
    public Environment environment;
    
    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername("sbasuparizona@gmail.com");
        javaMailSender.setPassword("rkreddy123");
        javaMailSender.setPort(587);
        javaMailSender.setJavaMailProperties(javaMailProperties());
        return javaMailSender;
    }
    
    private Properties javaMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);

        return properties;        
    }

//    @Bean
//    public SimpleMailMessage simpleMailMessage() {
//       SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//       simpleMailMessage.setFrom(from);
//       simpleMailMessage.setSubject(subject);
//       return simpleMailMessage;
//    }
}
