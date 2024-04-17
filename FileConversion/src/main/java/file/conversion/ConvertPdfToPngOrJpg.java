//disregard
//was assigned to code PDF > PNG/JPG but had to scrap and restart, coding HTML > PNG/JPG/PDF files

package file.conversion;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.spire.pdf.PdfDocument;


public class ConvertPdfToPngOrJpg {
	public static void main(String[] args) throws IOException {
		//Create a pdf document object/instance
		PdfDocument pdfDoc = new PdfDocument();
		//load the pdf document
		pdfDoc.loadFromFile("C:\\Users\\mohta\\Documents\\mus210002-workspace\\FinFinity\\FileConversion\\src\\main\\resources\\Mohtashim Syed Resume.pdf");
		
		BufferedImage img;
		//Traverse thru the pdf document's pages
		for (int i = 0; i < pdfDoc.getPages().getCount(); i++) {
			//Save every page as a PNG
			img = pdfDoc.saveAsImage(i);
			

			File file = new File("C:\\Users\\mohta\\Documents\\mus210002-workspace\\FinFinity\\FileConversion\\src\\main\\resources\\output" + String.format(("ToImage-img-%d.png"), i));
			ImageIO.write(img, "PNG", file);
			//System.out.println("Hello");
		}
		pdfDoc.close();
	}

}
