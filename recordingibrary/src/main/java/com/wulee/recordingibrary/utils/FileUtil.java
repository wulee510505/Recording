package com.wulee.recordingibrary.utils;

/**
 * Created by wulee on 2017/3/23 11:05
 */

public class FileUtil {

    /**
     * Byte与Byte的倍数
     */
    static int BYTE = 1;
    /**
     * KB与Byte的倍数
     */
    static int KB = 1024;
    /**
     * MB与Byte的倍数
     */
    static int MB = 1048576;
    /**
     * GB与Byte的倍数
     */
    static int GB = 1073741824;

    /**
     * 字节数转合适内存大小
     * <p>保留2位小数</p>
     *
     * @param byteNum 字节数
     * @return 合适内存大小
     */
    public static String byte2FitMemorySize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < KB) {
            return String.format("%.2fB", (double) byteNum + 0.0005);
        } else if (byteNum < MB) {
            return String.format("%.2fKB", (double) byteNum / KB + 0.0005);
        } else if (byteNum < GB) {
            return String.format("%.2fMB", (double) byteNum / MB + 0.0005);
        } else {
            return String.format("%.2fGB", (double) byteNum / GB + 0.0005);
        }
    }
}
