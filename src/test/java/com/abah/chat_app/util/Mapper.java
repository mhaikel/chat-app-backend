package com.abah.chat_app.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public final class Mapper {

  private static final ObjectMapper MAPPER;

  static {
    MAPPER =
        new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(new JavaTimeModule());
  }

  private Mapper() {}

  public static String serialize(Object request) {
    try {
      return MAPPER.writeValueAsString(request);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T deSerialize(String content, Class<T> responseClass) {
    try {
      return MAPPER.readValue(content, responseClass);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
