package com.android.wool.presenter;
import com.android.wool.base.DetachController;
import com.android.wool.view.activity.MsgListView;
import com.qiyukf.unicorn.api.pop.POPManager;
/**
 * Created by AiMr on 2017/11/29
 */
public class MsgPresenterImpl extends DetachController<MsgListView> implements MsgPresenter{
    public MsgPresenterImpl(MsgListView mView) {
        super(mView);
    }

    @Override
    public void delete(String shopId) {
        showLoading();
        POPManager.deleteSession(shopId,true);
    }
}
