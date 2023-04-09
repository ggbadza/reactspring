// package com.tankmilu.spring.config;

// import org.elasticsearch.client.RestHighLevelClient;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.elasticsearch.client.ClientConfiguration;
// import org.springframework.data.elasticsearch.client.RestClients;
// import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
// import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
// import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

// import com.tankmilu.spring.repository.ItemElasticsearchRepository;

// @EnableElasticsearchRepositories(basePackageClasses = ItemElasticsearchRepository.class)
// @Configuration
// public class ElasticsearchConfig {

//     @Bean
//     public RestHighLevelClient elasticsearchcClient() {
//         final ClientConfiguration clientConfiguration =
//             ClientConfiguration.builder()
//                 .connectedTo("localhost:9200")
//                 .build();
//             return RestClients.create(clientConfiguration).rest();
//     }

//     @Bean
//     public ElasticsearchOperations elasticsearchTemplate() {
//         ElasticsearchRestTemplate elasticsearchRestTemplate = new ElasticsearchRestTemplate(elasticsearchcClient());
//         return elasticsearchRestTemplate;
//     }
// }
