package com.devsecops.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

/** Tests d'integration : l'application demarre sur un port aleatoire et on l'appelle en HTTP. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LibraryApplicationTests {

    @Value("${local.server.port}")
    private int port;

    private final HttpClient client = HttpClient.newHttpClient();

    private HttpResponse<String> send(String method, String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .method(method, HttpRequest.BodyPublishers.noBody())
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void homePageResponds() throws Exception {
        assertThat(send("GET", "/").statusCode()).isEqualTo(200);
    }

    @Test
    void listBooksResponds() throws Exception {
        HttpResponse<String> response = send("GET", "/api/books");

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).contains("Clean Code");
    }

    @Test
    void borrowAndReturnFlow() throws Exception {
        assertThat(send("POST", "/api/books/3/borrow").statusCode()).isEqualTo(200);
        assertThat(send("POST", "/api/books/3/borrow").statusCode()).isEqualTo(409);
        assertThat(send("POST", "/api/books/3/return").statusCode()).isEqualTo(200);
        assertThat(send("POST", "/api/books/999/borrow").statusCode()).isEqualTo(404);
    }
}
