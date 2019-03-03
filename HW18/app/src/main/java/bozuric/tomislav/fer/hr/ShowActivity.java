package bozuric.tomislav.fer.hr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        String result = getIntent().getExtras().getString("result");

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        EditText inputResult = findViewById(R.id.input_result);
        inputResult.setText(result);

    }
}
