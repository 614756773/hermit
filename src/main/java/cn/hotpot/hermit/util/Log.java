package cn.hotpot.hermit.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author qinzhu
 * @since 2020/4/13
 */
public class Log {
    private static final int DEBUG = 0;
    private static final int INFO = 1;
    private static final int ERROR = 2;
    private static final Map<Integer, String> MAP;
    private static int CURRENT_LEVEL = INFO;

    static {
        MAP = new HashMap<>();
        MAP.put(0, "debug");
        MAP.put(1, "info");
        MAP.put(2, "error");
    }

    public static void debug(String message) {
        print(DEBUG, message, System.out::println);
    }

    public static void info(String message) {
        print(INFO, message, System.out::println);
    }

    public static void error(String message) {
        print(ERROR, message, System.err::println);
    }

    private static void print(int level, String message, Consumer<Object> consumer) {
        if (level >= CURRENT_LEVEL) {
            consumer.accept(produceStr(message, level));
        }
    }

    private static StringBuilder produceStr(String message, int level) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTrace[4];
        LocalDateTime now = LocalDateTime.now();
        return new StringBuilder(now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .append("\t")
                .append(caller.getClassName()).append(".")
                .append(caller.getMethodName())
                .append(" ")
                .append(caller.getLineNumber())
                .append(" [")
                .append(MAP.get(level))
                .append("]:\t")
                .append(message);
    }

    public static void setLevel(String level) {
        switch (level) {
            case "debug":
                Log.CURRENT_LEVEL = 0;
                break;
            case "info":
                Log.CURRENT_LEVEL = 1;
                break;
            case "error":
                Log.CURRENT_LEVEL = 2;
                break;
            default:
                throw new IllegalArgumentException("Log level error");
        }
    }
}
