package com.wulee.recordingibrary.view;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wulee.recordingibrary.R;
import com.wulee.recordingibrary.utils.VoiceManager;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

/**
 * 录音控件button
 */
public class RecordVoiceButton extends AppCompatButton implements View.OnClickListener {

    private Dialog recordIndicator;
    private ImageView mVolumeIv,mIvPauseContinue,mIvComplete,mIvCancel;
    private VoiceLineView voicLine;
    private TextView mRecordHintTv;
    private Context mContext;
    private EnRecordVoiceListener enRecordVoiceListener;
    private VoiceManager voiceManager;
    private String mPath;

    public RecordVoiceButton(Context context) {
        super(context);
        init();
    }

    public RecordVoiceButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    public RecordVoiceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        voiceManager = VoiceManager.getInstance(mContext);
        setOnClickListener(this);
    }

    /**
     * 设置监听
     *
     * @param enRecordVoiceListener
     */
    public void setEnrecordVoiceListener(EnRecordVoiceListener enRecordVoiceListener) {
        this.enRecordVoiceListener = enRecordVoiceListener;
    }

    /**
     * 启动录音dialog
     */
    private void startRecordDialog() {
        recordIndicator = new Dialog(getContext(), R.style.record_voice_dialog);
        recordIndicator.setContentView(R.layout.dialog_record_voice);
        recordIndicator.setCanceledOnTouchOutside(false);
        recordIndicator.setCancelable(false);
        mVolumeIv = (ImageView) recordIndicator.findViewById(R.id.iv_voice);
        voicLine= (VoiceLineView) recordIndicator.findViewById(R.id.voicLine);
        mRecordHintTv = (TextView) recordIndicator.findViewById(R.id.tv_length);
        mRecordHintTv.setText("00:00:00");
        mIvPauseContinue= (ImageView) recordIndicator.findViewById(R.id.iv_continue_or_pause);
        mIvComplete= (ImageView) recordIndicator.findViewById(R.id.iv_complete);
        mIvCancel= (ImageView) recordIndicator.findViewById(R.id.iv_cancel);

        //显示在屏幕底部
        Window dialogWindow = recordIndicator.getWindow();
        dialogWindow.setGravity( Gravity.BOTTOM);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0); //消除边距

        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = height/3;
        dialogWindow.setAttributes(lp);

        recordIndicator.show();
        //暂停或继续
        mIvPauseContinue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(voiceManager!=null){
                    voiceManager.pauseOrStartVoiceRecord();
                }
            }
        });
        //完成
        mIvComplete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(voiceManager!=null){
                    voiceManager.stopVoiceRecord();
                }
                recordIndicator.dismiss();
            }
        });
        //取消
        mIvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(voiceManager!=null){
                    voiceManager.cancelVoiceRecord();
                }
                recordIndicator.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        AndPermission
                .with(mContext)
                .runtime()
                .permission(Manifest.permission.RECORD_AUDIO)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        startRecordDialog();
                        voiceManager.setVoiceRecordListener(new VoiceManager.VoiceRecordCallBack() {
                            @Override
                            public void recDoing(long time, String strTime) {
                                mRecordHintTv.setText(strTime);
                            }

                            @Override
                            public void recVoiceGrade(int grade) {
                                voicLine.setVolume(grade);
                            }

                            @Override
                            public void recStart(boolean init) {
                                mIvPauseContinue.setImageResource(R.mipmap.icon_pause);
                                voicLine.setContinue();
                            }

                            @Override
                            public void recPause(String str) {
                                mIvPauseContinue.setImageResource(R.mipmap.icon_continue);
                                voicLine.setPause();
                            }
                            @Override
                            public void recFinish(long length, String strLength, String path) {
                                if (enRecordVoiceListener != null) {
                                    enRecordVoiceListener.onFinishRecord(length, strLength, path);
                                }
                            }

                            @Override
                            public void recCancel() {

                            }
                        });
                        voiceManager.startVoiceRecord(mPath);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(mContext, permissions)) {
                            Toast.makeText(mContext, "请开启录音权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).start();
    }

    /**
     * 结束回调监听
     */
    public interface EnRecordVoiceListener {
        void onFinishRecord(long length, String strLength, String filePath);
    }

    /**
     * 设置音频保存路径
     * @param path
     */
    public void setAudioSavePath(String path){
        mPath = path;
    }

}
