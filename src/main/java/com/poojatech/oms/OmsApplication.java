package com.poojatech.oms;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class OmsApplication {
	public static void main(String[] args) {
        SpringApplication.run(OmsApplication.class, args);


	}

}
