package com.writerpad.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.writerpad.domain.Article;

/**
 * <h1>ArticleRepository</h1>
 * <p>
 * ArticleRepository is extended with MongoRepository and
 * PagingAndSortingRepository with Article and ObjectId generics which will help
 * in performing database operations
 * </p>
 *
 * @author Ravindra Pawar
 */

@Repository
public interface ArticleRepository
		extends MongoRepository<Article, ObjectId>, PagingAndSortingRepository<Article, ObjectId> {

}
