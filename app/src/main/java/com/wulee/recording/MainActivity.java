package com.wulee.recording;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.wulee.recordingibrary.entity.Voice;
import com.wulee.recordingibrary.view.RecordVoiceButton;

public class MainActivity extends AppCompatActivity {

    /**
     * 语音列表
     */
    private RecyclerView recyclerView;
    /**
     * 开始录音
     */
    private VoiceAdapter adapter;
    private RecordVoiceButton mBtRec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtRec = (RecordVoiceButton) findViewById(R.id.button_rec);
        recyclerView = (RecyclerView) findViewById(R.id.lv);
        adapter = new VoiceAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        mBtRec.setAudioSavePath(Environment.getExternalStorageDirectory().getPath()+"/Recording/audio");
        mBtRec.setEnrecordVoiceListener(new RecordVoiceButton.EnRecordVoiceListener() {
            @Override
            public void onFinishRecord(long length, String strLength, String filePath) {
                adapter.add(new Voice(length, strLength, filePath) )                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             ;
            }
        });
    }

}
