-keep class com.checkout.** { *; }

# Risk SDK leaves Fingerprint's optional device-location collection disabled.
-dontwarn com.google.android.gms.location.**
-dontwarn com.google.android.gms.tasks.CancellationToken
-dontwarn com.google.android.gms.tasks.OnFailureListener
-dontwarn com.google.android.gms.tasks.OnSuccessListener
-dontwarn com.google.android.gms.tasks.Task