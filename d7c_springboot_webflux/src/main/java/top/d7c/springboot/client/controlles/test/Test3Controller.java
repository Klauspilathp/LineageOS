package top.d7c.springboot.client.controlles.test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Title: Test3Controller
 * @Package: top.d7c.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年7月7日 下午7:13:50
 * @Description: webflux flux 测试
 */
@Component
public class Test3Controller {

    /**
     * 利用 interval 生成每秒一个数据的流
     */
    public Mono<ServerResponse> test1(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
                Flux.interval(Duration.ofSeconds(1)).map(l -> new SimpleDateFormat("HH:mm:ss").format(new Date())),
                String.class);
    }

}