package com.ysnet.zdb.presenter;

import com.tgnet.basemvp.IView;
import com.tgnet.executor.ActionRunnable;
import com.ysnet.zdb.resource.bean.IndexShopCartBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fan-gk
 * @date 2017/12/11.
 */
public class IndexShopCartPresenter extends BasePresenter<IndexShopCartPresenter.IndexShopCartView> {

    public IndexShopCartPresenter(IndexShopCartView iView) {
        super(iView);
    }


    public interface IndexShopCartView extends IView {

        void onInitSuccess(List<IndexShopCartBean> list);

        void onNoMoreData();
    }


    private final int limit = 20;
    private int start = 0;

    public void initData() {
        newActionBuilder()
                .setRunnable(new ActionRunnable() {
                    @Override
                    public void run() throws Exception {
                        List<IndexShopCartBean> list = new ArrayList<>();
                        for (int i = 0; i < 20; i++) {
                            IndexShopCartBean bean = new IndexShopCartBean();
                            bean.content = "良品铺子 喂爱礼盒 10袋装 坚果零食大礼包 送礼 送女友";
                            bean.count = 2;
                            bean.imgUrl = "";
                            bean.price = "12.00";
                            list.add(bean);
                        }
                        getView().onInitSuccess(list);
                        getView().onNoMoreData();
                    }
                })
                .setRunBackground()
                .run();
    }

    public void onLoadMoreRequested() {
        newActionBuilder()
                .setRunnable(new ActionRunnable() {
                    @Override
                    public void run() throws Exception {

                        getView().onNoMoreData();
                    }
                })
                .setRunBackground()
                .run();
    }

    public int getLimit() {
        return limit;
    }
}
