package com.devsecops.library;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Page d'accueil : point d'entree du test "curl" et du scan OWASP ZAP. */
@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
                <!DOCTYPE html>
                <html lang="fr">
                <head><meta charset="utf-8"><title>Library API</title></head>
                <body>
                  <h1>Library API - projet DevSecOps</h1>
                  <p><a href="/api/books">/api/books</a></p>
                </body>
                </html>
                """;
    }
}
