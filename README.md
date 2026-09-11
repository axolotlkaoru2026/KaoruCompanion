# Kaoru Companion

スマートフォン単体での開発・クラウドビルドを想定したAndroidアプリのMVPです。

## 現在の機能
- Kotlin + Jetpack Compose
- メイン画面
- 簡易チャットUI
- 送信メッセージの表示
- 後からウィジェット、常駐表示、AI会話などを追加できる構成

## Debug APK
Gradle wrapperを用意した環境で:
`./gradlew assembleDebug`

生成先:
`app/build/outputs/apk/debug/app-debug.apk`
