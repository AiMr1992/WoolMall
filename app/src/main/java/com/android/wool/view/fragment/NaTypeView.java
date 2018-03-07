package com.android.wool.view.fragment;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.GoodsCartsEntity;
import com.android.wool.model.entity.NaTypeEntity;
import java.util.List;
/**
 * Created by AiMr on 2017/9/28
 */
public interface NaTypeView extends IBaseView{
    void getTypeGoodsList(NaTypeEntity entity);
    void refreshRight(List<GoodsCartsEntity> bottomList);
}
