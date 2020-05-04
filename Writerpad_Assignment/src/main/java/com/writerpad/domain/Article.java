package com.writerpad.domain;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * <h1>Article</h1>
 * <p>
 * This is the Article class, it consist of all related fields getters
 * </p>
 *
 * @author Ravindra Pawar
 */
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "article")
@JsonInclude(Include.NON_NULL)
@Component
public class Article extends Audit {

	@NotEmpty
	private String title;
	@NotEmpty
	private String description;
	@NotEmpty
	private String body;
	private String slug;
	private boolean favorited;
	private int favoritesCount;
	private String[] tags;
	private int occurance ;
	
	
}
