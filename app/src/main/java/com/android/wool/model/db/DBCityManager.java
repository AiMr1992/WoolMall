package com.android.wool.model.db;
import android.database.Cursor;
import com.android.wool.model.entity.CityInfoEntity;
import com.android.wool.util.LogUtil;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import java.util.List;
public class DBCityManager{
    private DbManager dbManager;
    public DBCityManager() {
        dbManager = DBHelper.getInstance().getDB();
    }

    /**
     * 根据父编码获取子区域
     * @param pCode
     * @return
     */
    public List<CityInfoEntity> selectCityByPCode(String pCode){
        if(dbManager != null) {
            try {
                List<CityInfoEntity> cityInfoEntities = dbManager.selector(CityInfoEntity.class)
                        .where("p_city_code", "=", pCode).findAll();
                return cityInfoEntities;
            }catch(DbException e){
                LogUtil.e(e.getMessage());
            }
        }
        return null;
    }

    /**
     * 判断本地是否有城市信息
     * @return
     */
    public boolean hasCityData(){
        if(dbManager != null){
            try{
                long count = dbManager.selector(CityInfoEntity.class).count();
                if(count > 0){
                    return true;
                }
            }catch (DbException e){
                LogUtil.e(e.getMessage());
            }
        }
        return false;
    }

    /**
     * 根据类型获取地区
     * @param type
     * @return
     */
    public List<CityInfoEntity> selectByType(String type){
        if(dbManager != null){
            Cursor cursor = null;
            try{
                List<CityInfoEntity> cityInfoEntities = dbManager.selector(CityInfoEntity.class)
                        .where("p_city_code", "=", type).findAll();
                return cityInfoEntities;
            }catch (DbException e){
                LogUtil.e(e.getMessage());
            }finally {
                if(cursor != null){
                    cursor.close();
                }
            }
        }
        return null;
    }

    public boolean insertData(Object o){
        if(dbManager != null){
            try {
                dbManager.saveOrUpdate(o);
                return true;
            }catch(DbException e){
                LogUtil.e(e.getMessage());
            }
        }
        return false;
    }

    public void closeDB(){
//        DBHelper.getInstance().closeDB();
    }
}
