package com.ccand99.bluetoothx_demo


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ccand99.mark.*
import com.ccand99.bluetoothx_demo.kthighfunction.showToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private fun PackageManager.missingSystemFeature(name: String): Boolean = !hasSystemFeature(name)

    private var blueTooth: BlueTooth?= null

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private val mHandler: Handler = Handler(Handler.Callback {
        when (it.what) {
            MESSAGE_STATE_CHANGE -> {
                when (it.arg1) {
                    BlueTooth.STATE_CONNECTED -> Log.i(TAG, "handleMessage: connected ")
                    BlueTooth.STATE_CONNECTING -> Log.i(TAG, "handleMessage: connecting...")
                    else -> Log.i(TAG, "handleMessage: not connected")
                }
            }
            MESSAGE_READ -> {
                val readBuf = it.obj as ByteArray
                // construct a string from the valid bytes in the buffer
                val readMessage = String(readBuf, 0, it.arg1)
                Log.i(TAG, "handle read Message: $readMessage")

            }
            MESSAGE_WRITE ->{
                val writeBuf = it.obj as ByteArray
                // construct a string from the valid bytes in the buffer
                val writeMessage = String(writeBuf)
                Log.i(TAG, "handle write Message: $writeMessage")
            }
            MESSAGE_DEVICE_NAME ->{
                // save the connected device's name
                val mConnectedDeviceName = it.data.getString(DEVICE_NAME)
                "$mConnectedDeviceName connected".showToast(applicationContext)
            }
            MESSAGE_TOAST -> {
                it.data.getString(TOAST)?.showToast(applicationContext)
            }
            else -> {}
        }
        false
    })

    /**
     * 请求权限的请求码
     */
    companion object{
        const val REQUEST_ENABLE_BT = 110
        const val ACTION_REQUEST_PERMISSIONS = 0x001
        const val TAG = "MainActivity"
    }

    private val NEEDED_PERMISSIONS = arrayOf(
        Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (checkPermissions(NEEDED_PERMISSIONS)){
            blueTooth = BlueTooth.getInstance(this, REQUEST_ENABLE_BT, mHandler)
            //scanLeDevice()
        }else{
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS)
        }

        connect.setOnClickListener {
            blueTooth?.chooseDeviceConnect()
        }

        send.setOnClickListener {
            blueTooth?.write("test".toByteArray())
        }
    }

    /**
     * 权限检查
     *
     * @param neededPermissions 需要的权限
     * @return 是否全部被允许
     */
    private fun checkPermissions(neededPermissions: Array<String>?): Boolean {
        if (neededPermissions == null || neededPermissions.isEmpty()) {
            return true
        }
        var allGranted = true
        for (neededPermission in neededPermissions) {
            allGranted = allGranted and (ContextCompat.checkSelfPermission(
                this,
                neededPermission
            ) == PackageManager.PERMISSION_GRANTED)
        }
        return allGranted
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var isAllGranted = true
        for (grantResult in grantResults) {
            isAllGranted = isAllGranted and (grantResult == PackageManager.PERMISSION_GRANTED)
        }
        afterRequestPermission(requestCode, isAllGranted)
    }

    private fun afterRequestPermission(requestCode: Int, isAllGranted: Boolean) {
        if (isAllGranted) {
            blueTooth = BlueTooth.getInstance(this, REQUEST_ENABLE_BT, mHandler)
        } else {
            R.string.rejected.showToast(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "onActivityResult: requestCode = $requestCode")
        Log.i(TAG, "onActivityResult: resultCode = $resultCode")
        if (REQUEST_ENABLE_BT == requestCode){
            blueTooth = BlueTooth.getInstance(this, REQUEST_ENABLE_BT, mHandler)
            //scanLeDevice()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 搜索le蓝牙设备
     */
    private fun scanLeDevice(){
        blueTooth?.startDiscoverable(0)
    }

    override fun onResume() {
        super.onResume()
        if (blueTooth?.getState() == BlueTooth.STATE_NONE) {
            blueTooth?.start()
        }
    }

    override fun onStop() {
        blueTooth?.stop()
        blueTooth=null
        super.onStop()
    }
}