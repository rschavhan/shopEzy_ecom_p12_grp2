package com.ecombackend.excelr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ecombackend.excelr")
public class EcommerceExcelRGrp2Application {

	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceExcelRGrp2Application.class, args);
		System.out.println("********---------Application Started---------**********");

	}

	
}
