package com.writerpad.domain;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * <h1>Audit</h1>
 * <p>
 * This is the Audit class, it consist of all related fields getters
 * </p>
 *
 * @author Ravindra Pawar
 */
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public abstract class Audit {

	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId articleId;
	@CreatedDate
	private Date createdAt;
	@LastModifiedDate
	private Date updatedAt;

	@JsonIgnore
	public Object serialize(Class<?> viewClass, Class<?> objectClass) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(mapper.writerWithView(viewClass).writeValueAsString(this), objectClass);
		} catch (IOException e) {

			return null;

		}
	}

	@JsonIgnore
	public Object serializeList(Class<?> viewClass, Class<?> objectClass) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(mapper.writerWithView(viewClass).writeValueAsString(this),
					new TypeReference<List<?>>() {
					});
		} catch (IOException e) {
		}
		return null;
	}

}
