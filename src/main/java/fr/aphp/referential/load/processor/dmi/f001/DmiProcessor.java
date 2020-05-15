package fr.aphp.referential.load.processor.dmi.f001;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;

import org.apache.camel.Message;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import fr.aphp.referential.load.domain.type.dmi.DmiEvent;
import fr.aphp.referential.load.message.dmi.f001.DmiMessage;
import io.vavr.control.Try;

import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class DmiProcessor {
    public static Iterator<Row> xlsRows(File dmi) {
        return Try.withResources(() -> new FileInputStream(dmi))
                .of(DmiProcessor::getXlsRowIterator)
                .get();
    }

    public static Optional<DmiMessage> optionalDmiMessage(Message message) {
        Row row = message.getBody(Row.class);
        if (isValidRow(row)) {
            int firstCellNum = row.getFirstCellNum();
            Function<Integer, String> getCell = cellId -> getCellAsString(row, cellId);
            Date startDate = toDateOptional(getCell.apply(firstCellNum + 1)).orElse(message.getHeader(VALIDITY_DATE, Date.class));
            DmiMessage dmiMessage = DmiMessage.builder()
                    .startDate(startDate)
                    .label(getCell.apply(firstCellNum + 2))
                    .lpp(getCell.apply(firstCellNum + 3))
                    .dmiEvent(DmiEvent.fromIdentifier(getCell.apply(firstCellNum + 4)))
                    .build();
            return Optional.of(dmiMessage);
        } else {
            return Optional.empty();
        }
    }

    private static Iterator<Row> getXlsRowIterator(FileInputStream ccamStream) throws IOException {
        Workbook workbook = new HSSFWorkbook(ccamStream);
        Sheet secondSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = secondSheet.iterator();

        // Skip the first row
        iterator.next();

        return iterator;
    }

    private static boolean isValidRow(Row row) {
        short firstCellNum = row.getFirstCellNum();
        if (0 > firstCellNum) {
            return false;
        } else {
            Optional<Cell> labelCell = Optional.ofNullable(row.getCell(firstCellNum + 2));
            return labelCell.isPresent()
                    && CellType.STRING == labelCell.get().getCellType()
                    && isNotBlank(labelCell.get().getStringCellValue());
        }
    }

    private static String getCellAsString(Row row, int cellId) {
        return new DataFormatter().formatCellValue(row.getCell(cellId));
    }

    private static Optional<Date> toDateOptional(String date) {
        return Try.of(() -> new SimpleDateFormat("M/d/yy").parse(date)).toJavaOptional();
    }
}
