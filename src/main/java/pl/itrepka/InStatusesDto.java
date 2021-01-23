package pl.itrepka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InStatusesDto {
    private List<InRecordDto> records;

    public InStatusesDto(){
    }

    public InStatusesDto(List<InRecordDto> records) {
        this.records = records;
    }

    public List<InRecordDto> getRecords() {
        return records;
    }

    public void setRecords(List<InRecordDto> records) {
        this.records = records;
    }
}
