# SimpleChat

# Android Firebase Codelab

## Введение

Добро пожаловать в кодлаб по разработке простого чата для Android при помощи
сервиса Firebase.

В этом кодлабе вы научитесь:
- Реализовать авторизацию
- Использовать базу данных реального времени
- Получать пуш уведомления
- Настраивать приложение при помощи Remote Config
- Собирать отчеты о падениях приложения при помощи Firebase Crash Reporting.

Что необходимо:
- Android Studio v2.0+
- Android Sdk
- Устройство с Android или эмулятор
- Примеры кода

## Примеры кода

Примеры находятся в данном репозитории:
- simple-chat - код готового приложения
- simple-chat-start - код для прохождения кодлаба

Скачайте или склонируйте репозиторий и откройте проект simple-chat-start в
Android studio

## Создание проекта в Firebase
 1. Переходим в консоль Firebase
 2. Кликаем "Создать новый проект"
!["example"](/assets/create-project.png)
 3. Выбираем Android
 !["example"](/assets/choose-android.png)

## Подключение Андройд к файрбейс
Получить ключ:
```
keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore -list -v -storepass android
```
Полученный файл закинуть в папку app, добавить google-services plugin
```
apply plugin: 'com.google.gms.google-services'
```

## Авторизация
Database выбрать rules
```
{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
```
В app/builg.gradle
```
compile 'com.google.firebase:firebase-auth:10.0.0'
```

## База данных
В app/builg.gradle
```
compile 'com.google.firebase:firebase-database:10.0.0'
```

## Уведомления
```
compile 'com.google.firebase:firebase-messaging:10.0.0'

```
## Remote Config
```
compile 'com.google.firebase:firebase-config:10.0.0'
```
## Crash tracker
```
compile 'com.google.firebase:firebase-crash:10.0.0'
```
