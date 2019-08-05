package akshay.kapase.tvsautomobile.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import akshay.kapase.tvsautomobile.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;

    @BindView(R.id.etUsername)
    EditText etUsername;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ButterKnife.bind(this);
        loginViewModel = createLoginViewModel();
        loginViewModel.isLoginSuccessful().observe(this,new LoginObserver());
    }

    private LoginViewModel createLoginViewModel() {
        return ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @OnClick(R.id.btnLogin)
    public void doLogin(){
        if(isOnline()){
            if(validateUserInput()){
                disableInputs();
                progressBar.setVisibility(View.VISIBLE);
                loginViewModel.loginUser(etUsername.getText().toString(),etPassword.getText().toString());
            } else{
                Toast.makeText(this, "Please enter correct input", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    private void enableInputs() {
        etUsername.setEnabled(true);
        etPassword.setEnabled(true);
        btnLogin.setEnabled(true);
    }

    private void disableInputs() {
        etUsername.setEnabled(false);
        etPassword.setEnabled(false);
        btnLogin.setEnabled(false);
    }

    private boolean validateUserInput() {
        if(!etUsername.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }

    class LoginObserver implements Observer<Boolean>{
        @Override
        public void onChanged(Boolean isLoginSuccessful) {
            if(isLoginSuccessful){
                progressBar.setVisibility(View.GONE);
                loginViewModel.navigateToNextScreen();
            } else{
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
            }
            enableInputs();
        }
    }

}
