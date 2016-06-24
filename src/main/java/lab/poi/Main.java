package lab.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {

	public static void main(String[] args) throws IOException {
		//Workbook wb = new HSSFWorkbook();
	    Workbook wb = new XSSFWorkbook();
	    
	  //add picture data to this workbook.
	    InputStream is = new FileInputStream("logo.png");
	    byte[] bytes = IOUtils.toByteArray(is);
	    int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
	    is.close();
	    
	    CreationHelper createHelper = wb.getCreationHelper();
	    Sheet sheet = wb.createSheet("Event Detail");
	    
	    sheet.setColumnWidth(0, 100);
	    
	 // Create the drawing patriarch.  This is the top level container for all shapes. 
	    Drawing drawing = sheet.createDrawingPatriarch();
	    
	  //add a picture shape
	    ClientAnchor anchor = createHelper.createClientAnchor();
	    //set top-left corner of the picture,
	    //subsequent call of Picture#resize() will operate relative to it
	    anchor.setCol1(0);
	    anchor.setRow1(0);
	    Picture pict = drawing.createPicture(anchor, pictureIdx);

	    //auto-size picture relative to its top-left corner
	    pict.resize();

	    
	    // Create a row and put some cells in it. Rows are 0 based.
	    Row row = sheet.createRow(7);

	    // Create a cell and put a date value in it.  The first cell is not styled
	    // as a date.
	    Cell cell = row.createCell(0);
	    cell.setCellValue("Event Detail");
	    /*
	    sheet.addMergedRegion(new CellRangeAddress(
	            0, //first row (0-based)
	            0, //last row  (0-based)
	            0, //first column (0-based)
	            2  //last column  (0-based)
	    ));*/
	    
	    CellStyle style = wb.createCellStyle();
	    Font font = wb.createFont();
	    font.setColor(IndexedColors.RED.index);
	    style.setFont(font);
	    
	    cell.setCellStyle(style);

	    // Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
	    wb.write(fileOut);
	    fileOut.close();
	                    
	}

}
