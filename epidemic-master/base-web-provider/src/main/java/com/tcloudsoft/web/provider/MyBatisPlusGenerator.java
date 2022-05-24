package com.tcloudsoft.web.provider;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBatisPlusGenerator {

  public static void main(String[] args) throws SQLException {
    String tableName = "t_isolation_exam";
    String database = "epidemic";
    // 1. 全局配置
    GlobalConfig config = new GlobalConfig();
    final String projectPath = System.getProperty("user.dir");
    System.out.println("projectPath:" + projectPath);
    config.setActiveRecord(true) // 是否支持AR模式
        .setAuthor("liuwei") // 作者
        .setOpen(false).setOutputDir(projectPath + "/base-web-provider/src/main/java").setFileOverride(true) // 文件覆盖
        .setIdType(IdType.UUID) // 主键策略
        .setServiceName("%sService") // 设置生成的service接口的名字的首字母是否为I
        .setServiceImplName("%sServiceImpl").setBaseResultMap(true)// 生成基本的resultMap
        .setBaseColumnList(true);// 生成基本的SQL片段

    // 2. 数据源配置
    DataSourceConfig dsConfig = new DataSourceConfig();
    dsConfig.setDbType(DbType.MYSQL) // 设置数据库类型
        .setDriverName("com.mysql.cj.jdbc.Driver")
        .setUrl("jdbc:mysql://localhost:3306/" + database+"?serverTimezone=UTC").setUsername("root")
        .setPassword("root");

    // 3. 策略配置globalConfiguration中
    StrategyConfig stConfig = new StrategyConfig();
    stConfig.setCapitalMode(true) // 全局大写命名
        .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
        .setColumnNaming(NamingStrategy.underline_to_camel).setInclude(tableName.split(",")) // 生成的表
        .setTablePrefix("t_").setRestControllerStyle(true);
    // 4. 包名策略配置
    PackageConfig pkConfig = new PackageConfig();
    pkConfig.setParent("com.tcloudsoft.web.provider").setMapper("mapper")// dao
        .setController("controller")// controller
        .setEntity("model")// mapper.xml
        .setServiceImpl("service.impl").setService("service");

    InjectionConfig cfg = new InjectionConfig() {
      @Override
      public void initMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
        this.setMap(map);
      }
    };
    // 自定义 xxList.jsp 生成
    List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
    String templatePath = "/templates/mapper.xml.ftl";
    // 自定义配置会被优先输出
    focList.add(new FileOutConfig(templatePath) {
      @Override
      public String outputFile(TableInfo tableInfo) {
        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
        return projectPath + "/base-web-provider/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper"
            + StringPool.DOT_XML;
      }
    });
    cfg.setFileOutConfigList(focList);

    // 5. 整合配置
    AutoGenerator ag = new AutoGenerator();
    ag.setGlobalConfig(config).setDataSource(dsConfig).setStrategy(stConfig)
        .setPackageInfo(pkConfig).setCfg(cfg).setTemplateEngine(new FreemarkerTemplateEngine())
        .setTemplate(new TemplateConfig().setXml(null));

    // 6. 执行
    ag.execute();
  }

}
