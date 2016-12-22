/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import java.io.IOException;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpsRequest {
	private boolean hasInit = false;

	private int socketTimeout = 10000;

	private int connectTimeout = 30000;
	private RequestConfig requestConfig;
	private CloseableHttpClient httpClient;

	public HttpsRequest() throws UnrecoverableKeyException,
			KeyManagementException, NoSuchAlgorithmException,
			KeyStoreException, IOException {
		init();
	}

	private void init() throws IOException, KeyStoreException,
			UnrecoverableKeyException, NoSuchAlgorithmException,
			KeyManagementException {
	}

	public String sendPost(String url, String postDataXML) throws IOException,
			KeyStoreException, UnrecoverableKeyException,
			NoSuchAlgorithmException, KeyManagementException {
		if (!(this.hasInit)) {
			init();
		}

		String result = null;

		HttpPost httpPost = new HttpPost(url);

		StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);

		httpPost.setConfig(this.requestConfig);

		System.out.println("executing request" + httpPost.getRequestLine());
		try {
			HttpResponse response = this.httpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();

			result = EntityUtils.toString(entity, "UTF-8");
		} catch (ConnectionPoolTimeoutException e) {
			System.out
					.println("http get throw ConnectionPoolTimeoutException(wait time out)");
		} catch (ConnectTimeoutException e) {
			System.out.println("http get throw ConnectTimeoutException");
		} catch (SocketTimeoutException e) {
			System.out.println("http get throw SocketTimeoutException");
		} catch (Exception e) {
			System.out.println("http get throw Exception");
		} finally {
			httpPost.abort();
		}

		return result;
	}

	public void setSocketTimeout(int socketTimeout) {
		socketTimeout = socketTimeout;
		resetRequestConfig();
	}

	public void setConnectTimeout(int connectTimeout) {
		connectTimeout = connectTimeout;
		resetRequestConfig();
	}

	private void resetRequestConfig() {
		this.requestConfig = RequestConfig.custom()
				.setSocketTimeout(this.socketTimeout)
				.setConnectTimeout(this.connectTimeout).build();
	}

	public void setRequestConfig(RequestConfig requestConfig) {
		requestConfig = requestConfig;
	}
}