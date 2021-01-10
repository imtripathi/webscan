package com.example.webscan.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webscan.service.WebScanService;
import com.example.webscan.utility.WebReadHtmlUtility;
import com.example.webscan.utility.WebscanLinkUtility;
import com.example.webscan.utility.Word;

@Service
public class WebScanServiceImpl implements WebScanService {

	@Autowired
	private WebReadHtmlUtility webReadHtmlUtility;
	@Autowired
	private WebscanLinkUtility webscanLinkUtility;

	@Override
	public String getWords(String url) {
		Map<String, Word> countMap = new HashMap<String, Word>();
		Map<String, String> link = new HashMap<String, String>();
		SortedMap<String, Integer> wrd = new TreeMap<String, Integer>();
		SortedMap<String, Integer> wrdpair = new TreeMap<String, Integer>();
		LinkedHashMap<String, Integer> finalword = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> finalwordpair = new LinkedHashMap<String, Integer>();
		Map<String, Word> countwordMap = new HashMap<String, Word>();

		JSONObject js = new JSONObject();
		try {
			webReadHtmlUtility.findWords(countMap, url, wrd, countwordMap, wrdpair);
			link = webscanLinkUtility.findLink(link, url);
			Stack<String> st = new Stack<String>();

			puttingToStack(url, st, link);

			operationOnStack(st, countMap, wrd, countwordMap, wrdpair);

			js.put("maxwordcount", webReadHtmlUtility.sortElement(wrd, finalword));
			js.put("maxwordpaircount", webReadHtmlUtility.sortElement(wrdpair, finalwordpair));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return js.toString();
	}

	private void puttingToStack(String url, Stack<String> st, Map<String, String> link) {
		for (Map.Entry<String, String> entry : link.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

			if (!entry.getKey().isEmpty() && !entry.getKey().equals("info@314e.com")) {
				if (!entry.getValue().equals("#") && entry.getValue().toString() != url
						&& !entry.getValue().equals("javascript:void(0);")
						&& entry.getValue() != "mailto:info@314e.com") {
					String cUrl = entry.getValue();
					st.push(cUrl);
				}

				// i++;
			}
		}

	}

	private void operationOnStack(Stack<String> st, Map<String, Word> countMap, Map<String, Integer> wrd,
			Map<String, Word> countwordMap, SortedMap<String, Integer> wrdpair) throws IOException {
		while (st != null && !st.isEmpty()) {
			Map<String, String> plink = new HashMap<String, String>();

			Stack<String> cSt = new Stack<String>();
			// if(cSt==null ||cSt.isEmpty()) {
			String url = st.pop();
			System.out.println(url);
			webReadHtmlUtility.findWords(countMap, url, wrd, countwordMap, wrdpair);
			// webscanLinkUtility.findLink(link, url);

		}
	}

}
