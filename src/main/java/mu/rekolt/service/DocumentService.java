package mu.rekolt.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;

// Creates the season report as a Word document.
public class DocumentService {
    public void writeTestDocument(String path) throws IOException {
        // The document and file are closed automatically after use.
        try (XWPFDocument document = new XWPFDocument();
             FileOutputStream out = new FileOutputStream(path)) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("Hello from REKOLT Produce Tracker.");
            run.setBold(true);
            document.write(out);
        }
    }
}