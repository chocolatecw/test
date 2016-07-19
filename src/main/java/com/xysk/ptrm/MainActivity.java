package com.xysk.ptrm;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

public class MainActivity extends AppCompatActivity implements ExpandableListView.OnGroupClickListener, PullToRefreshBase.OnRefreshListener2<ExpandableListView>{

    PullToRefreshExpandableListView mExpandList;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();

        mExpandList = (PullToRefreshExpandableListView) findViewById(R.id.expand_list);
        mExpandList.getRefreshableView().setGroupIndicator(null);
        mExpandList.getRefreshableView().setDivider(null);
        mExpandList.getRefreshableView().setSelector(android.R.color.transparent);
        mExpandList.getRefreshableView().setOnGroupClickListener(this);
        mExpandList.setOnRefreshListener(this);
        // set mode to BOTH
        mExpandList.setMode(PullToRefreshBase.Mode.BOTH);
        mExpandList.getLoadingLayoutProxy(true, true).setPullLabel("刷新完成");
        mExpandList.getLoadingLayoutProxy(true, true).setRefreshingLabel("正在刷新");
        mExpandList.getLoadingLayoutProxy(true, true).setReleaseLabel("放开以刷新...");
    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
        return false;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
        new GetDataTask().execute(true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
        new GetDataTask().execute(false);
    }

    class GetDataTask extends AsyncTask<Boolean, Void, Boolean> { //这里必须是Void，而不是void
        @Override
        protected Boolean doInBackground(Boolean... bool) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return bool[0];
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if(bool) {
                Toast.makeText(MainActivity.this, "onPullDownToRefresh...", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MainActivity.this, "onPullUpToRefresh...", Toast.LENGTH_SHORT).show();
            }

            mExpandList.onRefreshComplete();
        }
    }
}
