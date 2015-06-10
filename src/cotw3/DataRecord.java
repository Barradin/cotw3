/****************************************
 * Adam Tracy                           *
 * Countries of the World Assignment 3  *
 * Setup                                *
 * Handles the more in depth stuff      *
 * of managing the data records and     *
 * reading from the files               *
 ***************************************/

package cotw3;

import java.io.*;
import java.text.NumberFormat;

public class DataRecord {

	// declare stuff
	private static RandomAccessFile bWriter;
	private static RandomAccessFile bReader;
	private final int HEADER_REC = 2 + 2 + 2;
	private final int REC_SIZE = 82;
	private final short MAX_N_HOME_LOC = 20;
	private short nHome = 0;
	private short nColl = 0;
	private short tempLink;

	// ****************************************************
	/**
	 * constructor
	 * 
	 * @throws FileNotFoundException
	 */
	public DataRecord() throws FileNotFoundException {
		bWriter = new RandomAccessFile(new File("CountryData.bin"), "rws");
		bReader = new RandomAccessFile(new File("CountryData.bin"), "r");

	}

	// *******************************************************************

	/**
	 * write the data record to the .bin file.  Try to put in home address
	 * if you can't, insert into overflow.
	 * @param codeA
	 * @param idA
	 * @param countryNameA
	 * @param continentA
	 * @param areaA
	 * @param populationA
	 * @param lifeExpA
	 * @param link
	 * @param RRN
	 * @throws IOException
	 */
	public void write1Record(char[] codeA, short idA, char[] countryNameA,
			char[] continentA, int areaA, long populationA, float lifeExpA,
			short link, int RRN) throws IOException {
		// TODO Auto-generated method stub
		int offSet = ((RRN - 1) * REC_SIZE) + HEADER_REC;
		boolean valid = readARec(offSet);
		if (valid) {
			bWriter.seek(offSet);
			for (int i = 0; i < 3; i++) {
				bWriter.writeChar(codeA[i]);
			}
			bWriter.writeShort(idA);
			for (int i = 0; i < 15; i++) {
				bWriter.writeChar(countryNameA[i]);
			}
			for (int i = 0; i < 13; i++) {
				bWriter.writeChar(continentA[i]);
			}
			bWriter.writeInt(areaA);
			bWriter.writeLong(populationA);
			bWriter.writeFloat(lifeExpA);
			bWriter.writeShort(link);
			nHome++;
			writeToHeader();
		} else {
			tempLink = getTL();
			offSet = ((MAX_N_HOME_LOC + nColl) * REC_SIZE) + HEADER_REC;
			bWriter.seek(offSet);
			for (int i = 0; i < 3; i++) {
				bWriter.writeChar(codeA[i]);
			}
			bWriter.writeShort(idA);
			for (int i = 0; i < 15; i++) {
				bWriter.writeChar(countryNameA[i]);
			}
			for (int i = 0; i < 13; i++) {
				bWriter.writeChar(continentA[i]);
			}
			bWriter.writeInt(areaA);
			bWriter.writeLong(populationA);
			bWriter.writeFloat(lifeExpA);
			bWriter.writeShort(tempLink);
			nColl++;
			writeToHeader();
		}
	}

	/**
	 * checks a record to make sure its good
	 * write record if null or 0 bits
	 * else grab the link/HP and move it
	 * @param offSet
	 * @return
	 * @throws IOException
	 */
	private boolean readARec(int offSet) throws IOException {
		// TODO Auto-generated method stub
		bReader.seek(offSet);
		try {
			if (bReader.readInt() == 0) {
				return true;
			} else {
				bReader.seek(offSet + 80);
				tempLink = bReader.readShort();
				bWriter.seek(offSet + 80);
				short temp = (short) (MAX_N_HOME_LOC + nColl + 1);
				bWriter.writeShort(temp);
				return false;
			}
		} catch (Exception eof) {
			return true;
		}
	}

	/**
	 * search the hashFile for a matching tran
	 * if no match, travel down the syn family until found or -1
	 * @param RRN
	 * @param otherTran
	 * @return
	 * @throws IOException
	 */
	public boolean searchHash(int RRN, String otherTran) throws IOException {
		// TODO Auto-generated method stub

		int offSet = ((RRN - 1) * REC_SIZE) + HEADER_REC;
		bReader.seek(offSet);
		char[] tempCode = new char[3];
		for (int i = 0; i < 3; i++) {
			tempCode[i] = bReader.readChar();
		}
		String tempS = new String(tempCode);
		if (tempS.equalsIgnoreCase(otherTran)) {
			bReader.seek(offSet);
			printIt();
			return true;
		}
		else {
			while (tempLink != -1) {
				bReader.seek(offSet + 80);
				tempLink = bReader.readShort();
				if (tempLink != -1) {
					offSet = ((tempLink - 1) * REC_SIZE) + HEADER_REC;
					bReader.seek(offSet);
					for (int i = 0; i < 3; i++) {
						tempCode[i] = bReader.readChar();
					}
					tempS = new String(tempCode);
					if (tempS.equalsIgnoreCase(otherTran)) {
						bReader.seek(offSet);
						printIt();
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * if a match was found.  Print it out in a nice looking format
	 * *************Code borrowed from Joshua White's PPU**********
	 * @throws IOException
	 */
	private void printIt() throws IOException {
		// TODO Auto-generated method stub
		File file = new File("Log.txt");
		FileWriter write = new FileWriter(file, true);
		PrintWriter p = new PrintWriter(write);
		NumberFormat nf = NumberFormat.getInstance();
		char[] code = new char[3];
		for (int i = 0; i < 3; i++)
			code[i] = bReader.readChar();
		
	 	// Read country ID
		short id = bReader.readShort();
		
	 	// Read name
		char[] cName = new char[15];
		for (int i = 0; i < 15; i++)
			cName[i] = bReader.readChar();
		
	 	// Read continent
		char[] continent = new char[13];
		for (int i = 0; i < 13; i++)
			continent[i] = bReader.readChar();
		
	 	// Read area, population, life expectancy, and link
		int area = bReader.readInt();
		long population = bReader.readLong();
		float lifeExp = bReader.readFloat();
		p.printf("%3s %03d %-15.15s %-13.13s"
				+ " %10s %13s %4.1f%n", 
				new String(code), id, 
				new String(cName), new String(continent), 
				nf.format(area), nf.format(population),
				lifeExp);
		p.close();
	}

	/**
	 * get the link
	 * @return
	 */
	public short getTL() {
		return tempLink;
	}

	/**
	 * write information to the header of the file
	 * @throws IOException
	 */
	public void writeToHeader() throws IOException {
		bWriter.seek(0);
		bWriter.writeShort(MAX_N_HOME_LOC);
		bWriter.writeShort(nHome);
		bWriter.writeShort(nColl);
	}

	/**
	 * close file
	 * 
	 * @throws IOException
	 */
	public void closeFile() throws IOException {
		bWriter.close();
		bReader.close();
	}

}
