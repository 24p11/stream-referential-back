package fr.aphp.referential.load.message.ghmghs.f001;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import io.vavr.control.Try;

@CsvRecord(separator = ";", allowEmptyStream = true, quotingEscaped = true)
public class GhmGhsMessage {
    @DataField(pos = 1, trim = true)
    private String ghs;

    @DataField(pos = 2, trim = true)
    private String cmd;

    @DataField(pos = 3, trim = true)
    private String dcs;

    @DataField(pos = 4, trim = true)
    private String ghm;

    @DataField(pos = 5, trim = true)
    private String ghmLabel;

    @DataField(pos = 6, trim = true)
    private String seuLow;

    @DataField(pos = 7, trim = true)
    private String seuUp;

    @DataField(pos = 8, trim = true)
    private String ghsPrice;

    @DataField(pos = 9, trim = true)
    private String exbPackage;

    @DataField(pos = 10, trim = true)
    private String exbDaily;

    @DataField(pos = 11, trim = true)
    private String exhPrice;

    @DataField(pos = 12, trim = true)
    private String startDate;

    public String getGhs() {
        return ghs;
    }

    public String getCmd() {
        return cmd;
    }

    public String getDcs() {
        return dcs;
    }

    public String getGhm() {
        return ghm;
    }

    public String getGhmLabel() {
        return ghmLabel;
    }

    public String getSeuLow() {
        return seuLow;
    }

    public String getSeuUp() {
        return seuUp;
    }

    public String getGhsPrice() {
        return ghsPrice;
    }

    public String getExbPackage() {
        return exbPackage;
    }

    public String getExbDaily() {
        return exbDaily;
    }

    public String getExhPrice() {
        return exhPrice;
    }

    public Optional<Date> getStartDate() {
        return convertToStartDate();
    }

    private Optional<Date> convertToStartDate() {
        return Try.of(() -> new SimpleDateFormat("dd/MM/yyyy").parse(startDate)).toJavaOptional();
    }
}
