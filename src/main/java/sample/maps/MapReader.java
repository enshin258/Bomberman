package sample.maps;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Vector;

public class MapReader {

    public static void readMapFromXLSXFile(String pathToMapFile, GridPane mapGridPane, Vector<Title> titles) throws IOException, InvalidFormatException {

        XSSFWorkbook workbook = new XSSFWorkbook(new File(pathToMapFile));
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (int i=0;i<16;i++) {
            for(int j=0;j<16;j++)
            {
                switch ((int)sheet.getRow(i).getCell(j).getNumericCellValue())
                {
                    case 0:
                    {
                        Title title = new Title(new Image("/sprites/environment/floor.png"), TypeOfTitle.FLOOR);
                        mapGridPane.add(title.getRectangle(),i,j);
                        titles.add(title);
                        break;
                    }
                    case 1:
                    {
                        Title title = new Title(new Image("/sprites/environment/brick.png"),TypeOfTitle.BRICK);
                        mapGridPane.add(title.getRectangle(),i,j);
                        titles.add(title);
                        break;
                    }
                    case 2:
                    {
                        Title title = new Title(new Image("/sprites/environment/wall.png"),TypeOfTitle.WALL);
                        mapGridPane.add(title.getRectangle(),i,j);
                        titles.add(title);
                        break;
                    }
                }
            }
        }
        workbook.close();
    }
}
