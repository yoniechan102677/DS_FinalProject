import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.net.URL;

import java.net.URLConnection;

import java.util.HashMap;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

import java.io.*;

import java.net.HttpURLConnection;

public class WebpageQuery {

	public String content;
	private int httpOK = HttpURLConnection.HTTP_OK;
	private final int error504 = HttpURLConnection.HTTP_GATEWAY_TIMEOUT;
	private final int error403 = HttpURLConnection.HTTP_FORBIDDEN;
	private final int error500 = HttpURLConnection.HTTP_INTERNAL_ERROR;
	private final int error404 = HttpURLConnection.HTTP_NOT_FOUND;
	private int status;

	public WebpageQuery()

	{/*
		 * URL url = new URL(citeUrl); HttpURLConnection uc = (HttpURLConnection)
		 * url.openConnection(); uc.setConnectTimeout(10000); uc.setReadTimeout(10000);
		 * uc.connect();
		 */
	}

	// new
	public HttpURLConnection getConn(String url) throws IOException {
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setConnectTimeout(1000);
		conn.setReadTimeout(1000);
		conn.connect();
		status = conn.getResponseCode();
		return conn;
	}

	public String fetchContent(String url) throws IOException {
		String retVal = "";
		HttpURLConnection conn=getConn(url);
		//System.out.println(conn);
		System.out.println("3//////***********************/////");
		// conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");
		InputStream in = conn.getInputStream();
		System.out.println("4//////***********************/////");
		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		// System.out.println("5//////***********************/////");
		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;
		while ((line = bufReader.readLine()) != null) {
			retVal += line;
		}
		conn.disconnect();
		return retVal;

	}

	// new
	public HashMap<String, String> pagequery(String url) throws IOException

	{

		// System.out.println("//////***********************/////");
		// System.out.println(content + "\n");
		// System.out.println(fetchContent(url));
		if (content == null)

		{

			content = fetchContent(url);

		}
		System.out.println("\n"+content + "\n");
		HashMap<String, String> retVal = new HashMap<String, String>();

		Document doc = Jsoup.parse(content);
		//System.out.println(doc.text());
		Elements lis = doc.select("div");
		// System.out.println(lis);
		// lis = lis.select(".kCrYT");
		// System.out.println(lis.size());

		for (Element li : lis) {
			try

			{
				int num = 0;
				while (true) {
					String citeUrl = li.select("a").get(num).attr("href");
					if (citeUrl.equals("")) {
						break;
					}
					// String title = li.select("a").get(0).select(".vvjwJb").text();
					String title = li.select("a").get(num).text();
					// System.out.println(title + "," + citeUrl);
					if (!citeUrl.startsWith("http")) {
						break;
					}
					if(status != httpOK) {
						switch(status){
							case error504:
								System.out.println("連線網址逾時!");
								break;
							case error403:
								System.out.println("連線網址禁止!");
								break;
							case error500:
								System.out.println("連線網址錯誤或不存在!");
								break;
							case error404:
								System.out.println("連線網址不存在!");
								break;
							default:
								System.out.println("連線網址發生未知錯誤!");	
						}
						continue;
					}
					retVal.put(title, citeUrl);
					num++;
				}

			} catch (IndexOutOfBoundsException e) {

				// e.printStackTrace();

			}

		}

		return retVal;

	}
}
