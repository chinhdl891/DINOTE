package com.example.dinote.views.fragments;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinote.R;
import com.example.dinote.views.activities.MainActivity;
import com.example.dinote.adapter.MotionAdapter;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databases.DinoteDataBase;
import com.example.dinote.databinding.FragmentCreateDinoteBinding;
import com.example.dinote.model.Dinote;
import com.example.dinote.model.Motion;
import com.example.dinote.model.Tag;
import com.example.dinote.utils.Constant;
import com.example.dinote.utils.ReDesign;
import com.example.dinote.views.customs.AddTagView;
import com.example.dinote.viewmodel.MotionViewModel;
import com.example.dinote.views.dialogs.SavedDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import top.defaults.colorpicker.ColorPickerPopup;


public class CreateDinoteFragment extends BaseFragment<FragmentCreateDinoteBinding> implements View.OnClickListener, MotionAdapter.EditMotionListener, AddTagView.TagListener, DrawFragment.SendUriListerner {
    private boolean isLove;
    private Motion mMotion;
    private LinearLayout lnlCreateListTag;
    private Dialog dialog;
    private int textColor;
    // public Dinote(int id, long date, String content, String title, int motion, String imageUri, String imageDes, List<Tag> tagList) {
    private String title;
    private String content;
    private int motion = 1;
    private String imageUri = "no_data";
    private String imageDes;
    private List<Tag> tagList;
    private int isLike;
    private long dateCreate;
    private PopupWindow popup;
    private static final String TAG = "CreateDinoteFragment";


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_create_dinote;
    }

    @Override
    protected void initViews(View rootView) {
        lnlCreateListTag = rootView.findViewById(R.id.lnl_create_list_tag);
        AddTagView addTagView = new AddTagView(mContext);
        addTagView.setTag(mBinding.lnlCreateListTag.getChildCount());
        addTagView.setEditTagListener(this);
        lnlCreateListTag.addView(addTagView);
//        imvDrawer = rootView.findViewById(R.id.imv_create_drawer);
        mBinding.imvCreateDrawer.setVisibility(View.GONE);
        mBinding.edtCreateDesDrawer.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMotion = (Motion) getArguments().getSerializable("obj_emoji");

        }


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

    }


    @Override
    protected void onClickViews() {
        mBinding.tvDateSelection.setOnClickListener(this);
        mBinding.lnlCrateStatus.setOnClickListener(this);
        mBinding.imvCreateCancel.setOnClickListener(this);
        mBinding.imvCreateTextCustomText.setOnClickListener(this);
        mBinding.imvCreateTextCancel.setOnClickListener(this);
        mBinding.imvCreateTextLove.setOnClickListener(this);
        mBinding.imvCreateTextTag.setOnClickListener(this);
        mBinding.imvCreateTextRemove.setOnClickListener(this);
        mBinding.imvCreateColorPicker.setOnClickListener(this);
        mBinding.imvCreateTextAlignLeft.setOnClickListener(this);
        mBinding.imvCreateTextAlignRight.setOnClickListener(this);
        mBinding.imvCreateTextItalic.setOnClickListener(this);
        mBinding.imvCreateTextBolder.setOnClickListener(this);
        mBinding.imvCreateTextEdit.setOnClickListener(this);
        mBinding.tvCreateSave.setOnClickListener(this);


    }

    @Override
    protected void setView() {
        setDateDefault();
        if (mMotion != null) {
            mBinding.imvCreateMotion.setImageResource(mMotion.getImgMotion());
            mBinding.edtCreateStatus.setText(getString(mMotion.getMotion()));
        }


    }

    @Override
    protected void setTypeView() {

    }


    @Override
    public void onClick(View view) {
        if (view == mBinding.tvDateSelection) {
            selectDate();
        } else if (view == mBinding.lnlCrateStatus) {
            getParamView(mBinding.lnlCrateStatus);
            getPoint(mBinding.lnlCrateStatus);
            if (getActivity() != null) {
                onShowDiaLogMotion(getActivity());
            }
        } else if (view.getId() == R.id.imv_create_cancel) {
            getActivity().onBackPressed();
        } else if (view.getId() == R.id.imv_create_text_custom_text) {
            mBinding.lnlCrateOption.setVisibility(View.GONE);
            mBinding.lnlCreateTextCustom.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.imv_create_text_cancel) {
            mBinding.lnlCrateOption.setVisibility(View.VISIBLE);
            mBinding.lnlCreateTextCustom.setVisibility(View.GONE);
        } else if (view.getId() == R.id.imv_create_text_love) {
            if (!isLove) {
                mBinding.imvCreateTextLove.setImageResource(R.drawable.ic_text_loved);
                isLike = 1;
            } else {
                mBinding.imvCreateTextLove.setImageResource(R.drawable.ic_text_love);
                isLike = 0;
            }
            isLove = !isLove;
            Log.e(TAG, "onClick: " + isLike);
        } else if (view.getId() == R.id.imv_create_text_tag) {
            addTag(lnlCreateListTag, lnlCreateListTag.getChildCount(), mContext);
        } else if (view.getId() == R.id.imv_create_text_remove) {
            onShowDialogRemove(Gravity.CENTER);
        } else if (view.getId() == R.id.btn_dialog_continues) {
            dialog.dismiss();
        } else if (view.getId() == R.id.btn_dialog_continues_cancel) {
            dialog.dismiss();
            getActivity().onBackPressed();
        } else if (view.getId() == R.id.imv_create_color_picker) {
            onShowDialogColorPicker();
        } else if (view.getId() == R.id.imv_create_text_align_left) {
            mBinding.edtCreateContent.setGravity(Gravity.LEFT);

        } else if (view.getId() == R.id.imv_create_text_align_right) {

            mBinding.edtCreateContent.setGravity(Gravity.RIGHT);
        } else if (view.getId() == R.id.imv_create_text_bolder) {

            mBinding.edtCreateContent.setTypeface(null, Typeface.BOLD);
        } else if (view.getId() == R.id.imv_create_text_italic) {

            mBinding.edtCreateContent.setTypeface(null, Typeface.ITALIC);
        } else if (view.getId() == R.id.imv_create_text_underline) {

            mBinding.edtCreateContent.setTypeface(null, Typeface.BOLD);
            underlineText();
        } else if (view.getId() == R.id.imv_create_text_edit) {
            MainActivity mainActivity = (MainActivity) getActivity();
            DrawFragment fragment = new DrawFragment();
            fragment.setSendUriListerner(this);
            assert mainActivity != null;
            mainActivity.loadFragment(fragment, Constant.DRAW_FRAGMENT);

        } else if (view.getId() == R.id.tv_create_save) {
            onSaveData();

        }


    }

    private void onSaveData() {
        // public Dinote(int id, long date, String content, String title, int motion, String imageUri, String imageDes, List<Tag> tagList)

        imageDes = mBinding.edtCreateDesDrawer.getText().toString();
        title = mBinding.edtCreateTitle.getText().toString();
        content = mBinding.edtCreateContent.getText().toString();
        if (imageDes.length() == 0) {
            imageDes = "No description";
        }
        if (title.length() == 0) {
            title = "No Title";
        }
        if (content.length() == 0) {
            content = "No Content";
        }
        Dinote dinote = new Dinote(
                0
                , dateCreate
                , content
                , title
                , motion
                , imageUri
                , imageDes
                , isLike
                , getListTag()
        );

        DinoteDataBase.getInstance(getActivity()).dinoteDAO().insertDinote(dinote);
        showDiaLogSaveSuccess();

    }

    private void showDiaLogSaveSuccess() {
        SavedDialog dialog = new SavedDialog(getActivity());
        dialog.show();
        dialog.setCallbackSaveDialog(new SavedDialog.CallbackSaveDialog() {
            @Override
            public void onClickSaved() {
                getActivity().onBackPressed();
            }
        });
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


    private void underlineText() {

    }

    private void onShowDialogColorPicker() {

        new ColorPickerPopup.Builder(getActivity())
                .initialColor(Color.RED) // Set initial color
                .enableBrightness(true) // Enable brightness slider or not
                .enableAlpha(true) // Enable alpha slider or not
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(mBinding.edtCreateContent, new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {
                        mBinding.edtCreateContent.setTextColor(color);
                        textColor = color;
                    }

                });

    }


    private void onShowDialogRemove(int gravity) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_remove_dinote);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        Button btnContinues = dialog.findViewById(R.id.btn_dialog_continues);
        Button btnCancel = dialog.findViewById(R.id.btn_dialog_continues_cancel);
        btnCancel.setOnClickListener(this);
        btnContinues.setOnClickListener(this);
        dialog.show();


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
        popup.setWidth((int) (0.8 * widthDisplay));
        popup.setHeight((int) (0.4 * heightDisplay));
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, (int) (pointViewX * 1.3) + 22, (int) (pointViewY * 1.1));


    }

    public void setDateDefault() {
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mBinding.tvDateSelection.setText(simpleDateFormat.format(calendar.getTime()));
        dateCreate = calendar.getTimeInMillis();

    }


    private void selectDate() {
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                dateCreate = calendar.getTimeInMillis();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                mBinding.tvDateSelection.setText(simpleDateFormat.format(calendar.getTime()));

            }
        }, year, month, day);
        datePickerDialog.show();
    }

    //    public void handleEmotion(Motion mMotion) {
//        mBinding.imvCreateMotion.setImageResource(mMotion.getImgMotion());
//        mBinding.edtCreateStatus.setText(getString(mMotion.getMotion()));
//    }
//
    @Override
    public void onSelectMotion(Motion motion) {

        mBinding.imvCreateMotion.setImageResource(motion.getImgMotion());
        mBinding.edtCreateStatus.setText(getString(motion.getMotion()));
        this.motion = motion.getId();
        popup.dismiss();


    }

    @Override
    public void onDeleteTag(int getTag) {
        lnlCreateListTag.removeViewAt(getTag);

    }

    @Override
    public void onAddTag() {
        addTag(lnlCreateListTag, lnlCreateListTag.getChildCount(), mContext);
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
    public void onSendDate(String uri) {
        imageUri = uri;
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

    private CreateDinoteListener createDinoteListener;

    public void setCreateDinoteListener(CreateDinoteListener createDinoteListener) {
        this.createDinoteListener = createDinoteListener;
    }

    public interface CreateDinoteListener {

        void onShowSaveComplete();

    }
}