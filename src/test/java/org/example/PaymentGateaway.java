package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import static org.testng.AssertJUnit.assertEquals;



public class PaymentGateaway {

    public static ExtentTest test;
    ExtentHtmlReporter htmlReporter;

    ExtentReports extent;

    @BeforeTest
    public void startReport() {
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/testReport.html");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("Simple Automation Report");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }


    @Test
    public void checkout() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","D:\\webdriver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://demo.guru99.com/payment-gateway/index.php");

        driver.findElement(By.xpath("//select//option[@value='2']")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@class='button special']")).click();

        driver.findElement(By.xpath("//input[@id='card_nmuber']")).sendKeys("5197035190093748");
        Thread.sleep(200);
        driver.findElement(By.xpath("//select//option[@value='3']")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("//select[@id='year']//option[10]")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("//input[@id='cvv_code']")).sendKeys("111");
        Thread.sleep(200);
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        String expectText = "Payment successfull!";
        WebElement actualText = driver.findElement(By.xpath("//h2[contains(text(),'Payment successfull!')]"));
        assertEquals(actualText.getText(), expectText);

        test = extent.createTest("checkout", "PASSED test case");
        Assert.assertTrue(true);

        driver.close();

        }

//    }
//    @Test
//    public void testCase() {
//        test = extent.createTest("Test Case", "PASSED test case");
//        Assert.assertTrue(true);
//    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL,result.getThrowable());
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, result.getTestName());
        }
        else {
            test.log(Status.SKIP, result.getTestName());
        }
    }

    @AfterTest
    public void tearDown2() {

        extent.flush();
    }
}
