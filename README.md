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

## 🛠️ Setup & Installation  

1. **Projekt klonen**  
   ```sh
   git clone https://github.com/meelinaa/EchoBackend.git
   cd EchoBackend 
    ```
2. **Docker-Container für PostgreSQL starten**  
   ```sh
   docker-compose up -d
    ```
3. **Anwendung starten**  
   ```sh
   mvn spring-boot:run    
    ```



---

## Lizenz
Dieses Projekt steht unter der [MIT-Lizenz](LICENSE).

---

## Kontakt
Für Fragen oder Feedback kontaktieren Sie mich über [E-Mail](mailto:melinakiefer@hotmail.de) oder GitHub.