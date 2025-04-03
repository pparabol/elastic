package ru.test.elastic.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.client.RestClient;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;

@Configuration
public class OpenSearchConfig {

    @Value("${opensearch.host}")
    private String host;

    @Value("${opensearch.port}")
    private int port;

    @Value("${opensearch.username}")
    private String username;

    @Value("${opensearch.password}")
    private String password;

    @Value("${opensearch.scheme}")
    private String scheme;

//    @Value("${opensearch.ssl.truststore.path}")
//    private String truststorePath;

    @Bean
    public OpenSearchClient openSearchClient() throws Exception {
        final HttpHost host = new HttpHost(this.host, this.port, this.scheme);
//        KeyStore truststore = KeyStore.getInstance("JKS");
//        try (InputStream is = new ClassPathResource("opensearch-truststore.jks").getInputStream()) {
//            truststore.load(is, "changeit".toCharArray());
//        }
//
//        // Create SSL context
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        tmf.init(truststore);
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, tmf.getTrustManagers(), null);

        // Set up credentials
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(host),
                new UsernamePasswordCredentials(this.username, this.password));

        // Initialize the client with SSL and credentials
        final RestClient restClient = RestClient.builder(host)
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider))
                        //.setSSLContext(sslContext))
                .build();
        final OpenSearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new OpenSearchClient(transport);
    }
}
