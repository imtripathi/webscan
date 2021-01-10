package com.example.webscan.utility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;


@Service
public class WebReadHtmlUtility {
	public   Map<String, String> findWords( Map<String, Word> countMap,String url, Map<String, Integer> wrd) throws IOException {
		Document doc = Jsoup.connect(url).get();

		//Get the actual text from the page, excluding the HTML
		String text = doc.body().text();
		

		System.out.println("Analyzing text...");
		//Create BufferedReader so the words can be counted
		BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));
		String line;
		while ((line = reader.readLine()) != null) {
		    String[] words = line.split("[^A-ZÃƒâ€¦Ãƒâ€žÃƒâ€“a-zÃƒÂ¥ÃƒÂ¤ÃƒÂ¶]+");
		    for (String word : words) {
		        if ("".equals(word)) {
		            continue;
		        }

		        Word wordObj = countMap.get(word);
		        if (wordObj == null) {
		            wordObj = new Word();
		            wordObj.word = word;
		            wordObj.count = 0;
		            countMap.put(word, wordObj);
		        }

		        wordObj.count++;
		    }
		}

		reader.close();

		SortedSet<Word> sortedWords = new TreeSet<Word>(countMap.values());
		int i = 0;
		int maxWordsToDisplay = 10;

		String[] wordsToIgnore = {"the", "and", "a"};

		for (Word word : sortedWords) {
		    if (i >= maxWordsToDisplay) { //10 is the number of words you want to show frequency for
		        break;
		    }

		    if (Arrays.asList(wordsToIgnore).contains(word.word)) {
		        i++;
		        maxWordsToDisplay++;
		    } else {
		        System.out.println(word.count + "\t" + word.word);
		       
		        wrd.put(word.word, word.count);
		       
		        i++;
		    }

		}
		
			return null;
	}

	public LinkedHashMap<String, Integer> sortElement(Map<String,Integer> wrd,LinkedHashMap<String, Integer> finalword) {
		
		finalword = wrd .entrySet() .stream() .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10) .collect( toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		return finalword;
		
	}
	


}
