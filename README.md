# Pelvic Rotation Calculator
## About
This is a program to calculate true measurements and rotation amounts given measurements from x-ray film and the x-ray environment.

It has been developed for the non-profit [Gonstead Clinical Studies Society](gonstead.com).

The methodology is based on [this article](https://github.com/dereklopes/PelvicRotationCalculator/blob/master/Pelvic%20Rotation%20article.pdf).

It is written in Java with JavaFX.

## Prerequisites

Java 21 or later is required. Download it [here](https://java.com/download).

## Installation

Download the latest release for your platform:

- [Windows](https://github.com/dereklopes/PelvicRotationCalculator/releases/download/v1.2/PelvicRotationCalculator-windows-v1.2.zip)
- [macOS](https://github.com/dereklopes/PelvicRotationCalculator/releases/download/v1.2/PelvicRotationCalculator-macos-v1.2.dmg)
- [Linux](https://github.com/dereklopes/PelvicRotationCalculator/releases/download/v1.2/PelvicRotationCalculator-linux-v1.2.zip)

### Windows

1. Extract the downloaded `.zip` file
2. Open the extracted folder and launch `PelvicRotationCalculator.exe`

### macOS

1. Open the downloaded `.dmg` file
2. Drag `PelvicRotationCalculator` to your Applications folder
3. On first launch, macOS may block the app. Go to `System Preferences -> Privacy & Security`, scroll down, and click on `Open Anyway`

### Linux

1. Extract the downloaded `.zip` file
2. Open the extracted folder and run the `PelvicRotationCalculator` binary

## Usage Instructions

### Making a Calculation
Make measurements in accordance to the instructions in the [Usage Instructions PDF](https://github.com/dereklopes/PelvicRotationCalculator/blob/master/UsageInstructions.pdf)

Once you have your measurements, input them in the input section and select the units for the measurements. Then press the "Calculate" button.

### Understanding the Results

The three tables show your input and your results. As you make more calculations, the tables will fill with your input and results. 

Use the "Name" input field to help you differentiate between calculations.

### Saving

To save your calculations, click the "File" tab on the menu bar to open a drop down menu and select "Save" (keyboard shortcut ctrl + s on windows, command + s on mac). If you are saving for the first time, a file selection window will open to select a destination to save your calculations. If you are saving and already saved calculation, it will automatically save to the original file.

To save to a new location, select the "Save as..." option under the "File" tab on the menu bar (keyboard shortcut ctrl + shift + s on windows, command + shift + s on mac). This will always ask you to choose a location to save to.

### Opening Saved Results

To re-open a data file with saved results, do the following:

1. Open the Pelvic Rotation Calculator application
2. Click on the drop down "File" menu on the menu bar and select "Open" (keyboard shortcut ctrl + o on windows, command + o on mac). This will open a file selection window.
3. In the file selection window, open the folder where your results file was saved and select the saved data file.
4. Click on the "open" button to view the results. This will populate the tables with your saved results.

### Deleting Results

There are 3 ways to delete a result. First, select the result you wish to delete by left-clicking on the row. This will highlight the row. Next, do one of the following:

1. Right-click on the row and left-click "Delete Row..."
2. Click on the "Edit" tab on the menu bar and select "Delete Selected Row" from the drop down menu
3. Press the keyboard shortcut ctrl + backspace on windows or command + delete on mac

## Run Issues

### Windows

Error: PelvicRotationCalculator.jar does not open

Fix: Uninstall all versions of Java on your system, then reinstall by downloading it [here](https://java.com/download).

### Mac OS

Error: `"PelvicRotationCalculator.jar" can't be opened because it is from an unidentified developer."`

or

Error:: `"PelvicRotationCalculator.jar" Not Opened Apple could not verify "PelvicRotationCalculator" is free of malware that may harm your Mac or compromise your privacy.`

Fix: Go to `System Preferences->Privacy & Security`, scroll down, and click on `Open Anyway`

## Contributions

To contribute, please open a pull request. New code must have unit tests or the PR won't be accepted and merged.

## TODO

- Tool tips to help with correlating input fields to real world measurements (currently found in instruction PDF)
- Properly sign JAR
- Setting of the application "dock name" on Mac OS (currently shows as `java`)
