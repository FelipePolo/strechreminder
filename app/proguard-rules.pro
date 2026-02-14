############################################
# General
############################################
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes InnerClasses
-keepattributes EnclosingMethod

############################################
# Kotlin
############################################
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.** { *; }
-dontwarn kotlin.reflect.**

############################################
# Kotlin Serialization (CRÍTICO para R8)
############################################
# Mantener todas las clases serializables
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# Mantener los serializadores generados
-keep,includedescriptorclasses class com.fpstudio.**$$serializer { *; }
-keepclassmembers class com.fpstudio.** {
    *** Companion;
}
-keepclasseswithmembers class com.fpstudio.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Mantener clases anotadas con @Serializable
-keep @kotlinx.serialization.Serializable class ** { *; }

# Mantener el plugin de serialización
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class kotlinx.serialization.** { *; }
-dontwarn kotlinx.serialization.**

# Prevenir obfuscación de nombres de propiedades serializadas
-keepclassmembers @kotlinx.serialization.Serializable class ** {
    <fields>;
    <methods>;
}

############################################
# Coroutines
############################################
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

############################################
# Retrofit
############################################
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

############################################
# OkHttp
############################################
-dontwarn okhttp3.**

############################################
# Gson (si usas Gson)
############################################
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

############################################
# Moshi (si usas Moshi)
############################################
-keep class com.squareup.moshi.** { *; }
-dontwarn com.squareup.moshi.**

############################################
# Serialization models (MUY IMPORTANTE)
############################################
-keep class com.fpstudio.**.model.** { *; }
-keep class com.fpstudio.**.data.** { *; }

############################################
# Hilt / Dagger
############################################
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-dontwarn dagger.hilt.**

############################################
# Koin (CRÍTICO para dependency injection)
############################################
# Mantener TODA la información de tipos y reflexión
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations
-keepattributes AnnotationDefault
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Mantener TODO de Koin sin obfuscación
-keep class org.koin.** { *; }
-keep interface org.koin.** { *; }
-keep enum org.koin.** { *; }
-keepclassmembers class org.koin.** { *; }
-dontwarn org.koin.**

# Mantener información de tipos de Kotlin para Koin
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.** { *; }
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# Mantener TODOS los ViewModels completamente
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
    public <methods>;
    public <fields>;
}

# Mantener TODOS los módulos de Koin sin modificar
-keep class com.fpstudio.stretchreminder.util.di.** { *; }
-keepclassmembers class com.fpstudio.stretchreminder.util.di.** {
    public <methods>;
    public <fields>;
}

# Mantener TODAS las interfaces de repositorios
-keep interface com.fpstudio.stretchreminder.domain.repository.** { *; }
-keepclassmembers interface com.fpstudio.stretchreminder.domain.repository.** {
    <methods>;
}

# Mantener TODAS las implementaciones de repositorios
-keep class com.fpstudio.stretchreminder.data.repository.** { *; }
-keepclassmembers class com.fpstudio.stretchreminder.data.repository.** {
    <init>(...);
    public <methods>;
    public <fields>;
}

# Mantener TODOS los UseCases
-keep class com.fpstudio.stretchreminder.domain.usecase.** { *; }
-keepclassmembers class com.fpstudio.stretchreminder.domain.usecase.** {
    <init>(...);
    public <methods>;
    public <fields>;
}

# Mantener TODOS los datasources
-keep class com.fpstudio.stretchreminder.data.datasource.** { *; }
-keepclassmembers class com.fpstudio.stretchreminder.data.datasource.** {
    <init>(...);
    public <methods>;
    public <fields>;
}

# Prevenir obfuscación de CUALQUIER constructor
-keepclassmembers class * {
    public <init>(...);
    <init>(...);
}

# Mantener información de parámetros genéricos
-keepattributes Signature
-keep class * {
    <init>(...);
}

# Específico para Koin Compose
-keep class org.koin.androidx.compose.** { *; }
-keep class org.koin.compose.** { *; }

############################################
# Navigation
############################################
-keep class androidx.navigation.** { *; }

############################################
# Compose
############################################
-keep class androidx.compose.** { *; }

############################################
# Enum values (evita crashes raros)
############################################
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    **[] $VALUES;
    public *;
}

# Mantener todos los enums del proyecto (CRÍTICO)
-keep enum com.fpstudio.stretchreminder.** { *; }

############################################
# Room Database
############################################
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Mantener las entidades de Room
-keep class com.fpstudio.stretchreminder.data.** { *; }

############################################
# DataStore Preferences
############################################
-keep class androidx.datastore.*.** { *; }
-keepclassmembers class * extends androidx.datastore.preferences.protobuf.GeneratedMessageLite {
    <fields>;
}

############################################
# Gson Type Adapters
############################################
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

############################################
# RevenueCat
############################################
-keep class com.revenuecat.purchases.** { *; }
-dontwarn com.revenuecat.purchases.**

# RevenueCat optional dependencies (no usadas pero R8 las busca)
-dontwarn com.google.api.client.**
-dontwarn org.joda.time.**
-dontwarn com.google.crypto.tink.**