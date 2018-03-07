package com.android.wool.model.db;
import org.xutils.DbManager;
import org.xutils.x;
import java.util.concurrent.atomic.AtomicInteger;
public class DBHelper {

    private static final String DB_MANE = "db_cyb";
    private static final int DB_VERSION = 3;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private DbManager dbManager;

    /**
     * 获取数据库配置信息
     * @return
     */
    private static final DbManager.DaoConfig DAO_CONFIG = getConfig();

    private static final DbManager.DaoConfig getConfig(){
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName(DB_MANE);
        daoConfig.setDbVersion(DB_VERSION);
        return daoConfig;
    }

    private static DBHelper dbHelper = new DBHelper();

    private DBHelper(){

    }

    public static DBHelper getInstance(){
        return dbHelper;
    }

    /**
     * 获取数据库管理对象
     * @return
     */
    public synchronized DbManager getDB(){
        dbManager = x.getDb(DAO_CONFIG);
        return dbManager;
    }
}