package file.conversion;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException; 
import java.io.BufferedReader;

import javax.imageio.ImageIO;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfMargins;
import com.spire.pdf.htmlconverter.qt.HtmlConverter;
import com.spire.pdf.htmlconverter.LoadHtmlType;
import com.spire.pdf.htmlconverter.qt.Size;

public class ConvertHTMLtoPDF {
	public static void main(String[] args) throws IOException {
		//Invoke the custom method HtmlToString() to convert HTML file to string
		String htmlString = 
				//HtmlToString("C:\\Users\\mohta\\Documents\\mus210002-workspace\\FinFinity\\FileConversion\\src\\main\\resources\\goalTracker.html");
				HtmlToString("SE Project/goalTracker.html");
		//Specify the output file path
		// String outputFile = 
		// 		"C:\\Users\\mohta\\Documents\\mus210002-workspace\\FinFinity\\FileConversion\\src\\main\\resources\\HtmlToPdf.pdf";
		String outputFile = 
				"FileConversion/src/main/resources/HtmlToPDF.pdf";
		
		//Download plugins from the link:
		// https://www.e-iceblue.com/downloads/plugins/plugins-windows-x64.zip
		//and then specify the plugin path:
		String pluginPath = "C:\\Program Files\\plugins";
		
		
		//Set Plugin Path
		HtmlConverter.setPluginPath(pluginPath);
		
		//Convert HTML string to PDF
		HtmlConverter.convert(htmlString, outputFile, true, 100000, new Size(900, 700), new PdfMargins(0), LoadHtmlType.Source_Code);
		
	}
	
    //Convert a HTML file to string
    public static String HtmlToString(String filePath) throws IOException {
        String path = filePath;
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String temp = "";
        while ((temp = bufferedReader.readLine()) != null) {
            stringBuilder.append(temp + "\n");
        }
        bufferedReader.close();
        String str = stringBuilder.toString();
        return str;
    }

}
