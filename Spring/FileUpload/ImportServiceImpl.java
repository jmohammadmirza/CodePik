package com.onevoiceupload.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.onevoiceupload.bean.ExcelDataRow;
import com.onevoiceupload.bean.FileBean;
import com.onevoiceupload.dao.ExcelDataDaoImpl;
import com.onevoiceupload.transaction.SpringExcelTransManager;

public class ImportServiceImpl implements ImportService  {
	private static final Logger logger = Logger.getLogger(ImportServiceImpl.class);
	

	@Autowired
	private SpringExcelTransManager springExcelTransManager;
	
	boolean cellValidate;

	@Override
	public void importExcelData(FileBean fileBean) throws Exception {
		String filePath = null;
		 try {			 
			InputStream inputStream = fileBean.getFileData().getInputStream();
			filePath=fileBean.getFileData().getOriginalFilename();
			//filePath=fileBean.getFileName();
			
			if(filePath.endsWith(".xls")){
				//NPOIFSFileSystem f=new NPOIFSFileSystem(inputStream);
				POIFSFileSystem fs = new POIFSFileSystem(inputStream);
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				HSSFSheet sheet = wb.getSheetAt(0);
				int rowCount = sheet.getPhysicalNumberOfRows();
				logger.info("No. of rows"+rowCount);
                HSSFRow row =  sheet.getRow(0);
				int cellCount = row.getPhysicalNumberOfCells();
			
				cellValidate = retrieveCellValidateForXLS(row, cellCount);
				
				if (cellValidate){					
					//Get the excel data
					logger.info("**************************************Reading Excel Data*************************************");
					Map<Integer, ExcelDataRow> map=	GetExcelDataXLS(sheet);
					springExcelTransManager.initiateTransactionExcelUpdate(map);
					}
				else
				{
					logger.error("Received file does not have a standard excel Template");
					throw new IllegalArgumentException("Received file does not have a standard excel Template .");
				}
		    }
			else if(filePath.endsWith(".xlsx")){
				//XLSX
				XSSFWorkbook wb=new XSSFWorkbook(inputStream);
				XSSFSheet sheet = wb.getSheetAt(0);
				int rowCount = sheet.getPhysicalNumberOfRows();
				logger.info("No. of rows"+rowCount);
                XSSFRow row =  sheet.getRow(0);
				int cellCount = row.getPhysicalNumberOfCells();
			
				cellValidate = retrieveCellValidateForXLSX(row, cellCount);
				
				if (cellValidate){					
					//Get the excel data
					logger.info("**************************************Reading Excel Data*************************************");
					Map<Integer, ExcelDataRow> map=	GetExcelDataXLSX(sheet);
					springExcelTransManager.initiateTransactionExcelUpdate(map);
				}
				else
				{
					logger.error("Received file does not have a standard excel Template");
					throw new IllegalArgumentException("Received file does not have a standard excel Template .");
				}
				
		}
			inputStream.close();
		} catch (IOException e1) {
			logger.error("Error Occured", e1);
			throw e1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error Occured", e);
			throw e;
		}
 }

	

