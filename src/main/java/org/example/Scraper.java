package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Scraper {
    public static List<String> fetchCompanyUrls(String query) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\hi\\Downloads\\chromedriver-win32\\chromedriver-win32\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // Run in background
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1200");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String googleSearchURL = "https://www.google.com/search?q=" + query.replace(" ", "+");
        driver.get(googleSearchURL);

        List<WebElement> searchResults = driver.findElements(By.cssSelector("a[href]"));
        List<String> companyUrls = new ArrayList<>();

        for (WebElement result : searchResults) {
            String url = result.getAttribute("href");
            if (url.startsWith("http") && !url.contains("google.com")) {
                companyUrls.add(url);
            }
            if (companyUrls.size() >= 5) break;
        }

        driver.quit();
        return companyUrls;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter search query (e.g., top cricket companies): ");
        String query = scanner.nextLine().trim();

        List<String> companyUrls = fetchCompanyUrls(query);
        if (companyUrls.isEmpty()) {
            System.out.println("No company URLs found.");
        } else {
            System.out.println("Found company URLs:");
            for (String url : companyUrls) {
                System.out.println(url);
            }
        }
    }
}
