package fr.aphp.referential.load.processor.ccam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.function.Function;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.aphp.referential.load.bean.ReferentialBean;
import io.vavr.control.Try;

import static fr.aphp.referential.load.domain.type.SourceType.CCAM;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;
import static java.lang.String.format;

public class CcamF001Processor {
    public static Iterator<Row> xlsRows(File ccam) {
        return Try.withResources(() -> new FileInputStream(ccam))
                .of(CcamF001Processor::getXlsRowIterator)
                .getOrElseThrow(() -> new RuntimeException(format("Unable to read '%s'", ccam.getName())));
    }

    public static boolean isValidRow(Exchange exchange) {
        return isValidRow(exchange.getIn().getBody(Row.class));
    }

    public static ReferentialBean referentialBean(Message message) {
        Row row = message.getBody(Row.class);
        int firstCellNum = row.getFirstCellNum();
        Function<Integer, String> getCell = cellId -> getCellAsString(row, cellId);
        return ReferentialBean.builder()
                .type(CCAM)
                .domainId(getCell.apply(firstCellNum))
                .label(getCell.apply(firstCellNum + 5))
                .startDate(message.getHeader(VALIDITY_DATE, Date.class))
                .build();
    }

    private static Iterator<Row> getXlsRowIterator(FileInputStream ccamStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(ccamStream);
        Sheet secondSheet = workbook.getSheetAt(1);
        Iterator<Row> iterator = secondSheet.iterator();

        // Skip the first row
        iterator.next();

        return iterator;
    }

    private static String getCellAsString(Row row, int cellId) {
        return row.getCell(cellId).getStringCellValue();
    }

    /**
     * Will used for filter invalid row, when first cell is empty or length < 7
     */
    private static boolean isValidRow(Row row) {
        short firstCellNum = row.getFirstCellNum();
        if (0 > firstCellNum) {
            return false;
        } else {
            Cell firstCell = row.getCell(firstCellNum);
            return CellType.STRING == firstCell.getCellType() && 7 == firstCell.getStringCellValue().length();
        }
    }
}
