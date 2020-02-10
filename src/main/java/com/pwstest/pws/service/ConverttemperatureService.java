package com.pwstest.pws.service;

import java.util.concurrent.CompletableFuture;

import com.pwstest.pws.entity.TemperatureConversion;
/**
 * @author Sudhakar.k
 *
 */
public interface ConverttemperatureService {

	public CompletableFuture<TemperatureConversion> converttemperature(String type, Double value)
			throws InterruptedException;

}
