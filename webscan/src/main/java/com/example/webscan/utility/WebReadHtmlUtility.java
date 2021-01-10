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
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;
import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Service
public class WebReadHtmlUtility {
	Logger logger = LogManager.getLogger(WebReadHtmlUtility.class);

	public Map<String, String> findWords(Map<String, Word> countMap, String url, Map<String, Integer> wrd,
			Map<String, Word> countwordMap, SortedMap<String, Integer> wrdpair) throws IOException {
		Document doc = Jsoup.connect(url).get();
		logger.info("scan web page" + url);
		// Get the actual text from the page, excluding the HTML
		String text = doc.body().text();

		logger.info("Analyzing text...");

		// Create BufferedReader so the words can be counted
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));
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
			for (int i = 0; i < words.length - 1; i++) {
				String word1 = new StringBuffer(words[i]).toString().toLowerCase();
				String word2 = new StringBuffer(words[i + 1]).toString().toLowerCase();

				StringBuffer word = new StringBuffer();

				word = word.append(word1).append(" ").append(word2);

				Word wordObj = countwordMap.get(word);
				if (wordObj == null) {
					wordObj = new Word();
					wordObj.word = word.toString();
					wordObj.count = 0;
					countwordMap.put(word.toString(), wordObj);
				}

				wordObj.count++;
				word1 = word2 = null;
			}
		}

		reader.close();

		SortedSet<Word> sortedWords = new TreeSet<Word>(countMap.values());
		SortedSet<Word> sortedpairWords = new TreeSet<Word>(countwordMap.values());
		extractTenword(wrd, sortedWords);
		extractTenword(wrdpair, sortedpairWords);
		return null;
	}

	private void extractTenword(Map<String, Integer> wrd, SortedSet<Word> sortedWords) {
		int i = 0;
		int maxWordsToDisplay = 10;

		String[] wordsToIgnore = { "the", "and", "a" };

		for (Word word : sortedWords) {
			if (i >= maxWordsToDisplay) { // 10 is the number of words you want to show frequency for
				break;
			}

			if (Arrays.asList(wordsToIgnore).contains(word.word)) {
				i++;
				maxWordsToDisplay++;
			} else {
				logger.info(word.count + "\t" + word.word);

				wrd.put(word.word, word.count);

				i++;
			}

		}
	}

	public LinkedHashMap<String, Integer> sortElement(Map<String, Integer> wrd,
			LinkedHashMap<String, Integer> finalword) {

		finalword = wrd.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10)
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		return finalword;

	}

}
