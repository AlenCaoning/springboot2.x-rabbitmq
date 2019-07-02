/**
 * @author: Alen Cao
 * @description:
 * @time: 2019/7/2 14:17
 */

package com.alen.config.database;

import com.alibaba.druid.pool.DruidDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfig
{
   private static Logger logger = Logger.getLogger(DruidDataSourceConfig.class);

   @Autowired private DruidDataSourceSettings druidSettings;

   public static String DRIVER_CLASSNAME;

   @Bean
   public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
   {
      return new PropertySourcesPlaceholderConfigurer();
   }

   @Bean(name = "druidDataSource")
   public DataSource dataSource() throws SQLException
   {
      DruidDataSource ds = new DruidDataSource();
      ds.setDriverClassName(druidSettings.getDriverClassName());
      DRIVER_CLASSNAME = druidSettings.getDriverClassName();
      ds.setUrl(druidSettings.getUrl());
      ds.setUsername(druidSettings.getUsername());
      ds.setPassword(druidSettings.getPassword());

      ds.setInitialSize(druidSettings.getInitialSize());
      ds.setMinIdle(druidSettings.getMinIdle());
      ds.setMaxActive(druidSettings.getMaxActive());
      ds.setTimeBetweenEvictionRunsMillis(druidSettings.getTimeBetweenEvictionRunsMills());
      ds.setMinEvictableIdleTimeMillis(druidSettings.getMinEvictableIdleTimeMills());
      ds.setValidationQuery(druidSettings.getValidationQuery());
      ds.setTestWhileIdle(druidSettings.isTestWhileIdle());
      ds.setTestOnBorrow(druidSettings.isTestOnBorrow());
      ds.setTestOnReturn(druidSettings.isTestOnReturn());
      ds.setPoolPreparedStatements(druidSettings.isPoolPreparedStatements());
      ds.setMaxPoolPreparedStatementPerConnectionSize(druidSettings.getMaxPoolPreparedStatementPerConnectionSize());
      ds.setFilters(druidSettings.getFilters());
      ds.setConnectionProperties(druidSettings.getConnectionProperties());

      logger.info("ds : " + ds);
      return ds;
   }

   @Bean
   public PlatformTransactionManager transactionManager(DataSource druidDataSource) throws Exception
   {
      logger.info("druidDataSource : " + druidDataSource);
      DataSourceTransactionManager txManager = new DataSourceTransactionManager();
      txManager.setDataSource(druidDataSource);
      logger.info("txManager : " + txManager);
      return txManager;
   }
}
