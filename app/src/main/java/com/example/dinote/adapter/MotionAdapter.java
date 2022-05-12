package com.example.dinote.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinote.R;
import com.example.dinote.model.Motion;

import java.util.List;

public class MotionAdapter extends RecyclerView.Adapter<MotionAdapter.MotionViewHolder> {
    private List<Motion> motionList;

    public void setMotionList(List<Motion> motionList) {
        this.motionList = motionList;
    }

    @NonNull
    @Override
    public MotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_motion, parent, false);
        return new MotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MotionViewHolder holder, int position) {

        Motion motion = motionList.get(position);
        holder.imvItemMotion.setImageResource(motion.getImgMotion());
        holder.tvItemMotionName.setText(motion.getMotion());


    }

    @Override
    public int getItemCount() {
        if (motionList == null)
            return 0;
        else
            return motionList.size();
    }


    public class MotionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imvItemMotion;
        private TextView tvItemMotionName;

        public MotionViewHolder(@NonNull View itemView) {
            super(itemView);

            imvItemMotion = itemView.findViewById(R.id.imv_item_motion_emoji);
            tvItemMotionName = itemView.findViewById(R.id.tv_item_motion_text);
            itemView.setOnClickListener(this::onClick);
            itemView.setTag(R.string.item_motion);

        }

        @Override
        public void onClick(View view) {
            if (view.getTag().equals(R.string.item_motion)) {
                Log.e("aaa", "onClick: " + getLayoutPosition());
            }
        }
    }

    public interface EditMotion{
        void onSelectMotion();


    }



}
