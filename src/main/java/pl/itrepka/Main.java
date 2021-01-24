package pl.itrepka;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/*Program wczytuję plik statuses.json za pomocą mappera z biblioteki Jackson, przetwarza go na format csv i zapisuje
do pliku statuses.csv w folderze projektu*/

public class Main {

    /*Metoda główna wywołuje poszczególne metody odpowiadające za działanie programu*/
    public static void main(String[] args) throws IOException, ParseException {
        List<InRecordDto> records = getRecordsListFromJson();
        List<OutRecordDtoCSV> outRecords = prepareRecordsToSaveInCSVFormat(records);
        File file = clearFileIfExistsAlready("statuses.csv");
        writeRecordsToFile(outRecords, file);
    }
    /*Funkcja otrzymuje w parametrze listę obiektów OutRecordDtoCSV i zapisuje ich rekordy do pliku statuses.csv*/
    private static void writeRecordsToFile(List<OutRecordDtoCSV> outRecords, File file) throws IOException {
        FileWriter output = new FileWriter(file, true);
        for (OutRecordDtoCSV outRecord : outRecords) {
            output.write(outRecord.getRecordInCSV());
            output.write('\n');
        }
        output.close();
    }
    /*Funkcja, która usuwa plik o danej ścieżce jeśli wcześniej istniał, by zapobiec dopisywaniu resultatów przy
     wielokrotnym uruchamianiu*/
    private static File clearFileIfExistsAlready(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        return file;
    }
    /*Funkcja, przetwarza dane w sposób określony w zadaniu, tzn filtruje datę, sortuje po kolumnach
    klient_id oraz po statusie a dodatkowo mapuje w aplikacji obiekty z InRecordDto przez modelowy Record
    az do OutRecordDtoCsv*/
    private static List<OutRecordDtoCSV> prepareRecordsToSaveInCSVFormat(List<InRecordDto> records) throws ParseException {
        InRecordDtoToRecordMapper mapper1 = new InRecordDtoToRecordMapper();
        RecordToOutRecordDtoMapper mapper2 = new RecordToOutRecordDtoMapper();
        String boundDateString = "2017-06-30";
        Date boundDate = new SimpleDateFormat("yyyy-MM-dd").parse(boundDateString);
        List<OutRecordDtoCSV> outRecords = records.stream()
                .filter(record -> record.getKontakt_ts().after(boundDate))
                .sorted((r1, r2) -> r2.getKlient_id() - r1.getKlient_id())
                .sorted((r1,r2) -> r2.getKontakt_ts().compareTo(r1.getKontakt_ts()))
                .map(mapper1::map)
                .map(mapper2::map)
                .collect(Collectors.toList());
        return outRecords;
    }
    /*Funkcja wczytuje dane z pliku statuses.json w resources i za pomoca objectmappera zamienia je na javowe obiekty*/
    private static List<InRecordDto> getRecordsListFromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = ClassLoader.getSystemResourceAsStream("statuses.json");
        InStatusesDto inStatusesDto = mapper.readValue(is, InStatusesDto.class);
        List<InRecordDto> records = inStatusesDto.getRecords();
        return records;
    }
}
