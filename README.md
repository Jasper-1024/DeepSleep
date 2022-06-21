# DeepSleep

- This is English version(although a lot of google translation😂), for china user [中文版](https://github.com/Jasper-1024/DeepSleep/blob/dev/README/README_zh_cn.md)

- DeepSleep is a Xposed module. It can limit the background behavior of apps and reduce the power consumption of apps in the background.
  - Block acquire [wakelock](https://developer.android.com/training/scheduling/wakelock) and [alarms](https://developer.android.com/training/scheduling/alarms) to prevent redundant wake-ups.
  - Block start [services](https://developer.android.com/guide/components/services) to reduce memory consumption.
  - Block [sync](https://developer.android.com/training/sync-adapters?hl=zh-cn) to prevent redundant wake-ups.

- EDxposed/LSPosed scope Select the application to be limited.

- Serious warning
  - Using deepsleep is not recommended if your device has no standby power consumption problems.
  - Using deepsleep to limit the application may cause unusual problems with the application.
    - IM may experience message delays
    - Music player may not play on the lock screen
    - Other exceptions caused by restricting the background behavior of the app.

## About

- Due to some persion reason, NoWakeLock is gradually in a state of suspension, and some functions of NoWakeLock are too complicated, so DeepSleep was written.

## Features

- Beta:
  - ~~wakelock/alarm/service/sync restriction~~
  - ~~Data backup and recovery~~
  - ~~help/about fragment~~
  - ~~black/white list/regex support~~
  - ~~dark/light theme switch~~
  - broadcast restriction

- Release

  -

## Compatibility

- Android N ~
- Edxpoed LSPosed

## Compile

- [master](https://github.com/Jasper-1024/DeepSleep): Released

- [dev](https://github.com/Jasper-1024/DeepSleep/tree/dev): Bate

## Installation

- [Github releases](https://github.com/Jasper-1024/DeepSleep/releases), alpha/bate/Released.
- [酷安](https://www.coolapk.com/apk/260112), bate/Released
- [Play](https://play.google.com/store/apps/details?id=com.js.deepsleep) bate/Released

## Support

- Only DeepSleep downloaded from the above channels is supported
- Please submit [ISSUE](https://github.com/Jasper-1024/DeepSleep/issues)

## Contributing

- [Jasper Hale](https://github.com/Jasper-1024)

## License

- DeepSleep is released under GNU GPLv3 ([License](https://github.com/Jasper-1024/DeepSleep/blob/master/LICENSE)).
