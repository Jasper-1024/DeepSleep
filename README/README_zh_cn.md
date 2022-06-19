# DeepSleep

- DeepSleep 是 Xposed 模块.可以限制应用后台行为,节省待机电量消耗.
  - 禁止申请 [唤醒锁/wakelock](https://developer.android.com/training/scheduling/wakelock) 和 [闹钟/alarms](https://developer.android.com/training/scheduling/alarms),防止多余唤醒.
  - 禁止启动 [后台服务/Service](https://developer.android.com/guide/components/services),减少内存消耗.
  - 禁止 [同步/sync](https://developer.android.com/training/sync-adapters?hl=zh-cn),防止多余唤醒.

- EDxposed/LSPosed 作用域 选择需要限制应用.

- 严重警告
  - 如果您的设备没有待机耗电问题并不推荐使用 deepsleep.
  - 使用 deepsleep 限制应用,可能会导致应用出现异常问题.
    - 聊天应用 可能出现消息延迟
    - 音乐播放器 可能无法锁屏播放
    - 其他等因限制应用后台行为导致的异常.

## 关于

- 因为精力有限, NoWakeLock 逐渐处于停更状态,且 NoWakeLock 一些功能过于复杂,因此集中时间编写了 DeepSleep.

## 功能

- ~~Beta~~:
  - ~~wakelock/alarm/service/sync 限制~~
  - ~~帮助/关于界面~~
  - ~~黑白名单/正则支持~~
  - ~~深色/浅色主题切换~~
  - ~~备份/还原支持~~
  - 广播 限制

- Release:
  - 禁用方案加载

## 兼容性

- Android N ~
- Edxpoed LSPosed

## 编译

- [master](https://github.com/Jasper-1024/DeepSleep): 稳定版

- [dev](https://github.com/Jasper-1024/DeepSleep/tree/dev): 测试版,可能并不稳定.

## 安装

- [Github releases](https://github.com/Jasper-1024/DeepSleep/releases), 包括 alpha/bate/Released 版.
- [酷安](https://www.coolapk.com/apk/260112), bate/Released 版
- [Play] waiting

## 支持

- 仅支持在上述渠道下载的 DeepSleep
- 请提交 [ISSUE](https://github.com/Jasper-1024/DeepSleep/issues)

## 贡献者

- [Jasper Hale](https://github.com/Jasper-1024)

## 许可证

- DeepSleep 以 GNU GPLv3 ([许可](https://github.com/Jasper-1024/DeepSleep/blob/master/LICENSE)) 发布.
