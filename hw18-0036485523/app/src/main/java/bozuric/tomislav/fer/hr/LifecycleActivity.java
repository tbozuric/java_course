package bozuric.tomislav.fer.hr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LifecycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

        final EditText inputFirst = findViewById(R.id.input_first);
        final EditText inputSecond = findViewById(R.id.input_second);
        final TextView labelResult = findViewById(R.id.label_result);
        final Button btnCalculate = findViewById(R.id.btn_calculate);
        final Button btnSend = findViewById(R.id.btn_send);
        final Button btnComposeEmail = findViewById(R.id.btn_compose_email);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first = inputFirst.getText().toString();
                String second = inputSecond.getText().toString();

                int firstNumber = 0;
                int secondNumber = 0;

                try {
                    firstNumber = Integer.parseInt(first);
                    secondNumber = Integer.parseInt(second);
                } catch (NumberFormatException ignored) {
                }

                if (secondNumber != 0) {
                    labelResult.setText(String.valueOf((double) firstNumber / secondNumber));
                    return;
                }
                labelResult.setText(getString(R.string.unsupported_operation));

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent
                        (LifecycleActivity.this, ShowActivity.class);

                Bundle extras = new Bundle();
                extras.putString("result", labelResult.getText().toString());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        btnComposeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LifecycleActivity.this,
                        ComposeMailActivity.class);
                startActivity(intent);
            }
        });
    }
}
