package com.example.dinote.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.dinote.R;
import com.example.dinote.model.Motion;

import java.util.ArrayList;
import java.util.List;

public class MotionViewModel extends ViewModel {
public List<Motion> motionList(){
    List<Motion> motionList = new ArrayList<>();
    motionList.add(new Motion(1, R.drawable.ic_motion_item_fun,"Vui vẻ"));
    motionList.add(new Motion(1, R.drawable.ic_motion_item_happy,"Hạnh phúc"));
    motionList.add(new Motion(1, R.drawable.ic_motion_item_cute,"Đáng yêu"));
    motionList.add(new Motion(1, R.drawable.ic_motion_item_cool,"Ngầu"));
    motionList.add(new Motion(1, R.drawable.ic_motion_item_wow,"Kinh ngạc"));
    motionList.add(new Motion(1, R.drawable.ic_motion_item_worried,"Bệnh tật"));
    motionList.add(new Motion(1, R.drawable.ic_motion_item_interesting,"Nhạt"));
    motionList.add(new Motion(1, R.drawable.ic_motion_item_hate,"Tức Giận"));
    return motionList;
}

}
