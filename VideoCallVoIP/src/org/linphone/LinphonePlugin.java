package org.linphone;

import java.util.ArrayList;
import java.util.List;

import org.linphone.LinphonePreferences.AccountBuilder;
import org.linphone.core.CallDirection;
import org.linphone.core.LinphoneAddress.TransportType;
import org.linphone.core.LinphoneAuthInfo;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCallLog;
import org.linphone.core.LinphoneCallParams;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCore.RegistrationState;
import org.linphone.core.LinphoneCoreException;
import org.linphone.core.LinphoneProxyConfig;
import org.linphone.mediastream.Log;
import org.linphone.setup.EchoCancellerCalibrationFragment;
import org.linphone.ui.AddressText;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.OrientationEventListener;
import android.widget.Toast;

public class LinphonePlugin {

	public static final int CALL_ACTIVITY = 19;
	public static final int PICK_CONTACT = 30;
	public static final int PICK_CALL_LOG = 31;
	private static LocalOrientationEventListener mOrientationHelper;
	private static int mAlwaysChangingPhoneAngle = -1;


	private Context context;
	private LinphonePreferences mPrefs = LinphonePreferences.instance();

	
	
	private static void signOut(String sipUsername, String domain) {
		if (LinphoneManager.isInstanciated()) {

			String sipAddress = sipUsername + "@" + domain;
			List<Integer> accountIndexes = findAuthIndexOf(sipAddress);
			for (Integer accountIndex : accountIndexes) {
				deleteAccount(accountIndex);
			}

		}
	}

	public static void registerSip(Context context, LinphonePreferences mPrefs,
			String sipUsername, String domain, String password) {
		String sipAddress = sipUsername + "@" + domain;
		LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();

		// Get account index.
		int nbAccounts = LinphonePreferences.instance().getAccountCount();
		List<Integer> accountIndexes = findAuthIndexOf(sipAddress);

//		signOut(sipUsername, domain);

		nbAccounts = LinphonePreferences.instance().getAccountCount();
		accountIndexes = findAuthIndexOf(sipAddress);
		if (accountIndexes == null || accountIndexes.isEmpty()) { // User
																	// haven't
																	// registered
																	// in
																	// linphone
			logIn(context, mPrefs, sipUsername, password, domain, false);
			lc.refreshRegisters();
			accountIndexes.add(nbAccounts);
		}

		for (Integer accountIndex : accountIndexes) {
			if (LinphonePreferences.instance().getDefaultAccountIndex() != accountIndex) {

				LinphonePreferences.instance().setDefaultAccount(
						accountIndex);
				if (accountIndex < LinphonePreferences.instance()
						.getAccountCount()) {
					LinphonePreferences.instance().setAccountEnabled(
							accountIndex, true);
					lc.setDefaultProxyConfig((LinphoneProxyConfig) LinphoneManager
							.getLc().getProxyConfigList()[accountIndex]);
					lc.refreshRegisters();
				}
			} else {
				if (lc != null && lc.getDefaultProxyConfig() != null) {
					if (RegistrationState.RegistrationOk == LinphoneManager
							.getLc().getDefaultProxyConfig().getState()) {
						// callbackContext.success("User Sip have registered. Ignored action...");
					} else if (RegistrationState.RegistrationFailed == LinphoneManager
							.getLc().getDefaultProxyConfig().getState()
							|| RegistrationState.RegistrationNone == LinphoneManager
									.getLc().getDefaultProxyConfig()
									.getState()) {
						// logIn(sipUsername, password, domain, false);
						LinphonePreferences.instance().setAccountEnabled(
								accountIndex, true);
						lc.refreshRegisters();
						// callbackContext.success("Re-register sip:"+sipUsername+"@"+domain);
					}
				}
			}
		}
	}

	public static void deleteAccount(int n) {
		final LinphoneProxyConfig proxyCfg = getProxyConfig(n);

		if (proxyCfg != null)
			LinphoneManager.getLc().removeProxyConfig(proxyCfg);
		if (LinphoneManager.getLc().getProxyConfigList().length == 0) {
			LinphoneManager.getLc().refreshRegisters();
		} else {
			resetDefaultProxyConfig();
			LinphoneManager.getLc().refreshRegisters();
		}
	}

