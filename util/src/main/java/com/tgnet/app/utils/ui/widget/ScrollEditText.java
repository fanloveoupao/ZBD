package com.tgnet.app.utils.ui.widget;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.tgnet.app.utils.ui.text.CopyStringUtil;

/**
 * Created by lzh on 2017/10/25.
 */

public class ScrollEditText extends EditText {
    private Context mContext;

    public ScrollEditText(Context context) {
        super(context);
        mContext=context;
    }

    public ScrollEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }

    public ScrollEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
    }

    private final int MOVE_SLOP =20;
    private int mOffsetHeight;
    private boolean mBottomFlag=false;
    private boolean isCanScroll=false;
    private float lastY=0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int paddingTop;
        int paddingBottom;
        int height;
        int layoutHeight;

        Layout layout=getLayout();
        layoutHeight=layout.getHeight();
        paddingBottom=layout.getBottomPadding();
        paddingTop=layout.getTopPadding();
        height=getHeight();

        mOffsetHeight=layoutHeight+paddingTop+paddingBottom-height;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            mBottomFlag=false;
            isCanScroll=false;
            lastY=0;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!mBottomFlag)
            getParent().requestDisallowInterceptTouchEvent(true);
        if(event.getAction()==MotionEvent.ACTION_MOVE){
            if(lastY==0)
                lastY=event.getRawY();

            if(Math.abs(lastY-event.getRawY())>MOVE_SLOP)
                if(!isCanScroll)
                    getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        isCanScroll=true;
        if(vert==mOffsetHeight || vert==0){
            getParent().requestDisallowInterceptTouchEvent(false);
            mBottomFlag=true;
        }
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if(id==android.R.id.paste){
            setText(getText()+CopyStringUtil.paste(mContext));
            setSelection(getText().length());
            return true;
        }
        return super.onTextContextMenuItem(id);
    }

}
