package com.kafka10.consumer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

/**
 * 
 * <p>Description:</p>
 * @author hansen.wang
 * @date 2018年1月22日 下午2:11:07
 */
public class Kafka10TimestampConsumer {

    private static final String TOPIC = "TOPIC_TIMESTAMP_MSG";
    
    public static void main(String[] args) {
        
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.103:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "GROUP_TOPIC_TIMESTAMP");
        
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true"); //offset自动提交
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000"); //offset自动提交时间间隔
        
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000"); //comsumer的session的超时时间
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        
        //
        TopicPartition topicPartition0 = new TopicPartition(TOPIC, 0);
        Map<TopicPartition, Long> timestampToSearch = new HashMap<>();
        timestampToSearch.put(topicPartition0, 1516600885965l);
        
        consumer.offsetsForTimes(timestampToSearch);
        consumer.subscribe(Collections.singletonList(TOPIC));
        
        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(2);
            for (ConsumerRecord<String, String> consumerRecord : records) {
                System.out.println(consumerRecord);
            }
        }
    }

}
