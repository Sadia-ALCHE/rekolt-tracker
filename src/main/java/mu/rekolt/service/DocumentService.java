package mu.rekolt.service;

import mu.rekolt.model.Delivery;
import mu.rekolt.model.Member;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Creates the season report as a Word document.
public class DocumentService {
    public void writeSeasonReport(SeasonService season, String docxPath) throws IOException {
        // The document and file are closed automatically after use.
        try (XWPFDocument document = new XWPFDocument();
             FileOutputStream out = new FileOutputStream(docxPath)) {

            List<Member> members = new ArrayList<>();
            for(String id : season.getMemberIds()) {
                List<Delivery> deliveries = season.getDeliveriesPerMember().get(id);

                if (deliveries != null && !deliveries.isEmpty()) {
                    members.add(deliveries.get(0).getMember());
                }
            }
            boolean firstMember = true;
            for (Member member : members) {
                if (!firstMember) {
                    startNewPage(document);
                }
                writeMemberSection(document, season, member);
                firstMember = false;
            }
            document.write(out);
        }
    }

    private void startNewPage(XWPFDocument document) {
        XWPFParagraph pageBreak = document.createParagraph();
        XWPFRun run = pageBreak.createRun();
        run.addBreak(BreakType.PAGE);
    }

    private void writeMemberSection(
            XWPFDocument document, SeasonService season, Member member) {

        XWPFParagraph heading = document.createParagraph();
        XWPFRun headingRun = heading.createRun();
        headingRun.setText(member.getId() + " - " + member.getName());
        headingRun.setBold(true);
        headingRun.setFontSize(16);
    }
}