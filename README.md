# InstagramApp: UI Avançada

:warning:Informaçõoes importantes
* Linguagem de programação utilizada ```Java```
* Ferramenta de desenvolvimento utilizada ```Android Studio```
* Arquitetura do projeto ```MVP```
* Android ```4.2 | Jelly Bean```
* API ```17```

Descrição do Módulo 14, 15, 16, 17 do curso Android Express ministrado pelo instrutor @Tiago Aguiar:
* Módulo 14 | InstagramApp: UI Avançada
* Módulo 15 | InstagramApp: Lógica Avançada
* Módulo 16 | InstagramApp: Animações Avançadas
* Módulo 17 | InstagramApp: Ambiente Produção

Permissões do Android Manisfest:
 ```
 <uses-permission android:name="android.permission.INTERNET" />
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 <uses-permission android:name="android.permission.CAMERA" />
 ```
 
 Permissões do Android Image Cropper no Android Manifest:
 
```
<activity android:name="com.theartofdev.edmodo.cropper.CropImageActivity"
  android:theme="@style/Base.Theme.AppCompat"/>
```
 
 Linhas de implementação adicionadas no Android Manifest:
 ```
 <provider
  android:name="androidx.core.content.FileProvider"
  android:authorities="br.com.instagramremake.fileprovider"
  android:exported="false"
  android:grantUriPermissions="true">
  <meta-data
      android:name="android.support.FILE_PROVIDER_PATHS"
      android:resource="@xml/file_paths" />
</provider>
 ```
 
Bibliotecas de terceiros:

```
implementation 'com.google.android.material:material:1.3.0'

implementation 'com.heinrichreimersoftware:material-intro:2.0.0'

implementation 'com.jakewharton:butterknife:10.0.0'
annotationProcessor 'com.jakewharton:butterknife-compiler:10.0.0'

implementation 'com.theartofdev.edmodo:android-image-cropper:2.8.0'
```

 Links úteis:
 * [Firebase](https://firebase.google.com/)
 * [Butter Knife](https://github.com/JakeWharton/butterknife)
 * [Picasso](https://github.com/square/picasso)
 * [material-intro](https://github.com/heinrichreimer/material-intro)
 * [Android Image Cropper](https://github.com/ArthurHub/Android-Image-Cropper)
