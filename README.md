# bluetoothx

##蓝牙通信

基于[谷歌官方经典蓝牙示例](
https://github.com/android/connectivity-samples/tree/main/BluetoothChat)封装

##快速使用
```
implementation 'com.ccand99.mark:bluetoothx:1.0.0'
```

##权限说明
需要动态权限申请，demo有
权限声明
```
<uses-permission android:name="android.permission.BLUETOOTH"/>
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-feature android:name="android.hardware.bluetooth_le" android:required="false"/>
```

##初始化方式：
```
blueTooth = BlueTooth.getInstance(this, REQUEST_ENABLE_BT, mHandler)
```
其中this必须为Activity
REQUEST_ENABLE_BT，是允许蓝牙开启返回值等于onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)中的requestCode
mHandler为处理带回的消息

##使用方式<br>
被连接方需要在主动调用start()推荐在onResume()

主动连接方调用blueTooth?.showDeviceListDialog()，然后选择已配对完成的设备会自动连接。
