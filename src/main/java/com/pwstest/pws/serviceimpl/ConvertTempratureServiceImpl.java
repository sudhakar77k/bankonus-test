package com.pwstest.pws.serviceimpl;

import java.io.StringReader;
import java.util.concurrent.CompletableFuture;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pwstest.pws.entity.TemperatureConversion;
import com.pwstest.pws.service.ConvertTempratureService;

/**
 * @author Sudhakar.k
 *
 */
@Service
public class ConvertTempratureServiceImpl implements ConvertTempratureService {

	@Override
	 @Async("asyncExecutor")
	public CompletableFuture<TemperatureConversion> convertTemprature(String type, Double value) throws InterruptedException {
		
		 final String uri = "http://www.q88.com/WS/Q88WSInternal.asmx/ConvertTemperature?property=";
		 
		 TemperatureConversion temperatureConversion = null;
		    RestTemplate restTemplate = new RestTemplate();
		    String result = restTemplate.getForObject(uri+type+"&val="+value, String.class);
		     
		    JAXBContext jaxbContext;
		    try
		    {
		    	jaxbContext = JAXBContext.newInstance( TemperatureConversion.class );
		        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();	        
		        
		        result = result.replace("xmlns=\"urn:TemperatureConversion\"", "");
		        
		         temperatureConversion =(TemperatureConversion) unmarshaller.unmarshal( new StreamSource( new StringReader( result.toString() ) ) );
		      

		    }
		    catch (  JAXBException e) 
		    {
		        e.printStackTrace();
		    }
	   // Thread.sleep(500);
		return  CompletableFuture.completedFuture(temperatureConversion);
	}

}
