package com.example.dinote.viewmodel;

import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

public class CreateDenoteViewModel extends ViewModel {

    private String content;
    private boolean isLike;
    private String title;
    private ImageView imvCreateCancel, imvCreateTextCustom, imvCreatePenEdit;
    private ImageView imvCreateLove, imvCreateDrop, imvCreateTag;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public ImageView getImvCreateCancel() {
        return imvCreateCancel;
    }

    public void setImvCreateCancel(ImageView imvCreateCancel) {
        this.imvCreateCancel = imvCreateCancel;
    }

    public ImageView getImvCreateTextCustom() {
        return imvCreateTextCustom;
    }

    public void setImvCreateTextCustom(ImageView imvCreateTextCustom) {
        this.imvCreateTextCustom = imvCreateTextCustom;
    }

    public ImageView getImvCreatePenEdit() {
        return imvCreatePenEdit;
    }

    public void setImvCreatePenEdit(ImageView imvCreatePenEdit) {
        this.imvCreatePenEdit = imvCreatePenEdit;
    }

    public ImageView getImvCreateLove() {
        return imvCreateLove;
    }

    public void setImvCreateLove(ImageView imvCreateLove) {
        this.imvCreateLove = imvCreateLove;
    }

    public ImageView getImvCreateDrop() {
        return imvCreateDrop;
    }

    public void setImvCreateDrop(ImageView imvCreateDrop) {
        this.imvCreateDrop = imvCreateDrop;
    }

    public ImageView getImvCreateTag() {
        return imvCreateTag;
    }

    public void setImvCreateTag(ImageView imvCreateTag) {
        this.imvCreateTag = imvCreateTag;
    }
}
