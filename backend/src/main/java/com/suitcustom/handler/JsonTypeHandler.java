package com.suitcustom.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * JSON类型处理器
 * 
 * @author suitcustom
 */
@MappedTypes(Map.class)
public class JsonTypeHandler extends BaseTypeHandler<Object> {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
      throws SQLException {
    try {
      String json;
      if (parameter instanceof String) {
        json = (String) parameter;
      } else {
        json = MAPPER.writeValueAsString(parameter);
      }
      ps.setString(i, json);
    } catch (JsonProcessingException e) {
      throw new SQLException("Error converting Object to JSON", e);
    }
  }

  @Override
  public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String json = rs.getString(columnName);
    return parseJson(json);
  }

  @Override
  public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String json = rs.getString(columnIndex);
    return parseJson(json);
  }

  @Override
  public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String json = cs.getString(columnIndex);
    return parseJson(json);
  }

  private Object parseJson(String json) throws SQLException {
    if (json == null) {
      return null;
    }
    try {
      return MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {
      });
    } catch (JsonProcessingException e) {
      throw new SQLException("Error converting JSON to Object", e);
    }
  }
}