package com.android.wool.view.activity;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.GoodsInfoEntity;
import com.android.wool.model.entity.SpecEntity;
import java.util.List;
/**
 * Created by AiMr on 2017/10/13
 */
public interface GoodsInfoView extends IBaseView{
    void getGoodsInfo(GoodsInfoEntity entity,String cart_num,String user_code);
    void houseGoods(boolean flag);
    void goods();
    void addCart(String number);
    void getSpecList(List<SpecEntity> list);
}
