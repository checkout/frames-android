-keep class com.checkout.** { *; }

# Keep the classes annotated with @JsonClass(generateAdapter = true)
-keep class com.checkout.tokenization.request.TokenRequest { *; }
-keep class com.checkout.tokenization.entity.PhoneEntity { *; }
-keep class com.checkout.tokenization.entity.AddressEntity { *; }

# Keep the fields and methods of the classes annotated with @JsonClass(generateAdapter = true)
-keepclassmembers class com.checkout.tokenization.request.TokenRequest { *; }
-keepclassmembers class com.checkout.tokenization.entity.PhoneEntity { *; }
-keepclassmembers class com.checkout.tokenization.entity.AddressEntity { *; }