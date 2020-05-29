package cn.hotpot.hermit.netty;

import cn.hotpot.hermit.netty.http.RequestSelector;
import cn.hotpot.hermit.util.Log;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author qinzhu
 * @since 2020/4/14
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HttpResponse response = RequestSelector
                .selectHandler((HttpRequest) msg)
                .handle();

        ctx.writeAndFlush(response);
        FullHttpRequest request = (FullHttpRequest) msg;
        ByteBuf byteBuf = request.content();
        HttpHeaders headers = request.headers();
        headers.entries().forEach(e -> Log.debug(String.format("%s: %s", e.getKey(), e.getValue())));

        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        int count = 0;
        int begin = 0;
        for (int i = 0; i < bytes.length - 1; i++) {
            if (bytes[i] == 13 && bytes[i + 1] == 10) {
                String x = new String(bytes, begin, i, "UTF-8");
                System.out.println(x);
                System.out.println(count++);
                begin = i + 2;
            }
        }
//        System.out.println(new String(bytes, StandardCharsets.UTF_8));

//        File file = new File("C:\\Users\\HotPot\\Desktop\\新建文件夹\\test.png");
//        FileOutputStream fos = new FileOutputStream(file);
//        byteBuf.readBytes(fos, byteBuf.readableBytes());
        write(ctx);
    }

    private void write(ChannelHandlerContext ctx) {
        byte[] content = "你好啊".getBytes();

        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content));
        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

        response.headers().set(CONNECTION, KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        Log.debug("over");
    }
}
