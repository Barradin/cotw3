package cotw3;

/****************************************
 * Adam Tracy                           *
 * Countries of the World Assignment 3  *
 * DataTable                            *
 * Transition class between setup       *
 * and userapp with datarecord class    *
 ***************************************/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DataTable{
//declare some stuff
	private DataRecord dr = new DataRecord();
	private final short MAX_N_HOME_LOC = 20;
	private int homeAddress;
	private char[] codeA = new char[3];
	private short idA;
	private char[] countryNameA = new char[15];
	private char[] continentA = new char[13];
	private int areaA;
	private long populationA;
	private float lifeExpA;
	private short link;
	private boolean append = true;

	//************************************************************
	/**
	 * constructor
	 * @throws FileNotFoundException
	 */
	public DataTable() throws FileNotFoundException {

	}

	//*****************************************************************
	/**
	 * clean up the record further for insertion
	 * @param code
	 * @param id
	 * @param cn
	 * @param co
	 * @param a
	 * @param pop
	 * @param le
	 * @throws IOException
	 */
	public void insert(String code, int id, String cn, String co, long a,
			long pop, float le) throws IOException {
		// TODO Auto-generated method stub
		codeA = code.toCharArray();
		int RRN = hashFunction(codeA);
		idA = (short) id;
		countryNameA = cn.toCharArray();
		continentA = co.toCharArray();
		areaA = (int) a;
		populationA = pop;
		lifeExpA = le;
		link = -1;
		String temp2 = new String(countryNameA);
		temp2 = temp2.substring(0, Math.min(temp2.length(), 15));
		temp2 = padRight(temp2,15);
		countryNameA = temp2.toCharArray();
		String temp3 = new String(continentA);
		temp3 = padRight(temp3,13);
		continentA = temp3.toCharArray();
		dr.write1Record(codeA, idA, countryNameA, continentA, areaA, populationA, lifeExpA, link, RRN);
	}
	
	/**
	 * method to search the hashtable for a matching code
	 * @param RRN
	 * @param otherTran
	 * @return
	 * @throws IOException
	 */
	public boolean searchHash(int RRN, String otherTran) throws IOException {
		// TODO Auto-generated method stub
		boolean found = dr.searchHash(RRN, otherTran);
		if(found){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	/**
	 * the almighty hash function. ALL HAIL THE HASH
	 * @param codeA
	 * @return
	 */
	public int hashFunction(char[] codeA){
		int A = (int)codeA[0];
		int B = (int)codeA[1];
		int C = (int)codeA[2];
		
		homeAddress = A * B * C;
		homeAddress %= MAX_N_HOME_LOC;
		if(homeAddress == 0){
			homeAddress = MAX_N_HOME_LOC;
				return homeAddress;
		}
		else{
			return homeAddress;
		}
	}

	/**
	 * close a file
	 * @throws IOException
	 */
	public void closeFile() throws IOException {
		dr.closeFile();
	}

	/**
	 * padding the string so it will fit
	 * @param s
	 * @param size
	 * @return
	 */
	public String padRight(String s, int size) {
		int padSize = size - s.length();
		String pad = " ";
		if (padSize == 0) {
			return s;
		} else {
			for (int i = 0; i < padSize; i++) {
				s += pad;
			}
			return s;
		}
	}

	/**
	 * pad it some more to fit
	 * @param s
	 * @param size
	 * @return
	 */
	public String padLeft(String s, int size) {
		int padSize = size - s.length();
		String pad = " ";
		if (padSize == 0) {
			return s;
		} else {
			for (int i = 0; i < padSize; i++) {
				s = pad + s;
			}
			return s;
		}
	}
	
	/**
	 * for the various status messages throughout the program
	 * @param s
	 * @param n
	 * @throws IOException
	 */
	public void writeStatus(String s, int n) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("Log.txt");
		FileWriter write = new FileWriter(file, append);
		PrintWriter p = new PrintWriter(write);
		if(n == 0){
		p.printf(s + "%n");
		}
		else{
			p.printf(s + " " +  n + " countries processed.%n");
		}
		p.close();
	}
	
	/**
	 * more status messages
	 * @param s
	 * @throws IOException
	 */
	public void writeStatus(String s) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("Log.txt");
		FileWriter write = new FileWriter(file, append);
		PrintWriter p = new PrintWriter(write);
		p.printf(s + "%n");		
		p.close();
		
	}	
	}
