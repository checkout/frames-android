#!/bin/sh

# Please run this AFTER running scripts/setup-android-env.sh
yes | $ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --package_file=./scripts/android-sdk-packages  --sdk_root=$ANDROID_SDK_ROOT

# Create an AVD
$ANDROID_SDK_ROOT/cmdline-tools/latest/bin/avdmanager create avd -n testingAVD -d pixel --package "system-images;android-29;google_apis;x86_64"

# List AVDs
$ANDROID_SDK_ROOT/emulator/emulator -list-avds

# Update
$ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --update

# Start an emulator
$ANDROID_SDK_ROOT/emulator/emulator -avd testingAVD -no-accel -no-window -no-audio -no-snapshot -no-boot-anim -camera-back none -camera-front none &
#$ANDROID_SDK_ROOT/emulator/emulator -avd testingAVD -no-audio -no-window -no-boot-anim -no-snapshot -camera-back none -camera-front none -selinux permissive -qemu -vnc :0

