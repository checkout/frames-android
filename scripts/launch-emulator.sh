#!/bin/sh

# Install required packages
yes | $ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --package_file=./scripts/android-sdk-packages  --sdk_root=$ANDROID_SDK_ROOT

# Create an AVD
$ANDROID_SDK_ROOT/cmdline-tools/latest/bin/avdmanager create avd -n testingAVD -d pixel --package "system-images;android-29;google_apis;x86_64"

# Start an emulator
$ANDROID_SDK_ROOT/emulator/emulator -avd testingAVD -no-accel -no-window -no-audio -no-snapshot -no-boot-anim -camera-back none -camera-front none &

