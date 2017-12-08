package com.zbd.app.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tgnet.app.utils.ui.widget.TitleBar;
import com.ysnet.zdb.presenter.MyMessagePresenter;
import com.ysnet.zdb.resource.bean.MyMessageBean;
import com.zbd.app.BasePresenterActivity;
import com.zbd.app.R;
import com.zbd.app.activity.adapter.MyMessageAdapter;
import com.zbd.app.ioc.component.PresenterComponent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMessageActivity extends BasePresenterActivity<MyMessagePresenter, MyMessagePresenter.IMyMessageView>
        implements MyMessagePresenter.IMyMessageView, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private MyMessageAdapter mAdapter;

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);
        mTitleBar.setCenterText("我的消息");
        mTitleBar.setBackClick(this);
        mAdapter = new MyMessageAdapter(new ArrayList<MyMessageBean>());
        mAdapter.openLoadMore(getPresenter().getLimit());
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void injectPresenter(PresenterComponent component, MyMessagePresenter presenter) {
        component.inject(presenter);
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
