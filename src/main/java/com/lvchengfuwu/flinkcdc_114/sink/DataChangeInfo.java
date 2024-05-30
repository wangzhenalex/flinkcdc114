package com.lvchengfuwu.flinkcdc_114.sink;

import com.ververica.cdc.connectors.shaded.com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedHashMap;

/**
 * Auto-generated: 2023-08-13 8:52:54
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class DataChangeInfo {

    private LinkedHashMap before;

    private LinkedHashMap after;

    private Source source;

    private String op;

    @JsonProperty("ts_ms")
    private long tsMs;
    
    private String transaction;
}