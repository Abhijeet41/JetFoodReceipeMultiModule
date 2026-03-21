#core-network/consumer-rules.pro

############################################
# Retrofit
############################################
-keepattributes Signature
-keepattributes Exceptions

-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

-keepclassmembers interface * {
    @retrofit2.http.* <methods>;
}

############################################
# Gson
############################################
-keepattributes *Annotation*

-keep class com.google.gson.** { *; }

-keep class * {
 @com.google.gson.annotations.SerializedName <fields>;
}

############################################
# OkHttp
############################################
-dontwarn okhttp3.**
-dontwarn okio.**

############################################
# Hilt
############################################
-keep class dagger.hilt.** { *; }