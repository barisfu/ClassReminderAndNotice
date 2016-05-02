package com.example.shafiqur.classreminder.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shafiqur.classreminder.MainActivity;
import com.example.shafiqur.classreminder.R;
import com.example.shafiqur.classreminder.helper.ParseUtils;
import com.example.shafiqur.classreminder.helper.PrefManager;
import com.example.shafiqur.classreminder.model.Message;

import java.util.ArrayList;
import java.util.List;


public class MainActivityParse extends AppCompatActivity {

    private static String TAG = MainActivityParse.class.getSimpleName();
    public ListView listView;
    private Toolbar mToolbar;
    private List<Message> listMessages = new ArrayList<>();
    private MessageAdapter adapter;
    private PrefManager pref;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        b= (Button)findViewById(R.id.mainActivity);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivityParse.this,"Redirecting to MainActivityPage",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivityParse.this, MainActivity.class);
                startActivity(intent);

            }
        });


        listView = (ListView) findViewById(R.id.list_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        adapter = new MessageAdapter(this);
        pref = new PrefManager(getApplicationContext());

        listView.setAdapter(adapter);

        Intent intent = getIntent();

        String email = pref.getEmail();

        if (email != null) {
            ParseUtils.subscribeWithEmail(pref.getEmail());
        } else {
            Log.e(TAG, "Email is null. Not subscribing to parse!");
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String message = intent.getStringExtra("message");

        Message m = new Message(message, System.currentTimeMillis());
        listMessages.add(0, m);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            pref.logout();
            Intent intent = new Intent(MainActivityParse.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MessageAdapter extends BaseAdapter {

        LayoutInflater inflater;

        public MessageAdapter(Activity activity) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return listMessages.size();
        }

        @Override
        public Object getItem(int position) {
            return listMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.list_row, null);
            }

            TextView txtMessage = (TextView) view.findViewById(R.id.message);
            TextView txtTimestamp = (TextView) view.findViewById(R.id.timestamp);

            Message message = listMessages.get(position);
            txtMessage.setText(message.getMessage());

            CharSequence ago = DateUtils.getRelativeTimeSpanString(message.getTimestamp(), System.currentTimeMillis(),
                    0L, DateUtils.FORMAT_ABBREV_ALL);

            txtTimestamp.setText(String.valueOf(ago));

            return view;
        }
    }
}
