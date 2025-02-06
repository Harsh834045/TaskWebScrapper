package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebScraper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to enter URLs manually? (yes/no): ");
        boolean manualEntry = scanner.nextLine().trim().equalsIgnoreCase("yes");

        List<String> urls = new ArrayList<>();

        if (manualEntry) {
            System.out.print("Enter the number of URLs: ");
            int urlCount = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < urlCount; i++) {
                System.out.print("Enter URL " + (i + 1) + ": ");
                urls.add(scanner.nextLine().trim());
            }
        } else {
            System.out.print("Enter search query (e.g., top IT companies in USA): ");
            String query = scanner.nextLine().trim();
            urls =Scraper.fetchCompanyUrls(query);

            if (urls.isEmpty()) {
                System.out.println("No company URLs found for the given query.");
                return;
            }
        }

        List<Map<String, String>> extractedData = new ArrayList<>();

        for (String url : urls) {
            try {
                System.out.println("Scraping: " + url);
                extractedData.add(fetchCompanyData(url));
            } catch (IOException e) {
                System.err.println("Failed to scrape " + url + ": " + e.getMessage());
            }
        }

        try {
            saveToCSV("company_data.csv", extractedData);
            System.out.println("Data successfully saved to company_data.csv");
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    private static Map<String, String> fetchCompanyData(String url) throws IOException {
        Map<String, String> companyInfo = new LinkedHashMap<>();
        Document document = Jsoup.connect(url).get();

        companyInfo.put("Company Name", document.title());
        companyInfo.put("Company Description", extractMetaDescription(document));
        companyInfo.put("Email", extractEmail(document));
        companyInfo.put("Phone", extractPhoneNumber(document.text()));
        companyInfo.put("LinkedIn", findSocialMediaLink(document, "linkedin"));
        companyInfo.put("Twitter", findSocialMediaLink(document, "twitter"));
        companyInfo.put("Facebook", findSocialMediaLink(document, "facebook"));
        companyInfo.put("Website URL", url);

        return companyInfo;
    }

    private static String extractMetaDescription(Document document) {
        Elements metaTags = document.select("meta[name=description]");
        return metaTags.isEmpty() ? "Not Available" : metaTags.attr("content");
    }

    private static String extractEmail(Document document) {
        Elements emailElements = document.select("a[href^=mailto]");
        return emailElements.isEmpty() ? "Not Available" : emailElements.get(0).text();
    }

    private static String extractPhoneNumber(String text) {
        Pattern phonePattern = Pattern.compile("(\\+\\d{1,3}\\s?)?(\\d{3}[-.\\s]?\\d{4})");
        Matcher matcher = phonePattern.matcher(text);
        return matcher.find() ? matcher.group() : "Not Available";
    }

    private static String findSocialMediaLink(Document document, String platform) {
        Elements links = document.select("a[href*=" + platform + "]");
        return links.isEmpty() ? "Not Available" : links.get(0).attr("href");
    }

    private static void saveToCSV(String filename, List<Map<String, String>> data) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.append("Company Name,Company Description,Email,Phone,LinkedIn,Twitter,Facebook,Website URL\n");

            for (Map<String, String> entry : data) {
                writer.append(String.join(",", entry.values())).append("\n");
            }
        }
    }
}
