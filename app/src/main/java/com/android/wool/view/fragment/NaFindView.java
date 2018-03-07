package com.android.wool.view.fragment;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.MaterialEntity;
import java.util.List;
/**
 * Created by AiMr on 2017/11/9
 */
public interface NaFindView extends IBaseView{
    void getMaterialList(List<MaterialEntity> list,boolean flag);
}
