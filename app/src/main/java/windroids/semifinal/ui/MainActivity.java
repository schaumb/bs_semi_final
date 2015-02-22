package windroids.semifinal.ui;

import org.xmlpull.v1.XmlPullParserException;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import windroids.semifinal.R;
import windroids.semifinal.communication.Communicator;
import windroids.semifinal.communication.XmlParser;
import windroids.semifinal.logic.Logic;
import windroids.semifinal.logic.pattern.Pattern;
import windroids.semifinal.ui.keyboard.Keyboard;
import windroids.semifinal.util.Config;

public class MainActivity extends ActionBarActivity implements Keyboard.EventListener {

    private TextView inputView;
    private TextView infoView;
    private TextView counterView;
    private EditText ipaddressView;

    private State currentState;

    private enum State {
        START,
        TRAINING,
        TEST
    }

    private String password;
    private List<Pattern> training = new ArrayList<>(100);
    private Logic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Keyboard keyboard = new Keyboard();
        keyboard.setEventListener(this);
        getFragmentManager().beginTransaction().add(R.id.keyboard_container, keyboard, null).commit();

        inputView = (TextView) findViewById(R.id.input_view);
        infoView = (TextView) findViewById(R.id.description_view);
        counterView = (TextView) findViewById(R.id.counter_view);
        ipaddressView = (EditText) findViewById(R.id.ipaddress_view);

        changeToStartState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String testData;
        switch (item.getItemId()) {
            case R.id.menu_windroids:
                testData = "WinDroids";
                break;
            case R.id.menu_test1:
                testData = "TEST1";
                break;
            case R.id.menu_test2:
                testData = "TEST2";
                break;
            case R.id.menu_test3:
                testData = "TEST3";
                break;
            case R.id.menu_test4:
                testData = "TEST4";
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
//        startCommunication(Config.COMM_HOST, Config.COMM_PORT, testData, true);
        startCommunication(getIpFromText(), Config.COMM_PORT, testData, true);
        return super.onOptionsItemSelected(item);
    }

    private void changeToStartState() {
        infoView.setText(R.string.start_instructions);
        currentState = State.START;
        counterView.setVisibility(View.GONE);
        password = null;
        training = new ArrayList<>(100);
        logic = null;
    }

    private void changeToTrainingState() {
        infoView.setText(R.string.training_instructions);
        currentState = State.TRAINING;
        startCounter();
    }

    private void changeToTestState() {
        infoView.setText(R.string.test_instructions);
        currentState = State.TEST;
    }

    public void finishTraining() {
        if (training.size() == 0) {
            alertDialog(getString(R.string.training_failed));
            changeToStartState();
        }
        logic = new Logic(password, training);
        logic.init();
        changeToTestState();
    }

    private void startCounter() {
        new AsyncTask<Void, Integer, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        counterView.setVisibility(View.VISIBLE);
                    }
                });
                for (int i = 0; i < 30; ++i) {
                    publishProgress(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                counterView.setText(String.valueOf(values[0]));
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                counterView.setVisibility(View.GONE);
                finishTraining();
            }
        }.execute((Void[]) null);
    }

    private void startCommunication(String hostName, int portName, String dataName, boolean feedback) {
        Communicator communicator;
        String password;
        List<Pattern> training;
        Pattern actualPattern;
        ArrayList<String> testResultLogList = new ArrayList<>();
        boolean isMatching;
        try {
            communicator = new Communicator(hostName, portName, dataName);
            if (communicator.startCommunication()) {
                password = communicator.doPreCommuncationAndGetPassword();
                String patternListRaw = communicator.getXmlFromServer();
                training = XmlParser.parseTestData(patternListRaw);
                logic = new Logic(password, training);
                logic.init();
                String nextTestDataRaw = communicator.getNextTestDataXmlFile();
                while (!communicator.checkEndMessage(nextTestDataRaw)) {
                    actualPattern = XmlParser.parsePattern(nextTestDataRaw);
                    isMatching = logic.isMatching(actualPattern);
                    logTestData(testResultLogList, isMatching);

                    nextTestDataRaw = communicator.answerTestDataAndGetNext(isMatching);
                }
                if (feedback) {
                    alertDialog("Server communication finished.");
                    printTestData(testResultLogList, nextTestDataRaw);
                }
                communicator.endCommuncation();
            } else {
                alertDialog("Server communication failed.");
            }
        } catch (IOException | InterruptedException | ExecutionException | XmlPullParserException e) {
            e.printStackTrace();
        }
	}

    private void logTestData(ArrayList<String> list, boolean isMatching) {
        list.add("Own: " + (isMatching ? "ACCEPT" : "REJECT") + " Their: ");
    }

    private void printTestData(ArrayList<String> list, String resultData) {
        StringBuilder builder;
        String dataString = resultData.substring(resultData.indexOf("[") + 1, resultData.length() - 2);
        String[] array = dataString.split(" ");
        for (int i = 0; i < list.size(); ++i) {
            builder = new StringBuilder(list.get(i));
            builder.append(array[i]);
            //Log.d("PatternStudied",logic.patternStudied);
            //Log.d("PatternMatch",logic.patternMatch);
            Log.d(Config.LOG,builder.toString());
        }

    }

    public void alertDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .create()
                .show();
    }


    @Override
    public void onTextInput(String character) {
        String currentText = inputView.getText().toString();
        currentText += character;
        inputView.setText(currentText);
    }

    @Override
    public void onBackspace() {
        String currentText = inputView.getText().toString();

        if (currentText.length() == 0) {
            return;
        } else if (currentText.length() == 1) {
            inputView.setText("");
            return;
        }
        currentText = currentText.substring(0, currentText.length() - 1);
        inputView.setText(currentText);
    }

    @Override
    public void onSubmit(Pattern pattern) {
        switch (currentState) {
            case START:
                password = inputView.getText().toString();
                if (password.isEmpty()) {
                    alertDialog(getString(R.string.bad_password));
                }
                changeToTrainingState();
                break;
            case TRAINING:
                training.add(pattern);
                break;
            case TEST:
                if (logic.isMatching(pattern)) {
                    alertDialog(getString(R.string.successful_auth));
                } else {
                    alertDialog(getString(R.string.fail_auth));
                }
        }
        inputView.setText("");
    }

    public String getIpFromText() {
        return ipaddressView.getText().toString();
    }
}
