package cn.hotpot.hermit.netty.http;

import cn.hotpot.hermit.netty.http.handle.HttpGetHandler;
import cn.hotpot.hermit.netty.http.handle.HttpPostHandler;
import cn.hotpot.hermit.netty.http.handle.BaseHttpHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

/**
 * @author qinzhu
 * @since 2020/4/16
 * factory class
 */
public class RequestSelector {
    private RequestSelector() {
    }

    public static BaseHttpHandler selectHandler(HttpRequest request) {
        HttpMethod method = request.method();
        if (method.equals(HttpMethod.GET)) {
            return new HttpGetHandler(request);
        } else if (method.equals(HttpMethod.POST)) {
            return new HttpPostHandler((FullHttpRequest) request);
        } else {
            throw new IllegalStateException("Features not yet developed");
        }
    }
}
