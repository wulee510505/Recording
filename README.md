# Recording
快速实现录音功能


        RecordVoiceButton  mBtRec = (RecordVoiceButton) findViewById(R.id.button_rec);
 
        mBtRec.setAudioSavePath(Environment.getExternalStorageDirectory().getPath()+"/Recording/audio");//设置音频保存路径
 
        mBtRec.setEnrecordVoiceListener(new RecordVoiceButton.EnRecordVoiceListener() {
            @Override
            public void onFinishRecord(long length, String strLength, String filePath) {
                //录音完成do something                                                                                                                                                                                                                                                                                                                                                                                                                    
            }
        });
