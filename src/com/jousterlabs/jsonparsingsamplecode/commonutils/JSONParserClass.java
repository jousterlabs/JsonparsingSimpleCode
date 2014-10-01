package com.jousterlabs.jsonparsingsamplecode.commonutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParserClass {
	static InputStream is = null;
	static String json = "";
	JSONObject obj;

	public JSONParserClass() {

	}

	public JSONObject getJSONFromUrl(String url,
			ArrayList<NameValuePair> postParameter) {
		JSONObject jObj = null;
		HttpParams httpParams = new BasicHttpParams();
		int some_reasonable_timeout = 30000;
		HttpConnectionParams.setConnectionTimeout(httpParams,
				some_reasonable_timeout);
		HttpConnectionParams.setSoTimeout(httpParams, some_reasonable_timeout);
		// HttpClient client = new DefaultHttpClient(httpParams);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpPost httpPost = new HttpPost(url);
		HttpEntity entity=httpPost.getEntity();

		try {
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			StatusLine status = httpResponse.getStatusLine();
			if (status.getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}

			if (is != null) {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				json = sb.toString();
				jObj = new JSONObject(json);

			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jObj;

	}

}
