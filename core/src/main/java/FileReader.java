import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

// Class responsible for reading in data from the excel files provided
public class FileReader {
    private String fileName;
    private FileInputStream inFile;
    private FileOutputStream outFile;


    public FileReader(@NotNull String fileName) {
        this.fileName = (fileName.endsWith(".xlsx") || fileName.endsWith(".csv") || fileName.endsWith(".xls")) ? fileName : null;
//        System.out.println("fileName: " + fileName);
        this.inFile = null;
        this.outFile = null;
    }

    // method to read in the file name data
    // essentially needs to walk through the file,
    // parse the data in and create a Player while doing it
    // and push the Player into a List
    public boolean readFile(@NotNull List<Player> playerList) throws IOException {
        this.inFile = new FileInputStream(new File(fileName));


        // we create an XSSF Workbook object for our XLSX Excel File
        XSSFWorkbook workbook = new XSSFWorkbook(this.inFile);
        // we get first sheet
        XSSFSheet sheet = workbook.getSheetAt(0);

        // we iterate on rows
        Iterator<Row> rowIt = sheet.iterator();

        while(rowIt.hasNext()) {
            Row row = rowIt.next();
            if(row.getCell(0).toString().equals("Gamer Tag")){
                continue;
            }

            // iterate on cells for the current row
            Iterator<Cell> cellIterator = row.cellIterator();
            LinkedList<String> cellContents = new LinkedList<>();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                cellContents.add(cell.toString());
            }

            playerList.add(
                    new Player(
                            cellContents.get(0),
                            Double.parseDouble(cellContents.get(1)),
                            Double.parseDouble(cellContents.get(2)),
                            Double.parseDouble(cellContents.get(3)),
                            Double.parseDouble(cellContents.get(4)),
                            Double.parseDouble(cellContents.get(5)),
                            Double.parseDouble(cellContents.get(6))
                    )
            );
        }

        workbook.close();
        this.inFile.close();

        return true;
    }
}
