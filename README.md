Apple Main Page Testing
=========================

## Overview

The project tests Apple main page elements on the top using next requirements:

* Each element should be clickable
* Each request should return HTTP 200 code
* Tests should be run on next platforms / versions:
  * Chrome / macOS
  * Edge / Win 10
  * iOS 14.0+ (Safari)

## Tech stack

* Java 8
* Spring
* JUnit 5
* Appium - for testing mobile devices
* Gradle
* [Selenide](https://selenide.org/) - child project from Selenium

## Tests

All tests divided on 2 logical parts:
* Browser tests - Chrome (MacOS), Microsoft Edge (Windows 10)
* iOS tests (mobile) - iOS 14.+

## Tests configuration (feature implementation)

All tests supports flexible configuration parameters to setup the tests.
Each logical browser / mobile configuration placed in separate file with name "application-<type>.properties",
where 

Let's see what we have:
* Browser
  * <b>browser.name</b> = name of the browser what should be used - chrome, edge etc. Full list you can find [here](https://github.com/selenide/selenide/blob/master/src/main/java/com/codeborne/selenide/Browsers.java)
  * <b>platform.name</b> = platform name - like win10, macOS etc. Full list you can find [here](https://github.com/SeleniumHQ/selenium/blob/trunk/java/src/org/openqa/selenium/Platform.java)
* iOS
  * browser.name - name of the browser. Should be "safari"
  * platform.name - platform name - like iOS Full list you can find [here](https://github.com/SeleniumHQ/selenium/blob/trunk/java/src/org/openqa/selenium/Platform.java). Should be iOS for mobile testing on iPhone
  * deviceName - name of the device for emulation. You can get all devices list on MacOS using next command ```instruments -s devices```
  * platformVersion - version of platform. You also can take it using next command ```instruments -s devices```

### Mobile tests

Mobile tests required some preparation steps before run the tests(if you run them in the first time):

* Install Appium using npm (if you don't have it)
  > npm install -g appium
* Start Appium 
  > appium
* [Launch](https://developer.apple.com/library/archive/documentation/IDEs/Conceptual/iOS_Simulator_Guide/GettingStartedwithiOSSimulator/GettingStartedwithiOSSimulator.html)
iPhone Simulator on MacOS using device what you are using in config file (including version) and make sure it's loaded bofore tests started
 
## How-To run the tests

* Microsoft Edge:
  > ./gradlew clean test --tests "com.apple.pages.testing.browser.EdgeBrowserApplePageTests"
* Chrome: 
  > ./gradlew clean test --tests "com.apple.pages.testing.browser.ChromeBrowserApplePageTests"
* iOS:
  > ./gradlew clean test --tests "com.apple.pages.testing.mobile.IosApplePageTests"

## Test reports
* JUnit reports are located in the ```build/reports/tests/test```
* Selenide also allow to save screenshots for each test - you can find them under ```build/reports/tests```
