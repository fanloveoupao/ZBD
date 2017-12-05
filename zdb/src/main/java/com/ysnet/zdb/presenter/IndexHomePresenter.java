package com.ysnet.zdb.presenter;

import com.tgnet.basemvp.IView;
import com.tgnet.executor.ActionRunnable;
import com.ysnet.zdb.resource.AdvertisementsBean;
import com.ysnet.zdb.resource.HotProductBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fan-gk
 * @date 2017/12/4.
 */
public class IndexHomePresenter extends BasePresenter<IndexHomePresenter.IndexHomeView> {

    public IndexHomePresenter(IndexHomeView iView) {
        super(iView);
    }


    public interface IndexHomeView extends IView {

        void onInitList(List<HotProductBean> list);

        void onNoMoreData();

        void onInitHeaderList(List<AdvertisementsBean> list);
    }

    private final int limit = 20;
    private int start = 0;

    public void initData() {
        newActionBuilder()
                .setRunnable(new ActionRunnable() {
                    @Override
                    public void run() throws Exception {
                        List<HotProductBean> list = new ArrayList<>();
                        for (int i = 0; i < 20; i++) {
                            HotProductBean bean = new HotProductBean();
                            bean.name = "测试" + i;
                            bean.imgUrl = "www" + i;
                            bean.price = "21." + i;
                            list.add(bean);
                        }
                        getView().onInitList(list);
                    }
                })
                .setRunLoading()
                .run();
    }

    public void loadMoreData() {
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

    public void getHeaderListBean() {
        newActionBuilder()
                .setRunnable(new ActionRunnable() {
                    @Override
                    public void run() throws Exception {
                        List<AdvertisementsBean> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            AdvertisementsBean bean = new AdvertisementsBean();
                            bean.imgUrl = "www";
                            bean.productId = 123;
                            list.add(bean);
                        }
                        getView().onInitHeaderList(list);
                    }
                })
                .setRunBackground()
                .run();
    }

    public int getLimit() {
        return limit;
    }
}
