package top.d7c.springboot.gateway.filters;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;

import top.d7c.springboot.gateway.config.D7cProperties;
import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;

/**
 * @Title: DynamicFilter
 * @Package: top.d7c.springboot.gateway.filters
 * @author: 吴佳隆
 * @date: 2021年1月4日 下午3:24:51
 * @Description: 动态加载过滤器。
 */
@Configuration
public class DynamicFilter {
    private static final Logger logger = LoggerFactory.getLogger(DynamicFilter.class);
    /**
     * d7c 系统自定义属性
     */
    @Resource(name = "d7cProperties")
    private D7cProperties d7cProperties;

    /**
     * 项目启动时加载 groovy 语言写的过滤器脚本。
     */
    @PostConstruct
    public void groovyLoadFilter() {
        // FilterProcessor.setProcessor(new CustomFilterProcessor());
        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
        FilterFileManager.setFilenameFilter(new GroovyFileFilter());
        String scriptRoot = d7cProperties.getGroovyFilters() + File.separator;
        logger.info("zuul loads the script under {}", scriptRoot);
        try {
            FilterFileManager.init(d7cProperties.getGroovyRefreshInterval(), // 刷新时间间隔，单位秒
                    /*scriptRoot + FilterConstants.ERROR_TYPE,*/ scriptRoot + FilterConstants.POST_TYPE,
                    scriptRoot + FilterConstants.PRE_TYPE/*, scriptRoot + FilterConstants.ROUTE_TYPE*/);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}