import java.util.Scanner;



public class TestMain {
	

	public static void main(String[] args) {

		// TODO Auto-generated method stub

		/*
		 * String url = "https://www.nccu.edu.tw/"; WebPage test = new WebPage(url,
		 * "test"); try { test.count(); } catch (Exception ignored) { //Ignore it }
		 * System.out.print(test.getScore());
		 */
		WebTree t = new WebTree(new WebPage("google.com", "google"), "¥b¬O");
		// Scanner s = new Scanner(System.in);
		// System.out.println("ï¿½Ð¿ï¿½Jï¿½ï¿½ï¿½ï¿½rï¿½G");
		// String query = s.nextLine();

		try {
			t.buildTree("¥b¬O");
		}

		catch (Exception ignored) { // Ignore it

		}
		try {
			//t.setPostOrderScore();
		} catch (Exception ignored) {
		}
		t.eularPrintTree();

		/*
		 * try { t.gq.query();
		 * 
		 * } catch (Exception ignored) { // Ignore it
		 * 
		 * }
		 */
		
		 /* String url ="https://www.nccu.edu.tw/";
		  try { System.out.println(t.gq.fetchContent1(url));
		  System.out.println(t.gq.pagequery(url)); } // System.out.println(newGoogleQuery("NCCU").query()); }
		  catch (Exception ignored) { // Ignore it
		  
		 }
		 
		/*
		 * String url2 =
		 * "https://www.natureindex.com/institution-outputs/taiwan/national-taiwan-university-ntu/513906bb34d6b65e6a000127";
		 * try { System.out.println(t.gq.fetchContent1(url2));
		 * System.out.println(t.gq.pagequery(url2)); } // System.out.println(new
		 * GoogleQuery("NCCU").query()); } catch (Exception ignored) { // Ignore it
		 * 
		 * }
		 */

	}

}
