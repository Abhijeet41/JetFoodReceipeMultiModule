#receipe/data/consumer-rules.pro

# Retrofit
############################################
-keepclassmembers interface * {
 @retrofit2.http.* <methods>;
}

############################################
# DataStore
############################################
-keep class androidx.datastore.** { *; }

############################################
# Firebase
############################################
-keep class com.google.firebase.** { *; }

############################################
# Hilt
############################################
-keep class dagger.hilt.** { *; }