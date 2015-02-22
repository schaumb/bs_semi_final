package windroids.semifinal.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import windroids.semifinal.R;
import windroids.semifinal.communication.Communicator;
import windroids.semifinal.ui.keyboard.Keyboard;
import windroids.semifinal.util.Config;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().add(R.id.keyboard_container, new Keyboard(), null).commit();

        //teszt a kliens-szerver kommunikaciora. Kerlek allitsd be a Configban az ip cimedet, es a
        // futtatasa elott inditsd el a szervert (java -jar ECServer.jar)
//        testCommunication();
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
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
