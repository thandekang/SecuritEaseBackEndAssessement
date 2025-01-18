# SecuritEaseBackEndAssessement

This file is going to take us step by step on how to setup the project and execution

## Tools needed in this project
* JDK
* IntelliJ
* Maven

## Maven Dependencies
* rest-assured
* google gson
* json-simple
* allure testng
*
## Project Structure
This will illustrate the structure of the folders
* Common package - contains all re-usable classes
* Tests - This package will contain the tests classes

## Reporting
* The report will be saved under the folder allure-results
* To open the report - in the project location, type allure serve <path of the report files>

## Execution
choose any of the below option to execute your project locally
* mvn clean test
* Run the test class

## Pipeline
The below shows different way to trigger the pipeline build
* push something to the main branch - the build will  be triggered automatically
* trigger the build direct from github actions

## Report in the pipeline
* Open the 