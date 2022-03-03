package com.product.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.product.constant.ApplicationConstant;
import com.product.dto.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Configuration
@EnableKafka
public class SpringKafkaConfig {
	private static final Logger logger = LoggerFactory.getLogger(SpringKafkaConfig.class);
	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		logger.info("producerFactory method ()  SpringKafkaConfig class started ::" );
		Map<String, Object> configMap = new HashMap<>();
		configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationConstant.KAFKA_LOCAL_SERVER_CONFIG);
		configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		logger.info("producerFactory method () called SpringKafkaConfig class ended::" );
		return new DefaultKafkaProducerFactory<String, Object>(configMap);
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public ConsumerFactory<String, ResponseObject> consumerFactory() {
		Map<String, Object> configMap = new HashMap<>();
		logger.info("consumerFactory method ()  SpringKafkaConfig class started ::" );
		configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationConstant.KAFKA_LOCAL_SERVER_CONFIG);
		configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configMap.put(ConsumerConfig.GROUP_ID_CONFIG, ApplicationConstant.GROUP_ID_JSON);
		configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "com.product.dto");
		logger.info("consumerFactory method () called SpringKafkaConfig class ended::" );

		return new DefaultKafkaConsumerFactory<>(configMap);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ResponseObject> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, ResponseObject> factory = new ConcurrentKafkaListenerContainerFactory<String, ResponseObject>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	
}
