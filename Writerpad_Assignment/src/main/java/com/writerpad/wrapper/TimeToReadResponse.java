package com.writerpad.wrapper;

import org.bson.types.ObjectId;

import com.writerpad.dto.TimeToRead;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeToReadResponse {

	private ObjectId slugUuId;
	private TimeToRead timeToRead;
	
}
