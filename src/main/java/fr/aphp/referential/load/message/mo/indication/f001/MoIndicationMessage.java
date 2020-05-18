package fr.aphp.referential.load.message.mo.indication.f001;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import io.vavr.control.Try;

@CsvRecord(separator = ";", allowEmptyStream = true)
public class MoIndicationMessage {
    @DataField(pos = 1, trim = true)
    private String ucd7;

    @DataField(pos = 2, trim = true)
    private String specialty;

    @DataField(pos = 3, trim = true)
    private String les;

    @DataField(pos = 4, trim = true)
    private String registration;

    @DataField(pos = 5, trim = true)
    private String ucd13;

    @DataField(pos = 6, trim = true)
    private String dci;

    @DataField(pos = 7, trim = true)
    private String lab;

    @DataField(pos = 8, trim = true)
    private String lib;

    @DataField(pos = 9, trim = true)
    private String startDate;

    @DataField(pos = 10, trim = true)
    private String endDate;

    @DataField(pos = 11, trim = true)
    private String classInd1;

    @DataField(pos = 12, trim = true)
    private String classInd2;

    @DataField(pos = 13, trim = true)
    private String gener;

    public String getUcd7() {
        return ucd7;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getLes() {
        return les;
    }

    public String getRegistration() {
        return registration;
    }

    public String getUcd13() {
        return ucd13;
    }

    public String getDci() {
        return dci;
    }

    public String getLab() {
        return lab;
    }

    public String getLib() {
        return lib;
    }

    public String getClassInd1() {
        return classInd1;
    }

    public String getClassInd2() {
        return classInd2;
    }

    public String getGener() {
        return gener;
    }

    public Optional<Date> getStartDate() {
        return dateOptional(startDate);
    }

    public Optional<Date> getEndDate() {
        return dateOptional(endDate);
    }

    private static Optional<Date> dateOptional(String date) {
        return Try.of(() -> new SimpleDateFormat("dd/MM/yyyy").parse(date)).toJavaOptional();
    }
}
