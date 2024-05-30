package com.lvchengfuwu.flinkcdc_114.sink;

import com.lvchengfuwu.flinkcdc_114.util.JsonUtil;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.io.Serializable;


/**
 * Created on 2024/5/30.
 *
 * @author zhen wang
 * @description
 */
@Slf4j
public class NoticeSink implements SinkFunction<String>, Serializable {
    @Override
    public void invoke(String value, Context context) {
        try {
            //value 转换为 DataChangeInfo
            DataChangeInfo dataChangeInfo = JsonUtil.parseObject(value, new TypeReference<DataChangeInfo>() {
            }, null);
            System.out.println(String.format("事务id：%s, 表：%s", dataChangeInfo.getSource().getGtid(), dataChangeInfo.getSource().getTable()));
//            System.out.println(String.format("表：%s,修改前:%s，修改后:%s",dataChangeInfo.getSource().getTable(), JsonUtil.toJSONString(dataChangeInfo.getBefore()), JsonUtil.toJSONString(dataChangeInfo.getAfter())));
        } catch (Exception e) {
            log.error("处理数据变更异常", e);
        }
    }

}
