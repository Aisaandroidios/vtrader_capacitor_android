# ----------------------------
# Capacitor / Vue H5 (WebView)
# ----------------------------
# Keep Capacitor core and plugin classes (reflection/annotations).
-keep class com.getcapacitor.** { *; }
-dontwarn com.getcapacitor.**
-keep class ** extends com.getcapacitor.Plugin { *; }
-keep @com.getcapacitor.annotation.CapacitorPlugin class * { *; }
-keep @com.getcapacitor.NativePlugin class * { *; }
-keepclassmembers class * {
    @com.getcapacitor.annotation.PluginMethod *;
}

# Keep Cordova framework/plugins (if using cordova plugins via Capacitor).
-keep class org.apache.cordova.** { *; }
-dontwarn org.apache.cordova.**
-keep class capacitor.cordova.android.plugins.** { *; }

# Keep WebView JS interface methods.
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
