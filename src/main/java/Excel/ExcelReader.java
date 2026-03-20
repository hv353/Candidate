package Excel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


public class ExcelReader {

    public static List<List<String>> readExcel(String filePath) {

        List<List<String>> data = new ArrayList<>();

        try {

            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(0);

            for(Row row : sheet){

                if(row.getRowNum() == 0) continue; // bỏ header

                List<String> rowData = new ArrayList<>();

                for(Cell cell : row){
                    rowData.add(cell.toString());
                }

                data.add(rowData);
            }

            workbook.close();

        } catch(Exception e){
            e.printStackTrace();
        }

        return data;
    }
}
