package windroids.sensors.heartrate;

import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTING;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTING;
import static windroids.sensors.constants.General.ANIMATION_SHORT;
import android.animation.AnimatorSet;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import windroids.sensors.search.BluetoothDeviceAdapter;
import windroids.sensors.util.IntentAndBundleUtil;

import windroids.sensors.heartrate.HeartrateReceiver;

public class HeartrateFragment extends Fragment implements HeartrateReceiver {

    private ImageView heartImage;
    private TextView heartrateView;
    private HeartrateCommunicationContext heartrateContext;
    private HeartrateBroadcastReceiver receiver;
    private AnimatorSet heartAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.heartrate_fragment, container, false);
        //setupViews(rootView);
        return null; //rootView;
    }

    private void setupViews(View rootView) {
        //heartrateView = (TextView) rootView.findViewById(R.id.heartrateFragment_heartrateValue);
        //heartImage = (ImageView) rootView.findViewById(R.id.heartrateFragment_heartImage);
    }

    @Override
    public void onResume() {
        startHeartrate();
        super.onResume();
    }

    private void startHeartrate() {
        receiver = new HeartrateBroadcastReceiver();
        receiver.register(getActivity());
        receiver.setHeartrateReceiver(this);
        BluetoothDeviceAdapter deviceAdapter = IntentAndBundleUtil.loadBluetoothDeviceAdapter(getArguments());
        heartrateContext = new HeartrateCommunicationContext(getActivity(), deviceAdapter.getDevice());
        heartrateContext.start();
    }

    @Override
    public void onPause() {
        stopHeartrate();
        receiver.unregister();
        super.onPause();
    }

    private void stopHeartrate() {
        heartrateContext.stop(false);
    }

    @Override
    public void onHeartrateReceived(int connectionState, int heartrate) {
        switch(connectionState) {
            case STATE_CONNECTING:
            case STATE_DISCONNECTING:
                getActivity().setProgressBarIndeterminateVisibility(true);
                break;
            case STATE_CONNECTED:
                getActivity().setProgressBarIndeterminateVisibility(false);
                //animateHeart();
                break;
            case STATE_DISCONNECTED:
                getActivity().setProgressBarIndeterminateVisibility(false);
                //transitionServer.requestTransition(SearchFragment.class, null);
                break;
        }
        heartrateView.setText(Integer.toString(heartrate) + " bpm");
    }
}
