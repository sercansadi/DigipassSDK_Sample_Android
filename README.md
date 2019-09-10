# DigipassSDK_Sample_Android
This is the PoC Sample with DIGIPASS SDK to show Android capabilities of the product. The Android project includes several basic UI for the showcase of DigipassSDK, which provides multifactor authentication capabilities with symmetric key generation. establishing secure channel with DSAPP and integration of several OEM libraries such as face recognition or color QR code.

## src directory

### digipass.sdk.utils.wbc
This is the folder for auto-generated code which includes tables for encryption/obfuscation of the code by tables. This is achieved via **White Box Cryptography** method, with a seperate library before the production of the code

### labs.digipasssdk_sample_android_2016
Home directory for the *activity* classes for UI presentation of the functionality, *controllers* for the mentioned activities, *helper* classes for necessary manipulation, a borrowed library under *goebl.david* for HTTP interaction and the *model* for DSAPPServer which includes necessary configuration variables

## test directories
As most of the methods in project classes rely on Context object, test classes were not implemented at that time (there are only 2 mocks for Activity and DSAPP). The reason is for that the build **API level 23** and new library for tetsing Context object is introduced after **API level 24**: 

```
InstrumentationRegistry
```
