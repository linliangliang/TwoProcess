package liang.wust.com.twoprocess.constans;

/**
 * Created by lenovo on 2019/8/5.
 */

public enum Constans {
    //枚举类型实现单例
    INSTANCE;
    public static int openServiceDefend = 1;
    public static int stopServiceDefend = 0;
    public static int isOpenServiceDefend = 1;//是否开启进程守护，默认开启

    public static void setIsOpenServiceDefend(int isOpenServiceDefend) {
        Constans.isOpenServiceDefend = isOpenServiceDefend;
    }
}
