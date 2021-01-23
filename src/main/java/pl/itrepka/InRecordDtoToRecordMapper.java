package pl.itrepka;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class InRecordDtoToRecordMapper implements Mapper <InRecordDto, Record> {
    @Override
    public Record map(InRecordDto inRecordDto) {
        Integer contactId = inRecordDto.getKontakt_id();
        Integer clientId = inRecordDto.getKlient_id();
        Integer workerId = inRecordDto.getPracownik_id();
        String status = inRecordDto.getStatus();
        Date contact_ts = inRecordDto.getKontakt_ts();

        LocalDateTime contactTs = contact_ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return new Record(contactId, clientId, workerId, status, contactTs);
    }
}
