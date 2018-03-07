package com.android.wool.view.activity;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.SearchGoodsEntity;
import java.util.List;
/**
 * Created by AiMr on 2017/10/20
 */
public interface SearchGoodsListView extends IBaseView{
    int LINEAR_LAYOUT = 0;
    int GRID_LAYOUT = 1;
    void getSearchGoodsList(List<SearchGoodsEntity> list,boolean flag);
    void isLayoutType();
    void showLinearLayout();
    void showGridLayout();
    void refresh();
    void loading();
    void edit();
}
