package com.tcloudsoft.auth.provider.redis;


import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(RedisKeyExpirationListener.class);
  SimpMessagingTemplate simpMessagingTemplate;

  public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer,
      SimpMessagingTemplate simpMessagingTemplate) {
    super(listenerContainer);
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @Override
  public void onMessage(Message message, byte[] pattern) {
    String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
    // 过期的key
    String key = new String(message.getBody(), StandardCharsets.UTF_8);
    LOGGER.info("redis key 过期：pattern={},channel={},key={}", new String(pattern), channel, key);
    if (key.indexOf("-UID-") > -1 && key.endsWith("-TOKEN")) {
      String appId = key.substring(0, key.indexOf("-UID-"));
      String userId = key.substring(key.indexOf("-UID-") + 5, key.indexOf("-TOKEN"));
      simpMessagingTemplate.convertAndSend("/" + appId + "/" + userId, 1);
    }
  }
}
