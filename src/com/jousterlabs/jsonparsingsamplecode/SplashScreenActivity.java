package com.jousterlabs.jsonparsingsamplecode;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jousterlabs.jsonparsingsamplecode.R;
import com.jousterlabs.jsonparsingsamplecode.commonutils.AlertDialogBoxClass;
import com.jousterlabs.jsonparsingsamplecode.commonutils.CheckInterNetClass;
import com.jousterlabs.jsonparsingsamplecode.commonutils.JSONParserClass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class SplashScreenActivity extends Activity {

	TextView textView_SplashScreenActivity_appName;
	JSONParserClass jsonParserClass;
	JSONObject jsonObject;
	AsynTaskToParseData asynTaskToParseData;
	CheckInterNetClass checkInterNetClass;
	AlertDialogBoxClass alertDialogBoxClass;
	public static final String TAG_NAME = "name";
	public static final String TAG_EMAIL = "email";
	public static final String TAG_GENDER = "gender";
	public static final String TAG_PHONE = "phone";
	public static final String TAG_MOBILE = "mobile";
	private static String url = "http://api.androidhive.info/contacts/";
	public static ArrayList<HashMap<String, String>> arrayList_addData = new ArrayList<HashMap<String, String>>();

	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splashscreenactivity);
		textView_SplashScreenActivity_appName = (TextView) findViewById(R.id.textView_splashscreenactivity_appName);
		checkInterNetClass = new CheckInterNetClass(getApplicationContext());
		alertDialogBoxClass = new AlertDialogBoxClass(SplashScreenActivity.this);

		if (checkInterNetClass.isConnectingToInternet() == true) {

			// ---Start AsynTask---
			asynTaskToParseData = new AsynTaskToParseData();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				asynTaskToParseData.executeOnExecutor(
						AsyncTask.THREAD_POOL_EXECUTOR, "asyn");
			} else {
				asynTaskToParseData.execute("asyn");
			}
			// ---End Call AsynTask---
		} else {
			alertDialogBoxClass.AlertDialogBoxCheck(
					getResources().getString(R.string.app_name),
					"Internet seems to be Disabled Try Again");
		}

	}

	protected class AsynTaskToParseData extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (arrayList_addData != null && arrayList_addData.size() > 0) {
				arrayList_addData.clear();
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			jsonParserClass = new JSONParserClass();
			jsonObject = jsonParserClass.getJSONFromUrl(url, null);

			if (jsonObject != null) {
				try {
					JSONArray jsonArray = jsonObject.getJSONArray("contacts");
					for (int i = 0; i < jsonArray.length(); i++) {
						String string_Name = jsonArray.getJSONObject(i)
								.getString(TAG_NAME);
						if (string_Name.equalsIgnoreCase("Ravi Tamada")) {
							string_Name = "Sudhir Ranjan";
						}
						String string_Email = jsonArray.getJSONObject(i)
								.getString(TAG_EMAIL);
						if (string_Email.equalsIgnoreCase("ravi@gmail.com")) {
							string_Email = "ranjan.sudhir1@gmail.com";
						}
						String string_Gender = jsonArray.getJSONObject(i)
								.getString(TAG_GENDER);
						String string_Phone = jsonArray.getJSONObject(i)
								.getJSONObject(TAG_PHONE).getString(TAG_MOBILE);
						if (string_Phone.equalsIgnoreCase("+91 0000000000")) {
							string_Phone = "+91-7484279900";
						}

						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put(TAG_NAME, string_Name);
						hashMap.put(TAG_EMAIL, string_Email);
						hashMap.put(TAG_GENDER, string_Gender);
						hashMap.put(TAG_MOBILE, string_Phone);
						arrayList_addData.add(hashMap);

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Intent intent_SplashScreenActivity = new Intent(
					SplashScreenActivity.this, MainActivity.class);
			startActivity(intent_SplashScreenActivity);
			finish();

		}

	}
	// ---End AsynTask---
}
