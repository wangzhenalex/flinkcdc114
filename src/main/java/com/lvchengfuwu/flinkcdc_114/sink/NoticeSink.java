package com.lvchengfuwu.flinkcdc_114.sink;

import com.lvchengfuwu.flinkcdc_114.util.JsonUtil;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * Created on 2024/5/30.
 *
 * @author zhen wang
 * @description
 */
@Slf4j
public class NoticeSink implements SinkFunction<String>, Serializable {
    private Map<String, String> operationMap;

    public NoticeSink() {
        this.operationMap = new HashMap<>();
        this.operationMap.put("c", "增加");
        this.operationMap.put("u", "修改");
        this.operationMap.put("d", "删除");
    }

    @Override
    public void invoke(String value, Context context) {
        try {
            //value 转换为 DataChangeInfo
            DataChangeInfo dataChangeInfo = JsonUtil.parseObject(value, new TypeReference<DataChangeInfo>() {
            }, null);
            log.info(String.format("事务id：%s, 表：%s，操作：%s",
                    dataChangeInfo.getSource().getGtid(),
                    dataChangeInfo.getSource().getTable(),
                    operationMap.get(dataChangeInfo.getOp())));
            log.info(value);
        } catch (Exception e) {
            log.error("处理数据变更异常", e);
        }
    }

}
