package com.lvchengfuwu.flinkcdc_114;

import com.lvchengfuwu.flinkcdc_114.sink.NoticeSink;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created on 2024/5/30.
 *
 * @author zhen wang
 * @description
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * cwfree-uat.rwlb.rds.aliyuncs.com:3306
         * lmreader/C1mHU2nVjygY
         */
        MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
                .hostname("cwfree-uat.rwlb.rds.aliyuncs.com").port(3306)
                .databaseList("newsee-owner") // set captured database, If you need to synchronize the whole database, Please set tableList to ".*".
                .tableList("newsee-owner.*") // set captured table
                .username("lmreader")
                .password("C1mHU2nVjygY")
                .deserializer(new JsonDebeziumDeserializationSchema())
                .startupOptions(StartupOptions.latest())// converts SourceRecord to JSON String
                .build();

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // enable checkpoint
        env.enableCheckpointing(3000);
        env.fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "MySQL Source")
                // set 1 parallel source tasks
                .setParallelism(1).addSink(new NoticeSink()); // use parallelism 1 for sink
        env.execute("Print MySQL Snapshot + Binlog");
    }
}
