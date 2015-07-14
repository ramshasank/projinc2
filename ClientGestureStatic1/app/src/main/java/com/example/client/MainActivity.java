package com.example.client;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {


	private View v = null;
	private String filename = null;


    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    TextView textResponse;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear, buttonBackward, buttonForward, buttonLeft, buttonRight, buttonStop, buttonSmile, buttonGesture;
    String command;
    Boolean checkupdate = false;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonClear = (Button) findViewById(R.id.clear);
        buttonGesture = (Button) findViewById(R.id.gesture);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        textResponse = (TextView) findViewById(R.id.response);

        buttonBackward = (Button) findViewById(R.id.backward);
        buttonForward = (Button) findViewById(R.id.forward);
        buttonLeft = (Button) findViewById(R.id.left);
        buttonRight = (Button) findViewById(R.id.right);
        buttonSmile = (Button) findViewById(R.id.smile);
        buttonStop = (Button) findViewById(R.id.stop);
        getActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));

                    if(result.contains("forward"))
                    {
                        command="forward";
                        checkupdate=true;
                    }
                    if(result.contains("backward"))
                    {
                        command="backward";
                        checkupdate=true;
                    }
                    if (result.contains("left"))
                    {
                        command="left";
                        checkupdate=true;
                    }
                    if (result.contains("right"))
                    {
                        command="right";
                        checkupdate=true;
                    }
                    if (result.contains("smile"))
                    {
                        command="smile";
                        checkupdate=true;
                    }
                    if (result.contains("stop"))
                    {
                        command="stop";
                        checkupdate=true;
                    }
                    if (result.contains("up"))
                    {
                        command="up";
                        checkupdate=true;
                    }
                    if(result.contains("down"))
                    {
                        command="down";
                        checkupdate=true;
                    }
                    if(result.contains("sing"))
                    {
                        command="sing";
                        checkupdate=true;
                    }
                    if(result.contains("pause"))
                    {
                        command="pause";
                        checkupdate=true;
                    }
                    if(result.contains("call"))
                    {
                        command="call";
                        checkupdate=true;
                    }
                    if(result.contains("camera"))
                    {
                        command="camera";
                        checkupdate=true;
                    }
                    if(result.contains("movie"))
                    {
                        command="movie";
                        checkupdate=true;
                    }
                    if(result.contains("run"))
                    {
                        command="run";
                        checkupdate=true;
                    }



                }
                break;
            }

        }


        buttonStop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                command="stop";
                checkupdate=true;
            }

        });
        buttonSmile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                command="smile";
                checkupdate=true;
            }

        });
        buttonRight.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                command="right";
                checkupdate=true;
            }

        });
        buttonLeft.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                command="left";
                checkupdate=true;
            }

        });
        buttonBackward.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                command="backward";
                checkupdate=true;
            }

        });
        buttonForward.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                command="forward";
                checkupdate=true;
            }

        });

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);

        buttonGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PlayFragment.class));
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                textResponse.setText("");
            }});

    }

    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    MyClientTask myClientTask = new MyClientTask(
                            editTextAddress.getText().toString(),
                            Integer.parseInt(editTextPort.getText().toString()));
                    myClientTask.execute();
                }};

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String response = "";

        MyClientTask(String addr, int port){
            dstAddress = addr;
            dstPort = port;
        }
        @Override
        protected Void doInBackground(Void... arg0) {

            OutputStream outputStream;
            Socket socket = null;

            try {
                socket = new Socket(dstAddress, dstPort);
                Log.d("MyClient Task", "Destination Address : " + dstAddress);
                Log.d("MyClient Task", "Destination Port : " + dstPort + "");
                outputStream = socket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);

                while (true) {
                    if(checkupdate)
                    {
                        Log.d("Command", command);
                        Log.d("checkUpdate", checkupdate.toString());
                        printStream.print(command);
                        printStream.flush();
                        Log.d("Socekt connection", socket.isClosed()+"");
                        checkupdate=false;
                    }
                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            textResponse.setText(response);
            super.onPostExecute(result);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }








}
