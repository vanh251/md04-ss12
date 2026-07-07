package com.example.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import com.example.productservice.subscriber.PromotionSubscriber;

@Configuration
public class RedisPubSubConfig {

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("promotion-updates");
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(PromotionSubscriber promotionSubscriber) {
        return new MessageListenerAdapter(promotionSubscriber);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter messageListenerAdapter,
            ChannelTopic topic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListenerAdapter, topic);
        return container;
    }
}

