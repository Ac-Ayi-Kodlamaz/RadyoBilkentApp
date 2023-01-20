# RadyoBilkentApp
![RadyoBilkent](https://w3.bilkent.edu.tr/www/wp-content/uploads/sites/5/2022/04/RadyoBilkent-Logo.png)

This is an Android app for our CS102 project

There are two sides of the project: (1) Android client and (2) Admin Panel. With the Android client, the users can listen to the radio, vote for the upcoming songs, read blogs and navigate to spotify to listen to podcasts. The data on the app is pulled from Google Firebase, which is initially pushed by the radio producers who use the Admin Panel. With the Admin Panel, the producers are able to add, edit and delete content such as blogs or programs and control voting sessions. They can also see the feedback they receive from the users and manage them.

For the time being, the stream is not taken from Radyo Bilkent as they did not agree to cooperate with us. Therefore, the songs are streamed from an .mp3 host and their information is in our database, managed by the Admin Panel.
- - - -
### How to install the app on your Android Phone
1. Go to the following directory:
RadyoBilkentAndroid/APK/
2. Copy the app-debug.apk file to your Android phone and click on it on file manager
3. Install the app and open it
- - - - 
### How to open in Android Studio ###
1. Clone this repository 
2. Open Android Studio
3. Click open project
4. Select _RadyoBilkentAndroid_ folder in repository
5. Use emulator or phone via using USB cable to run the program
* You will need virtulisation technology activated through BIOS for using the emulator
* You need to grant USB Debugging permission through Developer Settings for using your phone
- - - - 
### How to open Admin Panel in IntelliJ ###
1. Clone the repository.
2. Open the Admin Panel folder inside RadyoBilkent folder in IntelliJ.
3. Inside Admin Panel we need to upload dependencies manually by clicking File tab and clicking Project Structure.
4. After that enter the libraries and from top left select "+" button and chose add from maven.
5. After that paste the given part below and apply the library.
Coppy it:
   <dependency>
   <groupId>com.google.firebase</groupId>
   <artifactId>firebase-admin</artifactId>
   <version>8.1.0</version>
   </dependency>
6. Find the mainApplication in src/main/java/radyo/MainApplication.java
7. Run the program
