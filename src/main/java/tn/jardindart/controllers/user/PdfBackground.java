package tn.jardindart.controllers.user;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
public class PdfBackground extends PdfPageEventHelper {
    private String backgroundImagePath;
    public PdfBackground(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            Image background = Image.getInstance(backgroundImagePath);
            background.setAbsolutePosition(0, 0);
            background.scaleAbsolute(document.getPageSize().getWidth(), document.getPageSize().getHeight());
            writer.getDirectContentUnder().addImage(background);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}