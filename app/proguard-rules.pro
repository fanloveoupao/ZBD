# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source com.ysnet.zdb.file name.
#-renamesourcefileattribute SourceFile
#============================================分享============================
#qq
-keep class com.tencent.**{*;}
#微博
-keep class com.sina.weibo.sdk.** { *; }
#微信
-keep class com.tencent.mm.opensdk.** { *; }
-keep class com.zbd.app.wxapi.** { *; }
#Gson
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
#Socialhelper
-keep class net.arvin.socialhelper.**{*;}