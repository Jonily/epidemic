/*
package com.tcloudsoft.auth.provider.config;

import com.tcloudsoft.auth.provider.model.Databasesource;
import com.tcloudsoft.auth.provider.model.RoomInfo;
import com.tcloudsoft.auth.provider.service.DatabasesourceService;
import com.tcloudsoft.auth.provider.service.RoomInfoService;
import com.tcloudsoft.auth.provider.utils.DateSource.DynamicDataSource;
import com.tcloudsoft.auth.provider.utils.SpringContextUtils;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class DynamicDataSourceInit {
    @Autowired
    DatabasesourceService DatabasesourceService;

    @PostConstruct
    public void InitDataSource()  {
        log.info("=====初始化动态数据源=====");
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        //查询当前默认数据源下所有的数据源配置信息
        List<Databasesource> databasesourceList = DatabasesourceService.list();
        for (Databasesource databasesource : databasesourceList) {
            log.info(databasesource.toString());
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl(databasesource.getUrl());
            dataSource.setUsername(databasesource.getUserName());
            dataSource.setPassword(databasesource.getPassWord());
            dataSourceMap.put(databasesource.getDatasourceId(), dataSource);
        }
        //设置数据源
        dynamicDataSource.setDataSources(dataSourceMap);
        //移交父级
        dynamicDataSource.afterPropertiesSet();
    }
}
*/
