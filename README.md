# data-processing<br>
Jeśli chodzi o zadanie pierwsze, to program wykonuje się po uruchomieniu funkcji main w klasie Main. Aby otworzyć projekt należy za pomocą IDE uruchomić plik pom.xml.<br>
Szczegółowe informacje w dokumentacji kodu.


Zadanie 2<br>
<br>
Na potrzeby zadania, na podstawie pliku statuses.json stworzyłem tabelę o nazwie records z kolumnami o nazwach kontakt_id,klient_id,pracownik_id,status,kontakt_ts;<br>
Testy wykonywalem na serwerze MySQL w wersji 8.0.17.<br>
Zapytanie będące odpowiedzią na zadanie to: <br>
<br>
select t.klient_id, t.status, t.kontakt_ts from records t<br>
inner join (<br>
select klient_id, max(kontakt_ts) as LastContact<br>
from records<br>
group by klient_id having count(*) >=3<br>
) tm on t.klient_id = tm.klient_id and t.kontakt_ts = tm.LastContact;<br>
<br>
Zadanie 3<br>
<br>
Na potrzeby tego zadania stworzyłem taką samą tabelę jak w zadaniu nr.2, rozwiązania dotyczą podpunktów 1,2,3,4. W tym punkt 1. rozwiązałem w troszkę inny sposób,<br> 
w podpunkcie a) zadanie rozwiązane jest dla końcowego stanu bazy po wczytaniu tam danych, natomiast w podpunkcie b) datę graniczną można przekazać ręcznie jako parametr<br>
ponieważ moj pomysl zakładał przekazanie każdej zgrupowanej daty jako parametr do procedury, niestety okazało się to dość skomplikowane w języku SQL by iterować po wierszach i wyciagac jakies wartosci z kolumn,<br>
a nastepnie przekazywac je jako parametry, właśnie dlatego oraz z powodu przygotowań do sesji zabrakło mi czasu by to opanować w odpowiednim stopniu. Podpunktu 5 oraz 6, niestety nie zrobiłem z tego samego powodu. :(
<br>
Wspomniene rozwiązania : 

a) w stanie końcowym<br>
<br>
delimiter $<br>
create procedure PoliczIluKlientowZOstatnimStatusem (IN ostatni_status TEXT, OUT ilosc INT)<br>
BEGIN<br>
select count(*) <br>
INTO ilosc<br>
from <br>
(<br>
select t.klient_id, t.status, t.kontakt_ts from records t <br>
inner join (<br>
select klient_id, max(kontakt_ts) as LastContact<br>
from records<br>
group by klient_id<br>
) <br>
tm on t.klient_id = tm.klient_id and t.kontakt_ts = tm.LastContact where status=ostatni_status<br>
) tt2;<br>
END $<br>
DELIMITER ;<br>
<br>
CALL PoliczIluKlientowZOstatnimStatusem("zainteresowany",@sukcesy);<br>
CALL PoliczIluKlientowZOstatnimStatusem("niezainteresowany",@straty);<br>
CALL PoliczIluKlientowZOstatnimStatusem("poczta_glosowa",@poczta);<br>
CALL PoliczIluKlientowZOstatnimStatusem("nie_ma_w_domu",@nieobecny);<br>
<br>
select @sukcesy as sukcesy, @straty as straty, (@poczta + @nieobecny) as do_ponowienia;<br>
<br>
<br>
b) stan do wybranej daty<br>
<br>
delimiter $<br>
create procedure PoliczIluKlientowZOstatnimStatusemDoWybranejDaty (IN ostatni_status TEXT, OUT ilosc INT, INOUT dataGraniczna DATE)<br>
BEGIN<br>
select count(*) <br>
INTO ilosc<br>
from <br>
(<br>
select twew1.klient_id, twew1.status, twew1.kontakt_ts from <br>
(select * from records where date(kontakt_ts) <= dataGraniczna) twew1 <br>
inner join (<br>
select klient_id, max(kontakt_ts) as LastContact<br>
from <br>
(select * from records where date(kontakt_ts) <= dataGraniczna) twew2<br>
group by klient_id<br>
) twew3 <br>
on twew1.klient_id = twew3.klient_id and twew1.kontakt_ts = twew3.LastContact <br>
where status=ostatni_status<br>
) tzew;<br>
END $<br>
DELIMITER ;<br>
<br>
SET @dataGraniczna = '2017-08-14';<br>
CALL PoliczIluKlientowZOstatnimStatusemDoWybranejDaty("zainteresowany",@sukcesy, @dataGraniczna);<br>
CALL PoliczIluKlientowZOstatnimStatusemDoWybranejDaty("niezainteresowany",@straty, @dataGraniczna);<br>
CALL PoliczIluKlientowZOstatnimStatusemDoWybranejDaty("poczta_glosowa",@poczta, @dataGraniczna);<br>
CALL PoliczIluKlientowZOstatnimStatusemDoWybranejDaty("nie_ma_w_domu",@nieobecny, @dataGraniczna);<br>
<br>
select @dataGraniczna as data, @sukcesy as sukcesy, @straty as straty, (@poczta + @nieobecny) as do_ponowienia;<br>
