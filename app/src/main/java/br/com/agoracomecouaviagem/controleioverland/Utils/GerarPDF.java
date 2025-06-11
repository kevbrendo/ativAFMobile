package br.com.agoracomecouaviagem.controleioverland.Utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
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
                if (document.getPdfDocument().getDefaultPageSize().getHeight() - document.getTopMargin() < document.getRenderer().getCurrentArea().getBBox().getHeight()) {
                    document.add(new AreaBreak());
                }
                document.add(new Paragraph(entry));
            }
            document.close();
            Toast.makeText(context, "PDF gerado com sucesso em " + pdfPath, Toast.LENGTH_LONG).show();
            uploadPDFToFirebase(pdfPath);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro ao gerar PDF", Toast.LENGTH_SHORT).show();
        } finally {
            if (pdfDocument != null) {
                pdfDocument.close();
            }
        }
    }

    public void uploadPDFToFirebase(String pdfPath) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pdfRef = storageRef.child("pdfs/generated_pdf.pdf");

        pdfRef.putFile(Uri.fromFile(new File(pdfPath)))
                .addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(context, "PDF enviado para o Firebase com sucesso", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Erro ao enviar PDF para o Firebase", Toast.LENGTH_SHORT).show();
                });
    }

}
