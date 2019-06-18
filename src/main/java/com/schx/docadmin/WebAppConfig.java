package com.schx.docadmin;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.schx.docadmin.aop.interceptor.LoginInterceptor;
import jetbrick.template.web.springmvc.JetTemplateViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @description: webapp配置
 * @author: xutao
 * @create: 2019-06-13 11:25
 **/
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ViewResolver jet= this.getJetTemplateViewResolver();
        registry.viewResolver(jet);
    }

    /**
     * 配置消息转换器--这里我用的是alibaba 开源的 fastjson
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2.添加fastJson的配置信息，比如：是否要格式化返回的json数据;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteDateUseDateFormat);
        //3处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        //5.将convert添加到converters当中.
        converters.add(fastJsonHttpMessageConverter);
    }
    private ViewResolver getJetTemplateViewResolver() {
        JetTemplateViewResolver ret=new JetTemplateViewResolver();
        ret.setOrder(1);
        ret.setContentType("text/html; charset=utf-8");
        ret.setPrefix("templates/");
        ret.setSuffix(".html");
        Properties props=new Properties();
        props.setProperty("jetx.input.encoding","utf-8");
        props.setProperty("jetx.output.encoding","utf-8");
        //必须设置 否则找不到文件
        props.setProperty("jetx.template.loaders","jetbrick.template.loader.ClasspathResourceLoader");
        ret.setConfigProperties(props);
        return ret;
    }
}
