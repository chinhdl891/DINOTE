package com.example.dinote.views.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.dinote.R;
import com.example.dinote.utils.ReDesign;

public class AddTagView extends ConstraintLayout implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = "AddTagView";
    private TagListener tagListener;
    private LinearLayout llAddTag;
    private Context mContext;
    private View mRootView;
    private EditText edtTagAdd;
    private ImageView imvTagCancel, imvTagAdd;

    public AddTagView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public AddTagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = inflater.inflate(R.layout.add_tag, this, true);
        llAddTag = mRootView.findViewById(R.id.lnl_add_tag);
        edtTagAdd = mRootView.findViewById(R.id.edt_add_tag);
        imvTagCancel = mRootView.findViewById(R.id.imv_tag_cancel);
        imvTagAdd = mRootView.findViewById(R.id.imv_add_tag);
        imvTagCancel.setOnClickListener(this::onClick);
        ReDesign.resizeImage(imvTagAdd, 48, 44);
        ReDesign.resizeImage(imvTagCancel, 52, 52);
        llAddTag.setBackgroundResource(R.drawable.un_focused_background);
        imvTagCancel.setVisibility(INVISIBLE);
        edtTagAdd.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    imvTagCancel.setVisibility(VISIBLE);
                    llAddTag.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.on_focused_background));
                } else {
                    llAddTag.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.un_focused_background));
                    imvTagCancel.setVisibility(INVISIBLE);
                    edtTagAdd.setHint(mContext.getString(R.string.add_tag));
                }
            }
        });
        edtTagAdd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (getTagString().length() > 0) {
                        tagListener.onAddTag();
                        edtTagAdd.setHint("");
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

    }

    public String getTagString() {
        return edtTagAdd.getText().toString().trim();
    }

    public void setUpString(String data) {
        edtTagAdd.setHint("");
        edtTagAdd.setText(data);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imv_tag_cancel) {
            tagListener.onDeleteTag((int) this.getTag());
        }

    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    public void setEditTagListener(TagListener tagListener) {
        this.tagListener = tagListener;
    }

    public interface TagListener {
        void onDeleteTag(int getTag);

        void onAddTag();

    }


}
