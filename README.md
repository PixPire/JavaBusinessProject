# JavaBusinessProject
Piotr Szumowski, Mateusz Węcławski, Jakub Hapunik
1) Sklep internetowy
   
Aplikacja zawiera:

-Przeglądanie/dodawanie/edycja/usuwanie produktów.

-Możliwość składania zamówienia na wybrane produkty (koszyk zakupów). (możliwość edycji)

-Możliwość przeglądania złożonych zamówień. (Historia zamówień, bez możliwości edycji)

-Przeglądanie/dodawanie/edycja/usuwanie promocji. (tylko menager i admin)

-Przeglądanie/dodawanie/edycja/usuwanie użytkowników. (tylko admin)

-Przesyłanie wiadomości o złożonym zamówieniu poprzez e-mail.

-Uwierzytelnianie.

-Podział na role Administatora, Menagera i Klienta.


Zalecane wersje Java 11, Jakarta 8, Payara 5.2022.5, baza danych H2 1.4.200

Przed uruchomieniem serwera
W pliku konfiguracyjnym \src\main\java\wipb\jsfcruddemo\web\Configuration.java 
Zmień w @DataSourceDefinition
url = "jdbc:h2:file:yourProjectAbsolutePath/h2data"

Ewentualnie można zamienić na url = "jdbc:h2:file:./h2data;", wtedy baza danych będzie zapisana tam gdzie jest zainstalowana Payara

Na podstawie https://cez2.wi.pb.edu.pl/moodle/mod/resource/view.php?id=22791
