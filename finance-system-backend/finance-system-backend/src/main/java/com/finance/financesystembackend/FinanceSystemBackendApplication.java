package com.finance.financesystembackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.finance.financesystembackend.**.mapper")
public class FinanceSystemBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceSystemBackendApplication.class, args);
	}
}
