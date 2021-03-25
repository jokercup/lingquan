package com.example.lingquan.view;

import com.example.lingquan.base.IBaseCallback;
import com.example.lingquan.model.domain.KoulingResult;

/**
 * Created by RenChao on 2020/5/15.
 */
public interface IkoulingCallBack extends IBaseCallback {

    void onKoulingLoad(String cover, KoulingResult kl);
}
