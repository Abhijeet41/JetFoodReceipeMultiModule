#receipe:domain module

############################################
# Kotlin Serialization
############################################
-keep class kotlinx.serialization.** { *; }

-keepclassmembers class * {
 @kotlinx.serialization.SerialName <fields>;
}

############################################
# DataStore
############################################
-keep class androidx.datastore.** { *; }