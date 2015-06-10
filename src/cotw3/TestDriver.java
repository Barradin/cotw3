package cotw3;

import java.io.File;
import java.io.IOException;

public class TestDriver {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File cFile = new File("CountryData.bin");
		File cFile2 = new File("Log.txt");
		if(cFile.exists()){
			cFile.delete();
		}
		if(cFile2.exists()){
			cFile2.delete();
		}
		
		Setup.main(args);		
		PrettyPrintUtility.main(args);
		UserApp.main(args);
		

	}

}
