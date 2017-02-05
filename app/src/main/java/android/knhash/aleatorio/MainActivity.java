package android.knhash.aleatorio;

import android.content.ClipboardManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final private ArrayList<String> adjectives = new ArrayList<>();
    final private ArrayList<String> nouns = new ArrayList<>();
    final private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Looky here
        final TextView text = (TextView) findViewById(R.id.textView);
        AssetManager assetManager = getAssets();
        try {

            //Populate adjectives ArrayList
            InputStream inputStream = assetManager.open("adjectives.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((bufferedReader.readLine() != null)) {
                adjectives.add(bufferedReader.readLine());
            }

            //Populate nouns ArrayList
            inputStream = assetManager.open("nouns.txt");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((bufferedReader.readLine() != null)) {
                nouns.add(bufferedReader.readLine());
            }

            inputStream.close();

            Log.d("FILE", "Success");
        } catch (IOException e) {
            Log.d("FILE", "Failure");
            e.printStackTrace();
        }

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textString;
                textString = adjectives.get(random.nextInt(adjectives.size())) + " " + nouns.get(random.nextInt(nouns.size()));
                text.setText(textString);

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        text.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard =
                        (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(text.getText());
                Toast.makeText(MainActivity.this, "\"" + text.getText() + "\"" + " copied to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.content_about)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle(R.string.app_name);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
