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

public class GoogleQuery

{

	public String searchKeyword;

	public String url;

	public String content;

	public GoogleQuery(String searchKeyword)

	{
		/*
		 * try{ this.searchKeyword = new
		 * String(searchKeyword.getBytes("UTF-8"),"UTF-8"); }catch (Exception ignored) {
		 * // Ignore it }//轉換中文編碼為utf-8
		 */
		this.searchKeyword = searchKeyword;

		this.url = "http://www.google.com/search?q=" + searchKeyword + "&oe=utf8&num=20";

	}

	public String fetchContent() throws IOException

	{
		String retVal = "";

		URL u = new URL(url);

		URLConnection conn = u.openConnection();

		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");

		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");

		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while ((line = bufReader.readLine()) != null) {
			retVal += line;

		}
		return retVal;
	}

	// new
	public String fetchContent1(String url) throws IOException {

		/*
		 * URL u = new URL(url); URLConnection conn = u.openConnection(); InputStream in
		 * = conn.getInputStream(); BufferedReader br = new BufferedReader(new
		 * InputStreamReader(in,"UTF-8")); //使用UTF-8讀取網頁內容 String retVal = "";
		 * 
		 * String line = null;
		 * 
		 * while ((line = br.readLine()) != null){ retVal = retVal + line + "\n"; }
		 * 
		 * return retVal;
		 */
		String retVal = "";

		URL u = new URL(url);

		URLConnection conn = u.openConnection();

		// conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");

		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");

		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while ((line = bufReader.readLine()) != null) {
			retVal += line;

		}
		return retVal;

	}

	public HashMap<String, String> query() throws IOException

	{

		if (content == null)

		{

			content = fetchContent();

		}

		HashMap<String, String> retVal = new HashMap<String, String>();

		Document doc = Jsoup.parse(content);
		//System.out.println(doc.text());
		Elements lis = doc.select("div");
		// System.out.println(lis);
		lis = lis.select(".kCrYT");
		// System.out.println(lis.size());

		for (Element li : lis) {
			try

			{
				String citeUrl = li.select("a").get(0).attr("href");
				String title = li.select("a").get(0).select(".vvjwJb").text();
				//System.out.println(title + "," + citeUrl);
				retVal.put(title, citeUrl);

			} catch (IndexOutOfBoundsException e) {

				// e.printStackTrace();

			}

		}
		//content=null;
		return retVal;

	}

	// new
	public HashMap<String, String> pagequery(String url) throws IOException

	{	
		System.out.println("//////***********************/////");
		System.out.println(content+"\n");
		//if (content == null)

		//{

			content = fetchContent1(url);
			
		//}
		System.out.println(content+"\n");
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
					//System.out.println(title + "," + citeUrl);
					if (citeUrl.startsWith("http")) {
						retVal.put(title, citeUrl);
					}
					num++;
				}

			} catch (IndexOutOfBoundsException e) {

				// e.printStackTrace();

			}

		}
		//content=null;
		return retVal;

	}

}