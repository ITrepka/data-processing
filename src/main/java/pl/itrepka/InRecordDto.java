package pl.itrepka;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class InRecordDto {
    private Integer kontakt_id;
    private Integer klient_id;
    private Integer pracownik_id;
    private String status;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date kontakt_ts;

    public InRecordDto() {}

    public void setKontakt_id(Integer kontakt_id) {
        this.kontakt_id = kontakt_id;
    }

    public void setKlient_id(Integer klient_id) {
        this.klient_id = klient_id;
    }

    public void setPracownik_id(Integer pracownik_id) {
        this.pracownik_id = pracownik_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setKontakt_ts(Date kontakt_ts) {
        this.kontakt_ts = kontakt_ts;
    }

    public Integer getKontakt_id() {
        return kontakt_id;
    }

    public Integer getKlient_id() {
        return klient_id;
    }

    public Integer getPracownik_id() {
        return pracownik_id;
    }

    public String getStatus() {
        return status;
    }

    public Date getKontakt_ts() {
        return kontakt_ts;
    }

    @Override
    public String toString() {
        return "InRecordDto{" +
                "kontakt_id=" + kontakt_id +
                ", klient_id=" + klient_id +
                ", pracownik_id=" + pracownik_id +
                ", status='" + status + '\'' +
                ", kontakt_ts=" + kontakt_ts +
                '}';
    }
}
