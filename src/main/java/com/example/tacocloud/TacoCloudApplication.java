package com.example.tacocloud;

import com.example.tacocloud.data.IngredientRepository;
import com.example.tacocloud.domain.Ingredient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TacoCloudApplication {
	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

}
