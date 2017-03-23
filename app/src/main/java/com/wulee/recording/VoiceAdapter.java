package com.wulee.recording;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wulee.recordingibrary.entity.Voice;
import com.wulee.recordingibrary.utils.FileUtil;
import com.wulee.recordingibrary.utils.ViewHolderUtil;
import com.wulee.recordingibrary.utils.VoiceManager;

/**
 * 类名：VoiceAdapter.java
 */

public class VoiceAdapter extends BaseCommonAdapter<Voice> {
    // 语音动画
    private AnimationDrawable voiceAnimation;
    private VoiceManager voiceManager;
    private int lastPosition = -1;

    public VoiceAdapter(Context context) {
        super(context);
        voiceManager =  VoiceManager.getInstance(context);
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_voice_list, parent, false);
        return new ListItemViewHolder(convertView);
    }
    @Override
    public void onBindViewHolder(final ListItemViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final Voice voice = getData().get(position);
        holder.tvLength.setText(voice.getStrLength());
        holder.tvFileLengtn.setText("file_size: "+ FileUtil.byte2FitMemorySize(voice.getLength()));
        holder.tvFilePath.setText("file_path: "+ voice.getFilePath());
        //播放
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceAnimation != null) {
                    voiceAnimation.stop();
                    voiceAnimation.selectDrawable(0);
                }
                if (voiceManager.isPlaying()&&lastPosition == position) {
                    voiceManager.stopPlay();
                }else{
                    voiceManager.stopPlay();
                    voiceAnimation = (AnimationDrawable) holder.ivVoic.getBackground();
                    voiceAnimation.start();
                    voiceManager.setVoicePlayListener(new VoiceManager.VoicePlayCallBack() {
                        @Override
                        public void voiceTotalLength(long time, String strTime) {

                        }

                        @Override
                        public void playDoing(long time, String strTime) {

                        }

                        @Override
                        public void playPause() {

                        }

                        @Override
                        public void playStart() {

                        }

                        @Override
                        public void playFinish() {
                            if (voiceAnimation != null) {
                                voiceAnimation.stop();
                                voiceAnimation.selectDrawable(0);
                            }
                        }
                    });
                    voiceManager.startPlay(voice.getFilePath());
                }
                lastPosition = position;
            }
        });
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivVoic;
        private CardView cardView;
        private TextView tvLength;
        private TextView tvFileLengtn;
        private TextView tvFilePath;
        public ListItemViewHolder(View convertView) {
            super(convertView);
            ivVoic = ViewHolderUtil.get(convertView, R.id.iv_voice);
            cardView = ViewHolderUtil.get(convertView, R.id.card_view);
            tvLength = ViewHolderUtil.get(convertView, R.id.tv_length);
            tvFileLengtn = ViewHolderUtil.get(convertView, R.id.tv_filelength);
            tvFilePath = ViewHolderUtil.get(convertView, R.id.tv_filepath);
        }
    }
}
