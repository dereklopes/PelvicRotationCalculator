# Pelvic Rotation Calculator
## About
This is a program to calculate true measurements and rotation amounts given measurements from x-ray film and the x-ray environment.

It has been developed for the non-profit [Gonstead Clinical Studies Society](gonstead.com).

The methodology is based on [this article](https://github.com/dereklopes/PelvicRotationCalculator/blob/master/Pelvic%20Rotation%20article.pdf).

It is written in Java with JavaFX so that it can easily be run on any device with Java installed.

## Prerequisites

The Java Runtime Environment is required to run this application. Older versions may work, but it is recommended that you keep your Java version up to date.

To install Java or update to the latest version, download it [here](https://java.com/download).

## Installation

Click [here](https://github.com/dereklopes/PelvicRotationCalculator/raw/master/out/build/PelvicRotationCalculator.jar) to download the jar file.

To run, simply open the downloaded program file.

## Run Issues

### Mac OS

Error: `"PelvicRotationCalculator.jar" can't be opened because it is from an unidentified developer."`

Fix: Go to `System Preferences->Security & Privacy->General` and click on `Open Anyway`

## How to Use

Make measurements in accordance to the instructions in the [Usage Instructions PDF](https://github.com/dereklopes/PelvicRotationCalculator/blob/master/UsageInstructions.pdf)

Once you have your measurements, input them in the input section and select the units for the measurements. Then press the "Calculate" button.

The two tables show your input and your results. As you make more calculations, the tables will fill with your input and results. 

Use the "Name" input field to help you differentiate between calculations.

## Contributions

To contribute, please open a pull request. New code must have unit tests or the PR won't be accepted and merged.

## TODO

- Pictures and tool tips to help with correlating input fields to real world measurements
- Properly sign JAR
- Show units in input and result tables
- Saving and loading of calculations
- Deletion of result rows in tables
- Setting of the application "dock name" on Mac OS (currently shows as `java`)
