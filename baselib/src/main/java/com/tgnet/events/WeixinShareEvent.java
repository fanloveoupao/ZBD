package com.tgnet.events;

/**
 * Created by lzh on 2017/9/15.
 */

public class WeixinShareEvent {
    public WeixinShareEvent(String result) {
        this.result = result;
    }

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
