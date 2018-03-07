package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.interactor.SearchKeyInteractor;
import com.android.wool.model.interactor.SearchKeyInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.SearchKeyView;
import org.xutils.x;
import java.util.List;
/**
 * Created by AiMr on 2017/10/14
 */
public class SearchKeyPresenterImpl extends DetachController<SearchKeyView> implements SearchKeyPresenter{
    private SearchKeyInteractor interactor;
    public SearchKeyPresenterImpl(SearchKeyView mView) {
        super(mView);
        interactor = new SearchKeyInteractorImpl();
    }

    @Override
    public void getHotSearch(final Context context) {
        interactor.getHotSearch(
                new SearchKeyInteractor.HotSearchListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            List<String> list = response.getStringList("keyword");
                            mView.getHotList(list);
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
                }
        );
    }
}
