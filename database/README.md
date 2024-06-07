# Baza danych MySQL


## Informacje:
 - Host: 127.0.0.1
 - Login: root
 - Hasło: 
 - Port: 3306
 - Baza danych: pzg2

# Struktura

### - **reservations**
 zawiera informacje o rezerwacjach zajęć
   - **id** - id rezerwacji
   - **room_id** - id z tabeli "rooms" pokoju którego dotyczny rezerwacja
   - **start** - data i godzina rozpoczęcia rezerwacji
   - **end** - data i godzina zakończenia rezerwacji
   - **name** - nazwa zajęć których dotyczy rezerwacja
   - **lecturer_mail** - adres email wykładowcy/ćwiczeniowcy (osoby, która zgłosiła rezerwacje)
   - **lecturer_first_name** - imię wykładowcy/ćwiczeniowcy (osoby, która zgłosiła rezerwacje)
   - **lecturer_last_name** - nazwisko wykładowcy/ćwiczeniowcy (osoby, która zgłosiła rezerwacje)
   - **participants_count** - orientacyjna liczba uczestników
   - **reservation_datetime** - data i godzina wysłania rezerwacji (timestamp rezerwacji)
   - **accepted** - informacja true/false (0/1) czy rezerwacja została zaakceptowana przez administracje/sekretariat

### - **rooms**
 zawiera informacje o dostępnych pokojach
   - **id** - id pokoju
   - **name** - nazwa sali
   - **capacity** - pojemność sali

### - **users**
 zawiera informacje o użytkownikach systemu
   - **id** - id użytkownika
   - **login** - login użytkownika
   - **password** - hasło użytkownika
   - **mail** - mail użytkownika


