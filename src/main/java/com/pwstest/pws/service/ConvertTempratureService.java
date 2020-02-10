package com.pwstest.pws.service;

import java.util.concurrent.CompletableFuture;

import com.pwstest.pws.entity.TemperatureConversion;
/**
 * @author Sudhakar.k
 *
 */
public interface ConvertTempratureService {

	public CompletableFuture<TemperatureConversion> convertTemprature(String type, Double value)
			throws InterruptedException;

}
