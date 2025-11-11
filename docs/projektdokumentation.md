## 1. Einleitung

Das Projekt zielt darauf ab, die täglichen Abläufe eines Student-Management-Systems zu vereinfachen. Bisher verteilt sich der Funktionsumfang auf verstreute Klassen und lose CLI-Kommandos. Mit der neuen Lösung wird eine konsistente Konsolenanwendung bereitgestellt, die Kurse, Prüfungen, Noten und Benutzerverwaltung zentral abbildet. Damit stehen Studierenden, Lehrkräften und Administratoren klar definierte Aktionen zur Verfügung, ohne dass Papierlisten oder manuelle Konsistenzprüfungen erforderlich sind.

## 2. Planung

Zu Projektbeginn wird der Status quo begutachtet: Mehrere Domänenklassen sind ungenutzt, die Bedienung folgt keinem roten Faden und Rollen lassen sich nicht klar unterscheiden. Um diesen Zustand zu verbessern, wird ein Dreischichtenmodell festgelegt. Die CLI übernimmt die Eingaben, schlanke Portale bilden die Rollenlogik ab, und ein gemeinsamer Datenkern versorgt alle Funktionen mit denselben Informationen. Ein Observer dient als leichtgewichtiges Monitoring, damit wichtige Ereignisse sofort nachvollziehbar sind.

### 2.1 Zieldefinition

- Die CLI führt jede Nutzergruppe über numerische Menüs zu ihren Aufgaben.  
- Studierende, Lehrkräfte und Administratoren greifen auf exakt definierte Szenarien zu.  
- Ein Observer protokolliert relevante Aktionen, damit Abläufe transparent bleiben.  
- Alle Datenobjekte liegen zentral in einem `DataStore`, wodurch Inkonsistenzen vermieden werden.

### 2.2 Abgrenzung und Nicht-Ziele

- Die Anwendung speichert keine Daten dauerhaft; der Fokus liegt auf dem Laufzeitverhalten.  
- Es wird bewusst keine grafische Oberfläche entwickelt.  
- Das Rollenmodell beschränkt sich auf die drei erwähnten Gruppen, zusätzliche Berechtigungsstufen sind nicht vorgesehen.  
- Die Benutzerführung bleibt vollständig deutschsprachig.

### 2.3 Anforderung

- Der Startbildschirm bietet eine Rollenwahl, anschließend erfolgt die Anmeldung über Benutzername und Passwort.  
- Studierende sehen ihre Kurse, Noten, Anwesenheit und den persönlichen Stundenplan und rufen Mitteilungen sowie Leistungsnachweise ab.  
- Lehrkräfte verwalten eigene Kurse, legen Prüfungen an, erfassen Noten, pflegen Anwesenheit und passen Termin-Slots an.  
- Administratoren kümmern sich um Benutzerkonten und Kursstammdaten.  
- Jede relevante Aktion wird über den Observer in der Konsole protokolliert.

#### 2.3.1 Funktionale Anforderungen

- `MenuCLI` stellt Rollenwahl, Login und Portalaufrufe bereit.  
- `StudentPortal` liefert die benötigten Ansichten für Studierende.  
- `TeacherPortal` bündelt Eingaben rund um Kurse, Prüfungen und Anwesenheit.  
- `AdminPortal` verwaltet Accounts sowie Kursinformationen.  
- `AuthService` übernimmt die Authentifizierung, `Observer`-Implementierungen reagieren auf Systemereignisse.

## 3. Analyse

Die Analysephase zeigt, dass viele Klassen wie `Department` oder `ReportCard` keine Verwendung finden und dadurch unnötige Komplexität erzeugen. Gleichzeitig fehlen geführte Abläufe für die Rollen, was zu Fehleingaben führt. Aus diesem Grund wird der Code bereinigt, die Rollenlogik neu strukturiert und die Navigation vollständig über nummerierte Menüs geregelt. Der Observer ergänzt dieses Setup, indem er Änderungen unmittelbar sichtbar macht, ohne separate Logdateien pflegen zu müssen.

## 4. Modellierung

Die Modellierung folgt einem klaren Schichtmodell:

- **Präsentation**: `MenuCLI` sowie `StudentPortal`, `TeacherPortal` und `AdminPortal`.  
- **Logik**: `AuthService` für Anmeldungen, `DataStore` als Fassade für alle Datenoperationen und ein Observer-Interface mit `ConsoleObserver`.  
- **Domain**: reduzierter Satz an POJOs (`Student`, `Teacher`, `Admin`, `Course`, `Grade`, `Role`), die direkt vom `DataStore` verwaltet werden.

Der `DataStore` initialisiert Demonstrationsdaten und stellt CRUD-Funktionen bereit. Über `registerObserver` wird der `ConsoleObserver` eingebunden, der Ereignisse mit kurzer Textausgabe bestätigt. Jede Rolle arbeitet ausschließlich mit den dafür vorgesehenen Menüeinträgen, womit Fehlbedienungen bereits auf CLI-Ebene verhindert werden.

## 5. Reflexion / Fazit

Durch das Entfernen ungenutzter Klassen und die klare Menüstruktur wirkt das System kompakt und leicht nachvollziehbar. Die deutschsprachige CLI sorgt für einen durchgängigen Bedienfluss. Der Observer erfüllt seine Aufgabe als unmittelbare Rückmeldung, wenn Aktionen wie Notenerfassung oder Kursänderungen stattfinden. Für spätere Ausbauschritte bieten sich persistente Speicherung und automatisierte Tests an; im aktuellen Zustand deckt die Lösung den geforderten Funktionsumfang vollständig ab und bleibt bewusst schlank.
