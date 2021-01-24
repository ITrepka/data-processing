package pl.itrepka;

import java.time.format.DateTimeFormatter;

public class RecordToOutRecordDtoMapper implements Mapper<Record, OutRecordDtoCSV>{
    @Override
    public OutRecordDtoCSV map(Record record) {
        StringBuilder sb = new StringBuilder();
        sb.append(record.getContactId())
                .append(",")
                .append(record.getClientId())
                .append(",")
                .append(record.getWorkerId())
                .append(",")
                .append(record.getStatus())
                .append(",")
                .append(record.getContactTs().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return new OutRecordDtoCSV(sb.toString());
    }
}
