package com.github.marcosws.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Map;

public class APIClient {
	
	private final static int TAM_MAX_BUFFER = 10240; // 10Kbytes
	
	private HttpURLConnection httpUrlConnection;
	private URL url;

	public HttpURLConnection getHttpUrlConnection() {
		return httpUrlConnection;
	}
	
	public APIClient url(String strUrl) {
		try {
			url = new URL(strUrl);
			httpUrlConnection = (HttpURLConnection) url.openConnection();
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public APIClient setRequestProperties(Map<String,String> requestProperties) {
		requestProperties.forEach((n,v) -> { httpUrlConnection.setRequestProperty(n,v); });
		return this;
	}
	
	public APIClient get() {
		try {
			httpUrlConnection.setRequestMethod("GET");
		} 
		catch (ProtocolException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public APIClient post() {
		try {
			httpUrlConnection.setRequestMethod("POST");
		} 
		catch (ProtocolException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public APIClient setText(String text) {
       
		try {
			OutputStream outputStream = httpUrlConnection.getOutputStream();
	        outputStream.write(text.getBytes("UTF-8"));
	        outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this;
	}
	
	public APIClient setFile(File file) {
		
		String attachmentName = "file";
	    String crlf = "\r\n";
	    String twoHyphens = "--";
	    String boundary =  "*****";
	   
	    System.out.println(file.getName());
		
	    try {
	    	DataOutputStream request = new DataOutputStream(httpUrlConnection.getOutputStream()); 
		    request.writeBytes(twoHyphens + boundary + crlf);
		    request.writeBytes("Content-Disposition: form-data; name=\"" + attachmentName + "\";filename=\"" + file.getAbsolutePath() + "\"" + crlf);
		    request.writeBytes(crlf);
		    request.write(Files.readAllBytes(file.toPath()));
		    request.writeBytes(crlf);
		    request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
		    request.flush();
		    request.close();
	    }
	    catch (IOException e) {
				e.printStackTrace();
		}
		return this; 

	}

	public APIClient printStatusMessage() {
		
		try {
			System.out.print("URL:      ");
			Message.message("[" + url.getHost() + "]");
			System.out.print("Endpoint: ");
			Message.message("[" + url.getPath() + "]");
			System.out.print("Method:   ");
			Message.message("[" + httpUrlConnection.getRequestMethod() + "]");
			if(httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
				System.out.print("Status:   ");
				Message.messageOK("[" + httpUrlConnection.getResponseCode() + "] - " + httpUrlConnection.getResponseMessage());
			}
			else {
				System.out.print("Status:   ");
				Message.messageNOK("[" + httpUrlConnection.getResponseCode() + "] - " + httpUrlConnection.getResponseMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
    public APIClient setDoOutput(boolean bool) {
    	httpUrlConnection.setDoOutput(bool);
        return this;
    }
   
    public APIClient setDoInput(boolean bool) {
    	httpUrlConnection.setDoInput(bool);
        return this;
    }
	public String getResult() {
		
		try {
			
			if(httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
				
				InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());    
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), TAM_MAX_BUFFER);
			    StringBuilder builder = new StringBuilder();
			    for(String line = null; (line = reader.readLine())!= null;)
			    	builder.append(line).append("\n");
			    return builder.toString();
			    
			}

		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return "";
		
	}
	

}
