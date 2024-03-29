package com.example.biz3slbappcomm.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.biz3slbappcomm.api.SCommonApi;
import com.example.biz3slbappcomm.bean.SNew2IndexLianxiBean;
import com.example.biz3slbappcomm.bean.SPaySuccessBean;
import com.example.biz3slbappcomm.view.SNew2IndexLianxiView;
import com.example.biz3slbappcomm.view.SPaySuccessView;
import com.haier.cellarette.libmvp.mvp.Presenter;
import com.haier.cellarette.libretrofit.RetrofitNetNew;
import com.haier.cellarette.libretrofit.utils.BanbenUtils;
import com.haier.cellarette.libretrofit.utils.ResponseSlbBean;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SNew2IndexLianxiPresenter extends Presenter<SNew2IndexLianxiView> {

    public void getNew2IndexLianxiData(String type) {
        JSONObject requestData = new JSONObject();
        requestData.put("type", type);
//        requestData.put("limit", limit);

//        requestData.put("token", CommonUtil.getEncryptToken(id, url_me, date, ((JSONObject) JSON.toJSON(params)).toJSONString()));
//        requestData.put("params", JSON.toJSON(params));

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(SCommonApi.class, getIdentifier()).get_index_new2_one(BanbenUtils.getInstance().getVersion(),
                BanbenUtils.getInstance().getImei(), BanbenUtils.getInstance().getToken(),requestBody).enqueue(new Callback<ResponseSlbBean<SNew2IndexLianxiBean>>() {
            @Override
            public void onResponse(Call<ResponseSlbBean<SNew2IndexLianxiBean>> call, Response<ResponseSlbBean<SNew2IndexLianxiBean>> response) {
                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() != 0) {
                    getView().OnNew2IndexLianxiNodata(response.body().getMsg());
                    return;
                }
                getView().OnNew2IndexLianxiSuccess(response.body().getData());
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseSlbBean<SNew2IndexLianxiBean>> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                getView().OnNew2IndexLianxiFail(string);
                call.cancel();
            }
        });

    }

}
