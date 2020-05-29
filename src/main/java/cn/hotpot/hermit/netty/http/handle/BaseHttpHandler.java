package cn.hotpot.hermit.netty.http.handle;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * @author qinzhu
 * @since 2020/4/14
 */
public abstract class BaseHttpHandler<T extends HttpRequest> {
    protected T request;

    public BaseHttpHandler(T request) {
        this.request = request;
    }

    public abstract HttpResponse handle();
}
