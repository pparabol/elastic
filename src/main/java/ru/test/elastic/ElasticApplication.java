package ru.test.elastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;

@SpringBootApplication(exclude = {ElasticsearchDataAutoConfiguration.class})
public class ElasticApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticApplication.class, args);
	}

}
