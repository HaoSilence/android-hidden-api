# Android Hidden API
**Android Hidden API** is a modified jar file which combining `android.jar` from Android SDK with `framework.jar` from real device. This jar makes you able to use Android internal/hidden APIs in development.

**What is Android internal and hidden APIs?**
Internal API is located in `com.android.internal` package which available in the `framework.jar` file from real Android device, while hidden API is located in `android.jar` file with *@hide* javadoc attribute. Although the classes & methods are `public`, but you cannot access it directly. There are pretty methods and resources you can use from this package. I will assume this is one API and will refer to it as to hidden API. Learn more about hidden API [here][1].

**What's the advantage?**
You don't need to create new classes, constants, methods or resources which you want to use the similar function in your project. For example, if you want to create a new `String` resource that contains 'accept' word. Then you want create a new translation to other language, e.g. Arabic, Indonesian, even with all languages in this world. This is too exhausting and waste your time. Why don't use `com.android.internal.R.string.accept` which is retrieved via `InternalAccessor.getString("accept")`?

## Usage
If you plan to use only Android internal resources rather than internal classes or methods,
do:

````gradle
dependencies {
    compile 'com.anggrayudi:android-hidden-api:0.0.6'
}
repositories {
    maven { url 'https://dl.bintray.com/anggrayudi/maven/' }
    // Or, you can use jCenter instead
    jcenter()
}
````

Here's some example of accessing internal resources:
    
```java
import com.anggrayudi.hiddenapi.r.Rc;

    String accept = InternalAccessor.getString(Rc.string.accept);
    float sbar_height = InternalAccessor.getDimension(Rc.dimen.status_bar_height);
    int notif_color = InternalAccessor.getColor("config_defaultNotificationColor");
```

If you also want to include the internal classes or methods, do the following:

1. Go to `<SDK location>/platforms/`.
2. Copy, paste and replace the downloaded hidden API file into this directory, e.g. `android-24/android.jar`.
3. Change `compileSdkVersion` and `targetSdkVersion` to 24 (for example).
4. Finally, rebuild your project.

Note: Higher `compileSdkVersion` and `targetSdkVersion` will be better.

## Limitation

Currently, Android Hidden API doesn't support for API 25. We are waiting someone to upload `framework.jar` from Android Nougat 7.1 physical device.

## License

    Copyright 2015-2017 Anggrayudi Hardiannicko A.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


  [1]: https://devmaze.wordpress.com/2011/01/18/using-com-android-internal-part-1-introduction
  [2]: https://github.com/anggrayudi/android-hidden-api/issues/9
