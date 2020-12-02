package com.kcanel.tareas;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;

import com.kcanel.tareas.databinding.ActivityMainBinding;
import com.kcanel.tareas.ui.BaseFragment;

public class MainActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener {

    NavController navController;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setStartDestination();
    }

    private void setStartDestination() {
        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHost.getNavController();

        NavInflater navInflater = navController.getNavInflater();
        NavGraph graph = navInflater.inflate(R.navigation.main_graph);
        graph.setStartDestination(R.id.listFragment);

    }



    @Override
    public boolean isConnected() {
        hideKeyboard();
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        } catch (Exception e) {
            connected = false;
        }
        if (!connected) showInternetError();
        return connected;
    }

    private void hideKeyboard() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null)
            manager.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
    }

    private void showInternetError() {
        Toast.makeText(this, "Revise su conexión a internet", Toast.LENGTH_SHORT).show();
    }
}