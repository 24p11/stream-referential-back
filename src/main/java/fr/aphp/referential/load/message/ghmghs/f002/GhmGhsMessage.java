package fr.aphp.referential.load.message.ghmghs.f002;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ";", allowEmptyStream = true, skipFirstLine = true)
public class GhmGhsMessage {
    @DataField(pos = 1, trim = true)
    private String ghm;

    @DataField(pos = 2, trim = true)
    private String price;

    public String getGhm() {
        return ghm;
    }

    public String getPrice() {
        return price;
    }
}
