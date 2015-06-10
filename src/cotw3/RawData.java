package cotw3;

import java.io.*;
import java.util.Scanner;

public class RawData {

	// declare variables
	private String code;
	private int id;
	private String countryName;
	private String continent;
	private long area;
	private long population;
	private float lifeExp;
	private String[] temp;
	private int lcp;
	private int rcp;

	// *********************************************************************
	/**
	 * constructor
	 */
	public RawData() {
	}

	/**
	 * constructor with content
	 * 
	 * @param code
	 * @param id
	 * @param countryName
	 * @param continent
	 * @param area
	 * @param population
	 * @param lifeExp
	 */
	public RawData(String code, int id, String countryName, String continent,
			long area, long population, float lifeExp) {
		this.code = code;
		this.id = id;
		this.countryName = countryName;
		this.continent = continent;
		this.area = area;
		this.population = population;
		this.lifeExp = lifeExp;
		this.lcp = -1;
		this.rcp = -1;
	}

	// ************************************************************************

	/**
	 * method to read 1 file and put in object
	 * 
	 * @param line
	 * @param rd
	 * @return
	 * @throws IOException
	 */
	public RawData Read1File(String line, RawData rd) throws IOException {
		temp = line.split(",");
		rd = new RawData(temp[0].substring(31, 34), Integer.parseInt(temp[1]),
				temp[2].replace("'", ""), temp[3].replace("'", ""),
				Long.parseLong(temp[5]), Long.parseLong(temp[7]),
				Float.parseFloat(temp[8]));

		return rd;
	}

	/**
	 * closes file
	 * 
	 * @param s
	 */
	public void finishUp(Scanner s) {
		s.close();

	}

	/**
	 * getter method for country code
	 * 
	 * @return code of country
	 */
	public String getCode() {
		return code;
	}

	/**
	 * getter method for country id
	 * 
	 * @return country id
	 */
	public int getID() {
		return id;
	}

	/**
	 * getter method for country name
	 * 
	 * @return country name
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * getter method for continent
	 * 
	 * @return continent name
	 */
	public String getContinent() {
		return continent;
	}

	/**
	 * getter method for area
	 * 
	 * @return area size
	 */
	public long getArea() {
		return area;
	}

	/**
	 * getter method for population
	 * 
	 * @return population size
	 */
	public long getPopulation() {
		return population;
	}

	/**
	 * getter method for life expectancy
	 * 
	 * @return life expectancy
	 */
	public float getLifeExp() {
		return lifeExp;
	}
	/**
	 * setter for left child
	 * @param lcp
	 */
	public void setLCP(int lcp){
		this.lcp = lcp;
	}
	/**
	 * getter for left child
	 * @return
	 */
	public int getLCP(){
		return lcp;
	}
	
	/**
	 * setter for right child
	 * @param rcp
	 */
	public void setRCP(int rcp){
		this.rcp = rcp;
	}
	/**
	 * getter for right child
	 * @return
	 */
	public int getRCP(){
		return rcp;
	}

}

