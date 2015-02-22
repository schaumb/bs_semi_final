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
		
		changeToStartState();

		// teszt a kliens-szerver kommunikaciora. Kerlek allitsd be a Configban az ip cimedet, es a
		// futtatasa elott inditsd el a szervert (java -jar ECServer.jar)
//        testCommunication();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void testCommunication() {
		Communicator communicator = null;
		try {
			communicator = new Communicator(Config.COMM_HOST, Config.COMM_PORT, "TEST3");
			communicator.startCommunication();
			String password = communicator.doPreCommuncationAndGetPassword();
			String patternListRaw = communicator.getXmlFromServer();
			List<Pattern> patternList = XmlParser.parseTestData(patternListRaw);
			String nextTestDataRaw = communicator.getNextTestDataXmlFile();
			while (!communicator.checkEndMessage(nextTestDataRaw)) {
				Pattern pattern = XmlParser.parsePattern(nextTestDataRaw);
				Log.d(Config.LOG, "Pattern events number: " + pattern.getEvents().size());
				nextTestDataRaw = communicator.answerTestDataAndGetNext(true);
			}
			communicator.endCommuncation();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
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
				changeToTrainingState();
				break;
			case TRAINING:
				training.add(pattern);
				break;
			case TEST:
				if (logic.isMatching(pattern)) {
					new AlertDialog.Builder(this)
							.setMessage(R.string.successful_auth)
							.create()
							.show();
				} else {
					new AlertDialog.Builder(this)
							.setMessage(R.string.fail_auth)
							.create()
							.show();
				}
		}
		inputView.setText("");
	}
}
