package cn.hotpot.hermit.netty.http.handle;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * @author qinzhu
 * @since 2020/4/14
 */
public class HttpGetHandler extends BaseHttpHandler<HttpRequest> {
    public HttpGetHandler(HttpRequest request) {
        super(request);
    }

    @Override
    public HttpResponse handle() {
        throw new IllegalStateException("Features not yet developed");
    }
}
