import java.io.IOException;
import java.util.ArrayList;
//幫每個node計算自己的網頁分數
public class WebPage {
	public String url;
	public String name;
	public WordCounter counter;
	public double score;
	
	public WebPage(String url,String name){
		this.url = url;
		this.name = name;
		this.counter = new WordCounter(url);	
	}
	
	public void setScore(ArrayList<Keyword> keywords) throws IOException{
		score = 0;
		for(Keyword k : keywords){			
			score += counter.countKeyword(k.name)* k.weight;	
		}
	}
	
}
