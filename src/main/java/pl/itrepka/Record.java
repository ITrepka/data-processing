package pl.itrepka;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

/*Modelowa klasa rekordow w naszej aplikacji, tzw. POJO*/
public class Record {
    private Integer contactId;
    private Integer clientId;
    private Integer workerId;
    private String status;
    private LocalDateTime contactTs;

    public Record(Integer contactId, Integer clientId, Integer workerId, String status, LocalDateTime contactTs) {
        this.contactId = contactId;
        this.clientId = clientId;
        this.workerId = workerId;
        this.status = status;
        this.contactTs = contactTs;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getContactTs() {
        return contactTs;
    }

    public void setContactTs(LocalDateTime contactTs) {
        this.contactTs = contactTs;
    }

    @Override
    public String toString() {
        return "Status{" +
                "contactId=" + contactId +
                ", clientId=" + clientId +
                ", workerId=" + workerId +
                ", status='" + status + '\'' +
                ", contactTs=" + contactTs +
                '}';
    }
}
