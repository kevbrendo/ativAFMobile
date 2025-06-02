package br.com.agoracomecouaviagem.controleioverland.Utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.AreaBreak;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GerarPDF {

    private Context context;

    public GerarPDF(Context context) {
        this.context = context;
    }

    public void generatePDF(List<String> data) {
        String downloadFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String pdfPath = downloadFolderPath + "/generated_pdf.pdf";
        PdfDocument pdfDocument = null;
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
            pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            for (String entry : data) {
                // Verificar se o texto cabe na página atual
                if (document.getPdfDocument().getDefaultPageSize().getHeight() - document.getTopMargin() < document.getRenderer().getCurrentArea().getBBox().getHeight()) {
                    document.add(new AreaBreak());
                }
                document.add(new Paragraph(entry)); // Adiciona cada entrada como um parágrafo no PDF
            }
            document.close();
            Toast.makeText(context, "PDF gerado com sucesso em " + pdfPath, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro ao gerar PDF", Toast.LENGTH_SHORT).show();
        } finally {
            if (pdfDocument != null) {
                pdfDocument.close();
            }
        }
    }

}
