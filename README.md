# library-api - Projet DevSecOps (Projet 2)

Application Java / Spring Boot tres simple : un catalogue de livres avec emprunt et retour.
Le travail a realiser est la partie **DevSecOps** : suivre le guide
"Guide DevSecOps - Projet 2 library-api" etape par etape.

## Endpoints
| Methode | URL | Role |
|---|---|---|
| GET | `/` | page d'accueil |
| GET | `/api/books` | catalogue (3 livres au demarrage) |
| POST | `/api/books/{id}/borrow` | emprunter (409 si deja emprunte) |
| POST | `/api/books/{id}/return` | rendre |

## Lancer
```bash
mvn clean package
java -jar target/library-api.jar   # http://localhost:8080
```

## Fichiers DevSecOps a completer (guide)
`.github/workflows/ci.yml`, `Dockerfile`, `.dockerignore`, `.trivyignore`, `.zap/rules.tsv`,
`monitoring/docker-compose.yml`, `monitoring/prometheus.yml`
+ ajouts dans `pom.xml` (JaCoCo, Actuator) et `application.properties` (Actuator).
