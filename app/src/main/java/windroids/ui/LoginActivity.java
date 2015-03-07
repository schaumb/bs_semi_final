package windroids.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import windroids.R;
import windroids.entities.User;
import windroids.storage.UserStorage;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

        Button submitButton = (Button)findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoginDatas();
            }
        });
	}

    public User getLoginDatas() {
        String userName = ((EditText) findViewById(R.id.account_name)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        User user = new User();
        try {
            user = UserStorage.checkLogin(userName,password);
        } catch (UserStorage.WrongPasswordException e) {
            alertDialog("Wrong username/password!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private void alertDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .show();
    }
}
