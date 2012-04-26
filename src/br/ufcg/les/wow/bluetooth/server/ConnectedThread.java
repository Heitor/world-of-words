package br.ufcg.les.wow.bluetooth.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.ufcg.les.wow.bluetooth.WoWHandle;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

/**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    public class ConnectedThread extends Thread {
    	private static final String TAG = "[ConnectedServerThread]";
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private final WoWHandle handle;

        public ConnectedThread(BluetoothSocket socket, WoWHandle handle) {
            Log.d(TAG, "create ConnectedThread");
            mmSocket = socket;
            this.handle = handle;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.read(buffer);

                    this.handle.obtainMessage(WoWHandle.RECEBER_MENSAGEM, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    break;
                }
            }
        }

        /**
         * Write to the connected OutStream.
         * @param buffer  The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);

                this.handle.obtainMessage(WoWHandle.ENVIAR_MENSAGEM, -1, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }