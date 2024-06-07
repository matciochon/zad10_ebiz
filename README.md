Programowanie Zespołowe
-------------------------------------------------------------------------------------------------------------------------------------


Projekt systemu umożliwiającego dokonywania rezerwacji sal uczelnianych w oparciu o dane zawarte w systemie UsosWeb.


-------------------------------------------------------------------------------------------------------------------------------------


Funkcjonalności:
* Rezerwacja sali (poprzez podanie imienia, nazwiska, okres rezerwacji - w kalendarzu) i nadanie identyfikatora rezerwacji
* Sprawdzenie kiedy dana sala jest dostępna, a kiedy zarezerwowana przez kogoś (w kalendarzu)
* Przeglądanie listy wszystkich sal
* Filtrowanie listy sal w zależności od podanej przez użytkownika wielkości sali lub/i okresu pożądanej rezerwacji
* Wysyłanie do administratora prośby o zatwierdzenie rejestracji
* Odsyłanie informacji do użytkownika o zaakceptowanej rezerwacji
* Sprawdzenie, kto jest odpowiedzialny za daną rezerwację sali
* Sprawdzenie mapy wydziału, aby móc zlokalizować daną salę
* Odwołanie rezerwacji sali (poprzez podanie identyfikatora rezerwacji)
* Logowanie dla administratorów systemu
* Przeglądanie przez administratorów listy aktualnych próśb o zaakceptowanie rezerwacji


-------------------------------------------------------------------------------------------------------------------------------------

Technologie:
- MySQL
- Spring 
- Angular

-------------------------------------------------------------------------------------------------------------------------------------

