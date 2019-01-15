package com.siddharth_vijay.arduinocontrol;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;


public class ledControl extends AppCompatActivity {

    // Button btnOn, btnOff, btnDis;
    Button upBtn, downBtn, leftBtn, rightBtn, Discnt, Abt;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(com.siddharth_vijay.arduinocontrol.DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //view of the ledControl
        setContentView(R.layout.activity_led_control);

        //call the widgets
        upBtn = (Button)findViewById(R.id.upButton);
        downBtn = (Button)findViewById(R.id.downButton);
        leftBtn = (Button)findViewById(R.id.leftButton);
        rightBtn = (Button)findViewById(R.id.rightButton);
        Discnt = (Button)findViewById(R.id.dis_btn);

        new ConnectBT().execute(); //Call the class to connect

        //commands to be sent to bluetooth
        //holding down buttons
        upBtn.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch(event.getAction()){
                    //button pressed down
                    case MotionEvent.ACTION_DOWN:
                            moveForward();      //method to turn on
                        return true;

                    //button released
                    case MotionEvent.ACTION_UP:
                        //stop movement
                        stopMove();
                        return true;

                    //finger dragged off button
                    case MotionEvent.ACTION_CANCEL:
                        // stop movement
                        stopMove();
                        return true;
                }
                return false;
            }
        });

        downBtn.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event2)
            {
                switch(event2.getAction()){
                    //button pressed down
                    case MotionEvent.ACTION_DOWN:
                        moveBackward();      //method to turn on
                        return true;

                    //button released
                    case MotionEvent.ACTION_UP:
                        //stop movement
                        stopMove();
                        return true;

                    //finger dragged off button
                    case MotionEvent.ACTION_CANCEL:
                        // stop movement
                        stopMove();
                        return true;
                }
                return false;
            }
        });

        rightBtn.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event3)
            {
                switch(event3.getAction()){
                    //button pressed down
                    case MotionEvent.ACTION_DOWN:
                        turnRight();      //method to turn on
                        return true;

                    //button released
                    case MotionEvent.ACTION_UP:
                        //stop movement
                        stopTurn();
                        return true;

                    //finger dragged off button
                    case MotionEvent.ACTION_CANCEL:
                        // stop movement
                        stopTurn();
                        return true;
                }
                return false;
            }
        });


        leftBtn.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event4)
            {
                switch(event4.getAction()){
                    //button pressed down
                    case MotionEvent.ACTION_DOWN:
                        turnRight();      //method to turn on
                        return true;

                    //button released
                    case MotionEvent.ACTION_UP:
                        //stop movement
                        stopTurn();
                        return true;

                    //finger dragged off button
                    case MotionEvent.ACTION_CANCEL:
                        // stop movement
                        stopTurn();
                        return true;
                }
                return false;
            }
        });


    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    private void stopMove()
    {
        if (btSocket!=null)
        {
            try
            {

                btSocket.getOutputStream().write("z".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }



    private void stopTurn()
    {
        if (btSocket!=null)
        {
            try
            {

                btSocket.getOutputStream().write("y".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void moveBackward()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("0".toString().getBytes());
                //btSocket.getOutputStream().write("z".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void moveForward()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("1".toString().getBytes());
                //btSocket.getOutputStream().write("z".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void turnLeft()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("3".toString().getBytes());
                //btSocket.getOutputStream().write("z".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }


    private void turnRight()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("2".toString().getBytes());
                //btSocket.getOutputStream().write("z".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}