package org.linphone;
/*
InCallActivity.java
Copyright (C) 2012  Belledonne Communications, Grenoble, France

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
import java.util.Arrays;
import java.util.List;

import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCall.State;
import org.linphone.core.LinphoneCallParams;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCoreException;
import org.linphone.core.LinphoneCoreFactory;
import org.linphone.core.LinphoneCoreListenerBase;
import org.linphone.core.LinphonePlayer;
import org.linphone.mediastream.Log;
import org.linphone.mediastream.video.capture.hwconf.AndroidCameraConfiguration;
import org.linphone.ui.AvatarWithShadow;
import org.linphone.ui.Numpad;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Sylvain Berfini
 */
public class InCallActivity extends FragmentActivity implements OnClickListener {
	private final static int SECONDS_BEFORE_HIDING_CONTROLS = 3000;
	private final static int SECONDS_BEFORE_DENYING_CALL_UPDATE = 30000;
	
	private static InCallActivity instance;

	private Handler mControlsHandler = new Handler(); 
	private Runnable mControls;
	private VideoCallFragment videoCallFragment;
	private boolean isSpeakerEnabled = false, isMicMuted = false, isTransferAllowed, isAnimationDisabled;
	private ViewGroup mControlsLayout;
	private boolean isVideoCallPaused = false;
	
	private LayoutInflater inflater;
	private ViewGroup container;
	private boolean showCallListInVideo = false;
	private LinphoneCoreListenerBase mListener;
	
	public static InCallActivity instance() {
		return instance;
	}
	
	public static boolean isInstanciated() {
		return instance != null;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.incall);
        
        isTransferAllowed = getApplicationContext().getResources().getBoolean(R.bool.allow_transfers);
        showCallListInVideo = getApplicationContext().getResources().getBoolean(R.bool.show_current_calls_above_video);
        isSpeakerEnabled = LinphoneManager.getLcIfManagerNotDestroyedOrNull().isSpeakerEnabled();
        
        isAnimationDisabled = getApplicationContext().getResources().getBoolean(R.bool.disable_animations) || !LinphonePreferences.instance().areAnimationsEnabled();
        
        mListener = new LinphoneCoreListenerBase(){
        	@Override
        	public void callState(LinphoneCore lc, final LinphoneCall call, LinphoneCall.State state, String message) {
        		if (LinphoneManager.getLc().getCallsNb() == 0) {
        			finish();
        			return;
        		}
        		
        		if (state == State.IncomingReceived) {
        			startIncomingCallActivity();
        			return;
        		}
        		
        		if (state == State.Paused || state == State.PausedByRemote ||  state == State.Pausing) {
        			if(isVideoEnabled(call)){
        				switchVideo();
        			}
        		}
        		
        		if (state == State.Resuming) {
        			if(LinphonePreferences.instance().isVideoEnabled()){
        				if(call.getCurrentParamsCopy().getVideoEnabled()){
        					showVideoView();
        				}
        			}
        		}

        		if (state == State.StreamsRunning) {
//        			switchVideo(isVideoEnabled(call)); original
        			switchVideo();

        			LinphoneManager.getLc().enableSpeaker(isSpeakerEnabled);

        			isMicMuted = LinphoneManager.getLc().isMicMuted();
        			enableAndRefreshInCallActions();
        			
        		}
        		
        		refreshInCallActions();
        		
        		if (state == State.CallUpdatedByRemote) {
        			// If the correspondent proposes video while audio call
        			boolean videoEnabled = LinphonePreferences.instance().isVideoEnabled();
        			if (!videoEnabled) {
        				acceptCallUpdate(false);
        				return;
        			}
        			
        			boolean remoteVideo = call.getRemoteParams().getVideoEnabled();
        			boolean localVideo = call.getCurrentParamsCopy().getVideoEnabled();
        			boolean autoAcceptCameraPolicy = LinphonePreferences.instance().shouldAutomaticallyAcceptVideoRequests();
        			if (remoteVideo && !localVideo && !autoAcceptCameraPolicy && !LinphoneManager.getLc().isInConference()) {
        				showAcceptCallUpdateDialog();
        				
        			} 
        		}
        		
        	}
        	
        	@Override
        	public void callEncryptionChanged(LinphoneCore lc, final LinphoneCall call, boolean encrypted, String authenticationToken) {
        	}
        	
        };
        
