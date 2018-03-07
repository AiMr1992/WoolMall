package com.android.wool.view.fragment;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.HomeEntity;
import com.android.wool.model.entity.RecommendEntity;
import java.util.List;
/**
 * Created by AiMr on 2017/9/26
 */
public interface HomeView extends IBaseView{
    void getHome(HomeEntity entity);
    void getHomeList(List<RecommendEntity> list,boolean flag);
}