	public static void resetDefaultProxyConfig() {
		int count = LinphoneManager.getLc().getProxyConfigList().length;
		for (int i = 0; i < count; i++) {
			if (isAccountEnabled(i)) {
				LinphoneManager.getLc()
						.setDefaultProxyConfig(getProxyConfig(i));
				break;
			}
		}

		if (LinphoneManager.getLc().getDefaultProxyConfig() == null) {
			LinphoneManager.getLc().setDefaultProxyConfig(getProxyConfig(0));
		}
	}

	public static boolean isAccountEnabled(int n) {
		return getProxyConfig(n).registerEnabled();
	}

	// Accounts settings
	private static LinphoneProxyConfig getProxyConfig(int n) {
		LinphoneProxyConfig[] prxCfgs = LinphoneManager.getLc()
				.getProxyConfigList();
		if (n < 0 || n >= prxCfgs.length)
			return null;
		return prxCfgs[n];
	}


	public static String getCurrentCallNumberForm() {
		LinphoneCore lc = LinphoneManager
				.getLcIfManagerNotDestroyedOrNull();
		if (lc == null
				|| lc.getCurrentCall() == null
				|| lc.getCurrentCall().getCallLog() == null
				|| lc.getCurrentCall().getCallLog().getFrom() == null
				|| lc.getCurrentCall().getCallLog().getFrom().getUserName() == null) {
			return "";
		}
		return lc.getCurrentCall().getCallLog().getFrom().getUserName();
	}

	private static List<Integer> findAuthIndexOf(String sipAddress) {
		int nbAccounts = LinphonePreferences.instance().getAccountCount();
		List<Integer> indexes = new ArrayList<Integer>();
		for (int index = 0; index < nbAccounts; index++) {
			String accountUsername = LinphonePreferences.instance()
					.getAccountUsername(index);
			String accountDomain = LinphonePreferences.instance()
					.getAccountDomain(index);
			String identity = accountUsername + "@" + accountDomain;
			if (identity.equals(sipAddress)) {
				indexes.add(index);
			}
		}
		return indexes;
	}

	public static void unregisterAllAuth() {
		if (LinphoneManager.isInstanciated()) {
			LinphoneAuthInfo[] authInfoList = LinphoneManager.getLc()
					.getAuthInfosList();
			if (authInfoList != null && authInfoList.length > 0) {
				for (int index = 0; index < authInfoList.length; index++) {
					LinphoneAuthInfo authInfo = authInfoList[index];
					LinphoneManager.getLc().removeAuthInfo(authInfo);
				}
			}
		}
	}


	public static void dialDtmf(char keyCode) {
		LinphoneCore lc = LinphoneManager.getLc();
		lc.stopDtmf();
		if (lc.isIncall()) {
			lc.sendDtmf(keyCode);
		}
	}

	public static float getCallQuality(LinphoneCall call) {
		return call.getCurrentQuality();
	}

