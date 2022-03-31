package test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class PdfTest extends PDFTextStripper
{
    public final static int SKIP_BEYOND_X = 100;
    public final static String PDF_FILE_NAME = "test.pdf";

    public PdfTest() throws IOException {
    }

    public static void main( String[] args ) throws IOException {
            System.out.println("In main");
            try (PDDocument document = PDDocument.load(new File(PDF_FILE_NAME)))
            {
                PDFTextStripper stripper = new PdfTest();
                stripper.setSortByPosition( true );
                stripper.setStartPage( 0 );
                stripper.setEndPage( document.getNumberOfPages() );
                Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
                stripper.writeText(document, dummy);
            }
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
        for (TextPosition text : textPositions)
        {
            if(text.getXDirAdj() < SKIP_BEYOND_X)
                System.out.println( string + ", " + text.getXDirAdj() + ", " +
                        text.getYDirAdj() );
            break;
        }
    }


}
