package project02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class IOUtil {
	
	private static InputStreamReader isr;
	private static BufferedReader br;
	private static URL url;
	
	public static BufferedReader getBufferedReader(String file, String charsetName) {

		try {
			
			url = new URL(file);
			isr = new InputStreamReader(url.openStream(), charsetName);
			br = new BufferedReader(isr);
		} catch (IOException e) {
			
		}

		return br;
	}
	
	public static void closeResource() throws IOException {
		if (br != null) {
			br.close();
		}
	}
}
