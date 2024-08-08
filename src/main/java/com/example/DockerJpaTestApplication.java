package com.example;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class DockerJpaTestApplication {

	public static void main(String[] args) {SpringApplication.run(DockerJpaTestApplication.class, args);


	}
	//생성자 주입
	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of("Creator");//Optional.of(UUID.randomUUID().toString());
	}

	//JPAQueryFactory 주입
	@Bean
	JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}
}
