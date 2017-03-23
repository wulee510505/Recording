# Recording
快速实现录音功能


 Step 1. Add the JitPack repository to your build file


 Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
        
 Step 2. Add the dependency

	dependencies {
	        compile 'com.github.wulee510505:Recording:1.0.0'
	}

        RecordVoiceButton  mBtRec = (RecordVoiceButton) findViewById(R.id.button_rec);
 
        mBtRec.setAudioSavePath(Environment.getExternalStorageDirectory().getPath()+"/Recording/audio");//设置音频保存路径
 
        mBtRec.setEnrecordVoiceListener(new RecordVoiceButton.EnRecordVoiceListener() {
            @Override
            public void onFinishRecord(long length, String strLength, String filePath) {
                //录音完成do something                                                                                                                                                                                                                                                                                                                                                                                                                    
            }
        });
