package com.kafka08.sample.stringmessage;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * 异步发送(达到满足条件)
 * <p>Description:</p>
 * @author hansen.wang
 * @date 2017年1月10日 上午11:14:09
 */
public class StringMsgProducerAsync {
	public static void main(String[] args) {
		
		Properties props = new Properties();
		props.put("metadata.broker.list", "192.168.56.101:9092");
		//msg的序列化实现类
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		//key的序列化实现类
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		
		/**
		 * 控制一个producer的请求怎样才能算完成
		 * 0:不会等待来自borker的ack
		 * 1:等待来自leader的ack
		 * -1:等待所有的broker都复制成功的ack
		 */
		props.put("request.required.acks", "1");
		
		//异步发送
		props.put("producer.type", "async");
		//每次批量的大小
		props.put("batch.num.messages", "5000");
		//发送时间间隔,毫秒为单位
		props.put("queue.buffering.max.ms", "3000");
		
		
		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> producer = new Producer<String, String>(config);
		
		String topic = "kafka-topic-async-1";
		
		long t1 = System.currentTimeMillis();
		System.out.println("start : " + t1);
		
		for(int i=0; i<10000; i++) {
			KeyedMessage<String, String> msg = new KeyedMessage<String, String>(topic, i + "");
			producer.send(msg);
		}
		
		long t2 = System.currentTimeMillis();
		System.out.println("use : " + (t2-t1) + "ms");
		
		producer.close();
	}
}
