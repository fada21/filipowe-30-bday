# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/fada21/Libs/android-sdk-linux/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# retrolambda
-dontwarn java.lang.invoke.*

# okhttp picasso
-dontwarn com.squareup.okhttp.**

# butterknife
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}

# jackson
-dontwarn com.fasterxml.jackson.**
-keepattributes EnclosingMethod,Signature
-keep public class com.fada21.android.filipa30bday.** {
  public void set*(***);
  public *** get*();
}
-keepnames class com.fasterxml.jackson.** { *; }

# eventbus
-keepclassmembers class ** {
    public void onEvent*(**);
}

# Parceler
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }
-keep class org.parceler.Parceler$$Parcels