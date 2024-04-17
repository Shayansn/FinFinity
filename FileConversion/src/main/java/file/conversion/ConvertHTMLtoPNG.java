package file.conversion;

import com.spire.doc.*;
import com.spire.doc.documents.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConvertHTMLtoPNG {
    public static void main(String[] args) throws IOException {
        //Create a Document instance
        Document document = new Document();

        //Load a sample HTML file
        //document.loadFromFile("C:\\Users\\mohta\\Documents\\mus210002-workspace\\FinFinity\\FileConversion\\src\\main\\resources\\goalTracker.html", FileFormat.Html, XHTMLValidationType.None);
        document.loadFromFile("SE Project/goalTracker.html", FileFormat.Html, XHTMLValidationType.None);
        
        //Save to image. You can convert HTML to BMP, JPEG, PNG, GIF, Tiff etc.
        BufferedImage image= document.saveToImages(0, ImageType.Bitmap);
        //String result = "C:\\Users\\mohta\\Documents\\mus210002-workspace\\FinFinity\\FileConversion\\src\\main\\resources\\HtmlToImage.png";
        String result = "FileConversion/src/main/resources/HtmlToImage.png"
        File file= new File(result);
        ImageIO.write(image, "PNG", file);
    }
}
