package com.example.kalkulator;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.simple_button)
    Button simpleButton;

    @BindView(R.id.advanced_button)
    Button advancedButton;

    @BindView(R.id.about_button)
    Button aboutButton;

    @BindView(R.id.exit_button)
    Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(value = {R.id.simple_button})
    public void onSimpleClick(View view) {
        Intent intent = new Intent(this,SimpleCalculatorActivity.class);
        this.startActivity(intent);
    }

    @OnClick(value = {R.id.advanced_button})
    public void onAdvancedClick(View view) {
        Intent intent = new Intent(this,AdvancedCalculatorActivity.class);
        this.startActivity(intent);
    }

    @OnClick(value = {R.id.exit_button})
    public void onExitClick(View view) {
        this.finish();
    }
}
