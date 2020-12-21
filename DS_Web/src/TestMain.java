
public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "https://www.books.com.tw/web/books_topm_01";
		WebPage test = new WebPage(url, "test");
		try {
			test.count();
		} catch (Exception ignored) {
			// Ignore it
		}
		System.out.print(test.getScore());
	}

}
