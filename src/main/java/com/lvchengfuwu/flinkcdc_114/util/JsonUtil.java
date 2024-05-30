package com.lvchengfuwu.flinkcdc_114.util;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.type.TypeReference;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.flink.shaded.netty4.io.netty.util.internal.StringUtil;

/**
 * Created on 2024/5/30.
 *
 * @author zhen wang
 * @description
 */
public class JsonUtil {

    public static <TObject> String toJSONString(TObject obj) {
        try {
            if (null == obj) {
                return "";
            }
            ObjectMapper mapper = new ObjectMapper();
            //2022年09月26日 打印入参 servletContext 报错的问题。
            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            //解决jackson无法反序列化LocalDateTime的问题
            return mapper.writeValueAsString(obj);
        } catch (org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <TEntity> TEntity parseObject(String json, TypeReference<TEntity> valueTypeRef, PropertyNamingStrategy strategy) {
        try {
            if (StringUtil.isNullOrEmpty(json)) {
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            //2022年04月28日 遇到json有的属性，但是接收对象没有的，不要报错
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            if (null == strategy) {
                mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            } else {
                mapper.setPropertyNamingStrategy(strategy);
            }
            return mapper.readValue(json, valueTypeRef);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
