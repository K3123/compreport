/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author k3123
 */
package weatherData;

public class weatherClass {

	private String cityName;
	private String cityHi;
	private String cityLow;
	private String cityDescription;
	private String cityIcon;
	
	
        weatherClass() {
		// TODO Auto-generated constructor stub
	}

	
        weatherClass( String cityName, String cityHi, String cityLow, String cityDescription, String cityIcon) {
		this.cityName = cityName;
		this.cityHi = cityHi;
		this.cityLow = cityLow;
		this.cityDescription = cityDescription;
		this.cityIcon = cityIcon;
	}
	
	

	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityHi() {
		return cityHi;
	}
	public void setCityHi(String cityHi) {
		this.cityHi = cityHi;
	}
	public String getCityLow() {
		return cityLow;
	}
	public void setCityLow(String cityLow) {
		this.cityLow = cityLow;
	}
	public String getCityDescription() {
		return cityDescription;
	}
	public void setCityDescription(String cityDescription) {
		this.cityDescription = cityDescription;
	}
	public String getCityIcon() {
		return cityIcon;
	}
	public void setCityIcon(String cityIcon) {
		this.cityIcon = cityIcon;
	}

	@Override
	public String toString() {
		return "weatherClass [cityName=" + cityName + ", cityHi=" + cityHi + ", cityLow=" + cityLow
				+ ", cityDescription=" + cityDescription + ", cityIcon=" + cityIcon + "]";
	}
	
	
	
}
