package ro.hackzurich.mongoose;


import java.io.File;
import java.io.IOException;

import ro.hackzurich.mongoose.settings.CameraSettings;

import android.hardware.Camera;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;

import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;

import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;

import android.os.Bundle;
import android.os.Environment;


public class ChallengeAcceptedRecordVideo 
	extends ActionBarActivity 
	implements SurfaceHolder.Callback,
	           OnInfoListener,
	           OnErrorListener{
	
	private Button btnRecStop;
	private Button btnPauseResume;
	private Button btnVideoOk;
	private Button btnVideoCancel;
	
	private TextView txtVideoRecording;
	private VideoView vvVideo;
	
	private Camera camera;
	private SurfaceHolder holder;
	private MediaRecorder recorder;
	private File outFile;
	
	private String currentState = "";
	private static final String STATE_RECORDING   = "STATE_RECORDING";
	private static final String STATE_REC_PAUSED  = "STATE_PAUSED";
	private static final String STATE_STOPPED     = "STATE_STOPPED";
	private static final String STATE_PLAYING     = "STATE_PLAYING";
	private static final String STATE_PLAY_PAUSED = "STATE_PLAY_PAUSED";
	
	private void enableButtons(boolean recStop, boolean pausePlay, boolean accept, boolean cancel){
		btnRecStop.setEnabled(recStop);
		btnPauseResume.setEnabled(pausePlay);
		btnVideoOk.setEnabled(accept);
		btnVideoCancel.setEnabled(cancel);
	}
	
	private void setState(String state) {
		currentState = state;
		if(state == STATE_STOPPED) {
			btnRecStop.setText(R.string.btnRec);
			btnPauseResume.setText(R.string.btnPlay);
			enableButtons(true,true,true,true);
			txtVideoRecording.setText("PLAYBACK");
			txtVideoRecording.setTextColor(Color.WHITE);
		} else if( state == STATE_RECORDING ) {
			btnRecStop.setText(R.string.btnStop);
			btnPauseResume.setText(R.string.btnPause);
			enableButtons(true,false,false,true);
			txtVideoRecording.setText("REC");
			txtVideoRecording.setTextColor(Color.RED);
		} else if( state == STATE_REC_PAUSED ) { // This doesn't exist YET
			btnRecStop.setText(R.string.btnStop);
			btnPauseResume.setText(R.string.btnPlay);
			enableButtons(false,true,false,true);
			txtVideoRecording.setText("RECORDING PAUSED");
			txtVideoRecording.setTextColor(Color.GREEN);
		} else if ( state == STATE_PLAYING ) {
			btnRecStop.setText(R.string.btnStop);
			btnPauseResume.setText(R.string.btnPause);
			enableButtons(true,true,true,true);
			txtVideoRecording.setText("REPLAY");
			txtVideoRecording.setTextColor(Color.WHITE);
		} else if ( state == STATE_PLAY_PAUSED ) {
			btnRecStop.setText(R.string.btnStop);
			btnPauseResume.setText(R.string.btnPlay);
			enableButtons(true,true,true,true);
			txtVideoRecording.setText("REPLAY PAUSED");
			txtVideoRecording.setTextColor(Color.WHITE);
		} else {
			dropVideo();
			enableButtons(true, true, false, true);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_record);
		
		btnRecStop        = (Button)    findViewById(R.id.btnRecStop);
		btnPauseResume    = (Button)    findViewById(R.id.btnPauseResume);
		btnVideoOk        = (Button)    findViewById(R.id.btnVideoOk);
		btnVideoCancel    = (Button)    findViewById(R.id.btnVideoCancel);
		txtVideoRecording = (TextView)  findViewById(R.id.txtVideoRecording);
		vvVideo           = (VideoView) findViewById(R.id.vvVideo);
		
		// INITIALIZE BUTTONS 
		btnRecStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentState == STATE_RECORDING){
					stopRecording();
					setState(STATE_STOPPED);
				} else if(currentState == STATE_REC_PAUSED){
					;
				} else if(currentState == STATE_STOPPED){
					dropVideo();
					releaseCamera();
					initCamera();
					releaseRecorder();
					initRecorder();
					beginRecording();
					setState(STATE_RECORDING);
				} else if(currentState == STATE_PLAYING){
					stopPlayback();
					setState(STATE_STOPPED);
				} else if(currentState == STATE_PLAY_PAUSED) {
					stopPlayback();
					setState(STATE_STOPPED);
				} else {
					dropVideo();
					initRecorder();
					beginRecording();
					setState(STATE_RECORDING);
				}
			}
		});
		
		btnPauseResume.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentState == STATE_RECORDING){
					pauseRecording(); // TODO
	//				setState(STATE_REC_PAUSED);
					// CANNOT BE IMPLEMENTED
				} else if(currentState == STATE_REC_PAUSED){
					resumeRecording();
					setState(STATE_RECORDING);
				} else if(currentState == STATE_STOPPED){
					playRecording();
					setState(STATE_PLAYING);
					btnPauseResume.setEnabled(false);
				} else if(currentState == STATE_PLAYING){
					pausePlayback();
					setState(STATE_PLAY_PAUSED);
				} else if(currentState == STATE_PLAY_PAUSED) {
					resumePlayback();
					setState(STATE_PLAYING);
				} else {
				}
			}
		});
	
		btnVideoOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CameraSettings.setOutFile(outFile);
				stopRecording();
				stopPlayback();
				releaseCamera();
				releaseRecorder();
				CameraSettings.setSuccessFlag(true);
				CameraSettings.setOutFile(outFile);
				finish();
			}
		});
		
		btnVideoCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopRecording();
				stopPlayback();
				releaseCamera();
				releaseRecorder();
				dropVideo();
				finish();
			}
		});
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		enableButtons(true, false, false, true);
		if(!initCamera())
			finish();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(currentState == STATE_RECORDING ){
			stopRecording();
			setState(STATE_STOPPED);
		}
		if(currentState == STATE_PLAYING) {
			pausePlayback();
			setState(STATE_PLAY_PAUSED);
		}
	}
	
	private void dropVideo() {
		try {
			outFile.delete();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
	
	private boolean initCamera() {
		if(camera != null)
			return true;
		try {
			camera = Camera.open();
			camera.lock();
			holder = vvVideo.getHolder();
			holder.addCallback(this);
			holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void initRecorder() {
		if(recorder != null)
			return;
		
		outFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), 
				CameraSettings.generateFilename() + ".mp4");
		
		try {
			camera.stopPreview();
			camera.unlock();
			recorder = new MediaRecorder();
			recorder.setCamera(camera);
			
			recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
			recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
			recorder.setOutputFile(outFile.getAbsolutePath());
			recorder.setVideoSize(1280, 1024);
			recorder.setVideoFrameRate(25);
			recorder.setPreviewDisplay(holder.getSurface());
	//		recorder.setMaxDuration(120000); // 2 minutes hardcoded
	//		recorder.setMaxFileSize(512000); // 50 mb
			
			recorder.prepare();
		} catch(IllegalStateException ise) {
			releaseRecorder();
			ise.printStackTrace();
		} catch( IOException e) {
			releaseRecorder();
			e.printStackTrace();
		}
	}
	
	private void beginRecording() {
		recorder.setOnInfoListener(this);
		recorder.setOnErrorListener(this);
		recorder.start();
	}
	
	private void pauseRecording() {
		// TODO
		// can't be implemented
	}
	
	private void resumeRecording() {
		// TODO
		// can't be implemented
	}
	
	private void stopRecording() {
		if(recorder != null) {
			recorder.setOnErrorListener(null);
			recorder.setOnInfoListener(null);
			try {
				recorder.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			releaseRecorder();
			releaseCamera();
		}
	}
	
	private void releaseCamera() {
		if(camera != null) {
			try{
				camera.reconnect();
			} catch(Exception e) {
				e.printStackTrace();
			}
			camera.release();
			camera = null;
		}
	}
	
	private void releaseRecorder(){
		if(recorder != null){
			recorder.release();
			recorder = null;
		}
	}
	
	private void playRecording() {
		MediaController mc = new MediaController(this);
		vvVideo.setMediaController(mc);
		vvVideo.setVideoPath(outFile.getAbsolutePath());
		vvVideo.start();
	}
	
	private void pausePlayback() {
		vvVideo.pause();
	}
	
	private void resumePlayback() {
		vvVideo.resume();
	}
	
	private void stopPlayback() {
		vvVideo.stopPlayback();
	}
	
	@Override
	public void onError(MediaRecorder arg0, int arg1, int arg2) {
		stopRecording();
		setState(STATE_STOPPED);
		dropVideo();
		enableButtons(true, false, false, true);
		Toast.makeText(this, "Recording error.",Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {
		if(true)
			return;
	//	if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED ||
	//			what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED)
	//	stopRecording();
	//	setState(STATE_STOPPED);
	//	Toast.makeText(this, "Recording limit reached.", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try{
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO
	}
	

}
