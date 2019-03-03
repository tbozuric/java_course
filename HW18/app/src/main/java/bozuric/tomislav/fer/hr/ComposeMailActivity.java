package bozuric.tomislav.fer.hr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComposeMailActivity extends AppCompatActivity {

    public static final String RECEIVERS = "ana@baotic.org, marcupic@gmail.com";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_mail);

        final EditText emailAddress = findViewById(R.id.input_mail);
        final EditText title = findViewById(R.id.input_title);
        final EditText message = findViewById(R.id.input_message);
        final Button sendMail = findViewById(R.id.btn_send_mail);

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddressText = emailAddress.getText().toString();
                String titleText = title.getText().toString();
                String messageText = message.getText().toString();

                if (emailAddressText.isEmpty() || titleText.isEmpty() ||
                        messageText.isEmpty()) {
                    Toast.makeText(view.getContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emailAddressText).matches()) {
                    Toast.makeText(view.getContext(), getString(R.string.invalid_email_form), Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddressText});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, titleText);
                emailIntent.putExtra(Intent.EXTRA_TEXT, messageText);
                emailIntent.putExtra(Intent.EXTRA_CC, new String[]{RECEIVERS});

                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                }

                finish();
            }
        });
    }
}
