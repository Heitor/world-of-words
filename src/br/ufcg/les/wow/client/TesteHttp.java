package br.ufcg.les.wow.client;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class TesteHttp {
	private static final String SOAP_ACTION = "WorldOfWordsServerService";
	private static final String ANOTHER_OPERATION_NAME = "getStrings";
	private static final String WSDL_TARGET_NAMESPACE = "http://server.wow.les.ufcg.br/";
	private static final String SOAP_ADDRESS = "http://192.168.0.23:9001/world-of-words";
	
	public void testeHttpConnection() {
		System.out.println("============================== 1");
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, ANOTHER_OPERATION_NAME);
		//request.addProperty("text", "Testing...");
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
		HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		httpTransport.debug = true;
		
		try {
			System.out.println("============================== 2");
		httpTransport.call(SOAP_ACTION, envelope);
		System.out.println("============================== 3");
		//Object response = envelope.getResponse(); wow-server-0.1_lib
		SoapObject soapObject = (SoapObject)envelope.bodyIn;
		//SoapObject soapObject = (SoapObject)envelope.getResponse();
		//System.out.println(response.toString());
		System.out.println("============================== 4");
		int returnedObjects = soapObject.getPropertyCount();
		System.out.println("Count: " +returnedObjects);
		for (int i = 0; i < returnedObjects; i++) {
			String res = soapObject.getProperty(i).toString();
			System.out.println("Property"+ i +": " + res);
		}
		//String res = soapObject.getProperty(0).toString();
		//
		System.out.println("============================== 5");
		 
		//textView.setText(response.toString());
		 
		}catch (Exception exception)
		 
{
//		/57.textView.setText(exception.toString());
}
	}
}
