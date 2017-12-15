package com.zbd.app.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.tgnet.app.utils.ui.widget.TitleBar;
import com.ysnet.zdb.presenter.IndexShopCartPresenter;
import com.ysnet.zdb.resource.bean.IndexShopCartBean;
import com.zbd.app.R;
import com.zbd.app.activity.adapter.IndexShopCartAdapter;
import com.zbd.app.ioc.component.PresenterComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndexShopCartFragment extends IocPresenterFragment<IndexShopCartPresenter, IndexShopCartPresenter.IndexShopCartView>
        implements IndexShopCartPresenter.IndexShopCartView {


    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private IndexShopCartAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_index_shop_cart, container, false);
        ButterKnife.bind(this, view);
        mTitleBar.setLeftViewIsGone();
        mTitleBar.setCenterText("购物车");
        mTitleBar.setRightText("管理");
        mTitleBar.setRightViewIsVisible();
        initView();
        initData();
        return view;
    }

    private void initView() {
        mAdapter = new IndexShopCartAdapter(new ArrayList<IndexShopCartBean>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.openLoadMore(getPresenter().getLimit());
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
            getPresenter().onLoadMoreRequested();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        getPresenter().initData();
    }

    @Override
    protected void injectPresenter(PresenterComponent component, IndexShopCartPresenter presenter) {
        component.inject(presenter);
    }

    @Override
    public void onInitSuccess(List<IndexShopCartBean> list) {
        mAdapter.setNewData(list);
    }

    @Override
    public void onNoMoreData() {
        mAdapter.addNoMoreDataFooter(getContext(), mRecyclerView);
    }
}