Opis struktury bazy danych w pliku:
[/database/README.md](https://github.com/programowanie-zespolowe-grupa2/programowanie-zespolowe-projekt/blob/main/database/README.md)
  
-------------------------------------------------------------------------------------------------------------------------------------


**Uruchamianie:**

Aplikacje można uruchomić za pomoca Docker Compose:
```
docker-compose up pzg2-database pzg2-backend pzg2-frontend
```


-------------------------------------------------------------------------------------------------------------------------------------

Zakres obowiązków:
* Patrycja Trojan - Scrum Master
* Jan Malczewski - Product Owner
* Marek Ostafin - Deweloper
* Konrad Wyka - Deweloper
* Wojciech Makosiej - Deweloper
* Mateusz Cichoń - Deweloper
* Mikołaj Golowski - Deweloper

-------------------------------------------------------------------------------------------------------------------------------------

Przydatne linki:
- Jira - https://programowanie-zespolowe-grupa2.atlassian.net/jira/software/projects/PZG2/boards/1
- GitHub - https://github.com/programowanie-zespolowe-grupa2/programowanie-zespolowe-projekt

-------------------------------------------------------------------------------------------------------------------------------------

Organizacja pracy - praca asynchroniczna z raportowaniem na czacie + spotkania w czwartki o 10:30

Spotkanie z Klientem kończące sprint - czwartki o 9:30

-------------------------------------------------------------------------------------------------------------------------------------

# Wytyczne ujednolicające kod dla Java i Angular

Celem tego rozdziału jest przedstawienie zasad i najlepszych praktyk programistycznych, które mają na celu zapewnienie wysokiej jakości, spójności i czytelności kodu źródłowego w naszym projekcie wykorzystującycm, Jave i Angulaar. Przestrzeganie tych wytycznych ma na celu ułatwienie współpracy w zespole, ułatwienie wdrażania nowych członków zespołu, a także zapewnienie łatwości w utrzymaniu i rozwijaniu aplikacji.

## 1. Wytyczne dla Java

### 1.1 Formatowanie kodu

-   **Wcięcia**: Używaj 4 spacji dla wcięcia, nie tabulatorów.
-   **Klamry**: Otwierająca klamra powinna być umieszczona w tej samej linii co deklaracja metody lub instrukcja kontrolna, a zamykająca klamra w nowej linii.
-   **Długość linii**: Staraj się, aby linie kodu nie przekraczały 120 znaków.

### 1.2 Nazewnictwo

-   **Klasy**: Nazwy klas powinny być rzeczownikami w formie CamelCase, np. `EmployeeService`.
-   **Metody**: Nazwy metod powinny być czasownikami w camelCase, np. `findAllEmployees`.
-   **Zmienne**: Używaj znaczących nazw zmiennych w camelCase, np. `employeeList`.

### 1.3 Zasady ogólne

-   **Unikaj 'magic numbers'**: Wszystkie stałe numeryczne powinny być zdefiniowane jako stałe.
-   **Ogranicz zakres zmiennych**: Zmienne powinny być deklarowane jak najbliżej miejsca ich pierwszego użycia.

## 2. Wytyczne dla Angulara

### 2.1 Struktura projektu

-   **Modularność**: Organizuj kod w moduły w oparciu o funkcjonalność lub funkcje biznesowe, np. `UsersModule`, `OrdersModule`.
-   **Komponenty**: Nazwy plików komponentów powinny odzwierciedlać ich funkcjonalność, np. `user-list.component.ts`.
-   **Atomic design**: Komponenty powinny być atomowymi fragmentami kodu.

### 2.2 Formatowanie i styl kodu

-   **Wcięcia**: Używaj 4 spacji na wcięcie.
-   **Dekoratory**: Umieszczaj dekoratory w jednej linii bezpośrednio przed deklaracją klasy, metody lub właściwości.

### 2.3 Nazewnictwo

-   **Komponenty**: Nazwy komponentów powinny kończyć się sufiksem `Component`, np. `UserListComponent`.
-   **Serwisy**: Nazwy serwisów powinny kończyć się sufiksem `Service`, np. `UserService`.
-   **Interfejsy**: Nazwy interfejsów powinny zaczynać się od `I`, np. `IUser`.

### 2.4 Zasady ogólne

-   **Single Responsibility Principle (SRP)**: Każdy komponent lub serwis powinien mieć jedną odpowiedzialność.

## 3. Narzędzia i dodatkowe praktyki

-   **Używaj narzędzi do analizy statycznej kodu**: Takie jak ESLint dla Angulara i Checkstyle dla Java, aby automatycznie egzekwować część tych wytycznych.
-   **Przeprowadzaj regularne code review**: Aby zapewnić przestrzeganie tych wytycznych oraz dzielić się wiedzą i doświadczeniem w zespole.
-   **Pisz testy jednostkowe**: Aby zapewnić wysoką jakość kodu i ułatwić refaktoryzację w projekcie pisz testy jendostkowe do swojego kodu.



# Opis API

### 1. POST /reservations/add
AUTH REQUIRED
BODY: {start, end, lecturer_mail, lecturer_first_name, lecturer_last_name, participants_count, reservation_datetime, accepted}
RETURNED: {room_id, start, end, lecturer_mail, lecturer_first_name, lecturer_last_name, participants_count, reservation_datetime, accepted}
Opis: Dodaje rezerwacje sali

### 2. GET /rooms/checkAvailability
AUTH NOT REQUIRED
BODY: {room_id, date}
RETURNED: {availability}
Opis: Sprawdza czy rezerwacja sali jest możliwa w wybranym terminie

### 3. GET /rooms
AUTH NOT REQUIRED
BODY: none
RETURNED: {list: {id, name, capacity}, ... }
Opis: Pobiera listę wszystkich sal

### 4. GET /rooms/quote
AUTH NOT REQUIRED
BODY: {capacity, start, end}
RETURNED: { list: {id, name, capacity}, ... }
Opis: Zwraca listę wszystkich sal spełniających warunki

### 5. GET /admin/pendingList
AUTH REQUIRED - ROLE: ADMIN
BODY: none
RETURNED: { list: {room_id, start, end, lecturer_mail, lecturer_first_name, lecturer_last_name, participants_count, reservation_datetime, accepted = FALSE}, ... }
Opis: Zwraca listę wszystkich oczekujących na zatwierdzenie rezerwacji

### 6. POST /admin/quoteSetStatus
AUTH REQUIRED - ROLE: ADMIN
BODY: {accepted = TRUE}
RETURNED: {room_id, start, end, lecturer_mail, lecturer_first_name, lecturer_last_name, participants_count, reservation_datetime, accepted}
Opis: Akceptuje rezerwacje

### 7. DELETE /reservations/cancel
AUTH REQUIRED
BODY: {room_id}
RETURNED: none
Opis: Usuwa istniejcą rezerwację

### 8. POST /auth/login
AUTH NOT REQUIRED
BODY: {login, password}
RETURNED: {token}
Opis: Zwraca token JWT

### 8. POST /auth/register
AUTH NOT REQUIRED
BODY: {login, password, mail}
RETURNED: {id, login, password, mail, token}
Opis: Rejestruje nowe konto w systemie i zwraca token JWT
