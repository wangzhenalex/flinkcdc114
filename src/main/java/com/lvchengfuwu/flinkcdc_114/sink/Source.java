package com.lvchengfuwu.flinkcdc_114.sink;

import com.ververica.cdc.connectors.shaded.com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Auto-generated: 2023-08-13 8:52:54
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */

@Data
public class Source {

    private String version;

    private String connector;

    private String name;

    @JsonProperty("ts_ms")
    private long tsMs;

    private String snapshot;

    private String db;

    private String sequence;

    private String table;

    @JsonProperty("server_id")
    private int serverId;

    private String gtid;

    private String file;

    private int pos;

    private int row;

    private String thread;

    private String query;
}