	public Uri getPhotoUri(long contactId) {
		ContentResolver contentResolver = this.context.getContentResolver();

		try {
			Cursor cursor = contentResolver
					.query(ContactsContract.Data.CONTENT_URI,
							null,
							ContactsContract.Data.CONTACT_ID
									+ "="
									+ contactId
									+ " AND "

									+ ContactsContract.Data.MIMETYPE
									+ "='"
									+ ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
									+ "'", null, null);

			if (cursor != null) {
				if (!cursor.moveToFirst()) {
					return null; // no photo
				}
			} else {
				return null; // error in cursor process
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Uri person = ContentUris.withAppendedId(
				ContactsContract.Contacts.CONTENT_URI, contactId);
		return Uri.withAppendedPath(person,
				ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
	}

	public static void hangUp() {
		LinphoneCore lc = LinphoneManager.getLc();
		LinphoneCall currentCall = lc.getCurrentCall();

		if (currentCall != null) {
			lc.terminateCall(currentCall);
		} else if (lc.isInConference()) {
			lc.terminateConference();
		} else {
			lc.terminateAllCalls();
		}

		// mThread.interrupt();
	}

	public static boolean videoCall(Activity activity, AddressText mAddress) {
		if (wifiCall(activity, mAddress)) {
			LinphoneCore lc = LinphoneManager.getLc();
			LinphoneCall currentCall = lc.getCurrentCall();
			startVideoActivity(activity, currentCall);
			return true;
		}
		return false;
	}

	public static void startVideoActivity(Activity activity, LinphoneCall currentCall) {
		Intent intent = new Intent(activity
				.getApplicationContext(), InCallActivity.class);
		intent.putExtra("VideoEnabled", true);
		startOrientationSensor(activity);
		activity.startActivityForResult(intent, CALL_ACTIVITY);
		LinphoneCallParams params = currentCall.getRemoteParams();
		params.setVideoEnabled(true);
		LinphoneManager.getLc().updateCall(currentCall, params);
	}

	/**
	 * Register a sensor to track phoneOrientation changes
	 */
	private static synchronized void startOrientationSensor(Activity activity) {
		if (mOrientationHelper == null) {
			mOrientationHelper = new LocalOrientationEventListener(activity);
		}
		mOrientationHelper.enable();
	}

	public static void logIn(Context context, LinphonePreferences mPrefs,
			String username, String password, String domain,
			boolean sendEcCalibrationResult) {

		saveCreatedAccount(context, mPrefs, username, password, domain);

		if (LinphoneManager.getLc().getDefaultProxyConfig() != null) {
			// launchEchoCancellerCalibration(mPrefs, sendEcCalibrationResult);
		}
	}

	public static void launchEchoCancellerCalibration(
			LinphonePreferences mPrefs, boolean sendEcCalibrationResult) {
		boolean needsEchoCalibration = LinphoneManager.getLc()
				.needsEchoCalibration();
		if (needsEchoCalibration && mPrefs.isFirstLaunch()) {
			EchoCancellerCalibrationFragment fragment = new EchoCancellerCalibrationFragment();
			fragment.enableEcCalibrationResultSending(sendEcCalibrationResult);
			// changeFragment(fragment);
			// currentFragment = SetupFragmentsEnum.ECHO_CANCELLER_CALIBRATION;
			// back.setVisibility(View.VISIBLE);
			// next.setVisibility(View.GONE);
			// next.setEnabled(false);
			// cancel.setEnabled(false);
		} else {
			if (mPrefs.isFirstLaunch()) {
				mPrefs.setEchoCancellation(false);
			}
			success(mPrefs);
		}
	}

	public static void success(LinphonePreferences mPrefs) {
		mPrefs.firstLaunchSuccessful();
		// setResult(Activity.RESULT_OK);
		// finish();
	}

	public static void saveCreatedAccount(Context context,
			LinphonePreferences mPrefs, String username, String password,
			String domain) {

		boolean isMainAccountLinphoneDotOrg = domain.equals(context
				.getResources().getString(R.string.default_domain));
		boolean useLinphoneDotOrgCustomPorts = context.getResources()
				.getBoolean(R.bool.use_linphone_server_ports);
		AccountBuilder builder = new AccountBuilder(LinphoneManager.getLc())
				.setUsername(username).setDomain(domain).setPassword(password);

		if (isMainAccountLinphoneDotOrg && useLinphoneDotOrgCustomPorts) {
			if (context.getResources().getBoolean(
					R.bool.disable_all_security_features_for_markets)) {
				builder.setProxy(domain + ":5228").setTransport(
						TransportType.LinphoneTransportTcp);
			} else {
				builder.setProxy(domain + ":5223").setTransport(
						TransportType.LinphoneTransportTls);
			}

			builder.setExpires("604800")
					.setOutboundProxyEnabled(true)
					.setAvpfEnabled(true)
					.setAvpfRRInterval(3)
					.setQualityReportingCollector(
							"sip:voip-metrics@sip.linphone.org")
					.setQualityReportingEnabled(true)
					.setQualityReportingInterval(180)
					.setRealm("sip.linphone.org");

			mPrefs.setStunServer(context.getResources().getString(
					R.string.default_stun));
			mPrefs.setIceEnabled(true);
			mPrefs.setPushNotificationEnabled(true);
		} else {
			String forcedProxy = context.getResources().getString(
					R.string.setup_forced_proxy);
			if (!TextUtils.isEmpty(forcedProxy)) {
				builder.setProxy(forcedProxy).setOutboundProxyEnabled(true)
						.setAvpfRRInterval(5);
			}
		}

		if (context.getResources().getBoolean(R.bool.enable_push_id)) {
			String regId = mPrefs.getPushNotificationRegistrationID();
			String appId = context.getResources().getString(
					R.string.push_sender_id);
			if (regId != null && mPrefs.isPushNotificationEnabled()) {
				String contactInfos = "app-id=" + appId
						+ ";pn-type=google;pn-tok=" + regId;
				builder.setContactParameters(contactInfos);
			}
		}

		try {
			builder.saveNewAccount();
		} catch (LinphoneCoreException e) {
			e.printStackTrace();
		}
	}

	public static boolean wifiCall(Activity activity, AddressText mAddress) {
		try {
			if (!LinphoneManager.getInstance().acceptCallIfIncomingPending()) {
				LinphoneManager.getInstance().routeAudioToReceiver();
				LinphoneManager.getLc().enableSpeaker(false);

				if (mAddress.getText().length() > 0) {
					LinphoneManager.getInstance().newOutgoingCall(mAddress);
				} else {

					if (activity.getResources().getBoolean(
							R.bool.call_last_log_if_adress_is_empty)) {
						LinphoneCallLog[] logs = LinphoneManager.getLc()
								.getCallLogs();
						LinphoneCallLog log = null;
						for (LinphoneCallLog l : logs) {
							if (l.getDirection() == CallDirection.Outgoing) {
								log = l;
								break;
							}
						}
						if (log == null) {
							return false;
						}

						LinphoneProxyConfig lpc = LinphoneManager.getLc()
								.getDefaultProxyConfig();
						if (lpc != null
								&& log.getTo().getDomain()
										.equals(lpc.getDomain())) {
							mAddress.setText(log.getTo().getUserName());
						} else {
							mAddress.setText(log.getTo().asStringUriOnly());
						}
						mAddress.setSelection(mAddress.getText().toString()
								.length());
						mAddress.setDisplayedName(log.getTo().getDisplayName());
					}
				}
			}
		} catch (LinphoneCoreException e) {
			LinphoneManager.getInstance().terminateCall();
			onWrongDestinationAddress(activity, mAddress);
			return false;
		}
		;

		return true;
	}

	private static void onWrongDestinationAddress(Context context, AddressText mAddress) {
		Toast.makeText(
				context,
				String.format(
						context.getResources().getString(
								R.string.warning_wrong_destination_address),
						mAddress.getText().toString()), Toast.LENGTH_LONG)
				.show();
	}

	private static class LocalOrientationEventListener extends OrientationEventListener {
		public LocalOrientationEventListener(Context context) {
			super(context);
		}

		@Override
		public void onOrientationChanged(final int o) {
			if (o == OrientationEventListener.ORIENTATION_UNKNOWN) {
				return;
			}

			int degrees = 270;
			if (o < 45 || o > 315)
				degrees = 0;
			else if (o < 135)
				degrees = 90;
			else if (o < 225)
				degrees = 180;

			if (mAlwaysChangingPhoneAngle == degrees) {
				return;
			}
			mAlwaysChangingPhoneAngle = degrees;

			Log.d("Phone orientation changed to ", degrees);
			int rotation = (360 - degrees) % 360;
			LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
			if (lc != null) {
				lc.setDeviceRotation(rotation);
				LinphoneCall currentCall = lc.getCurrentCall();
				if (currentCall != null && currentCall.cameraEnabled() && currentCall.getCurrentParamsCopy().getVideoEnabled()) {
					lc.updateCall(currentCall, null);
				}
			}
		}
	}
}
