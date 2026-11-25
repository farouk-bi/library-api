package com.devsecops.library;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Ajoute les en-tetes de securite HTTP signales par OWASP ZAP
 * sur toutes les reponses de l'application.
 */
@Component
public class SecurityHeadersFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Empeche le navigateur de deviner le type de contenu (MIME sniffing)
        response.setHeader("X-Content-Type-Options", "nosniff");
        // Interdit l'affichage de l'application dans une iframe (clickjacking)
        response.setHeader("X-Frame-Options", "DENY");
        // Seules les ressources du site lui-meme sont autorisees (limite le XSS)
        response.setHeader("Content-Security-Policy",
                "default-src 'self'; frame-ancestors 'none'; form-action 'self'");
        // Ne transmet pas l'URL d'origine aux autres sites
        response.setHeader("Referrer-Policy", "no-referrer");
        // Desactive les API sensibles du navigateur
        response.setHeader("Permissions-Policy", "geolocation=(), camera=(), microphone=()");
        // Isolation contre les attaques de type Spectre
        response.setHeader("Cross-Origin-Resource-Policy", "same-origin");
        response.setHeader("Cross-Origin-Opener-Policy", "same-origin");
        response.setHeader("Cross-Origin-Embedder-Policy", "require-corp");
        // Les reponses ne doivent pas etre gardees en cache
        response.setHeader("Cache-Control", "no-store");
        chain.doFilter(request, response);
    }
}