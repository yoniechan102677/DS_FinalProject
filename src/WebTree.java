
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.net.URLDecoder;

public class WebTree {
	public WebNode root;
	public GoogleQuery gq;
	public WebpageQuery wq;

	public WebTree(WebPage rootPage, String keyword) {
		this.root = new WebNode(rootPage);
		this.gq = new GoogleQuery(keyword);
		this.wq = new WebpageQuery();
	}

	public void setPostOrderScore() throws IOException {
		setPostOrderScore(root, root.webPage.keywords);
	}

	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException {
		// 4. compute the score of children nodes postorder
		for (WebNode child : startNode.children) {
			setPostOrderScore(child, keywords);
			child.setNodeScore(keywords);
		}
		// 5.setNode score of startNode
		startNode.setNodeScore(keywords);
	}

	public void eularPrintTree() {
		eularPrintTree(root);
	}

	private void eularPrintTree(WebNode startNode) {
		int nodeDepth = startNode.getDepth();

		if (nodeDepth > 1)
			System.out.print("\n" + repeat("\t", nodeDepth - 1));
		// print "("
		System.out.print("(");
		// print "name","score"
		System.out.print(startNode.webPage.name + "," + startNode.nodeScore);
		// System.out.print(startNode.webPage.name + "," + startNode.webPage.url);//test
		// 7.print child preorder
		for (WebNode child : startNode.children) {
			eularPrintTree(child);
		}

		// print ")"
		System.out.print(")");

		/*
		 * for example (Publication,286.2)
		 */
		if (startNode.isTheLastChild())
			System.out.print("\n" + repeat("\t", nodeDepth - 2));

	}

	private String repeat(String str, int repeat) {
		String retVal = "";
		for (int i = 0; i < repeat; i++) {
			retVal += str;
		}
		return retVal;
	}

	public void search(String search) throws IOException {
		HashMap<String, String> page = gq.query();
		Set<String> keys = page.keySet();
		// System.out.println(search);
		for (String key : keys) {
			// System.out.println(key);
			String url = page.get(key);
			if (url.startsWith("/url?q=")) {
				int last = url.indexOf("&sa=");
				url = url.substring(7, last);
			} // get url
				// System.out.println(url);// test
			if (!key.equals("")) {
				WebNode subpage = new WebNode(new WebPage(url, key));
				System.out.println("node--" + subpage.webPage.name + ": " + subpage.webPage.url);
				this.root.addChild(subpage);
			}
		}

	}

	public void searchpage(WebNode wn) throws IOException {
			for (WebNode n : wn.children) {
			System.out.println("node------" + n.webPage.name + ": " + n.webPage.url);
			// System.out.println(gq.pagequery(n.webPage.url));
			HashMap<String, String> page1 = wq.pagequery(n.webPage.url);
			// System.out.println(page1);
			Set<String> keys1 = page1.keySet();
			for (String key : keys1) {
				WebNode subpage = new WebNode(new WebPage(page1.get(key), key));
				 System.out.println(" sub--" + subpage.webPage.name + ": " +subpage.webPage.url);
				n.addChild(subpage);
			}
		}
	}

	public void buildTree(String search) throws IOException {
		search(search);
		searchpage(this.root);
	}
}
