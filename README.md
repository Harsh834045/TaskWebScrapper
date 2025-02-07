Web Scraper in Java

This is a powerful web scraper built with Java, Jsoup, and Selenium to extract company details from multiple websites. The extracted data is saved in a CSV file for easy access and analysis.
This tool allows users to either search companies using Selenium (Google Search automation) or directly scrape specific websites using Jsoup. It efficiently gathers company details, including names, descriptions, emails, phone numbers, and social media links.

Features
-- User-Based Search (Selenium): Users can input a search query, and Selenium will fetch relevant company URLs from Google.
-- Scrapes multiple URLs at once
-- Extracts key details, such as:
   Company Name
   Company Description
   Email Address
   Phone Number
   Social Media Links (LinkedIn, Twitter, Facebook)
   Website URL
-- Handles errors gracefully to prevent crashes
-- Saves data to a CSV file for easy sharing and analysis

Data Extraction Levels
  This scraper supports different levels of data extraction based on the details needed:
1. Basic – Only company name and description
2. Medium – Adds email, phone number, and social media links
3. Advanced – Extracts structured data like metadata, deep links, and more

Setup & Installation
Prerequisites
Before running the scraper, make sure you have:
1. Java 17+ installed
2. IntelliJ IDEA (or any Java IDE)
3. Jsoup Library (for web scraping)
4. Selenium & ChromeDriver (for Google search automation)
     
How to Run
1️. Compile & Run the Code
javac -cp ".;jsoup-1.14.3.jar;selenium-java.jar;chromedriver.exe" WebScraper.java  
java -cp ".;jsoup-1.14.3.jar;selenium-java.jar;chromedriver.exe" WebScraper    
2️. Choose Scraping Mode
-- Search Mode (Selenium): The program will ask for a search query (e.g., "top IT companies"). Selenium will fetch the top Google results.
-- Direct Mode (Jsoup): Enter the number of websites you want to scrape and paste each URL one by one.
3️. Check Extracted Data
--The data will be saved in a file named company_data.csv in the project folder.

Error Handling & Improvements
-- Handles network errors (e.g., website down, incorrect URL)
-- Improved phone number detection with a more accurate regex
-- Better CSV formatting to prevent broken data in Excel
-- Cleans extracted text (removes unwanted symbols for better readability)

Why This Approach?
-- Selenium + ChromeDriver automates Google Search, helping users find relevant company links.
-- Jsoup is lightweight and perfect for web scraping in Java.
-- CSV storage keeps things simple and easy to analyze.
-- Error handling ensures the scraper doesn’t crash when a website is down.
-- Regex improvements make email and phone extraction more accurate.

Future Improvements
-- Add database support 
-- Support JSON output (for API-based applications)
-- Extract addresses and more company details


