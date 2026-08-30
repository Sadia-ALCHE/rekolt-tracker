package mu.rekolt.service;

import mu.rekolt.model.Delivery;
import mu.rekolt.model.Member;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Creates the season report as a Word document.
public class DocumentService {
    public void writeSeasonReport(SeasonService season, String docxPath, String logPath) throws IOException {
        List<Member> members = new ArrayList<>();
        for(String id : season.getMemberIds()) {
            List<Delivery> deliveries = season.getDeliveriesPerMember().get(id);
            if (deliveries != null && !deliveries.isEmpty()) {
                members.add(deliveries.get(0).getMember());
            }
        }

        // The document and file are closed automatically after use.
        try (XWPFDocument document = new XWPFDocument();
             FileOutputStream out = new FileOutputStream(docxPath)) {

            boolean firstMember = true;
            for (Member member : members) {
                if (!firstMember) {
                    startNewPage(document);
                }
                writeMemberSection(document, season, member);
                firstMember = false;
            }
            startNewPage(document);
            writeClosingSection(document, members);
            document.write(out);
        }
        appendRunLog(logPath, members.size());
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

        List<Delivery> deliveries = season.getDeliveriesPerMember().get(member.getId());
        XWPFTable table = document.createTable(deliveries.size() + 1, 6);

        // Header row
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Delivery");
        header.getCell(1).setText("Produce");
        header.getCell(2).setText("Mass");
        header.getCell(3).setText("Grade");
        header.getCell(4).setText("Commission");
        header.getCell(5).setText("Net payable");

        double totalCommission = 0.0;
        double totalLevy = 0.0;
        double totalNet = 0.0;

        // Add each delivery to the table
        for (int i = 0; i < deliveries.size(); i++) {
            Delivery delivery = deliveries.get(i);
            XWPFTableRow row = table.getRow(i + 1);

            double commission = delivery.commission();
            double levy = delivery.transportLevy();
            double net = delivery.netPayable();

            row.getCell(0).setText(delivery.getDeliveryId());
            row.getCell(1).setText(delivery.getProduce().getCode());
            row.getCell(2).setText(String.format("%.1f", delivery.getMass()));
            row.getCell(3).setText(delivery.getGrade().toString());
            row.getCell(4).setText(String.format("%.2f", commission));
            row.getCell(5).setText(String.format("%.2f", net));
            totalCommission += commission;
            totalLevy += levy;
            totalNet += net;
        }
        // Member totals
        XWPFParagraph totals = document.createParagraph();
        XWPFRun totalsRun = totals.createRun();
        totalsRun.setText(String.format("Total commission: %.2f MUR", totalCommission));
        totalsRun.setText(String.format("Total transport levy: %.2f MUR", totalLevy));

        // Net payable
        XWPFParagraph netPara = document.createParagraph();
        XWPFRun netRun = netPara.createRun();
        netRun.setBold(true);
        netRun.setFontSize(13);
        netRun.setText(
                "NET PAYABLE TO " + member.getName().toUpperCase() + ": " + String.format("%.2f", totalNet) + " MUR");

        // Signature
        XWPFParagraph signature = document.createParagraph();
        XWPFRun sigRun = signature.createRun();
        sigRun.setText("Signature: ___________________________");
        sigRun.setText("Date: ________________________________");
    }
    private void writeClosingSection(XWPFDocument document, List<Member> members) {
        XWPFParagraph heading = document.createParagraph();
        XWPFRun run = heading.createRun();
        run.setText("Season totals");
        run.setBold(true);
        run.setFontSize(16);

        XWPFTable table = document.createTable(members.size() + 1, 2);
        table.getRow(0).getCell(0).setText("Member");
        table.getRow(0).getCell(1).setText("Net payable (MUR)");

        double seasonTotal = 0.0;
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            double total = member.netPayable();
            XWPFTableRow row = table.getRow(i + 1);
            row.getCell(0).setText(member.getId() + " " + member.getName());
            row.getCell(1).setText(String.format("%.2f", total));
            seasonTotal += total;
        }

        XWPFParagraph totalParagraph = document.createParagraph();
        XWPFRun totalRun = totalParagraph.createRun();
        totalRun.setBold(true);
        totalRun.setFontSize(13);
        totalRun.setText("TOTAL PAYABLE FOR THE SEASON: " + String.format("%.2f", seasonTotal) + " MUR");
    }

    private void appendRunLog(String logPath, int sectionCount) throws IOException {
        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String line = timestamp + " - report of the season generated, " + sectionCount + " member sections" + System.lineSeparator();

        try (java.io.FileWriter writer = new java.io.FileWriter(logPath, true)) {
            writer.write(line);
        }
    }
}