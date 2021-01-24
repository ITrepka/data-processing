package pl.itrepka;
/*Model klasy rekord służący do transferowania danych na zewnątrz w formacie csv*/
public class OutRecordDtoCSV {
    private String recordInCSV;

    public OutRecordDtoCSV(String recordInCSV) {
        this.recordInCSV = recordInCSV;
    }

    public String getRecordInCSV() {
        return recordInCSV;
    }
}
