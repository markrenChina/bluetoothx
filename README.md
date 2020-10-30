# bluetoothx

[ ![Download](https://api.bintray.com/packages/markren/maven/bluetoothx/images/download.svg?version=1.0.1) ](https://bintray.com/markren/maven/bluetoothx/1.0.1/link)

## 蓝牙通信

基于[谷歌官方经典蓝牙示例](
https://github.com/android/connectivity-samples/tree/main/BluetoothChat)封装

## 快速使用
```
implementation 'com.ccand99.mark:bluetoothx:1.0.0'
```

## 权限说明
需要动态权限申请，demo有
权限声明
```
<uses-permission android:name="android.permission.BLUETOOTH"/>
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-feature android:name="android.hardware.bluetooth_le" android:required="false"/>
```

## 初始化方式：
```
blueTooth = BlueTooth.getInstance(this, REQUEST_ENABLE_BT, mHandler)
```
其中this必须为Activity
REQUEST_ENABLE_BT，是允许蓝牙开启返回值等于onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)中的requestCode
mHandler为处理带回的消息

## 使用方式<br>
被连接方需要在主动调用start()推荐在onResume()

主动连接方调用blueTooth?.chooseDeviceConnect()，然后选择已配对完成的设备会自动连接。

## 通用常量
kt文件下
```
// Message types sent from the BluetoothChatService Handler
const val  MESSAGE_STATE_CHANGE = 1
const val MESSAGE_READ = 2
const val MESSAGE_WRITE = 3
const val MESSAGE_DEVICE_NAME = 4
const val MESSAGE_TOAST = 5

// Key names received from the BluetoothChatService Handler
const val DEVICE_NAME = "device_name"
const val TOAST = "toast"
```

BlueTooth下
```
// Constants that indicate the current connection state
const val STATE_NONE = 0 // we're doing nothing
const val STATE_LISTEN = 1 // now listening for incoming connections
const val STATE_CONNECTING = 2 // now initiating an outgoing connection
const val STATE_CONNECTED = 3 // now connected to a remote device
```

# v1.0.1
1.新整getRemoteDevice(mac: String) 可以用mac获得BluetoothDevice 然后用connect(device: BluetoothDevice, secure: Boolean)直接连接<br>
2.可修改BlueTooth.MY_UUID_INSECURE 和 BlueTooth.MY_UUID_SECURE 请在getInstance之前修改<br>
3.修改showDeviceListDialog()方法名字为chooseDeviceConnect()<br>
