package com.example.dinote.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.dinote.R;
import com.example.dinote.model.Motion;

import java.util.ArrayList;
import java.util.List;

public class MotionViewModel extends ViewModel {
    public static List<Motion> motionList() {
        List<Motion> motionList = new ArrayList<>();
        motionList.add(new Motion(0, R.drawable.ic_motion_item_fun, R.string.funny));
        motionList.add(new Motion(1, R.drawable.ic_motion_item_happy, R.string.happy));
        motionList.add(new Motion(2, R.drawable.ic_motion_item_cute, R.string.Cute));
        motionList.add(new Motion(3, R.drawable.ic_motion_item_cool, R.string.cool));
        motionList.add(new Motion(4, R.drawable.ic_motion_item_wow, R.string.wow));
        motionList.add(new Motion(5, R.drawable.ic_motion_item_worried, R.string.diseases));
        motionList.add(new Motion(6, R.drawable.ic_motion_item_interesting, R.string.noninteresting));
        motionList.add(new Motion(7, R.drawable.ic_motion_item_hate, R.string.hate));
        return motionList;
    }

}
