
package com.tcloudsoft.auth.provider.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class RedisListenerConfig {
  @Autowired
  private RedisConnectionFactory redisConnectionFactory;

  @Autowired
  SimpMessagingTemplate simpMessagingTemplate;


  @Bean
  public RedisMessageListenerContainer redisMessageListenerContainer() {
    RedisMessageListenerContainer redisMessageListenerContainer =
        new RedisMessageListenerContainer();
    redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
    return redisMessageListenerContainer;
  }

  @Bean
  public RedisKeyExpirationListener keyExpiredListener() {
    return new RedisKeyExpirationListener(this.redisMessageListenerContainer(),
        simpMessagingTemplate);
  }
}
