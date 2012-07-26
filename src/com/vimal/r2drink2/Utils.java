package com.vimal.r2drink2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

public class Utils 
{
	//public static String BASE_URL = "http://10.0.2.2/";
	public static String BASE_URL = "http://192.168.1.20/";
	
    public static String getString(String url)
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url); 
        HttpResponse response;
        try 
        {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                instream.close();
                return result;
            }
        } 
        catch (Exception e) 
        {
			e.printStackTrace();
        }
    	return "";        	
    }

	private static String convertStreamToString(InputStream is) 
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try 
		{
			while ((line = reader.readLine()) != null) 
			{
				sb.append(line + "\n");
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				is.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

    public static String postString(String url, String body)
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url); 
        
        HttpResponse response;
        try 
        {
            StringEntity se = new StringEntity(body,HTTP.UTF_8);
            httppost.setEntity(se);
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                instream.close();
                return result;
            }
        } 
        catch (Exception e) 
        {
			e.printStackTrace();
        }
    	return "";        	
    }

	
}
