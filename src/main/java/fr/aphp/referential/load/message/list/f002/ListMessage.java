package fr.aphp.referential.load.message.list.f002;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import fr.aphp.referential.load.message.Message;
import fr.aphp.referential.load.processor.list.f002.ListProcessor;

@CsvRecord(separator = ListProcessor.SEPARATOR)
public class ListMessage implements Message {
    @DataField(pos = 1, trim = true, length = 7)
    private String code;

    @DataField(pos = 2, trim = true)
    private String codeLabel;

    @DataField(pos = 3, trim = true)
    private String codes;

    public String getCode() {
        return code;
    }

    public String getCodeLabel() {
        return codeLabel;
    }

    public String getCodes() {
        return codes;
    }
}
