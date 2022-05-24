package com.tcloudsoft.auth.provider.config;

import javax.sql.DataSource;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**
 * @description: TODO
 * @author zhuolin.Huang
 * @date 2022/5/24 19:18
 * @version 1.0
 */
@Configuration
public class DruidConfig {
  /**
   * 将自定义的 Druid 数据源添加到容器中，不再让 Spring Boot 自动创建 这样做的目的是：绑定全局配置文件中的 druid 数据源属性到
   * com.alibaba.druid.pool.DruidDataSource 从而让它们生效
   * 
   * @ConfigurationProperties(prefix = "spring.datasource")：作用就是将 全局配置文件中 前缀为 spring.datasource
   *                                 的属性值注入到 com.alibaba.druid.pool.DruidDataSource 的同名参数中
   *
   * @return
   */
  @ConfigurationProperties(prefix = "spring.datasource")
  @Bean
  public DataSource dataSource() {
    DruidDataSource dataSource = new DruidDataSource();
    return dataSource;
  }

/*  @Bean(name = "dynamicDataSource")
  @Qualifier("dynamicDataSource")
  public DynamicDataSource dynamicDataSource() throws SQLException {
    DynamicDataSource dynamicDataSource = new DynamicDataSource();
    dynamicDataSource.setDebug(false);
    //配置缺省的数据源
    // 默认数据源配置 DefaultTargetDataSource
    dynamicDataSource.setDefaultTargetDataSource(dataSource());
    Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
    //额外数据源配置 TargetDataSources
    targetDataSources.put("main", dataSource());
    dynamicDataSource.setTargetDataSources(targetDataSources);
    return dynamicDataSource;
  }

  @Bean
  public MybatisSqlSessionFactoryBean sqlSessionFactory() throws Exception {

    MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dynamicDataSource());
    sqlSessionFactoryBean.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
    return sqlSessionFactoryBean;
  }*/
}
