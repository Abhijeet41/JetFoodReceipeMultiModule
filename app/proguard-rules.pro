############################################
# Kotlin
############################################
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**

############################################
# Retrofit
############################################
-keepattributes Signature
-keepattributes Exceptions

-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

############################################
# OkHttp
############################################
-dontwarn okhttp3.**
-dontwarn okio.**

############################################
# Gson (Retrofit Converter)
############################################
-keepattributes *Annotation*

-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**


############################################
# Kotlinx Serialization
############################################
-keep class kotlinx.serialization.** { *; }
-keep class kotlinx.serialization.json.** { *; }

-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
}

############################################
# Hilt / Dagger
############################################
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }

-keep class hilt_aggregated_deps.** { *; }
-keep class dagger.hilt.internal.aggregatedroot.codegen.** { *; }
-keep class dagger.hilt.android.internal.managers.** { *; }

-dontwarn dagger.hilt.**

############################################
# Room Database
############################################
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

############################################
# Paging 3
############################################
-keep class androidx.paging.** { *; }
-dontwarn androidx.paging.**

############################################
# Navigation Compose
############################################
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**

############################################
# Jetpack Compose
############################################
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

############################################
# Coil Image Loader
############################################
-keep class coil.** { *; }
-dontwarn coil.**

############################################
# Jsoup
############################################
-keep class org.jsoup.** { *; }
-dontwarn org.jsoup.**

############################################
# DataStore
############################################
-keep class androidx.datastore.** { *; }

############################################
# Firebase
############################################
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

############################################
# Dexter Permission Library
############################################
-keep class com.karumi.dexter.** { *; }

############################################
# Coroutines
############################################
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

############################################
# Keep Application class
############################################
-keep class * extends android.app.Application { *; }

############################################
# Keep Parcelable
############################################
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
# Keep your Domain Models (to prevent breaking serialization/mapping)
-keep class com.abhi46.receipe.domain.models.** { *; }
-keep class com.abhi46.recipe.core_database.entity.** { *; }
