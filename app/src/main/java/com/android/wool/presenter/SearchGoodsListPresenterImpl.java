package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.SearchGoodsEntity;
import com.android.wool.model.interactor.IndexOrderInteractor;
import com.android.wool.model.interactor.IndexOrderInteractorImpl;
import com.android.wool.model.interactor.SearchKeyGoodsListInteractor;
import com.android.wool.model.interactor.SearchKeyGoodsListInteractorImpl;
import com.android.wool.model.interactor.TypeGoodsListInteractor;
import com.android.wool.model.interactor.TypeGoodsListInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.SearchGoodsListView;
import org.xutils.x;
import java.util.List;
/**
 * Created by AiMr on 2017/10/20
 */
public class SearchGoodsListPresenterImpl extends DetachController<SearchGoodsListView>
        implements SearchGoodsListPresenter{
    private SearchKeyGoodsListInteractor keyGoodsListInteractor;
    private TypeGoodsListInteractor listInteractor;
    private IndexOrderInteractor orderInteractor;
    public SearchGoodsListPresenterImpl(SearchGoodsListView mView) {
        super(mView);
        keyGoodsListInteractor = new SearchKeyGoodsListInteractorImpl();
        listInteractor = new TypeGoodsListInteractorImpl();
        orderInteractor = new IndexOrderInteractorImpl();
    }

    @Override
    public void getSearchGoodsList(final Context context, String keywords, String sales_num_order,
                                   String price_order, String time_order,String page) {
        keyGoodsListInteractor.getSearchGoodsList(keywords, sales_num_order, price_order, time_order,page,
                new SearchKeyGoodsListInteractor.SearchKeyGoodsListListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            List<SearchGoodsEntity> mList = response.parsFromData("list",new SearchGoodsEntity());
                            mView.getSearchGoodsList(mList,response.isLoad());
                        }
                        disMissProgress();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                        disMissProgress();
                    }

                    @Override
                    public void onFailed() {
                        disMissProgress();
                    }
                });
    }

    @Override
    public void getTypeListId(final Context context,String car_id, String sales_num_order, String price_order, String time_order,String page) {
        listInteractor.getTypeList(car_id, sales_num_order, price_order,time_order,page,
                new TypeGoodsListInteractor.TypeGoodsListListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            List<SearchGoodsEntity> mList = response.parsFromData("list",new SearchGoodsEntity());
                            mView.getSearchGoodsList(mList,response.isLoad());
                        }
                        disMissProgress();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                        disMissProgress();
                    }

                    @Override
                    public void onFailed() {
                        disMissProgress();
                    }
                });
    }

    @Override
    public void getIndexOrder(String type, String page) {
        orderInteractor.getIndexOrder(type, page,
                new IndexOrderInteractor.IndexOrderListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            List<SearchGoodsEntity> mList = response.parsFromData("list",new SearchGoodsEntity());
                            mView.getSearchGoodsList(mList,response.isLoad());
                        }
                        disMissProgress();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                        disMissProgress();
                    }

                    @Override
                    public void onFailed() {
                        disMissProgress();
                    }
                });
    }
}