        if (findViewById(R.id.fragmentContainer) != null) {
            
            if (LinphoneManager.getLc().getCallsNb() > 0) {
            	LinphoneCall call = LinphoneManager.getLc().getCalls()[0];

            	if (LinphoneUtils.isCallEstablished(call)) {
	    			enableAndRefreshInCallActions();
            	}
            }
            
            if (savedInstanceState != null) { 
            	// Fragment already created, no need to create it again (else it will generate a memory leak with duplicated fragments)
            	isSpeakerEnabled = savedInstanceState.getBoolean("Speaker");
            	isMicMuted = savedInstanceState.getBoolean("Mic");
            	isVideoCallPaused = savedInstanceState.getBoolean("VideoCallPaused");
            	refreshInCallActions();
            	return;
            }
            
            
            Fragment callFragment = new VideoCallFragment();
        	videoCallFragment = (VideoCallFragment) callFragment;
        	isSpeakerEnabled = true;
        	
            callFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, callFragment).commitAllowingStateLoss();

        }
	}
	
	private boolean isVideoEnabled(LinphoneCall call) {
		if(call != null){
			return call.getCurrentParamsCopy().getVideoEnabled();
		}
		return false;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("Speaker", LinphoneManager.getLc().isSpeakerEnabled());
		outState.putBoolean("Mic", LinphoneManager.getLc().isMicMuted());
		outState.putBoolean("VideoCallPaused", isVideoCallPaused);
		
		super.onSaveInstanceState(outState);
	}
	
	private boolean isTablet() {
		return getResources().getBoolean(R.bool.isTablet);
	}
	
	
	
	private void refreshInCallActions() {
		if (isVideoEnabled(LinphoneManager.getLc().getCurrentCall())) {
		} else {
		}
		
		try {
			if (isSpeakerEnabled) {
			} else {
				if (BluetoothManager.getInstance().isUsingBluetoothAudioRoute()) {
				} else {
				}
			}
		} catch (NullPointerException npe) {
			Log.e("Bluetooth: Audio routes menu disabled on tablets for now (4)");
		}
		
		if (isMicMuted) {
		} else {
		}

		if (LinphoneManager.getLc().getCallsNb() > 1) {
		} else {
			
			List<LinphoneCall> pausedCalls = LinphoneUtils.getCallsInState(LinphoneManager.getLc(), Arrays.asList(State.Paused));
			if (pausedCalls.size() == 1) {
			} else {
			}
		}
	}
	
	private void enableAndRefreshInCallActions() {

		if(LinphoneManager.getLc().getCurrentCall() != null && LinphonePreferences.instance().isVideoEnabled() && !LinphoneManager.getLc().getCurrentCall().mediaInProgress()) {
		}
		if(!isTablet()){
    	}

		refreshInCallActions();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		
		if (isVideoEnabled(LinphoneManager.getLc().getCurrentCall())) {
			displayVideoCallControlsIfHidden();
		}

		if (id == R.id.video) {		
			enabledOrDisabledVideo(isVideoEnabled(LinphoneManager.getLc().getCurrentCall()));	
		} 
		else if (id == R.id.switchCamera) {
			if (videoCallFragment != null) {
				videoCallFragment.switchCamera();
			}
		}
		
		else if (id == R.id.callStatus) {
			LinphoneCall call = (LinphoneCall) v.getTag();
			pauseOrResumeCall(call);
		}
		else if (id == R.id.conferenceStatus) {
			pauseOrResumeConference();
		}
	}

	private void enabledOrDisabledVideo(final boolean isVideoEnabled) {
		final LinphoneCall call = LinphoneManager.getLc().getCurrentCall();
		if (call == null) {
			return;
		}
		
		if (isVideoEnabled) {
			LinphoneCallParams params = call.getCurrentParamsCopy();
			params.setVideoEnabled(false);
			LinphoneManager.getLc().updateCall(call, params);
		} else {
			if (!call.getRemoteParams().isLowBandwidthEnabled()) {
				LinphoneManager.getInstance().addVideo();
			} else {
				displayCustomToast(getString(R.string.error_low_bandwidth), Toast.LENGTH_LONG);
			}
		}
	}

	public void displayCustomToast(final String message, final int duration) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toastRoot));

		TextView toastText = (TextView) layout.findViewById(R.id.toastMessage);
		toastText.setText(message);

		final Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(duration);
		toast.setView(layout);
		toast.show();
	}
	
	private void switchVideo() {
		final LinphoneCall call = LinphoneManager.getLc().getCurrentCall();
		if (call == null) {
			return;
		}
		
		//Check if the call is not terminated
		if(call.getState() == State.CallEnd || call.getState() == State.CallReleased) return;
		
		if (!call.getRemoteParams().isLowBandwidthEnabled()) {
			LinphoneManager.getInstance().addVideo();
			if (videoCallFragment == null || !videoCallFragment.isVisible())
				showVideoView();
		} else {
			displayCustomToast(getString(R.string.error_low_bandwidth), Toast.LENGTH_LONG);
		}
	}
	
	private void showAudioView() {
		LinphoneManager.startProximitySensorForActivity(InCallActivity.this);
		setCallControlsVisibleAndRemoveCallbacks();
	}
	
	private void showVideoView() {
		if (!BluetoothManager.getInstance().isBluetoothHeadsetAvailable()) {
			Log.w("Bluetooth not available, using speaker");
			LinphoneManager.getInstance().routeAudioToSpeaker();
			isSpeakerEnabled = true;
		}
		
		LinphoneManager.stopProximitySensorForActivity(InCallActivity.this);
		replaceFragmentAudioByVideo();
		displayVideoCallControlsIfHidden();
	}
	
	private void replaceFragmentAudioByVideo() {
//		Hiding controls to let displayVideoCallControlsIfHidden add them plus the callback
		videoCallFragment = new VideoCallFragment();
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragmentContainer, videoCallFragment);
		try {
			transaction.commitAllowingStateLoss();
		} catch (Exception e) {
		}
	}
	
	private void toggleMicro() {
		LinphoneCore lc = LinphoneManager.getLc();
		isMicMuted = !isMicMuted;
		lc.muteMic(isMicMuted);
		if (isMicMuted) {
		} else {
		}
	}
	
	private void toggleSpeaker() {
		isSpeakerEnabled = !isSpeakerEnabled;
		if (isSpeakerEnabled) {
			LinphoneManager.getInstance().routeAudioToSpeaker();
			LinphoneManager.getLc().enableSpeaker(isSpeakerEnabled);
		} else {
			Log.d("Toggle speaker off, routing back to earpiece");
			LinphoneManager.getInstance().routeAudioToReceiver();
		}
	}
	
	private void pauseOrResumeCall() {
		LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
		if (lc != null && lc.getCallsNb() >= 1) {
			LinphoneCall call = lc.getCalls()[0];
			pauseOrResumeCall(call);
		}
	}
	
	public void pauseOrResumeCall(LinphoneCall call) {
		LinphoneCore lc = LinphoneManager.getLc();
		if (call != null && LinphoneUtils.isCallRunning(call)) {
			if (call.isInConference()) {
				lc.removeFromConference(call);
				if (lc.getConferenceSize() <= 1) {
					lc.leaveConference();
				}
			} else {
				lc.pauseCall(call);
				if (isVideoEnabled(LinphoneManager.getLc().getCurrentCall())) {
					isVideoCallPaused = true;
					showAudioView();
				}
			}
		} else if (call != null) {
			if (call.getState() == State.Paused) {
				lc.resumeCall(call);
				if (isVideoCallPaused) {
					isVideoCallPaused = false;
					showVideoView();
				}
			}
		}
	}
	
	private void hangUp() {
		LinphoneCore lc = LinphoneManager.getLc();
		LinphoneCall currentCall = lc.getCurrentCall();
		
		if (currentCall != null) {
			lc.terminateCall(currentCall);
		} else if (lc.isInConference()) {
			lc.terminateConference();
		} else {
			lc.terminateAllCalls();
		}
	}
	
	private void enterConference() {
		LinphoneManager.getLc().addAllToConference();
	}
	
	public void pauseOrResumeConference() {
		LinphoneCore lc = LinphoneManager.getLc();
		if (lc.isInConference()) {
			lc.leaveConference();
		} else {
			lc.enterConference();
		}
	}
	
	public void displayVideoCallControlsIfHidden() {
		if (mControlsLayout != null) {
			if (mControlsLayout.getVisibility() != View.VISIBLE) {
				if (isAnimationDisabled) {
					mControlsLayout.setVisibility(View.VISIBLE);
				} else {
				}
			}
			resetControlsHidingCallBack();
		}		
	}

	public void resetControlsHidingCallBack() {
		if (mControlsHandler != null && mControls != null) {
			mControlsHandler.removeCallbacks(mControls);
		}
		mControls = null;
		
		if (isVideoEnabled(LinphoneManager.getLc().getCurrentCall()) && mControlsHandler != null) {
			mControlsHandler.postDelayed(mControls = new Runnable() {
				public void run() {
					
					if (isAnimationDisabled) {
						mControlsLayout.setVisibility(View.GONE);
					} else {					
					}
				}
			}, SECONDS_BEFORE_HIDING_CONTROLS);
		}
	}

	public void setCallControlsVisibleAndRemoveCallbacks() {
		if (mControlsHandler != null && mControls != null) {
			mControlsHandler.removeCallbacks(mControls);
		}
		mControls = null;
		
//		mControlsLayout.setVisibility(View.VISIBLE);
	}
	
	
	
	public void acceptCallUpdate(boolean accept) {
		 
		LinphoneCall call = LinphoneManager.getLc().getCurrentCall();
		if (call == null) {
			return;
		}
		 
		LinphoneCallParams params = call.getCurrentParamsCopy();
		if (accept) {
			params.setVideoEnabled(true);
			LinphoneManager.getLc().enableVideo(true, true);
		}
		 
		try {
			LinphoneManager.getLc().acceptCallUpdate(call, params);
		} catch (LinphoneCoreException e) {
			e.printStackTrace();
		}
	}
	
	public void startIncomingCallActivity() {
		startActivity(new Intent(this, IncomingCallActivity.class));
	}

	
	
	private void showAcceptCallUpdateDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AcceptCallUpdateDialogFragment callUpdateDialog = new AcceptCallUpdateDialogFragment();
        callUpdateDialog.show(fm, "Accept Call Update Dialog");
    }

	@Override
	protected void onResume() {
		instance = this;
		
		if (isVideoEnabled(LinphoneManager.getLc().getCurrentCall())) {
			displayVideoCallControlsIfHidden();
		} else {
			LinphoneManager.startProximitySensorForActivity(this);
			setCallControlsVisibleAndRemoveCallbacks();
		}
		
		super.onResume();
		
		LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
		if (lc != null) {
			lc.addListener(mListener);
		}

		
		handleViewIntent();
	}
	
	private void handleViewIntent() {
		Intent intent = getIntent();
		if(intent != null && intent.getAction() == "android.intent.action.VIEW") {
			LinphoneCall call = LinphoneManager.getLc().getCurrentCall();
			if(call != null && isVideoEnabled(call)) {
				LinphonePlayer player = call.getPlayer();
				String path = intent.getData().getPath();
				Log.i("Openning " + path);
				int openRes = player.open(path, new LinphonePlayer.Listener() {
					
					@Override
					public void endOfFile(LinphonePlayer player) {
						player.close();
					}
				});
				if(openRes == -1) {
					String message = "Could not open " + path;
					Log.e(message);
					Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
					return;
				}
				Log.i("Start playing");
				if(player.start() == -1) {
					player.close();
					String message = "Could not start playing " + path;
					Log.e(message);
					Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
	
	@Override
	protected void onPause() {
		LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
		if (lc != null) {
			lc.removeListener(mListener);
		}
		
		super.onPause();

		if (mControlsHandler != null && mControls != null) {
			mControlsHandler.removeCallbacks(mControls);
		}
		mControls = null;

		if (!isVideoEnabled(LinphoneManager.getLc().getCurrentCall())) {
			LinphoneManager.stopProximitySensorForActivity(this);
		}
	}
	
	@Override
	protected void onDestroy() {
		LinphoneManager.getInstance().changeStatusToOnline();
		
		if (mControlsHandler != null && mControls != null) {
			mControlsHandler.removeCallbacks(mControls);
		}
		mControls = null;
		mControlsHandler = null;
		
		unbindDrawables(findViewById(R.id.topLayout));
		instance = null;
		super.onDestroy();
	    System.gc();
	}
	
	private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
        	view.getBackground().setCallback(null);
        }
        if (view instanceof ImageView) {
        	view.setOnClickListener(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            	unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (LinphoneUtils.onKeyVolumeAdjust(keyCode)) return true;
 		if (LinphoneUtils.onKeyBackGoHome(this, keyCode, event)) return true;
 		return super.onKeyDown(keyCode, event);
 	}

	public void bindVideoFragment(VideoCallFragment fragment) {
		videoCallFragment = fragment;
	}
	
	
	private void displayCall(Resources resources, LinphoneCall call, int index) {
		String sipUri = call.getRemoteAddress().asStringUriOnly();
        LinphoneAddress lAddress;
		try {
			lAddress = LinphoneCoreFactory.instance().createLinphoneAddress(sipUri);
		} catch (LinphoneCoreException e) {
			Log.e("Incall activity cannot parse remote address",e);
			lAddress= LinphoneCoreFactory.instance().createLinphoneAddress("uknown","unknown","unkonown");
		}

        // Control Row
    	LinearLayout callView = (LinearLayout) inflater.inflate(R.layout.active_call_control_row, container, false);
    	callView.setId(index+1);
		setContactName(callView, lAddress, sipUri, resources);
		displayCallStatusIconAndReturnCallPaused(callView, call);
		setRowBackground(callView, index);
		registerCallDurationTimer(callView, call);
    	
		// Image Row
    	LinearLayout imageView = (LinearLayout) inflater.inflate(R.layout.active_call_image_row, container, false);
        Uri pictureUri = LinphoneUtils.findUriPictureOfContactAndSetDisplayName(lAddress, imageView.getContext().getContentResolver());
		displayOrHideContactPicture(imageView, pictureUri, false);
    	
    	callView.setTag(imageView);
    	callView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getTag() != null) {
					View imageView = (View) v.getTag();
					if (imageView.getVisibility() == View.VISIBLE)
						imageView.setVisibility(View.GONE);
					else
						imageView.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	
	private void setContactName(LinearLayout callView, LinphoneAddress lAddress, String sipUri, Resources resources) {
		TextView contact = (TextView) callView.findViewById(R.id.contactNameOrNumber);
		
		LinphoneUtils.findUriPictureOfContactAndSetDisplayName(lAddress, callView.getContext().getContentResolver());
		String displayName = lAddress.getDisplayName();

		if (displayName == null) {
	        if (resources.getBoolean(R.bool.only_display_username_if_unknown) && LinphoneUtils.isSipAddress(sipUri)) {
	        	contact.setText(LinphoneUtils.getUsernameFromAddress(sipUri));
			} else {
				contact.setText(sipUri);
			}
		} else {
			contact.setText(displayName);
		}
	}
	
	private boolean displayCallStatusIconAndReturnCallPaused(LinearLayout callView, LinphoneCall call) {
		boolean isCallPaused, isInConference;
		ImageView callState = (ImageView) callView.findViewById(R.id.callStatus);
		callState.setTag(call);
		callState.setOnClickListener(this);
		
		if (call.getState() == State.Paused || call.getState() == State.PausedByRemote || call.getState() == State.Pausing) {
			callState.setImageResource(R.drawable.pause);
			isCallPaused = true;
			isInConference = false;
		} else if (call.getState() == State.OutgoingInit || call.getState() == State.OutgoingProgress || call.getState() == State.OutgoingRinging) {
			callState.setImageResource(R.drawable.call_state_ringing_default);
			isCallPaused = false;
			isInConference = false;
		} else {
			if (call.isInConference()) {
				callState.setImageResource(R.drawable.remove);
				isInConference = true;
			} else {
				callState.setImageResource(R.drawable.play);
				isInConference = false;
			}
			isCallPaused = false;
		}
		
		return isCallPaused || isInConference;
	}
	
	private void displayOrHideContactPicture(LinearLayout callView, Uri pictureUri, boolean hide) {
		AvatarWithShadow contactPicture = (AvatarWithShadow) callView.findViewById(R.id.contactPicture);
		if (pictureUri != null) {
        	LinphoneUtils.setImagePictureFromUri(callView.getContext(), contactPicture.getView(), Uri.parse(pictureUri.toString()), R.drawable.unknown_small);
        }
		callView.setVisibility(hide ? View.GONE : View.VISIBLE);
	}
	
	private void setRowBackground(LinearLayout callView, int index) {
		int backgroundResource;
		if (index == 0) {
//			backgroundResource = active ? R.drawable.cell_call_first_highlight : R.drawable.cell_call_first;
			backgroundResource = R.drawable.cell_call_first;
		} else {
//			backgroundResource = active ? R.drawable.cell_call_highlight : R.drawable.cell_call;
			backgroundResource = R.drawable.cell_call;
		}
		callView.setBackgroundResource(backgroundResource);
	}
	
	private void registerCallDurationTimer(View v, LinphoneCall call) {
		int callDuration = call.getDuration();
		if (callDuration == 0 && call.getState() != State.StreamsRunning) {
			return;
		}
		
		Chronometer timer = (Chronometer) v.findViewById(R.id.callTimer);
		if (timer == null) {
			throw new IllegalArgumentException("no callee_duration view found");
		}
		
		timer.setBase(SystemClock.elapsedRealtime() - 1000 * callDuration);
		timer.start();
	}
	
	
}
