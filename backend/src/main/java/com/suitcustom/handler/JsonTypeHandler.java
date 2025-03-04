package com.suitcustom.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
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
public class JsonTypeHandler extends BaseTypeHandler<Map<String, Object>> {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType)
      throws SQLException {
    try {
      ps.setString(i, MAPPER.writeValueAsString(parameter));
    } catch (JsonProcessingException e) {
      throw new SQLException("Error converting Map to JSON", e);
    }
  }

  @Override
  public Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String json = rs.getString(columnName);
    return parseJson(json);
  }

  @Override
  public Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String json = rs.getString(columnIndex);
    return parseJson(json);
  }

  @Override
  public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String json = cs.getString(columnIndex);
    return parseJson(json);
  }

  private Map<String, Object> parseJson(String json) throws SQLException {
    try {
      return json == null ? null : MAPPER.readValue(json, Map.class);
    } catch (JsonProcessingException e) {
      throw new SQLException("Error converting JSON to Map", e);
    }
  }
}