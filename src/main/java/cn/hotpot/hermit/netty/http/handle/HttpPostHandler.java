package cn.hotpot.hermit.netty.http.handle;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qinzhu
 * @since 2020/4/14
 */
public class HttpPostHandler extends BaseHttpHandler<FullHttpRequest> {
    /**
     * Temporary directory for storing files
     */
    private final String temporaryDirectory = "hermit_temp";

    /**
     * file count
     * Prevent simultaneous generation of files with the same name
     */
    private AtomicInteger fileCount = new AtomicInteger(0);

    public HttpPostHandler(FullHttpRequest request) {
        super(request);
    }

    @Override
    public HttpResponse handle() {
        FullHttpRequest req = request;
        return doHandle(req);
    }

    private HttpResponse doHandle(FullHttpRequest request) {
        HttpHeaders headers = request.headers();
        if ("multipart/form-data".equals(headers.get("content-type"))) {
            uploadFile(request);
        } else {
            throw new IllegalStateException("Features not yet developed");
        }
        return null;
    }

    private void uploadFile(FullHttpRequest request) {
        ByteBuf byteBuf = request.content();
        StringBuilder sb = new StringBuilder(temporaryDirectory);
        try {
            StringBuilder tmpFile = sb.append(System.currentTimeMillis()).append(fileCount.getAndIncrement());
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(tmpFile.toString())));
            byteBuf.readBytes(os, byteBuf.readableBytes());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
