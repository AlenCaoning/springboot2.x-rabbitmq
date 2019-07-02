/**
 * @author: Alen Cao
 * @description:
 * @time: 2019/7/2 14:41
 */

package com.alen.config.database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;


@Configuration
public class MybatisDataSourceConfig
{
   @Autowired
   private DataSource druidDataSource;

   @Bean(name = "sqlSessionFactory")
   public SqlSessionFactory sqlSessionFactory()throws Exception
   {
      SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
      bean.setDataSource(druidDataSource);
      //添加XML目录
      ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

      try
      {
         bean.setMapperLocations(resolver.getResources("classpath:com/alen/mapping/*.xml"));
         SqlSessionFactory factory = bean.getObject();
         factory.getConfiguration().setCacheEnabled(true);
         return factory;
      }
      catch (Exception ex)
      {
         throw new RuntimeException(ex);
      }
   }
}
