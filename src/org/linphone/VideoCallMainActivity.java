package org.linphone;

import static android.content.Intent.ACTION_MAIN;

import org.linphone.ui.AddressText;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class VideoCallMainActivity extends FragmentActivity {
	
	private LinphonePreferences mPrefs = LinphonePreferences.instance();
	private Handler mHandler;
	private ServiceWaitThread mThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_call_main);
		
		mHandler = new Handler();
		
		if (LinphoneService.isReady()) {
			onServiceReady();
		} else {
			// start linphone as background  
			startService(new Intent(ACTION_MAIN).setClass(this, LinphoneService.class));
			mThread = new ServiceWaitThread();
			mThread.start();
		}
		
		
		ImageButton image1 = (ImageButton)findViewById(R.id.image1);
		image1.setOnClickListener(new Button.OnClickListener() {
		    public void onClick(View v) {
		    	String address = "sip:alyouhah@sip.linphone.org";
		    	AddressText mAddress = new AddressText(VideoCallMainActivity.this, null);
				mAddress.setContactAddress(address, address);
		        LinphonePlugin.videoCall(VideoCallMainActivity.this, mAddress);
		    }
		});
		ImageButton image2 = (ImageButton)findViewById(R.id.image2);
		image2.setOnClickListener(new Button.OnClickListener() {
		    public void onClick(View v) {
		    	String address = "sip:alyouhah1@sip.linphone.org";
		    	AddressText mAddress = new AddressText(VideoCallMainActivity.this, null);
				mAddress.setContactAddress(address, address);
		        LinphonePlugin.videoCall(VideoCallMainActivity.this, mAddress);
		    }
		});
		ImageButton image3 = (ImageButton)findViewById(R.id.image3);
		image3.setOnClickListener(new Button.OnClickListener() {
		    public void onClick(View v) {
		    	String address = "sip:alyouhah2@sip.linphone.org";
		    	AddressText mAddress = new AddressText(VideoCallMainActivity.this, null);
				mAddress.setContactAddress(address, address);
		        LinphonePlugin.videoCall(VideoCallMainActivity.this, mAddress);
		    }
		});
		ImageButton image4 = (ImageButton)findViewById(R.id.image4);
		image4.setOnClickListener(new Button.OnClickListener() {
		    public void onClick(View v) {
		    	String address = "sip:alyouhah3@sip.linphone.org";
		    	AddressText mAddress = new AddressText(VideoCallMainActivity.this, null);
				mAddress.setContactAddress(address, address);
		        LinphonePlugin.videoCall(VideoCallMainActivity.this, mAddress);
		    }
		});
	}

	private void registerMySip() {
		String myUsername = "novas@sip.linphone.org";
		String myPasswrod = "novas123";
		String myDomain = "sip.linphone.org";
		LinphonePlugin.registerSip(this, mPrefs, myUsername, myDomain, myPasswrod);
	}
	
	protected void onServiceReady() {
		final Class<? extends Activity> classToStart = LinphoneActivity.class;
		LinphoneService.instance().setActivityToLaunchOnIncomingReceived(IncomingCallActivity.class);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
//				startActivity(new Intent().setClass(VideoCallMainActivity.this, classToStart).setData(getIntent().getData()));
//				finish();
				registerMySip();
			}
		}, 1000);
	}


	private class ServiceWaitThread extends Thread {
		public void run() {
			while (!LinphoneService.isReady()) {
				try {
					sleep(30);
				} catch (InterruptedException e) {
					throw new RuntimeException("waiting thread sleep() has been interrupted");
				}
			}

			mHandler.post(new Runnable() {
				@Override
				public void run() {
					onServiceReady();
				}
			});
			mThread = null;
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
//		registerMySip();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		registerMySip();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.gc();
	}

}
