package windroids.semifinal.communication;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutionException;

import windroids.semifinal.logic.pattern.Pattern;
import windroids.semifinal.util.Config;

/**
 * Created by Tamás on 21/02/2015.
 */
public class Communicator {

    private static final String ENCODING = "ISO-8859-1";

    private static final String COMM_MSG_HELLO_CLIENT = "BSP 1.0 CLIENT HELLO";
    private static final String COMM_MSG_HELLO_SERVER = "BSP 1.0 SERVER HELLO";
    private static final String COMM_MSG_ASK_ID = "SEND YOUR ID";
    private static final String COMM_MSG_RQST_CLIENT = "RQSTDATA";
    private static final String COMM_MSG_RQST_TRAIN_CLIENT = "RQSTTRAIN";
    private static final String COMM_MSG_RQST_TEST_CLIENT = "RQSTTEST";
    private static final String SERVER_MESSAGE = "SERVER_MESSAGE";

    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;
    private String dataId;
    private int port;
    private String address;
    private boolean socketEnabled;

    public Communicator(final String address, final int port, final String dataId) throws IOException, ExecutionException, InterruptedException {
        this.dataId = dataId; //example: TEST3
        this.address = address;
        this.port = port;
    }

    public Boolean startCommunication() throws ExecutionException, InterruptedException {
        boolean result = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    socket = new Socket(address, port);
                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();
                    br = new BufferedReader(new InputStreamReader(inputStream, ENCODING));
                    outputStream = new BufferedOutputStream(outputStream);
                    pw = new PrintWriter(new OutputStreamWriter(outputStream, ENCODING));
                    socketEnabled = true;
                    return socketEnabled;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return socketEnabled;
            }
        }.execute().get();
        Log.d("Connection succeed: ", String.valueOf(result));
        return result;
    }

    public Boolean endCommuncation() throws IOException, ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    socket.close();
                    socketEnabled = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }.execute().get();

    }

    public String doPreCommuncationAndGetPassword() throws ExecutionException, InterruptedException {
        return new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String message = null;
                message = getMessage(); //BSP 1.0 SERVER HELLO
                if (message.equals(COMM_MSG_HELLO_SERVER)) {
                    sendMessage(COMM_MSG_HELLO_CLIENT);
                }
                message = getMessage(); //SEND YOUR ID
                if (message.equals(COMM_MSG_ASK_ID)) {
                    sendMessage(dataId);
                }
                getMessage(); //TESTX ID ACK - WAITING FOR REQUEST
                sendMessage(COMM_MSG_RQST_CLIENT);
                message = getMessage(); // PASSWORD=jelszó
                return message.split("=")[1];
            }
        }.execute().get();

    }

    public String getXmlFromServer() throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                sendMessage(COMM_MSG_RQST_TRAIN_CLIENT);
                String response = null;
                try {
                    response = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return response;
            }
        }.execute().get();
    }

    public String getNextTestDataXmlFile() throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                sendMessage(COMM_MSG_RQST_TEST_CLIENT);
                String response = null;
                response = getMessage();
                return response;
            }
        }.execute().get();
    }

    public String answerTestDataAndGetNext(boolean suceedState) throws ExecutionException, InterruptedException {
        String clientAnswer;
        clientAnswer = suceedState ? "ACCEPT" : "REJECT";

        return new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                sendMessage(params[0]);
                return getMessage();
            }
        }.execute(clientAnswer).get();
    }

    private void sendMessage(String message) {
        pw.println(message);
        pw.flush();
    }

    private String getMessage() {
        String message = null;
        try {
            message = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(SERVER_MESSAGE, message);
        return message;
    }

    public boolean checkEndMessage(String message) {
        return message.startsWith("GOODBYE");
    }
}

