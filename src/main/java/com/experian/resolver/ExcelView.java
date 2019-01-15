package com.experian.resolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.experian.dto.neo4j.finalResponse.SavedDataResponse;

public class ExcelView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
    	// change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"BRD.xls\"");

        List<SavedDataResponse> savedDataResponseList = (List<SavedDataResponse>) model.get("request");
    
        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.GREEN.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
        
        // create style 
        CellStyle style1 = workbook.createCellStyle();
        Font font1 = workbook.createFont();
        font1.setFontName("Arial");
        style1.setFillForegroundColor(HSSFColor.WHITE.index);
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font1.setBold(true);
        font1.setColor(HSSFColor.GREEN.index);
        font1.setFontHeightInPoints((short)30);
        style1.setFont(font1);
        
        // create style 
        CellStyle style2 = workbook.createCellStyle();
        Font font2 = workbook.createFont();
        font2.setFontName("Arial");
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style2.setWrapText(true);
        font2.setBold(true);
        font2.setColor(HSSFColor.GREEN.index);
        font2.setFontHeightInPoints((short)18);
        style2.setFont(font2);
        
        Sheet coverSheet = workbook.createSheet("CoverSheet");
        coverSheet.setColumnWidth(0,2400);
        coverSheet.setColumnWidth(1,15000);
        coverSheet.setColumnWidth(2,19000);
        Row coverSheetRow = coverSheet.createRow(8);
        try {
        	File file = new ClassPathResource("experian.png").getFile();
            FileInputStream stream =
			        new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(stream);
			int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			stream.close();

			CreationHelper helper = workbook.getCreationHelper();

			//create sheet
			
			// Create the drawing patriarch. This is the top level container for all shapes. 
			Drawing drawing = coverSheet.createDrawingPatriarch();

			//add a picture shape
			ClientAnchor anchor = helper.createClientAnchor();
			//set top-left corner of the picture,
			//subsequent call of Picture#resize() will operate relative to it
			anchor.setCol1(0);
			anchor.setRow1(9);
			Picture pict = drawing.createPicture(anchor, pictureIdx);

			//auto-size picture relative to its top-left corner
			pict.resize();
			
			coverSheetRow.setHeight((short)600);
			coverSheetRow.createCell(1).setCellValue("Requirement Elaboration");
			coverSheetRow.getCell(1).setCellStyle(style2);
			coverSheetRow.createCell(2);
			//coverSheetRow.getCell(2).setCellStyle(style2);
			
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // create excel xls sheet
        Sheet documentPurpose = workbook.createSheet("Document Purpose");
        documentPurpose.setDefaultColumnWidth(30);
        documentPurpose.setColumnWidth(1, 21000);
        Row documentPurposeRow = documentPurpose.createRow(2);
        documentPurposeRow.createCell(1).setCellValue("Document Purpose");
        documentPurposeRow.getCell(1).setCellStyle(style1);
        Row documentPurposeRow3 = documentPurpose.createRow(3);
        documentPurposeRow3.createCell(1);
        Sheet sheet = workbook.createSheet("Requirement Elaboration");
        sheet.setDefaultColumnWidth(30);

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Taxonomy Level 1");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("Taxonomy Level 2");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("Taxonomy Level 3");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("Taxonomy Level 4");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("Requirement Statenent");
        header.getCell(4).setCellStyle(style);

        int rowCount = 1;

        for (SavedDataResponse savedDataResponse : savedDataResponseList) {
            Row userRow = sheet.createRow(rowCount++);
            documentPurposeRow3.getCell(1).setCellValue(savedDataResponse.getDocumentPurpose());
            coverSheetRow.getCell(2).setCellValue(savedDataResponse.getDocumentElaboration());
            userRow.createCell(0).setCellValue(savedDataResponse.getTaxonomyLevel1());
            userRow.createCell(1).setCellValue(savedDataResponse.getTaxonomyLevel2());
            userRow.createCell(2).setCellValue(savedDataResponse.getTaxonomyLevel3());
            userRow.createCell(3).setCellValue(savedDataResponse.getTaxonomyLevel4());
            userRow.createCell(4).setCellValue(savedDataResponse.getRequirementStatement());
        }	
	}
}