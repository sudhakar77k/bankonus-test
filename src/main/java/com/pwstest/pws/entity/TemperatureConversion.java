package com.pwstest.pws.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author Sudhakar
 */

@AllArgsConstructor
@NoArgsConstructor
//@XmlRootElement(name="TemperatureConversion",namespace = "http://q88.com/webservices/ConvertTemperature")
@XmlRootElement(name = "TemperatureConversion")
@XmlAccessorType(XmlAccessType.FIELD)
public class TemperatureConversion implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "Celsius")
	private String Celsius;

	@XmlElement(name = "Fahrenheit")
	private String Fahrenheit;

	public String getCelsius() {
		return Celsius;
	}

	public void setCelsius(String celsius) {
		Celsius = celsius;
	}

	public String getFahrenheit() {
		return Fahrenheit;
	}

	public void setFahrenheit(String fahrenheit) {
		Fahrenheit = fahrenheit;
	}

}
