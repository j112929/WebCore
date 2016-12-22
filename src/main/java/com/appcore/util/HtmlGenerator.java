/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HtmlGenerator {
	HttpClient httpClient = null;
	GetMethod getMethod = null;
	BufferedWriter fw = null;
	String page = null;
	String webappname = null;
	BufferedReader br = null;
	InputStream in = null;
	StringBuffer sb = null;
	String line = null;

	public HtmlGenerator(String webappname) {
		this.webappname = webappname;
	}

	private boolean createHtmlPage(String url, String htmlFileName) {
		boolean status = false;
		int statusCode = 0;
		try {
			this.httpClient = new HttpClient();

			this.httpClient.getParams().setParameter(
					"http.protocol.content-charset", "UTF-8");

			this.getMethod = new GetMethod(url);

			this.getMethod.getParams().setParameter(
					"http.method.retry-handler",
					new DefaultHttpMethodRetryHandler());

			this.getMethod.addRequestHeader("Content-Type",
					"text/html;charset=UTF-8");

			statusCode = this.httpClient.executeMethod(this.getMethod);
			if (statusCode != 200) {
				System.out.println("静态页面引擎在解析" + url + "产生静态页面" + htmlFileName
						+ "时出错!");
			} else {
				this.sb = new StringBuffer();
				this.in = this.getMethod.getResponseBodyAsStream();

				this.br = new BufferedReader(new InputStreamReader(this.in,
						"UTF-8"));
				while ((this.line = this.br.readLine()) != null) {
					this.sb.append(this.line + "\n");
				}
				if (this.br != null)
					this.br.close();
				this.page = this.sb.toString();

				this.page = formatPage(this.page);

				writeHtml(htmlFileName, this.page);
				status = true;
			}
		} catch (Exception ex) {
			System.out.println("静态页面引擎在解析" + url + "产生静态页面" + htmlFileName
					+ "时出错:" + ex.getMessage());
		} finally {
			this.getMethod.releaseConnection();
		}
		return status;
	}

	private synchronized void writeHtml(String htmlFileName, String content)
			throws Exception {
		this.fw = new BufferedWriter(new FileWriter(htmlFileName));
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(
				htmlFileName), "UTF-8");
		fw.write(this.page);
		if (fw == null)
			return;
		fw.close();
	}

	private String formatPage(String page) {
		page = page.replaceAll("\\.\\./\\.\\./\\.\\./", this.webappname + "/");
		page = page.replaceAll("\\.\\./\\.\\./", this.webappname + "/");
		page = page.replaceAll("\\.\\./", this.webappname + "/");
		return page;
	}

	public static boolean generatorHtml(String webappname, String url,
			String htmlFileName) {
		HtmlGenerator h = new HtmlGenerator(webappname);
		boolean isSuccess = h.createHtmlPage(url, htmlFileName);
		return isSuccess;
	}

	public static void main(String[] args) {
		boolean isSuccess = generatorHtml(
				"webappname",
				"http://im.app987.com:8001/nodo/front/goodsContent/detail?skuId=376",
				"D:/yifu22.html");
		if (isSuccess)
			System.out.println("静态页面生成成功！");
	}
}