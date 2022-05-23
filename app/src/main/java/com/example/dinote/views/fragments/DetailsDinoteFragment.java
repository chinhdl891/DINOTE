package com.example.dinote.views.fragments;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinote.R;
import com.example.dinote.adapter.MotionAdapter;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databases.DinoteDataBase;
import com.example.dinote.databinding.FragmentDetailsDinoteBinding;
import com.example.dinote.model.Dinote;
import com.example.dinote.model.Motion;
import com.example.dinote.model.Tag;
import com.example.dinote.utils.Constant;
import com.example.dinote.utils.EditTextUtils;
import com.example.dinote.utils.ReDesign;
import com.example.dinote.viewmodel.MotionViewModel;
import com.example.dinote.views.activities.MainActivity;
import com.example.dinote.views.customs.AddTagView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DetailsDinoteFragment extends BaseFragment<FragmentDetailsDinoteBinding> implements View.OnClickListener, DrawFragment.SendUriListerner, AddTagView.TagListener, MotionAdapter.EditMotionListener {
    private Dinote mDinote;
    private String imgUri;
    private int isLike,motion;
    private List<Tag> tagList;
    private LinearLayout lnlListTag;
    private PopupWindow popup;



    private static final String TAG = "DetailsDinoteFragment";


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_details_dinote;
    }

    @Override
    protected void initViews(View rootView) {
        lnlListTag = rootView.findViewById(R.id.lnl_create_list_tag);
        getParamView(mBinding.lnlCrateStatus);
        getPoint(mBinding.lnlCrateStatus);
    }

    @Override
    protected void setView() {

        mBinding.lnlCrateOption.setVisibility(View.GONE);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDinote = (Dinote) bundle.getSerializable(Constant.SEND_DATA_OBJ_DINOTE);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mDinote.getDate());
        mBinding.tvDateSelection.setText(simpleDateFormat.format(calendar.getTime()));
        imgUri = mDinote.getImageUri();
        mBinding.edtCreateContent.setText(mDinote.getContent());
        mBinding.edtCreateDesDrawer.setText(mDinote.getImageDes());
        mBinding.edtCreateTitle.setText(mDinote.getTitle());
        Motion motion = MotionViewModel.motionList().get(mDinote.getMotion());
        mBinding.imvCreateMotion.setImageResource(motion.getImgMotion());
        mBinding.edtCreateStatus.setText(motion.getMotion());
        try {

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver()
                    , Uri.parse(mDinote.getImageUri()));
            int height = bitmap.getHeight();
            mBinding.imvCreateDrawer.getLayoutParams().height = height / 2;
            mBinding.imvCreateDrawer.setImageBitmap(bitmap);

        } catch (IOException e) {
            mBinding.imvCreateDrawer.setVisibility(View.GONE);
            mBinding.edtCreateDesDrawer.setVisibility(View.GONE);

        }

        isLike = mDinote.getIsLike();
        if (isLike == 0) {
            mBinding.imvDetailIsLoved.setImageResource(R.drawable.ic_text_love);
        } else {
            mBinding.imvDetailIsLoved.setImageResource(R.drawable.ic_text_loved);
        }
        tagList = new ArrayList<>();
        tagList = mDinote.getTagList();
        setListTag(tagList);

    }

    @Override
    protected void resizeViews() {

        ReDesign.resizeImage(mBinding.imvCreateCancel, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateTextCustomText, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateTextEdit, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateTextLove, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateTextRemove, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateTextTag, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateCancel, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateMotion, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextAlignLeft, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextAlignRight, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateColorPicker, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextCancel, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextBolder, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextItalic, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextUnderline, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextBullet, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextListNumber, 48, 48);
        ReDesign.resizeImage(mBinding.imvDetailIsLoved, 64, 64);
        ReDesign.resizeImage(mBinding.imvDetailIsDrop, 64, 64);

    }

    @Override
    protected void onClickViews() {

        mBinding.tvCreateSave.setOnClickListener(this);
        mBinding.imvCreateCancel.setOnClickListener(this);
        mBinding.imvDetailIsDrop.setOnClickListener(this);
        mBinding.imvDetailIsLoved.setOnClickListener(this);
        mBinding.imvCreateTextEdit.setOnClickListener(this);
        mBinding.lnlCrateStatus.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_create_save:
                if (!isEdit) {
                    mBinding.tvCreateSave.setText(R.string.save);
                    EditTextUtils.enableEditText(mBinding.edtCreateContent);
                    EditTextUtils.enableEditText(mBinding.edtCreateDesDrawer);
                    EditTextUtils.enableEditText(mBinding.edtCreateTitle);
                    mBinding.lnlCrateOption.setVisibility(View.VISIBLE);
                } else {
                    mBinding.tvCreateSave.setText(R.string.update);
                    EditTextUtils.disableEditText(mBinding.edtCreateContent);
                    EditTextUtils.disableEditText(mBinding.edtCreateDesDrawer);
                    EditTextUtils.disableEditText(mBinding.edtCreateTitle);
                    onSaveEditDinote();
                    getActivity().onBackPressed();
                }
                isEdit = !isEdit;
                break;
            case R.id.imv_create_cancel:
                getActivity().onBackPressed();
                break;
            case R.id.imv_detail_is_drop:
                DinoteDataBase.getInstance(getActivity()).dinoteDAO().deleteDinote(mDinote);
                getActivity().onBackPressed();
                break;
            case R.id.imv_detail_is_loved:
                if (isLike == 1) {
                    mBinding.imvDetailIsLoved.setImageResource(R.drawable.ic_text_love);
                    isLike = 0;
                } else {
                    mBinding.imvDetailIsLoved.setImageResource(R.drawable.ic_text_loved);
                    isLike = 1;
                }
                onSaveEditDinote();
                break;
            case R.id.imv_create_text_edit:
                MainActivity mainActivity = (MainActivity) getActivity();
                DrawFragment fragment = new DrawFragment();
                fragment.setSendUriListerner(this);
                assert mainActivity != null;
                mainActivity.loadFragment(fragment, Constant.DRAW_FRAGMENT);
                break;
            case R.id.lnl_crate_status:
                onShowDiaLogMotion(getActivity());
                break;

        }
    }

    private void onShowDiaLogMotion(@NonNull FragmentActivity context) {

        LinearLayout viewGroup = context.findViewById(R.id.cv_popup_motion);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.dialog_motion_pop_up, viewGroup, true);
        RecyclerView rcvMotion = layout.findViewById(R.id.rcv_pop_up_motion);
        rcvMotion.setLayoutManager(new GridLayoutManager(context, 3));
        MotionAdapter motionAdapter = new MotionAdapter();
        motionAdapter.setMotionList(MotionViewModel.motionList());
        rcvMotion.setAdapter(motionAdapter);
        motionAdapter.setEditMotionListener(this);

        popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth((int) (0.9 * widthDisplay));
        popup.setHeight((int) (0.6 * heightDisplay));
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAtLocation(layout, Gravity.CENTER_VERTICAL, (int) (pointViewX * 1.3) + 22, (int) (pointViewY * 1.1));
    }


    private void setListTag(List<Tag> tagList) {
        for (int i = 0; i < tagList.size(); i++) {
            AddTagView addTagView = new AddTagView(getContext());
            addTagView.setTag(i);
            addTagView.setUpString(tagList.get(i).getContentTag());
            addTagView.setEditTagListener(this);
            mBinding.lnlCreateListTag.addView(addTagView);
        }


    }

    @Override
    protected void setTypeView() {
        onShowDetail();
    }

    private void onShowDetail() {
        EditTextUtils.disableEditText(mBinding.edtCreateContent);
        EditTextUtils.disableEditText(mBinding.edtCreateDesDrawer);
        EditTextUtils.disableEditText(mBinding.edtCreateTitle);
    }


    private boolean isEdit;

    @SuppressLint("NonConstantResourceId")
    private void onSaveEditDinote() {
        mDinote.setContent(mBinding.edtCreateContent.getText().toString());
        mDinote.setImageDes(mBinding.edtCreateDesDrawer.getText().toString());
        mDinote.setTitle(mBinding.edtCreateTitle.getText().toString());
        mDinote.setImageUri(imgUri);
        mDinote.setIsLike(isLike);
        mDinote.setMotion(motion);
        mDinote.setTagList(getListTag());
        DinoteDataBase.getInstance(getActivity()).dinoteDAO().updateDinote(mDinote);
        getActivity().onBackPressed();
    }

    private List<Tag> getListTag() {
        tagList = new ArrayList<>();
        int size = mBinding.lnlCreateListTag.getChildCount();
        for (int i = 0; i < size; i++) {
            if (mBinding.lnlCreateListTag.getChildAt(i) instanceof AddTagView) {
                String tag = ((AddTagView) mBinding.lnlCreateListTag.getChildAt(i)).getTagString();
                if (tag.length() > 0) {
                    tagList.add(new Tag(0, tag));
                    if (DinoteDataBase.getInstance(getActivity()).tagDAO().getCount(tag) > 0) {
                        continue;
                    } else {
                        DinoteDataBase.getInstance(getActivity()).tagDAO().insertTag(new Tag(0, tag));
                    }
                }
            }
        }
        return tagList;

    }

    @Override
    public void onSendDate(String uri) {
        imgUri = uri;
        mBinding.imvCreateDrawer.setVisibility(View.VISIBLE);
        mBinding.edtCreateDesDrawer.setVisibility(View.VISIBLE);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(uri));

            int height = bitmap.getHeight();

            mBinding.imvCreateDrawer.getLayoutParams().height = (int) height / 2;

            mBinding.imvCreateDrawer.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(uri)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDeleteTag(int getTag) {
        mBinding.lnlCreateListTag.removeViewAt(getTag);
    }

    @Override
    public void onAddTag() {
        addTag(mBinding.lnlCreateListTag, mBinding.lnlCreateListTag.getChildCount(), getActivity());
    }

    private void addTag(LinearLayout lnlCreateListTag, int lnlChildCount, Context mContext) {

        if (lnlChildCount > 0) {
            if (lnlCreateListTag.getChildAt(lnlChildCount - 1) instanceof AddTagView) {
                int tagSize = (((AddTagView) lnlCreateListTag.getChildAt(lnlChildCount - 1)).getTagString().length());
                if (tagSize > 0) {
                    AddTagView addTagView = new AddTagView(mContext);
                    addTagView.setTag(lnlCreateListTag.getChildCount());
                    addTagView.setEditTagListener(this);
                    lnlCreateListTag.addView(addTagView);
                }
            }

        } else {
            AddTagView addTagView = new AddTagView(mContext);
            addTagView.setTag(lnlCreateListTag.getChildCount());
            addTagView.setEditTagListener(this);
            lnlCreateListTag.addView(addTagView);
        }

    }

    @Override
    public void onSelectMotion(Motion motion) {
        this.motion = motion.getId();
        mBinding.imvCreateMotion.setImageResource(motion.getImgMotion());
        mBinding.edtCreateStatus.setText(getString(motion.getMotion()));
        popup.dismiss();
    }
}