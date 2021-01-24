package pl.itrepka;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        List<InRecordDto> records = getRecordsListFromJson();
        List<OutRecordDtoCSV> outRecords = prepareRecordsToSaveInCSVFormat(records);
        File file = clearFileIfExistsAlready("statuses.csv");
        writeRecordsToFile(outRecords, file);
    }

    private static void writeRecordsToFile(List<OutRecordDtoCSV> outRecords, File file) throws IOException {
        FileWriter output = new FileWriter(file, true);
        for (OutRecordDtoCSV outRecord : outRecords) {
            output.write(outRecord.getRecordInCSV());
            output.write('\n');
        }
        output.close();
    }

    private static File clearFileIfExistsAlready(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        return file;
    }

    private static List<OutRecordDtoCSV> prepareRecordsToSaveInCSVFormat(List<InRecordDto> records) throws ParseException {
        InRecordDtoToRecordMapper mapper1 = new InRecordDtoToRecordMapper();
        RecordToOutRecordDtoMapper mapper2 = new RecordToOutRecordDtoMapper();
        String boundDateString = "2017-06-30";
        Date boundDate = new SimpleDateFormat("yyyy-MM-dd").parse(boundDateString);
        List<OutRecordDtoCSV> outRecords = records.stream()
                .filter(record -> record.getKontakt_ts().after(boundDate))
                .sorted((r1, r2) -> r1.getKlient_id() - r2.getKlient_id())
                .sorted(Comparator.comparing(InRecordDto::getStatus))
                .map(mapper1::map)
                .map(mapper2::map)
                .collect(Collectors.toList());
        return outRecords;
    }

    private static List<InRecordDto> getRecordsListFromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = ClassLoader.getSystemResourceAsStream("statuses.json");
        InStatusesDto inStatusesDto = mapper.readValue(is, InStatusesDto.class);
        List<InRecordDto> records = inStatusesDto.getRecords();
        return records;
    }
}
