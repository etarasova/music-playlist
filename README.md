# Music playlist

This repository was created for the purpose of learning data structures at Brooklyn College's Department of CIS.

This program reads in multiple CSV files that contain songs during certain weeks from Spotify top chart. As an example, I used four global weekly charts from January 24, 2020 to February 21, 2020. 

The "Playlist.csv" file produced by the program contains a list of songs from all charts files in ascending order. Also, this program allows to track the recently listened to songs (in Main method).
## Dependencies

* [Java 8](https://docs.oracle.com/javase/8/docs/api/index.html)
* [OpenCSV](http://opencsv.sourceforge.net/)
* [Travis CI](https://travis-ci.com/)
* [IntelliJ Idea](https://www.jetbrains.com/idea/)s

Java 8 is used here because it's the department's officially supported language and version.
OpenCSV is a library which is used to read a CSV file (spotify chart in this case) and write data to CSV files (output of this program). This repository is linked to Travis-CI by way of a `.travis.yml` file in the root of the directory.
I used IntelliJ Idea as my IDE.

## Folder Structure

* Code is saved into the `src` folder.
* Data is saved into the `data` folder.
* Output files are saved into the `output` folder.
* The required libraries are in the `lib` folder.

## How to build and run

1. Open terminal window
2. Navigate to go "scripts" folder
3. Execute `./run.sh`

Note: You may need to give execute permission to the script by running `chmod +x *.sh` from "scripts" folder