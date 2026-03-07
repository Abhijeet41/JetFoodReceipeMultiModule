#3. core-database module

############################################
# Room
############################################
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep class androidx.room.** { *; }

############################################
# Gson
############################################
-keep class * {
 @com.google.gson.annotations.SerializedName <fields>;
}

############################################
# Hilt
############################################
-keep class dagger.hilt.** { *; }