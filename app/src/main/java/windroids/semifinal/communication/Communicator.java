package windroids.semifinal.communication;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

/**
 * Created by Tamás on 21/02/2015.
 */
public class Communicator {

    private static final String ENCODING = "ISO-8859-1";

    private static final String COMM_MSG_HELLO_CLIENT = "BSP 1.0 CLIENT HELLO";
    private static final String COMM_MSG_HELLO_SERVER = "BSP 1.0 SERVER HELLO";
    private static final String COMM_MSG_ASK_ID = "SEND YOUR ID";
//    private static final String COMM_MSG_DATA_NAME = "TEST3";
//    private static final String COMM_MSG_RQST_SERVER = "TEST3 ID ACK - WAITING FOR REQUEST";
    private static final String COMM_MSG_RQST_CLIENT = "RQSTDATA";
    private static final String COMM_MSG_RQST_TRAIN_CLIENT = "RQSTTRAIN";
    private static final String COMM_MSG_RQST_TEST_CLIENT = "RQSTTEST";
    private static final String SERVER_MESSAGE = "SERVER_MESSAGE";

    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;
    private String dataId;

    public Communicator(String address, int port, String dataId) throws IOException {
        this.dataId = dataId; //example: TEST3

        socket = new Socket(address, port);

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        br = new BufferedReader(new InputStreamReader(inputStream, ENCODING));
        outputStream = new BufferedOutputStream(outputStream);
        pw = new PrintWriter(new OutputStreamWriter(outputStream, ENCODING));
    }

    public void endCommuncation() throws IOException {
        socket.close();
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
                message = getMessage(); //TEST3 ID ACK - WAITING FOR REQUEST
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

    public void answerTestData(boolean suceedState) throws ExecutionException, InterruptedException {
        String clientAnswer;
        clientAnswer = suceedState ? "ACCEPT" : "REJECT";

        new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... params) {
                sendMessage(params[0]);
                return null;
            }
        }.execute(clientAnswer);
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
}
