package com.teethen.xsdk.ninegrid.complex.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.teethen.sdk.xhttp.okgo.OkHttp;
import com.teethen.sdk.xhttp.okgo.cache.CacheMode;
import com.teethen.sdk.xhttp.okgo.model.HttpParams;
import com.teethen.sdk.xhttp.okgo.model.Response;
import com.teethen.sdk.xrefresh.refreshmore.PtrClassicFrameLayout;
import com.teethen.sdk.xrefresh.refreshmore.PtrDefaultHandler;
import com.teethen.sdk.xrefresh.refreshmore.PtrFrameLayout;
import com.teethen.xsdk.R;
import com.teethen.xsdk.ninegrid.complex.news.bean.NewsContent;
import com.teethen.xsdk.ninegrid.complex.news.bean.NewsImage;
import com.teethen.xsdk.test.PhotoViewTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFragment extends Fragment {

    @BindView(R.id.ptr) PtrFrameLayout ptr;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<NewsContent> newsContentList;
    private NewsContentAdapter mAdapter;
    private String channelId;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        channelId = bundle.getString("channelId");
        page = bundle.getInt("page");

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new NewsContentAdapter(getActivity(), new ArrayList<NewsContent>());
        recyclerView.setAdapter(mAdapter);

        initData(false);

        //ptr.setLastUpdateTimeRelateObject(this);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData(true);
            }
        });

        return view;
    }

    private void initData(final boolean isMore) {
        if(newsContentList==null) newsContentList = new ArrayList<>();

        HttpParams params = new HttpParams();
        params.put("channelId", channelId);
        params.put("page", String.valueOf(page));
        OkHttp.<String>get(Urls.NEWS)
                .tag(this)
                .params(params)
                .cacheKey("NEWS")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new NewsCallBack() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String s = response.body();
                            JSONArray object = new JSONObject(s).getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist");
                            Type newsContentType = new TypeToken<List<NewsContent>>() {}.getType();
                            if (isMore) {
                                List<NewsContent> more = new Gson().fromJson(object.toString(), newsContentType);
                                newsContentList.addAll(0, more);
                            } else {
                                newsContentList = new Gson().fromJson(object.toString(), newsContentType);
                            }
                            mAdapter.setData(newsContentList);
                            page++;
                            ptr.refreshComplete();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        if (newsContentList.size() == 0) {
            String[] channels = new String[]{"新华网","腾讯网","网易新闻","搜狐网","新浪网",
                    "乐视网","中华网","央视新闻","淘宝网","百度新闻"};
            List<String> bdImgs = PhotoViewTest.getBaiduImgs();

            /* Random random = new Random(9);
            NewsContent nc1 = new NewsContent();
            nc1.setChannelId("新浪");
            nc1.setChannelName("新浪");
            nc1.setDesc("厉害了，我的国! - 新浪");
            NewsImage img1 = new NewsImage();
            img1.setUrl(bdImgs.get(random.nextInt(9)));
            img1.setWidth(30);
            img1.setHeight(30);
            NewsImage img2 = new NewsImage();
            img2.setUrl(bdImgs.get(random.nextInt(9)));
            img2.setWidth(30);
            img2.setHeight(30);
            List<NewsImage> newsImages = new ArrayList<>();
            newsImages.add(img1);
            newsImages.add(img2);
            nc1.setImageUrls(newsImages);
            newsContentList.add(nc1);*/

            Random random = new Random(9);
            for(int i=0; i<10; i++) {
                NewsContent nc = new NewsContent();
                nc.setChannelId(channels[i].substring(0, 2));
                nc.setChannelName(channels[i]);
                nc.setDesc("厉害了，我的国! - " + channels[random.nextInt(9)]);

                NewsImage img1 = new NewsImage();
                img1.setUrl(bdImgs.get(random.nextInt(9)));
                img1.setWidth(30);
                img1.setHeight(30);
                NewsImage img2 = new NewsImage();
                img2.setUrl(bdImgs.get(random.nextInt(9)));
                img2.setWidth(30);
                img2.setHeight(30);
                List<NewsImage> newsImages = new ArrayList<>();
                newsImages.add(img1);
                newsImages.add(img2);
                nc.setImageUrls(newsImages);

                newsContentList.add(nc);
            }

            mAdapter.setData(newsContentList);
            page++;
            ptr.refreshComplete();
        }
    }
}
