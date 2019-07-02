/**
 * @author: Alen Cao
 * @description:
 * @time: 2019/7/2 14:52
 */

package com.alen.config.database;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;



@Configuration
//在数据源加载完成后自动装配
@AutoConfigureAfter(MybatisDataSourceConfig.class)
public class MybatisMapperScannerConfig
{
   public MapperScannerConfigurer mapperScannerConfigurer()
   {
      MapperScannerConfigurer  mapperScannerConfigurer = new MapperScannerConfigurer();
      mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
      mapperScannerConfigurer.setBasePackage("com.alen.mapper");
      return mapperScannerConfigurer;
   }
}
