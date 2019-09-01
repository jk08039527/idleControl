#---------------------基础部分,基本不用改动---------------------------------------------
-ignorewarning
-verbose
-dontpreverify
-dontoptimize
-dontpreverify
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers

-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keepattributes InnerClasses
-printmapping proguardMapping.txt
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-optimizations !method/marking/static,!method/removal/parameter,!code/removal/advanced
#-----------------------------------------------------------------------------------



#--------------------默认保留区域----------------------------------------------------
-keep public class android.os.Bundle
-keep public class android.content.Intent
-keep public class * extends android.app.Service
-keep public class * extends android.app.Activity
-keep public class * extends android.os.IInterface
-keep public class * extends android.app.Application
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

-keep class android.support.** { *; }
-keep public class * extends java.lang.Throwable {*;}
-keep public class * extends java.lang.Exception {*;}

-keep public class **.R$* {
    *;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * {
    void *(**On*Event);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    public <fields>;
}

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
   public <init>(android.content.Context);
   public <init>(android.content.Context, android.util.AttributeSet);
   public <init>(android.content.Context, android.util.AttributeSet, int);
}
#------------------------------------------------------------------------------------

#-----------------------------webview------------------------------------------------
-keep class android.webkit.**
-keep public class * extends android.webkit.WebChromeClient

-keepattributes *JavascriptInterface*

-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}

-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}

-keepclasseswithmembernames class * extends android.webkit.WebChromeClient{*;}
#------------------------------------------------------------------------------------

#------------------------------------------------------------------------------------

#-----------------------------实体类------------------------------------------------
-keep public class com.jerry.control.bean.*{*;}
-keep public class com.jerry.baselib.common.retrofit.request.*{*;}
-keep public class com.jerry.baselib.common.retrofit.response.*{*;}
#------------------------------------------------------------------------------------

#-----------------腾讯sdk,bugly------------
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
# tinker混淆规则
-dontwarn com.tencent.tinker.**
-keep class com.tencent.tinker.** { *; }
-keep class com.tencent.** {*;}
-keep public interface com.tencent.**
-keep public class * extends com.tencent.tinker.loader.app.ApplicationLifeCycle {*;}
#------------------------------------------

#------------------okhttp/okio/Retrofit--------------------
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**

-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

#------------------fastjson------------------
-keep class com.alibaba.fastjson.**
-dontwarn com.alibaba.fastjson.**

#------------------glide------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#----------------其它-------------------------------------------------------------
-keep class javax.**
-keep class org.**{*;}
-keep class android.net.SSLCertificateSocketFactory{*;}
-keepattributes EnclosingMethod
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

## 注解支持
-keepclassmembers class *{
   void *(android.view.View);
}