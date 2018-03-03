package tonimor.vdkans.shareintentapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import tonimor.vdkans.shareintentapp.share.ShareHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton = (Button)findViewById(R.id.my_button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick();
            }
        });
    }

    private void buttonClick()
    {
        String subject = "Asunto del mensaje";
        String body = "Cuerpo del mensaje que voy a enviar";
        ShareHelper helper = new ShareHelper(this, subject, body);
        helper.share();
    }


    // o bien, directamente a partir del stándard chooser...
    private void shareWithStandardChooser()
    {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        String share_body = "Cuerpo del mensaje";
        String share_subject = "Subject del mensaje";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, share_subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, share_body);

        String share_via = "Compartir vía...";
        startActivity(Intent.createChooser(sharingIntent, share_via));
    }
}
