# Echo - Backend

## 📌 Überblick  

**Echo** ist ein Health-Tracker, der sowohl mentale als auch körperliche Gesundheitsdaten speichert und analysiert. Das Backend wurde mit **Java 21** und **Spring Boot** entwickelt und verwendet **PostgreSQL**, das mit **Docker** aufgesetzt wurde. Es basiert auf **JPA** für die Datenbankverwaltung und nutzt **Lombok** zur Code-Vereinfachung.  

Für die Qualitätssicherung wurden umfassende **JUnit-Tests** geschrieben, um sicherzustellen, dass alle Funktionen zuverlässig arbeiten.  

## 🎯 Funktionsweise  

Das System speichert Gesundheitsdaten eines Nutzers **täglich** in verschiedenen Kategorien:  

- **Allgemeine Daten**: Größe, Gewicht, Alter, BMI  
- **Mentale Daten**: Stimmung, Gedanken, Träume  
- **Körperliche Daten**: Getrunkene Flüssigkeitsmenge, Schritte, Schlafdauer, Sportaktivitäten  

Die Daten werden pro Tag erfasst, sodass der Nutzer jeden Tag neue Informationen einträgt. Diese können mit **GET**- und **PUT**-Requests abgerufen und aktualisiert werden.  

Zudem können **gesammelte Daten** abgefragt werden, z. B.:  
- Alle Schlaf-Einträge eines Benutzers  
- Alle Sport-Einträge eines Benutzers  

### 🔮 Zukünftige Erweiterungen  

Das System wird weiterhin aktiv entwickelt. In Zukunft sollen zusätzliche Funktionen hinzugefügt werden, darunter:  
- **Zeitbasierte Abfragen**: Alle Sport-Einträge für einen bestimmten Zeitraum  
- **Statistiken & Analysen**: Durchschnittswerte für verschiedene Kategorien, z. B.  
  - „Deine durchschnittliche Schlafdauer beträgt…“  
  - „Du hast in den letzten 7 Tagen X Liter Wasser getrunken.“  

Das Backend ist so konzipiert, dass neue Features leicht integriert werden können.  

## 🔥 Technologie-Stack  

- **Spring Boot** – REST API Entwicklung  
- **Java 21** – Hauptprogrammiersprache  
- **Spring Data JPA** – ORM für PostgreSQL  
- **PostgreSQL** – Datenbank (Docker-Container)  
- **Lombok** – Reduzierung von Boilerplate-Code  
- **JUnit & Mockito** – Unit-Tests für Service- und Controller-Methoden  
- **Docker** – Containerisierung der Datenbank  

## Tests & Qualitätssicherung
- **100% Test Coverage** mit **JUnit**
- Getestet wurden alle Services und Controller 
- Fokus auf **Fehlerbehandlung und Validierung**

## 🛠️ Setup & Installation  

1. **Projekt klonen**  
   ```sh
   git clone https://github.com/meelinaa/EchoBackend.git
   cd EchoBackend 
    ```
2. **Docker-Container für PostgreSQL starten**  
   ```sh
   docker run --name echo-db -e POSTGRES_USER=Echo -e POSTGRES_PASSWORD=passwordEcho -e POSTGRES_DB=databaseEcho -p 5433:5432 -d postgres
    ```
3. **Anwendung starten**  
   ```sh
   mvn spring-boot:run    
    ```

---

## API-Endpunkte

### **Allgemeine Daten**
| Methode | Endpoint | Beschreibung |
|---------|---------|--------------|
| `GET` | `/allgemein/alles` | Ruft alle allgemeinen Daten eines Benutzers ab |
| `PUT` | `/allgemein/hinzufügen` | Aktualisiert oder setzt allgemeine Daten |

### **Mentale Daten**
| Methode | Endpoint | Beschreibung |
|---------|---------|--------------|
| `GET` | `/gedanken/{datum}` | Ruft Gedanken für ein bestimmtes Datum ab |
| `PUT` | `/gedanken/hinzufügen` | Fügt neue Gedanken hinzu |
| `GET` | `/gemüt/{datum}` | Ruft Gemütszustand für ein bestimmtes Datum ab |
| `PUT` | `/gemüt/hinzufügen` | Fügt neuen Gemütszustand hinzu |
| `GET` | `/träume/{datum}` | Ruft Traumdaten für ein bestimmtes Datum ab |
| `PUT` | `/träume/hinzufügen` | Fügt neue Traumdaten hinzu |

### **Körperliche Daten**
| Methode | Endpoint | Beschreibung |
|---------|---------|--------------|
| `GET` | `/schlaf/{datum}` | Ruft Schlafdaten für ein bestimmtes Datum ab |
| `PUT` | `/schlaf/hinzufügen` | Fügt neue Schlafdaten hinzu |
| `GET` | `/schritte/{datum}` | Ruft Schritte für ein bestimmtes Datum ab |
| `PUT` | `/schritte/hinzufügen` | Fügt neue Schritte hinzu |
| `GET` | `/sport/{datum}` | Ruft Sportdaten für ein bestimmtes Datum ab |
| `PUT` | `/sport/hinzufügen` | Fügt neue Sportdaten hinzu |
| `GET` | `/trinken/{datum}` | Ruft Trinkdaten für ein bestimmtes Datum ab |
| `PUT` | `/trinken/hinzufügen` | Fügt neue Trinkdaten hinzu |

---

## Lizenz
Dieses Projekt steht unter der [MIT-Lizenz](LICENSE).

---

## Kontakt
Für Fragen oder Feedback kontaktieren Sie mich über [E-Mail](mailto:melinakiefer@hotmail.de) oder GitHub.