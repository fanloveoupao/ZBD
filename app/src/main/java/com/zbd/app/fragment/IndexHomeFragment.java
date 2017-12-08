package com.zbd.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.ysnet.zdb.presenter.IndexHomePresenter;
import com.ysnet.zdb.resource.bean.AdvertisementsBean;
import com.ysnet.zdb.resource.bean.HotProductBean;
import com.zbd.app.R;
import com.zbd.app.activity.ShareAppActivity;
import com.zbd.app.activity.adapter.HotProductAdapter;
import com.zbd.app.activity.home.BeVipActivity;
import com.zbd.app.activity.home.MyMessageActivity;
import com.zbd.app.ioc.component.PresenterComponent;
import com.zbd.app.widget.AdvertisementBanner;
import com.zbd.app.widget.LocalImageHolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexHomeFragment extends IocPresenterFragment<IndexHomePresenter, IndexHomePresenter.IndexHomeView>
        implements IndexHomePresenter.IndexHomeView, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private HotProductAdapter mAdapter;
    private View mHeaderView;
    private AdvertisementBanner mBanner;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_be_vip_lay:
                    getBaseActivity().launch(BeVipActivity.class, false);
                    break;
                case R.id.ll_share_lay:
                    getBaseActivity().launch(ShareAppActivity.class, false);
                    break;
            }
        }
    };

    public IndexHomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index_home, container, false);
        ButterKnife.bind(this, view);
        mHeaderView = inflater.inflate(R.layout.header_home_lay, container, false);
        initView();
        initListener();
        initData();
        return view;
    }

    private void initView() {
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getBaseActivity(), 2));
        mAdapter = new HotProductAdapter(new ArrayList<HotProductBean>());
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.addHeaderView(mHeaderView);
        mAdapter.openLoadMore(getPresenter().getLimit());
        mRecyclerView.setAdapter(mAdapter);
        mBanner = mHeaderView.findViewById(R.id.advertBanner);
    }

    private void initListener() {
        mHeaderView.findViewById(R.id.ll_be_vip_lay).setOnClickListener(onClickListener);
        mHeaderView.findViewById(R.id.ll_share_lay).setOnClickListener(onClickListener);
    }

    private void initData() {
        getPresenter().initData();
        getPresenter().getHeaderListBean();
    }

    @Override
    protected void injectPresenter(PresenterComponent component, IndexHomePresenter presenter) {
        component.inject(presenter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.ll_search_lay, R.id.iv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search_lay:
                break;
            case R.id.iv_message:
                getBaseActivity().launch(MyMessageActivity.class,false);
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        getPresenter().loadMoreData();
    }

    @Override
    public void onRefresh() {
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onInitList(List<HotProductBean> list) {
        mAdapter.setNewData(list);
    }

    @Override
    public void onNoMoreData() {
        mAdapter.addNoMoreDataFooter(getContext(), mRecyclerView);
    }

    @Override
    public void onInitHeaderList(List<AdvertisementsBean> list) {
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, list);
        if (list.size() <= 1)
            mBanner.setCanLoop(false);
        else {
            mBanner.setCanLoop(true);
            mBanner.startTurning(3000);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null)
            mBanner.startTurning(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        if (mBanner != null)
            mBanner.stopTurning();
    }
}
