package com.ysnet.zdb.presenter;

import com.tgnet.basemvp.IView;

/**
 * @author fan-gk
 * @date 2017/12/1.
 */
public class IndexPresenter extends BasePresenter<IndexPresenter.IndexView>{
    public IndexPresenter(IndexView iView) {
        super(iView);
    }

    public interface IndexView extends IView{

    }
}
