package com.experian.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.experian.dto.neo4j.request.FinalNeo4JRequest;
import com.experian.dto.neo4j.request.Neo4JFileRequest;

/**
 * 
 * @author manchanda.a
 *
 */
@Service
public class FileWriterService extends AbstractXlsView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest arg2,
			HttpServletResponse response) throws Exception {
		// change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"BRD.xls\"");

        @SuppressWarnings("unchecked")
        FinalNeo4JRequest finalNeo4JRequest = (FinalNeo4JRequest) model.get("request");

        Sheet coverSheet = workbook.createSheet("CoverSheet");
        coverSheet.setDefaultColumnWidth(30);
        
        // create header row
        Row coverHeader = coverSheet.createRow(20);
        coverHeader.createCell(20).setCellValue(finalNeo4JRequest.getDocumentRequest().getRequirementElaboration());
        
        // create excel xls sheet
        Sheet sheet = workbook.createSheet("Requirement Elaboration");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);


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

        for (Neo4JFileRequest neo4jFileRequest : finalNeo4JRequest.getStatementModels()) {
            Row userRow = sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue(neo4jFileRequest.getTaxonomyLevel1());
            userRow.createCell(1).setCellValue(neo4jFileRequest.getTaxonomyLevel2());
            userRow.createCell(2).setCellValue(neo4jFileRequest.getTaxonomyLevel3());
            userRow.createCell(3).setCellValue(neo4jFileRequest.getTaxonomyLevel4());
            userRow.createCell(4).setCellValue(neo4jFileRequest.getRequirementStatement());
        }	
	}
}
