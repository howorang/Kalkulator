package com.example.kalkulator;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdvancedCalculatorActivity extends AppCompatActivity {

    @BindView(R.id.equation_display)
    TextView equationDisplay;

    @BindView(R.id.result_display)
    TextView resultDisplay;

    @BindView(R.id.c_button)
    Button cancelButton;

    @BindView(R.id.addition_button)
    Button additionButton;

    @BindView(R.id.bksp_button)
    Button backSpaceButton;

    @BindView(R.id.division_button)
    Button divisionButton;

    @BindView(R.id.equals_button)
    Button equalsButton;

    @BindView(R.id.multiplication_button)
    Button multiplicationButton;

    @BindView(R.id.period_button)
    Button periodButton;

    @BindView(R.id.sign_change_button)
    Button signChangeButton;

    @BindView(R.id.subtraction_button)
    Button subtractionButton;

    @BindView(R.id.number_pad_grid)
    GridLayout numberPadGrid;

    @BindView(R.id.zero)
    Button zeroButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_calculator);
        ButterKnife.bind(this);
        buildNumberButtons();
        zeroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberListener(0);
            }
        });
    }

    private void buildNumberButtons() {
        int[] numbers = {7, 8, 9, 4, 5, 6, 1, 2, 3};

        for (final int number : numbers) {
            Button numberPadButton = new Button(this);
            numberPadButton.setText(String.valueOf(number));
            numberPadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numberListener(number);
                }
            });
            GridLayout.LayoutParams parem = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, 1f));
            numberPadButton.setLayoutParams(parem);
            numberPadGrid.addView(numberPadButton);

        }
    }

    private void numberListener(int number) {
        equationDisplay.append(String.valueOf(number));
    }

    @OnClick(value = {R.id.subtraction_button, R.id.addition_button, R.id.multiplication_button, R.id.division_button, R.id.period_button})
    public void onOperatorClick(View view) {
        Button button = (Button) view;
        equationDisplay.append(button.getText());
    }

    @OnClick(value = {R.id.equals_button})
    public void onEqualsClick(View view) {
        String equation = equationDisplay.getText().toString();
        Parser parser = new Parser(equation);
        parser.process();
        resultDisplay.setText(String.valueOf(parser.getResult()));
    }

    @OnClick(value = {R.id.bksp_button})
    public void onBackSpaceClick(View view) {
        String equation = equationDisplay.getText().toString();
        if (equation.length() > 0) {
            equationDisplay.setText(equation.substring(0, equation.length() - 1));
        }
    }

    @OnClick(value = {R.id.c_button})
    public void onClearButtonClick(View view) {
        equationDisplay.setText("");
        resultDisplay.setText("");
    }

    @OnClick(value = {R.id.sign_change_button})
    public void onSignChangeButtonClick(View view) {
        String equation = equationDisplay.getText().toString();
        List<String> tokens = Tokenizer.tokenize(equation);
        String lastToken = tokens.get(tokens.size() - 1);
        if (Parser.isNumeric(lastToken)) {
            double lastTokenValue = Double.parseDouble(lastToken);
            lastTokenValue = lastTokenValue * -1;
            String result = replaceLast(equation,lastToken,String.valueOf(lastTokenValue));
            equationDisplay.setText(result);
        }
    }

    public static String replaceLast(String string, String toReplace, String replacement) {
        int pos = string.lastIndexOf(toReplace);
        if (pos > -1) {
            return string.substring(0, pos)
                    + replacement
                    + string.substring(pos + toReplace.length(), string.length());
        } else {
            return string;
        }
    }

    @OnClick(value = {R.id.sin, R.id.cos, R.id.tan,
            R.id.ln, R.id.sqrt, R.id.power_of_two, R.id.log})
    public void onFunctionButtonClick(View view) {
        Button functionButton = (Button) view;
        String equation = equationDisplay.getText().toString();
        Parser parser = new Parser(equation);
        parser.process();
        handleFunction(functionButton.getText().toString(), parser.getResult());
    }

    private void handleFunction(String functionName, double argument) {
        String result = String.valueOf(Parser.calculateFunction(functionName, argument));
        resultDisplay.setText(result);
    }
}
