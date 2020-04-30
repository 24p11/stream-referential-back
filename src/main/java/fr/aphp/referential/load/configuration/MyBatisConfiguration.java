package fr.aphp.referential.load.configuration;

import java.util.stream.Stream;

import javax.sql.DataSource;

import org.apache.camel.component.mybatis.MyBatisComponent;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class MyBatisConfiguration {

    @Value("classpath:mybatis-config.xml")
    private Resource configLocation;

    @Value("classpath*:mybatis/*.xml")
    private Resource[] mapperLocation;

    @Bean(name = "mybatis")
    public MyBatisComponent myBatisComponent(SqlSessionFactory sqlSessionFactory) {
        MyBatisComponent myBatisComponent = new MyBatisComponent();

        myBatisComponent.setSqlSessionFactory(sqlSessionFactory);

        return myBatisComponent;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        sqlSessionFactoryBean.setDataSource(dataSource);
        Stream.of(configLocation)
                .filter(Resource::exists)
                .forEach(sqlSessionFactoryBean::setConfigLocation);
        sqlSessionFactoryBean.setMapperLocations(mapperLocation);

        return sqlSessionFactoryBean;
    }
}
