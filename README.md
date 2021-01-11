# webscan
Problem-
Write an app/program to scan through a given webpage, and display a single consolidated top 10 frequent words and the top 10 frequent word pairs (two words in the same order) along with their frequency. In case the webpage contains hyperlinks, these hyperlinked urls need to be expanded and the words on these pages also should be scanned to come up with the frequent words. 


Pre-requisites:
1. Java must be installed
2. Install maven module (https://maven.apache.org/install.html).

Steps:
1. To run this application, do the following:
    1.a. Go to the project root directory.
    1.b. Run the following commands in the terminal/command line to build the app:
        - mvn clean install
    1.c  go to the target folder     
    1.d. Run the following command(s) in the terminal/command line to run the app:
        java -jar webscan-0.0.1-SNAPSHOT.jar

2. Go to http://localhost:8080/swagger-ui.html in your browser to view it.



To Run the service Enter the Url in the request parameter  to be scanned .
https://www.314e.com/
