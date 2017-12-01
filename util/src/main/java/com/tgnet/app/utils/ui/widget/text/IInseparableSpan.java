package com.tgnet.app.utils.ui.widget.text;

/**
 * Created by fan-gk on 2017/4/26.
 * 表示一个完整的span，让span继承它并添加InseparableTextWatcher可实现span自动删除
 * @see InseparableTextWatcher
 */
public interface IInseparableSpan {
    String getDisplayText();
}