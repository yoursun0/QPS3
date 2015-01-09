package qpses.business;
import com.lowagie.text.pdf.*;
import com.lowagie.text.*;
import com.lowagie.text.ExceptionConverter;

import qpses.util.SysManager;

public final class PdfHelper extends PdfPageEventHelper
{
    // Font
    private BaseFont  BFontTimesRoman = null;
    public static final Font FontTitle       = new Font(Font.TIMES_ROMAN, 14 , Font.BOLD);
    public static final Font FontTableHeader = new Font(Font.TIMES_ROMAN, 12 , Font.BOLD);
    public static final Font FontDefault     = new Font(Font.TIMES_ROMAN, 12 , Font.NORMAL);
    public static final Font FontSmall       = new Font(Font.TIMES_ROMAN, 10 , Font.NORMAL);
    
    public static final float DefaultCellHeight = 20f;
    private PdfTemplate PageTemplate = null;
    
    private String DisplayUserName = "";
    private String ReportTimeStamp = "";
    private String HeaderText = "";
    private String FooterText = "";
    
    private int HeaderTextPos = 50;
    private int FooterTextPos = 70;
    
    /** Creates a new instance of PdfHelper */
    public PdfHelper()
    {
        try
        {
            this.BFontTimesRoman = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont. WINANSI, false);
            this.ReportTimeStamp = SysManager.getCurDateTimeStr("yyyy.MM.dd kk:mm:ss");
        }
        catch (Exception e)
        {
            throw new ExceptionConverter(e);
        }
    }
    
    public void setHeaderTextPos(int pos)
    { this.HeaderTextPos = pos; }

    public void setFooterTextPos(int pos)
    { this.FooterTextPos = pos; }
    
    public void setHeaderText(String someText)
    {
        this.HeaderText = someText;
    }

    public void setFooterText(String someText)
    {
         this.FooterText = someText;
    }    

    public String getHeaderText()
    {
        return this.HeaderText;
    }

    public String getFooterText()
    {
         return this.FooterText;
    }     
    
    public void setDisplayUserName(String userName)
    {
        this.DisplayUserName = userName;
    }
    
    public Paragraph getTitleParagraph(String someText)
    {
        return new Paragraph(someText, this.FontTitle);
    }
    
    public Paragraph getParagraph(String someText)
    {
        return new Paragraph(someText, this.FontDefault);
    }
    
    public PdfPCell getCell(String someText)
    {
        PdfPCell aPdfPCell = new PdfPCell(new Paragraph(someText, this.FontDefault));
        aPdfPCell.setMinimumHeight(this.DefaultCellHeight);
        return aPdfPCell;
    }
    
    public PdfPCell getCellSmall(String someText)
    {
        PdfPCell aPdfPCell = new PdfPCell(new Paragraph(someText, this.FontSmall));
        aPdfPCell.setMinimumHeight(this.DefaultCellHeight);
        return aPdfPCell;
    }
    
    public PdfPCell getHeaderCell(String someText)
    {
        PdfPCell aPdfPCell = new PdfPCell(new Paragraph(someText, this.FontTableHeader));
        aPdfPCell.setMinimumHeight(this.DefaultCellHeight);
        return aPdfPCell;
    }
    
    public void onOpenDocument(PdfWriter aPdfWriter, Document aPdfDoc)
    {
        try
        {
            this.PageTemplate = aPdfWriter.getDirectContent().createTemplate(100, 100);
        }
        catch (Exception e)
        {
            throw new ExceptionConverter(e);
        }
    }
    
    public void onStartPage(PdfWriter aPdfWriter, Document aPdfDoc)
    {
        try
        {
            PdfPTable aPdfTable = null;
            PdfPCell cell       = null;
            
            // Header
            aPdfTable = new PdfPTable(1);
            aPdfTable.setTotalWidth(aPdfDoc.getPageSize().width() - aPdfDoc.leftMargin() - aPdfDoc.rightMargin());
            cell = new PdfPCell(new Paragraph(this.getHeaderText(), this.FontTitle));
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setBorder(0);
            aPdfTable.addCell(cell);
            
            //aPdfTable.writeSelectedRows(0, -1, aPdfDoc.leftMargin(), aPdfDoc.getPageSize().height() - 10, aPdfWriter.getDirectContent());
            aPdfTable.writeSelectedRows(0, -1, aPdfDoc.leftMargin(), aPdfDoc.getPageSize().height() - this.HeaderTextPos, aPdfWriter.getDirectContent());
            
            // Footer
            aPdfTable = new PdfPTable(4);
            aPdfTable.setTotalWidth(aPdfDoc.getPageSize().width() - aPdfDoc.leftMargin() - aPdfDoc.rightMargin());
            int tableColWidths[] = {37, 15, 8, 40}; // percentage //{40, 15, 20, 30}
            aPdfTable.setWidths(tableColWidths);
            
            cell = new PdfPCell(new Paragraph(this.ReportTimeStamp, this.FontTitle));
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cell.setBorder(0);
            aPdfTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("Page " + aPdfWriter.getPageNumber() + " of ", this.FontTitle));
            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cell.setBorder(0);
            aPdfTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("", this.FontTitle));
            cell.setBorder(0);
            aPdfTable.addCell(cell);

            cell = new PdfPCell(new Paragraph("Printed by " + this.DisplayUserName, this.FontTitle));
            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cell.setBorder(0);
            aPdfTable.addCell(cell);            

            cell = new PdfPCell(new Paragraph(this.getFooterText(), this.FontTitle));
            cell.setColspan(4);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setBorder(0);
            aPdfTable.addCell(cell);
            
            //aPdfTable.writeSelectedRows(0, -1, aPdfDoc.leftMargin(), 50, aPdfWriter.getDirectContent());
            aPdfTable.writeSelectedRows(0, -1, aPdfDoc.leftMargin(), this.FooterTextPos, aPdfWriter.getDirectContent());
        }
        catch (Exception e)
        {
            throw new ExceptionConverter(e);
        }
    }
    
    public void onEndPage(PdfWriter aPdfWriter, Document aPdfDoc)
    {
        //PdfContentByte cb = aPdfWriter.getDirectContent();
        //aPdfWriter.getDirectContent().addTemplate(this.PageTemplate, 442, 34);
   
        //aPdfWriter.getDirectContent().addTemplate(this.PageTemplate, (aPdfDoc.getPageSize().width() / 2) + (aPdfDoc.getPageSize().width() / 40) , 34);
        //aPdfWriter.getDirectContent().addTemplate(this.PageTemplate, (aPdfDoc.getPageSize().width() / 2) + (aPdfDoc.getPageSize().width() / 40) , 54);
        aPdfWriter.getDirectContent().addTemplate(this.PageTemplate, (aPdfDoc.getPageSize().width() / 2) + (aPdfDoc.getPageSize().width() / 40) , this.FooterTextPos - 16);
    }
    
    public void onCloseDocument(PdfWriter aPdfWriter, Document aPdfDoc)
    {
        // Add total page number
        this.PageTemplate.beginText();
        this.PageTemplate.setFontAndSize(this.BFontTimesRoman, 14);
        this.PageTemplate.setTextMatrix(0, 0);
        this.PageTemplate.showText("" + (aPdfWriter.getPageNumber() - 1));
        this.PageTemplate.endText();
    }
}
