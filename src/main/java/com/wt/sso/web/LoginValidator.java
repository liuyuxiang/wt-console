package com.wt.sso.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.hirisun.hea.api.domain.AuthenticationProfile;

public class LoginValidator
{

	public boolean validate(AuthenticationProfile profile, SSOUserInfo userinfo)
	{
		boolean loginable = false;
		HttpClient httpclient = new DefaultHttpClient();

		HttpUriRequest httpUriRequest = null;
		
		if (StringUtils.equalsIgnoreCase(profile.getSubmitMethod(), "post")) {
			HttpPost hPost = new HttpPost(profile.getActionUrlServerSide());
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair(profile.getInputNameUserid(), userinfo.getUsername()));
			nvps.add(new BasicNameValuePair(profile.getInputNamePassword(), userinfo.getPassword()));
			try {
				hPost.setEntity(new UrlEncodedFormEntity(nvps, profile.getCharset()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			httpUriRequest = hPost;
		} else {

			HttpGet hGet = new HttpGet(getAppURL(profile, userinfo));

			httpUriRequest = hGet;
		}

		HttpResponse response;
		try {
			String res = null;

			response = httpclient.execute(httpUriRequest);

			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				Header locationHeader = response.getFirstHeader("location");
				if (locationHeader != null) {
					String location = locationHeader.getValue();

					HttpEntity entity = response.getEntity();
					EntityUtils.consume(entity);

					if (location != null) {
						HttpGet hget = new HttpGet(location);

						response = httpclient.execute(hget);
						res = responseHandler.handleResponse(response);
					}
				}

			} else if (statusCode == HttpServletResponse.SC_OK) {

				res = responseHandler.handleResponse(response);
			}
			if (res != null) {
				if (!(res.indexOf(profile.getErrorKeyword()) > -1)) {
					loginable = true;
				}
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//httpclient.getConnectionManager().shutdown();

		}
		return loginable;
	}

	public String getAppURL(AuthenticationProfile profile, SSOUserInfo userinfo)
	{
		String params = null;
		try {
			params = profile.getInputNameUserid() + "="
					+ URLEncoder.encode(userinfo.getUsername(), profile.getCharset()) + "&"
					+ profile.getInputNamePassword() + "="
					+ URLEncoder.encode(userinfo.getPassword(), profile.getCharset()) + "&";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (profile.getActionUrlServerSide().indexOf("?") > -1) {
			params = "&" + params;
		} else {
			params = "?" + params;
		}

		String url = profile.getActionUrlServerSide()+ params;
		return url;
	}
}
