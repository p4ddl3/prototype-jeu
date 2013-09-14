package isomap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Petit d�pot fourre-tout de m�thodes pratiques
 * @author Simon
 *
 */
public class Utils {
	public static String loadJSON(String file){
		String json = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null){
				json += line+"\n";
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
}
