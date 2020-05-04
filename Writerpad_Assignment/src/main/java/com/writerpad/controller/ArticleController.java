package com.writerpad.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.writerpad.domain.Article;
import com.writerpad.dto.TimeToRead;
import com.writerpad.repository.ArticleRepository;
import com.writerpad.wrapper.TimeToReadResponse;

/**
 * <h1>ArticleController</h1>
 * <p>
 * This is the ArticleController class, containing CRUD and other operations
 * with respective HTTP request which communicates with articleRepository to
 * perform the operations in database
 * </p>
 *
 * @author Ravindra Pawar
 */
@RestController
@RequestMapping("/api")
@Validated
public class ArticleController {

	@Autowired
	private ArticleRepository articleRepository;

	public ArticleController(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	/*
	 * Story 1: REST API to create an article
	 */
	/**
	 * <p>
	 * This method is used to create an article from HTTP POST request with a
	 * request body which will pass article object to articleRepository to save it
	 * into the database
	 * </p>
	 *
	 * @param article is the body that is retrieved from a request
	 * @return the article
	 */
	@PostMapping("/articles")
	public ResponseEntity<Article> create(@RequestBody @Valid Article article) {
		return new ResponseEntity<Article>(articleRepository.save(article), HttpStatus.CREATED);
	}

	/*
	 * Story 2: Update an article
	 */
	@SuppressWarnings("unchecked")
	@PutMapping("/update")
	public ResponseEntity<Article> updateArticle(@RequestBody Article article) {
		Optional<Article> existingArticle = articleRepository.findById(article.getArticleId());
		if (existingArticle.isPresent()) {
			articleRepository.save(article);
			return ResponseEntity.ok().build();
		} else
			return (ResponseEntity<Article>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
	}

	/*
	 * Story 3: Get an article
	 */
	@GetMapping("/{id}")
	public Optional<Article> getById(@PathVariable ObjectId id) {
		return articleRepository.findById(id);
	}

	/*
	 * Story 4: List articles with pagination
	 */
	@GetMapping("/all")
	public List<Article> getAllArticle() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Article> allProductsSortedByName = (Page<Article>) articleRepository.findAll(pageable);
		return (List<Article>) allProductsSortedByName.toList();
	}

	/*
	 * Story 5: Delete an article
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Article> deleteById(@PathVariable ObjectId id) {
		Optional<Article> existingArticle = articleRepository.findById(id);
		if (existingArticle.isPresent()) {
			articleRepository.delete(existingArticle.get());
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/*
	 * Story 6: Find time to read for an article
	 */
	@GetMapping("/article/{id}")
	public TimeToReadResponse timetoread(@PathVariable ObjectId id) throws Exception {
		Optional<Article> articleOptional = articleRepository.findById(id);
		if (articleOptional.get() != null) {

			int titleLength = articleOptional.get().getTitle().toString().length();
			int descriptionLength = articleOptional.get().getDescription().toString().length();
			int bodyLength = articleOptional.get().getBody().toString().length();
			int totalwords = titleLength + descriptionLength + bodyLength;

			System.out.println(titleLength);
			System.out.println(bodyLength);
			System.out.println(descriptionLength);
			System.out.println(totalwords);

			double humanspeed = 10;
			int minutes = (int) ((totalwords / humanspeed) > 60 ? Math.floor(totalwords / humanspeed) : 0);
			int second = (int) ((totalwords / humanspeed) > 60 ? Math.floor(totalwords % humanspeed)
					: Math.floor(totalwords / humanspeed));
			System.out.println("In minutes :" + minutes);
			System.out.println("In seconds :" + second);

			TimeToRead timeToRead = new TimeToRead(minutes, second);
			return new TimeToReadResponse(articleOptional.get().getArticleId(), timeToRead);

		} else
			return null;

	}

	/*
	 * Story 7: Generate Tag based metric
	 */

	@GetMapping("/article/tags")
	public List<Article> getTagOccurence() {
		List<Article> articleList = articleRepository.findAll();

		String[] tagArray1 = { "JAVA", "SPRING BOOT", "xyz" };
		String[] tagArray2 = { "java", "SPRING boot", "xyz" };
		String[] tagArray3 = { "java", "SPRING boot", "Xyz" };
		List<String[]> alrticles = new ArrayList<String[]>();
		alrticles.add(tagArray1);
		alrticles.add(tagArray2);
		alrticles.add(tagArray3);

		Map<String, Integer> tagValueMap = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
		// iterating each article
		alrticles.forEach(article -> {
			// iterating each Tag and entering value into Map
			Arrays.asList(article).forEach(tag -> {
				if (tagValueMap.containsKey(tag)) {
					tagValueMap.put(tag, tagValueMap.get(tag) + 1);
				} else {
					tagValueMap.put(tag.toLowerCase(), 1);
				}
			});
		});

		// Create a list from elements of HashMap
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(tagValueMap.entrySet());
		Comparator<Map.Entry<String, Integer>> mapValueComp = (entry1, entry2) -> {
			return entry1.getValue().compareTo(entry1.getValue());
		};

		Collections.sort(list, mapValueComp);

		// put data from sorted list to hashmap
		HashMap<String, Integer> sortedTagValueMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			sortedTagValueMap.put(aa.getKey(), aa.getValue());
		}

		for (Map.Entry<String, Integer> entry : sortedTagValueMap.entrySet())
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		return articleList;
	}

	/*
	 * Story 8: A user should not be able to submit article with same body twice
	 */
	@SuppressWarnings("unchecked")
	@PutMapping("/checkAndUpdate")
	public ResponseEntity<Article> checkBody(@RequestBody Article article) {
		Optional<Article> existingArticle = articleRepository.findById(article.getArticleId());
		if (existingArticle.isPresent()) {
			return (ResponseEntity<Article>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
		} else
			articleRepository.save(article);
		return ResponseEntity.ok().build();
	}

}
