package cotw3;

import java.io.*;
import java.util.Scanner;

public class UI {

	//declare variables
	private String filePath;
	private File file;
	private Scanner cFile;
	private String line;
	private String command;
	private String restOfLine;
	private boolean append = true;
	private boolean done;
	
	//*******************************************************************

	/**
	 * UI constructor for file
	 * @param transDataSuffix
	 * @throws FileNotFoundException
	 */
	public UI(String transDataSuffix) throws FileNotFoundException {

		filePath = "A3TransData" + transDataSuffix + ".txt";

		file = new File(filePath);
		cFile = new Scanner(file);

	}

	//*********************************************************************
	/**
	 * process the transaction data
	 * @returns the type of process for userApp to handle
	 */
	public String processTrans() {
		if (cFile.hasNextLine()) {
			done = false;
			line = cFile.nextLine();

			if (line.length() == 2) {
				command = line;
			} else {
				command = line.substring(0, 2);
				restOfLine = line.substring(3, line.length());
			}
		} else {
			done = true;
		}
		return command;

	}

	/**
	 * done yet?
	 * @return
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * anything extra to return
	 * @return
	 */
	public String getRestOfLine() {
		return restOfLine;
	}
	
	/**
	 * write method when there's just a string to write
	 * @param s
	 * @throws IOException
	 */
	public void writeToLog(String s) throws IOException {
		File file = new File("Log.txt");
		FileWriter write = new FileWriter(file, append);
		PrintWriter p = new PrintWriter(write);

		p.printf(s + "%n");
		p.close();
	}

	/**
	 * close up the trans file
	 */
	public void finishUp() {
		// TODO Auto-generated method stub
		cFile.close();
	}

	/**
	 * write method when the transaction has restofline
	 * @param s
	 * @param s2
	 * @throws IOException
	 */
	public void writeToLog(String s, String s2) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("Log.txt");
		FileWriter write = new FileWriter(file, append);
		PrintWriter p = new PrintWriter(write);

		p.printf(s + " " + s2 + "%n");
		p.close();
	}

}

