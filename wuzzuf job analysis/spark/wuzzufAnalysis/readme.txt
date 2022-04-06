# Final-Java-Project-On-Wuzzuf-DataSet

Wuzzuf jobs in Egypt data set at Kaggle
https://www.kaggle.com/omarhanyy/wuzzuf-jobs

### This repository contains our Final project in Java For Machine Learning Course 
We created Exploratory Data analysis on wuzzuf Dataset using Spark core and spark SQL then deployed our job using Spring boot web service
And made some visualization Using Xchart and display All outputs in the webservice 


# Project Functionality:

1- Read dataset From csv and convert it to spark DataSet of Row.

2- Display structure and summary of data using webservice.

3- removing nulls and duplicated values.

4- count the number of job offers made by each company and display that in descending order.

5- create a pieChart of the number of joboffers made by Top Companies and export it as image to diaplay on browser.
![alt text](https://github.com/mehraiel/WuzzufJobsAnalysis/blob/master/wuzzufAnalysis/src/main/resources/Images/pieChart.png)

6- finding the most popular job titles.

7- drawing category chart represents the frequency of each Job Title in the dataset.
![alt text](https://github.com/mehraiel/WuzzufJobsAnalysis/blob/master/wuzzufAnalysis/src/main/resources/Images/BarChartOne.png)

8- find the most popular areas.

9- create category chart represents the frequency of each Area in the dataset.
![alt text](https://github.com/mehraiel/WuzzufJobsAnalysis/blob/master/wuzzufAnalysis/src/main/resources/Images/BarChartTwo.png)

10- print each skill and the frequency of it to get the most common skills .

11- factorize the YearsEXP column to numbers then add the new column to the dataset


  
 # Environment:
    
  path to spring webService:  http://localhost:8080/  
    
    
    EX:http://localhost:8080/display-data   to view first 10 jobs in the dataset
    
  - Java intellij Ultimate Edition
    
  - Java 11
  
  - Spring boot
  
  - Spark
    