package com.splunk.sampleproject.dataread;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author jagadeesh_j
 *
 */

// TODO: Auto-generated Javadoc
/**
 * The Class ExcelUtil.
 */
public class ExcelUtil {
	
	/** The row. */
	XSSFRow row = null;
	
	/** The cell. */
	XSSFCell cell = null;
	
	/** The value. */
	public String value = "";
	
	/** The file. */
	File file = null;

	/** The formatter. */
	public DataFormatter formatter;

	/**
	 * Instantiates a new excel util.
	 */
	public ExcelUtil() {
		formatter = new DataFormatter();
		value = new File("resources//data.xlsx").getAbsolutePath();
		file = new File(value);
	}

	/**
	 * Read xl col.
	 *
	 * @param sheetName the sheet name
	 * @param colname the colname
	 * @return the list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List readXlCol(String sheetName, String colname) throws IOException {

		FileInputStream InputStream = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(InputStream);
		XSSFSheet sheet = workbook.getSheet(sheetName);

		String columnWanted = colname;
		Integer columnNo = null;
		// output all not null values to the list
		List<Cell> cells = new ArrayList<Cell>();

		// Get the first cell.
		Row row = sheet.getRow(0);
		// Cell cell = row.getCell(0);
		for (Cell cell : row) {
			// Column header names.
			// System.out.println(cell.toString());
			String cellVal = formatter.formatCellValue(cell);
			if (cellVal.equals(columnWanted)) {
				columnNo = cell.getColumnIndex();
			}
		}

		if (columnNo != null) {
			for (Row row1 : sheet) {
				Cell c = row1.getCell(columnNo);
				if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK) {
					// Nothing in the cell in this row, skip it
				} else {
					DataFormatter formatter = new DataFormatter();
					String val = formatter.formatCellValue(c);
					if (!val.equals(columnWanted)) {
						cells.add(c);
					}
				}
			}
			System.out.println(cells);

		} else {
			System.out.println("could not find column ");
		}

		return cells;
	}

	/**
	 * Gets the first row cell values.
	 *
	 * @param sheetName the sheet name
	 * @return the first row cell values
	 * @throws Exception the exception
	 */
	/*public ArrayList getFirstRowCellValues(String sheetName) throws Exception {
		DataFormatter formatter = new DataFormatter();
		ArrayList<String> rowValues = new ArrayList<>();

		try {

			FileInputStream InputStream = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(InputStream);
			XSSFSheet sheet = workbook.getSheet(sheetName);

			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				String str = formatter.formatCellValue(row.getCell(i)).trim();
				rowValues.add(str);
			}

		} catch (Exception ex) {

		}
		return rowValues;

	}
*/
}
