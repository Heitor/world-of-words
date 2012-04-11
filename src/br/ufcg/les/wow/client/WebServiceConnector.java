package br.ufcg.les.wow.client;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import br.ufcg.les.wow.adedonha.persistence.AdedonhaDAOImpl;
import br.ufcg.les.wow.adedonha.persistence.WordAdedonhaDAO;
import br.ufcg.les.wow.exceptions.NoInternetAccessException;
import br.ufcg.les.wow.persistence.User;
import br.ufcg.les.wow.persistence.UsersDAOImpl;

/**
 * This should be used to exchange informations with Web Server Server. 
 * 
 * @author heitor@lsd.ufcg.edu.br
 *
 */
public class WebServiceConnector {
	private static final String SOAP_ACTION = "WorldOfWordsServerService";
	private static final String GET_WORD_LIST = "getWordList";
	private static final String GET_BEST_USERS = "getBestUsers";
	private static final String SEND_USER_POINTS = "sendPontuation";
	private static final String TARGET_NAMESPACE = "http://server.wow.les.ufcg.br/";
	//private static final String SERVER_ADDRESS = "http://jaleco.no-ip.org:9000/world-of-words";
	private static final String SERVER_ADDRESS = "http://192.168.0.23:9000/world-of-words";
	private static final int ADEDONHA = 1;
	
	private Context context;

	public WebServiceConnector(Context context) {
		this.context = context;
	}
	
	/**
	 * This method should be user to send the user point to server.
	 * Server must ordenate this according to the pontuation.
	 * 
	 * @param user The user to be added at server.
	 */
	public void sendUser(User user) {
		SoapObject request = new SoapObject(TARGET_NAMESPACE, SEND_USER_POINTS);
		request.addProperty("username", user.getUserName());
		request.addProperty("gametype", user.getGameType());
		request.addProperty("points", user.getPointing());
		request.addProperty("time", user.getTime());
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
		HttpTransportSE httpTransport = new HttpTransportSE(SERVER_ADDRESS);
		httpTransport.debug = true;
		
		try {
						
			httpTransport.call(SOAP_ACTION, envelope);
			SoapObject soapObject = (SoapObject)envelope.bodyIn;
			
			int returnedObjects = soapObject.getPropertyCount();
			System.out.println("Count: " + returnedObjects);
		 
		}catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * This method should be used to update the local database.
	 * 
	 * @throws NoInternetAccessException When there is no internet connection.
	 */
	public void updateDataBase() throws NoInternetAccessException {
		updateUsersDataBase();
		
		// TODO if not updated.
		updateAdedonhaDataBase();
	}
	
	private void updateUsersDataBase() {
		SoapObject request = new SoapObject(TARGET_NAMESPACE, GET_BEST_USERS);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
		HttpTransportSE httpTransport = new HttpTransportSE(SERVER_ADDRESS);
		httpTransport.debug = true;
		
		UsersDAOImpl usersDao = new UsersDAOImpl(this.context);
		try {
			
			usersDao.open();
			usersDao.clear();
			
			httpTransport.call(SOAP_ACTION, envelope);
			SoapObject soapObject = (SoapObject)envelope.bodyIn;
			
			int returnedObjects = soapObject.getPropertyCount();
			System.out.println("Count: " + returnedObjects);
			for (int i = 0; i < returnedObjects; i+=4) {
				// {username, gametype, points, time}
				User newUser = new User();
				
				String tmpVariable = soapObject.getProperty(i).toString();
				newUser.setUserName(tmpVariable);
				
				tmpVariable = soapObject.getProperty(i+1).toString();
				Integer gametype = Integer.valueOf(tmpVariable);
				newUser.setGameType(gametype);
				
				tmpVariable = soapObject.getProperty(i+2).toString();
				Integer points = Integer.valueOf(tmpVariable);
				newUser.setPointing(points);
				
				tmpVariable = soapObject.getProperty(i+3).toString();
				Long time = Long.valueOf(tmpVariable); 
				newUser.setTime(time);
				
				usersDao.inserirObjeto(newUser);
				
				System.out.println(newUser);
			}
		 
		}catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			usersDao.close();
		}
	}
	
	private void updateAdedonhaDataBase() {
		SoapObject request = new SoapObject(TARGET_NAMESPACE, GET_WORD_LIST);
		request.addProperty("gametype", ADEDONHA);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
		HttpTransportSE httpTransport = new HttpTransportSE(SERVER_ADDRESS);
		httpTransport.debug = true;
		AdedonhaDAOImpl adedonhaDao = new AdedonhaDAOImpl(this.context);
		try {
			
			adedonhaDao.open();
			adedonhaDao.clear();// TODO It should not do this in future.
			
			httpTransport.call(SOAP_ACTION, envelope);
			SoapObject soapObject = (SoapObject)envelope.bodyIn;
			
			int returnedObjects = soapObject.getPropertyCount();
			for (int i = 0; i < returnedObjects; i++) {
				String tmpWebAnswer = soapObject.getProperty(i).toString();
				adedonhaDao.inserirObjeto(new WordAdedonhaDAO(tmpWebAnswer));
			}
		 
		}catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			adedonhaDao.close();
		}
	}
}
