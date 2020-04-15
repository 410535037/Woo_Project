package com.example.woo_project.record;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class CreatePDF {
    public static void createPdf(String url,String outFileName) throws Exception {

        Document document = new Document(PageSize.A4);
        PdfWriter pdfWriter = PdfWriter.getInstance(document,new FileOutputStream(outFileName));
        document.open();
        document.addAuthor("蝸蝸遊旅行網");
        document.addTitle("蝸蝸遊旅行網電子合同");
        document.addCreator("蝸蝸遊旅行網");
        document.addHeader("wowoyoo.com", "蝸蝸遊");
        document.addKeywords("蝸蝸遊,電子合同 ");
        document.addSubject("蝸蝸遊旅行網電子合同");



    }
}
