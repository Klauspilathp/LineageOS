# d7c_springboot_plugins

## 介绍

d7c_springboot_plugins 项目是基于 SpringBoot 2.1.x 和 SpringCloud Greenwich.SR6 开发的插件集，致力于提供各种工具、服务 API 的简洁调用方式。

## 软件架构

d7c_springboot_plugins 主框架是 SpringBoot 2.1.x 和 SpringCloud Greenwich.SR6，主要是对各种工具、服务 API 进行封装，如需运维文档请前往 [d7c_docs](https://item.taobao.com/item.htm?ft=t&id=637839200595)。

## 安装教程

1.  克隆项目到本地
2.  导入开发工具，获取 [开发工具](https://pan.baidu.com/s/1bNUzfSV7d-kQdXC5tkl-FA)，提取码：7777
3.  根据需要运行 src/test/java 包下的程序测试类

## 模块介绍
### 1. d7c_springboot_plugins_core

该模块是 d7c_springboot_plugins 插件集的核心类库，主要提供容器类、持久层基础类库、枚举类工具类、异常处理类、接收页面数据及返回页面数据等核心类。

### 2. d7c_springboot_plugins_net

该模块主要提供了一些网络操作工具类，主要有 HttpServletRequest 数据获取和解析、IP 获取、Mac 地址获取、Cookie 操作等工具类，最重要的是对 HTTP 请求进行了深度封装，一个实体类即可完成各种请求和响应。

```
package top.d7c.plugins.net.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.StringUtil;
import top.d7c.plugins.core.enums.RequestMethodEnum;
import top.d7c.plugins.net.tools.HttpUtil;
import top.d7c.plugins.net.tools.RequestUtil;

public class HttpTest {

    @Test
    public void test1() throws IOException {
        long currentTimeMillis = System.currentTimeMillis();

        /*for (int i = 0; i < 1; i++) {
            HttpRequestConfig request = new HttpRequestConfig();
            request.setUrl("http://127.0.0.1:8887/api/login?aa=aa1&bb=bb1");
            request.setRequestMethod(RequestMethodEnum.POST);
            request.setCharset(StringUtil.UTF_8);
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", "admin");
            params.put("password", "123456");
            String json = "{\"username\":\"admin\",\"password\":\"123456\"}";
            request.setParams(json);
            CloseableHttpResponse closeableHttpResponse = RequestUtil.http(request);
            int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
            System.out.println(statusCode);
        
            Header response_body = closeableHttpResponse.getLastHeader("response-body");
            if (response_body != null) {
                System.out.println(response_body.getValue());
            }
        }*/

        for (int i = 0; i < 5; i++) {
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    HttpRequestConfig request = new HttpRequestConfig();
                    request.setUrl("https://www.baidu.com");
                    request.setRequestMethod(RequestMethodEnum.POST);
                    request.setCharset(StringUtil.UTF_8);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", "admin");
                    params.put("password", "123456");
                    String json = "{\"username\":\"admin\",\"password\":\"123456\"}";
                    request.setParams(json);
                    CloseableHttpResponse closeableHttpResponse = RequestUtil.http(request);
                    int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
                    System.out.println(statusCode);

                    Header response_body = closeableHttpResponse.getLastHeader("response-body");
                    if (response_body != null) {
                        System.out.println(response_body.getValue());
                    }
                }
            }, "at" + i);
            t1.start();
            Thread t2 = new Thread(new Runnable() {
                public void run() {
                    HttpRequestConfig request = new HttpRequestConfig();
                    request.setUrl("http://127.0.0.1:8887/api/login?aa=aa1&bb=bb1");
                    request.setRequestMethod(RequestMethodEnum.POST);
                    request.setCharset(StringUtil.UTF_8);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", "admin");
                    params.put("password", "123456");
                    String json = "{\"username\":\"admin\",\"password\":\"123456\"}";
                    request.setParams(json);
                    /*CloseableHttpResponse closeableHttpResponse = RequestUtil.http(request);
                    int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
                    System.out.println(statusCode);
                    
                    Header response_body = closeableHttpResponse.getLastHeader("response-body");
                    if (response_body != null) {
                        System.out.println(response_body.getValue());
                    }*/
                    PageResult byHttp = RequestUtil.byHttp(request);
                    Object data = byHttp.getData();
                    System.out.println(data.toString());
                }
            }, "bt" + i);
            t2.start();
        }
        //8265
        System.out.println(System.currentTimeMillis() - currentTimeMillis);
        System.in.read();
    }

    @Test
    public void test2() throws IOException {
        byte[] loadByteByFile = HttpUtil.loadByteByFile("classpath:a.txt");
        System.out.println(new String(loadByteByFile));
    }

    @Test
    public void test3() throws IOException {
        SSLContext sslContext = HttpUtil.getSSLContext(new FileInputStream("xxxxxx\\apiclient_cert.p12"), "xxx",
                new FileInputStream("E:\\Trash\\temp\\cacert.jks"), "0000001");
        System.out.println(sslContext);
    }

    @Test
    public void test4() throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpUtil.getHttpClient(true, new FileInputStream("xxxx\\apiclient_cert.p12"),
                "xxxx", new FileInputStream("E:\\Trash\\temp\\cacert.jks"), "0000001");
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8887/api/login?aa=aa1&bb=bb1");
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", "admin");
        params.put("password", "123456");
        String json = "{\"username\":\"admin\",\"password\":\"123456\"}";
        StringEntity postEntity = new StringEntity(json, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        String string = EntityUtils.toString(httpEntity, "UTF-8");
        System.out.println(string);
    }

}
```

### 3. d7c_springboot_plugins_tools

该模块是一个重要的工具类模块，提供的工具类包括验证码生成、日期处理、文档处理、文件处理、主键序列号生成器、安全工具类等众多工具类。

### 4. spring-boot-starter-aliyun-oss

该模块主要是对 aliyun oss 云存储服务操作的封装。

### 5. spring-boot-starter-fastDFS

该模块主要提供了对大、中型分布式文件系统 fastDFS 操作的封装。

### 6. spring-boot-starter-mybatis

该模块主要提供了利用 mybatis 拦截器实现查询自动分页功能、默认事务配置和对一主无限多从数据源的支持。

### 7. spring-boot-starter-jwt

该模块主要是对 JWT 采用 RSA 加解密方式操作的封装。

### 8. spring-boot-starter-oauth2

该模块主要是对 oauth2 操作的封装。

### 9. spring-boot-starter-redis

该模块主要是对 redis 缓存技术和 redisson 操作的封装，支持集群模式，同时提供了一些 redisson 操作 redis 的示例代码。

```
package top.d7c.redis.springboot.tests;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * RedissonClient 测试。
 * 可重入锁：也叫递归锁，指的是同一线程外层函数获得锁之后，内层递归函数仍然有获取该锁的代码，但不受影响。
 * 不可重入锁：若当前线程执行中已经获取了锁，如果再次获取该锁时，就会获取不到被阻塞，会出现死锁。
 * 公平锁：多个线程按照申请锁的顺序去获得锁，线程会直接进入队列去排队，永远都是队列的第一位才能得到锁。
 * 非公平锁：多个线程去获取锁的时候，会直接去尝试获取，获取不到，再去进入等待队列，如果能获取到，就直接获取到锁。
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:/application.properties")
@SpringBootApplication()
public class RedissonClientTest {
    private static final Logger logger = LoggerFactory.getLogger(RedissonClientTest.class);
    /**
     * Redisson 客户端。
     * Redisson 客户端锁机制：
     *    线程去获取锁，获取成功：执行 lua 脚本，保存数据到 redis 数据库；
     *    线程去获取锁，获取失败：一直通过 while 循环尝试获取锁，获取成功后，执行 lua 脚本，保存数据到 redis 数据库。
     * Redisson 分布式锁的缺点：
     *    在 Redis 哨兵模式下，“客户端1”对某个 master 节点写入了 redisson 锁，此时会异步复制给对应的 slave 节点。
     * 但是这个过程中一旦发生 master 节点宕机，主备切换，slave 节点从变为了 master 节点。这时“客户端2”来尝试加锁
     * 的时候，在新的 master 节点上也能加锁，此时就会导致多个客户端对同一个分布式锁完成了加锁。这时系统在业务语义上
     * 一定会出现问题，导致各种脏数据的产生。
     */
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test1() {
        logger.info(redissonClient.toString());
    }

    // org.redisson.RedissonLock 可重入锁，存在节点挂掉时锁丢失问题。
    /**
     * 测试非公平同步可重入锁。
     * 同时执行两次，第一次获取成功，第二次会获取失败。
     * @throws InterruptedException 
     */
    @Test
    public void test2() throws InterruptedException {
        RLock lock = redissonClient.getLock("lock_key");
        boolean lockStatus = lock.tryLock(3, // 获取锁的最大等待时间
                40, // 持有锁的最大时间
                TimeUnit.SECONDS);
        if (!lockStatus) {
            logger.warn("获取锁失败！");
            return;
        }
        try {
            logger.info("获取锁成功！");
            // 业务执行时间不能大于持有锁的时间，否则解锁时会抛异常，因为解锁和加锁已经不是同一个线程了。
            // Thread.sleep(400);
            Thread.sleep(39000);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    /**
     * 测试非公平异步可重入锁。
     * 同时执行两次，第一次获取成功，第二次会获取失败。
     * @throws InterruptedException 
     * @throws ExecutionException 
     */
    @Test
    public void test3() throws InterruptedException, ExecutionException {
        RLock lock = redissonClient.getLock("lock_key");
        lock.lockAsync();
        lock.lockAsync(10, TimeUnit.SECONDS);
        RFuture<Boolean> rFuture = lock.tryLockAsync(3, // 获取锁的最大等待时间
                40, // 持有锁的最大时间
                TimeUnit.SECONDS);
        if (!rFuture.get()) {
            logger.warn("获取锁失败！");
            return;
        }
        try {
            logger.info("获取锁成功！");
            // 业务执行时间不能大于持有锁的时间，否则解锁时会抛异常，因为解锁和加锁已经不是同一个线程了。
            // Thread.sleep(400);
            Thread.sleep(39000);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    // org.redisson.RedissonFairLock 公平锁。
    /**
     * 测试公平同步锁。
     * 同时执行两次，第一次获取成功，第二次会获取失败。
     * @throws InterruptedException 
     */
    @Test
    public void test4() throws InterruptedException {
        RLock lock = redissonClient.getFairLock("lock_key");
        boolean lockStatus = lock.tryLock(3, // 获取锁的最大等待时间
                40, // 持有锁的最大时间
                TimeUnit.SECONDS);
        if (!lockStatus) {
            logger.warn("获取锁失败！");
            return;
        }
        try {
            logger.info("获取锁成功！");
            // 业务执行时间不能大于持有锁的时间，否则解锁时会抛异常，因为解锁和加锁已经不是同一个线程了。
            // Thread.sleep(400);
            Thread.sleep(39000);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    // org.redisson.RedissonMultiLock 联锁将多个 RLock 对象关联为一个联锁，每个RLock对象实例可以来自于不同的 Redisson 实例。
    @Test
    public void test5() throws InterruptedException {
        // 创建多个锁对象
        RLock lock1 = redissonClient.getLock("lock1");
        RLock lock2 = redissonClient.getLock("lock2");
        RLock lock3 = redissonClient.getLock("lock3");

        RLock lock = redissonClient.getMultiLock(lock1, lock2, lock3);
        boolean lockStatus = lock.tryLock(3, // 获取锁的最大等待时间
                40, // 持有锁的最大时间
                TimeUnit.SECONDS);
        if (!lockStatus) {
            logger.warn("获取锁失败！");
            return;
        }
        try {
            logger.info("获取锁成功！");
            // 业务执行时间不能大于持有锁的时间，否则解锁时会抛异常，因为解锁和加锁已经不是同一个线程了。
            // Thread.sleep(400);
            Thread.sleep(39000);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    // org.redisson.RedissonRedLock 分布式锁（红锁），org.redisson.RedissonMultiLock 的子类，解决了单点故障锁丢失问题。
    @Test
    public void test6() throws InterruptedException {
        // 创建多个锁对象
        RLock lock1 = redissonClient.getLock("lock1");
        RLock lock2 = redissonClient.getLock("lock2");
        RLock lock3 = redissonClient.getLock("lock3");

        // redLock 算法创建 org.redisson.RedissonRedLock 锁对象
        RLock lock = redissonClient.getRedLock(lock1, lock2, lock3);
        boolean lockStatus = lock.tryLock(3, // 获取锁的最大等待时间
                40, // 持有锁的最大时间
                TimeUnit.SECONDS);
        if (!lockStatus) {
            logger.warn("获取锁失败！");
            return;
        }
        try {
            logger.info("获取锁成功！");
            // 业务执行时间不能大于持有锁的时间，否则解锁时会抛异常，因为解锁和加锁已经不是同一个线程了。
            // Thread.sleep(400);
            Thread.sleep(39000);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

}
```

### 10. spring-boot-starter-elasticsearch

该模块主要提供了对 elasticsearch 分布式搜索和分析引擎操作的封装。

### 11. spring-boot-starter-mongodb

该模块主要提供了对 mongodb 操作的封装，同时使操作支持事务。

### 12. spring-boot-starter-activemq

该模块主要提供了对 activemq 操作的封装。

### 13. spring-boot-starter-activiti

该模块主要是对 activiti jar 包版本进行管理，同时为日后扩展提供可能。

### 14. spring-boot-starter-flowable

该模块主要是对 flowable jar 包版本进行管理，同时提供了任务回滚操作命令。

```
org.flowable.engine.ManagementService.executeCommand(new TaskRollbackCmd(taskId, assignee));
```

## 捐助打赏

如果您觉得我们的开源软件对你有所帮助，请扫下方二维码打赏我们一杯咖啡。
![微信收款码](https://images.gitee.com/uploads/images/2021/0222/174352_b22739f5_1070311.jpeg "微信收款码.jpg")
![微信赞赏码](https://images.gitee.com/uploads/images/2021/0222/174521_67e18b39_1070311.jpeg "微信赞赏码.jpg")
![支付宝收款码](https://images.gitee.com/uploads/images/2021/0222/174540_94a9ac41_1070311.jpeg "支付宝收款码.jpg")

## 参与贡献

1.  Fork 本仓库
2.  新建 d7c_springboot_plugins_xxx 分支
3.  提交代码
4.  新建 Pull Request

## 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)

<style>p{text-indent:2em}</style>
