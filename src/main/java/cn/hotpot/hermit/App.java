package cn.hotpot.hermit;

import cn.hotpot.hermit.netty.Server;
import cn.hotpot.hermit.util.Log;

/**
 * @author qinzhu
 * @since 2020/4/13
 */
public class App {
    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            Log.setLevel(args[0]);
        }
        Log.info("「hermit」 start");
        Server.create().start();
    }
}
