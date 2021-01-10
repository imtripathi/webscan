package com.example.webscan.utility;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
@Service
public class WebscanLinkUtility {
	
	
	public  Map<String, String> findLink(Map<String, String> link, String url) {
		Document doc=null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements resultLinks = doc.select("a[href]");
		System.out.println("number of links: " + resultLinks.size());
		
		
		for (Element links : resultLinks) {
		 
		    String href = links.attr("href");
		    //System.out.println("Title: " + link.text());
		    //System.out.println("Url: " + href);
		    link.put(links.text(), href);
		}
		return link;
		
	}

	
	

}
