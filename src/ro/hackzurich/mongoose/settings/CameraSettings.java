package ro.hackzurich.mongoose.settings;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import ro.hackzurich.mongoose.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class CameraSettings {

	private static Activity activity;
	
	private static int ACTIVITY_RECORDING = 101;
	private static int ACTIVITY_PHOTO     = 102;
	private static int ACTIVITY_BROWSING  = 103;
	private static int ACTIVITY_TYPING    = 104;
	private static int ACTIVITY_THIS      = 105;
	private static int ACTIVITY_CURRENT   = ACTIVITY_THIS;
	
	public static void setActivityRecording() 	{	ACTIVITY_CURRENT = ACTIVITY_RECORDING;			}
	public static void setActivityPhoto()		{	ACTIVITY_CURRENT = ACTIVITY_PHOTO;				}
	public static void setActivityBrowsing()	{	ACTIVITY_CURRENT = ACTIVITY_BROWSING;			}
	public static void setActivityTyping()		{	ACTIVITY_CURRENT = ACTIVITY_TYPING;				}
	public static void setActivityThis() 		{	ACTIVITY_CURRENT = ACTIVITY_THIS;				}
	
	public static boolean isActivityRecording() {	return ACTIVITY_CURRENT == ACTIVITY_RECORDING;	}
	public static boolean isActivityPhoto() 	{	return ACTIVITY_CURRENT == ACTIVITY_PHOTO;		}
	public static boolean isActivityBrowsing()	{	return ACTIVITY_CURRENT == ACTIVITY_BROWSING;	}
	public static boolean isActivityTyping()	{	return ACTIVITY_CURRENT == ACTIVITY_TYPING;		}
	public static boolean isActivityThis()		{	return ACTIVITY_CURRENT == ACTIVITY_THIS;		}
	
	private static File outFile;
	private static boolean taskSuccess;
	
	public static final int TAKE_PICTURE = 1;
	public static final int FILM_VIDEO   = 2;
	public static final int WRITE_TEXT   = 4;
	
	public static void setActivity(Activity a) {
		activity = a;
	}
	
	public static void setOutFile(File f) {
		outFile = f;
	}
	
	public static File getOutFile() {
		return outFile;
	}
	
	public static void setSuccessFlag(boolean val) {
		taskSuccess = val;
	}
	
	public static boolean getSuccessFlag() {
		return taskSuccess;
	}
	
	public static String generateFilename() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd Hms");
		return sdf.format(new Date());
	}
	
	public static void setCameraPreview(int type) {
		activity.setContentView(R.layout.camera);
		View preview = (View) activity.findViewById(R.id.viewPreview);
		ViewGroup parent = (ViewGroup) preview.getParent();
		LayoutParams lp = preview.getLayoutParams();
		
		int viewIndex = parent.indexOfChild(preview);
		switch (type) {
			case CameraSettings.TAKE_PICTURE:
				parent.removeViewAt(viewIndex);
				preview = new ImageView(activity);
				preview.setLayoutParams(lp);
				preview.setId(R.id.viewPreview);
				parent.addView(preview,viewIndex);
				try {
					Bitmap bitmap = BitmapFactory.decodeFile(CameraSettings.getOutFile().getAbsolutePath()); 
					((ImageView)preview).setImageBitmap(bitmap);
				} catch (Exception e) {
					
				}
				break;
			
			case CameraSettings.FILM_VIDEO:
				parent.removeViewAt(viewIndex);
				preview = new VideoView(activity);
				preview.setLayoutParams(lp);
				preview.setId(R.id.viewPreview);
				parent.addView(preview,viewIndex);
				try {
					MediaController mc = new MediaController(activity);
					((VideoView)preview).setMediaController(mc);
					((VideoView)preview).setVideoPath(CameraSettings.getOutFile().getAbsolutePath());
				} catch (Exception e) {
				}
				break;
			case CameraSettings.WRITE_TEXT:
				break;
				
			default:
				break;
		}
	}
}