	   private static Map<Integer, ExcelDataRow> GetExcelDataXLS(HSSFSheet sheet) {
		   String year,month,orgCode;
		   
		    ExcelDataRow excelDataRow=new ExcelDataRow();
			Map<Integer, ExcelDataRow> exceldatamap = new HashMap<Integer, ExcelDataRow>();
			 DataFormatter formatter = new DataFormatter();
			 Sheet sheet1=(Sheet)sheet;
			  for (Row row : sheet1) {
	               if (row.getRowNum() == 0) {
	            	   //Skip the header 
	            	   continue;
	               }
	                 // Iterator<Cell> cellIterator = row.cellIterator();
	                 // while (cellIterator.hasNext()) {
	                     // Cell cell = cellIterator.next();
	                      
	                     // switch(cell.getColumnIndex()) {
	                        
	                     // case 0:
	                    	  System.out.print(row.getCell(0) + "\t\t");
	                    	   year=formatter.formatCellValue(row.getCell(0));
	                    	  excelDataRow.setYear(year);
	                         //  break;
	                     // case 1:
	                    	  System.out.print(row.getCell(1) + "\t\t");
	                    	   month=formatter.formatCellValue(row.getCell(1));
	                          excelDataRow.setMonth(month);
	                        //  break;
	                    //  case 2:
	                          System.out.print(row.getCell(2) + "\t\t");
	                    	  orgCode=formatter.formatCellValue(row.getCell(2));
	                    	//Remove Decimal value and store integer in string
	                    	// Cell cellvalue=(row.getCell(2));
	                        //orgCode=Integer.toString((int)cellvalue.getNumericCellValue());
	                    	  excelDataRow.setOrgCode(orgCode);
	                    	//  break; 
	                     // case 3:
	                    	  System.out.print(row.getCell(3) + "\t\t\n");
	                    	  Cell supplierScore=(row.getCell(3));
	                    	  excelDataRow.setSupplierScore((int)supplierScore.getNumericCellValue());
	                        //  break; 
	                      // default:
	                        	  //error
	                        	//  break;
	                 // }
               //  }
	                  System.out.print("\n Entry added in Map ");
	                  exceldatamap.put(row.getRowNum(),new ExcelDataRow(excelDataRow.getYear(),excelDataRow.getMonth(),excelDataRow.getOrgCode(),excelDataRow.getSupplierScore()));
	           }
			   System.out.println("Excel Data Content ");
			   for (Map.Entry<Integer, ExcelDataRow> entry : exceldatamap.entrySet()) {
				  
			       System.out.println("\n Key = " + entry.getKey() + ", Value = " + entry.getValue());
			       logger.info("\n Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
			   return exceldatamap;
	}

		private Map<Integer, ExcelDataRow> GetExcelDataXLSX(XSSFSheet sheet) {
			String year,month,orgCode;	
			ExcelDataRow excelDataRow=new ExcelDataRow();
			Map<Integer, ExcelDataRow> exceldatamap = new HashMap<Integer, ExcelDataRow>();
			 DataFormatter formatter = new DataFormatter();
			  for (Row row : sheet) {
	               if (row.getRowNum() == 0) {
	            	   //Skip the header 
	            	   continue;
	               }
	           
	                // Iterator<Cell> cellIterator = row.cellIterator();
	                 // while (cellIterator.hasNext()) {
	                     // Cell cell = cellIterator.next();
	                      
	                     // switch(cell.getColumnIndex()) {
	                        
	                     // case 0:
	                    	  System.out.print(row.getCell(0) + "\t\t");
	                    	   year=formatter.formatCellValue(row.getCell(0));
	                    	  excelDataRow.setYear(year);
	                         //  break;
	                     // case 1:
	                    	  System.out.print(row.getCell(1) + "\t\t");
	                    	   month=formatter.formatCellValue(row.getCell(1));
	                          excelDataRow.setMonth(month);
	                        //  break;
	                    //  case 2:
	                          System.out.print(row.getCell(2) + "\t\t");
	                    	   orgCode=formatter.formatCellValue(row.getCell(2));
	                          //Cell cellvalue=(row.getCell(2));
	                    	 // orgCode=Integer.toString((int)cellvalue.getNumericCellValue());
	                    	  excelDataRow.setOrgCode(orgCode);
	                    	//  break; 
	                     // case 3:
	                    	  System.out.print(row.getCell(3) + "\t\t\n");
	                    	  Cell supplierScore=(row.getCell(3));
	                    	  excelDataRow.setSupplierScore((int)supplierScore.getNumericCellValue());
	                        //  break; 
	                      // default:
	                        	  //error
	                        	//  break;
	                 // }
               //  }
	                  System.out.print("\n Entry added in Map ");
	                  exceldatamap.put(row.getRowNum(),new ExcelDataRow(excelDataRow.getYear(),excelDataRow.getMonth(),excelDataRow.getOrgCode(),excelDataRow.getSupplierScore()));
	           }
			   System.out.println("Excel Data Content ");
			   for (Map.Entry<Integer, ExcelDataRow> entry : exceldatamap.entrySet()) {
				  
			       System.out.println("\n Key = " + entry.getKey() + ", Value = " + entry.getValue());
			       logger.info("\n Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
			   return exceldatamap;
	}
		
		
		private boolean retrieveCellValidateForXLS(HSSFRow row, int cellCount) {
		if (cellCount!=4){
				//Error
				logger.error("Column count is greater than 4.");
				cellValidate=false;
				//throw new IllegalArgumentException("Received file does not have a standard excel Template .");
				}
			else{
				 cellValidate = true;
				for (int id = 0; id < cellCount; id++) {
					//HSSFCell cell = row.getCell(Short.parseShort(Integer.toString(id)));
				     HSSFCell cell=row.getCell(id);
					String cellValue = cell.getStringCellValue().trim();
					if(id == 0){
						if(!"D_YR".equalsIgnoreCase(cellValue)){
							cellValidate = false;
						}
					}else if(id == 1){
						if(!"D_MO".equals(cellValue)){
							cellValidate = false;
						}
					}else if(id == 2){
						if(!"I_ORG_CODE".equals(cellValue)){
							cellValidate = false;
						}
					}else if(id == 3){
						if(!"P_SUPLR_ONE_VOICE".equals(cellValue)){
							cellValidate = false;
					 }
					}
		         }
			}
	
		return cellValidate;
	}
		private boolean retrieveCellValidateForXLSX(XSSFRow row, int cellCount) {
			if (cellCount!=4){
				//Error
				logger.error("Column count is greater than 4.");
				cellValidate=false;
				//throw new IllegalArgumentException("Received file does not have a standard excel Template .");
				}
			else{
				 cellValidate = true;
				for (int id = 0; id < cellCount; id++) {
					//HSSFCell cell = row.getCell(Short.parseShort(Integer.toString(id)));
				     XSSFCell cell=row.getCell(id);
					String cellValue = cell.getStringCellValue().trim();
					if(id == 0){
						if(!"D_YR".equalsIgnoreCase(cellValue)){
							cellValidate = false;
						}
					}else if(id == 1){
						if(!"D_MO".equals(cellValue)){
							cellValidate = false;
						}
					}else if(id == 2){
						if(!"I_ORG_CODE".equals(cellValue)){
							cellValidate = false;
						}
					}else if(id == 3){
						if(!"P_SUPLR_ONE_VOICE".equals(cellValue)){
							cellValidate = false;
					 }
					}
		         }
			}
			
			return cellValidate;
		}
}
