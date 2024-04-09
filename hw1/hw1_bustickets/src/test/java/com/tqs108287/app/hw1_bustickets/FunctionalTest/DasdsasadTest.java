// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class DasdsasadTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void dasdsasad() {
    driver.get("http://localhost:4200/");
    driver.manage().window().setSize(new Dimension(904, 1033));
    {
      WebElement dropdown = driver.findElement(By.id("originCity"));
      dropdown.findElement(By.xpath("//option[. = 'Stop: Lisboa; District: Lisboa']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("originCity"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("originCity"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("originCity"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    {
      WebElement dropdown = driver.findElement(By.id("destinationCity"));
      dropdown.findElement(By.xpath("//option[. = 'Stop: Aveiro; District: Aveiro']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("destinationCity"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("destinationCity"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("destinationCity"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    {
      WebElement dropdown = driver.findElement(By.cssSelector(".form-select:nth-child(1)"));
      dropdown.findElement(By.xpath("//option[. = 'Jun']")).click();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".form-select:nth-child(1)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".form-select:nth-child(1)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".form-select:nth-child(1)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".ngb-dp-week:nth-child(2) > .ngb-dp-day:nth-child(7) > .btn-light")).click();
    {
      WebElement dropdown = driver.findElement(By.id("currencyPicker"));
      dropdown.findElement(By.xpath("//option[. = 'USD']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("currencyPicker"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("currencyPicker"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("currencyPicker"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("search_trips_button")).click();
    {
      WebElement element = driver.findElement(By.id("search_trips_button"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector(".list-group-item:nth-child(3)")).click();
    {
      WebElement dropdown = driver.findElement(By.id("seatNumberPicker"));
      dropdown.findElement(By.xpath("//option[. = '11']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("seatNumberPicker"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("seatNumberPicker"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("seatNumberPicker"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".mb-3:nth-child(2) > .form-control")).click();
    driver.findElement(By.cssSelector(".mb-3:nth-child(2) > .form-control")).sendKeys("dasdsa");
    driver.findElement(By.cssSelector(".ng-untouched")).click();
    driver.findElement(By.cssSelector(".ng-untouched")).sendKeys("asddsa");
    driver.findElement(By.cssSelector(".btn:nth-child(4)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".btn:nth-child(4)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector(".card-body")).click();
    assertThat(driver.findElement(By.cssSelector("b")).getText(), is("Your reservation was successfull"));
    driver.findElement(By.cssSelector(".card-body")).click();
    driver.findElement(By.cssSelector(".card-body")).click();
    driver.findElement(By.cssSelector(".card-body")).click();
    driver.findElement(By.cssSelector(".card-body")).click();
    assertThat(driver.findElement(By.cssSelector(".card-body")).getText(), is("Your reservation was successfull\\\\nReservation Number: bb81c885-d451-4ee0-a891-0830900efd1f\\\\nSeat Number: 11\\\\nClient Name: dasdsa\\\\nClient Address: asddsa\\\\nRoute: Lisboa -> Coimbra -> Aveiro"));
    driver.findElement(By.cssSelector(".card-body")).click();
    assertThat(driver.findElement(By.cssSelector(".card-body")).getText(), is("Your reservation was successfull\\\\nReservation Number: bb81c885-d451-4ee0-a891-0830900efd1f\\\\nSeat Number: 11\\\\nClient Name: dasdsa\\\\nClient Address: asddsa\\\\nRoute: Lisboa -> Coimbra -> Aveiro"));
    driver.findElement(By.cssSelector(".card-body")).click();
    assertThat(driver.findElement(By.cssSelector(".card-body")).getText(), is("Your reservation was successfull\\\\nReservation Number: bb81c885-d451-4ee0-a891-0830900efd1f\\\\nSeat Number: 11\\\\nClient Name: dasdsa\\\\nClient Address: asddsa\\\\nRoute: Lisboa -> Coimbra -> Aveiro"));
    driver.findElement(By.cssSelector(".card-body")).click();
    driver.findElement(By.cssSelector(".card-body")).click();
    driver.findElement(By.cssSelector(".card-body")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".card-body"));
      Actions builder = new Actions(driver);
      builder.doubleClick(element).perform();
    }
    driver.findElement(By.cssSelector(".card-body")).click();
    assertThat(driver.findElement(By.cssSelector(".card-body")).getText(), is("Your reservation was successfull\\\\nReservation Number: bb81c885-d451-4ee0-a891-0830900efd1f\\\\nSeat Number: 11\\\\nClient Name: dasdsa\\\\nClient Address: asddsa\\\\nRoute: Lisboa -> Coimbra -> Aveiro"));
    driver.findElement(By.cssSelector(".card-body")).click();
    assertThat(driver.findElement(By.cssSelector(".card-body")).getText(), is("Your reservation was successfull\\\\nReservation Number: bb81c885-d451-4ee0-a891-0830900efd1f\\\\nSeat Number: 11\\\\nClient Name: dasdsa\\\\nClient Address: asddsa\\\\nRoute: Lisboa -> Coimbra -> Aveiro"));
  }
}
