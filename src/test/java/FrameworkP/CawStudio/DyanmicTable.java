package FrameworkP.CawStudio;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class DyanmicTable {


	@Test()
	public void checkFlow() throws IOException, ParseException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Administrator\\Downloads\\chromedriver-win32 (1)\\chromedriver-win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(59));
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
		driver.findElement(By.xpath("//*[contains(text(),'Table Data')]")).click();
		driver.findElement(By.xpath("//textarea[@id=\"jsondata\"]")).clear();
		String reader = System.getProperty("user.dir") + "\\src\\test\\java\\dataFile\\CawStudio\\TestData.json";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode payload = mapper.readTree(new File(reader));
		driver.findElement(By.xpath("//textarea[@id=\"jsondata\"]")).sendKeys(payload.toString());
		driver.findElement(By.xpath("//button[@id=\"refreshtable\"]")).click();
		List<WebElement> row = driver.findElements(By.xpath("//tr"));
		int count = row.size();
		for (int i = 1; i < count; i++) {

			Assert.assertTrue(row.get(i).getText().contains(payload.get(i - 1).get("name").asText()));
			Assert.assertTrue(row.get(i).getText().contains(payload.get(i - 1).get("age").asText()));
			Assert.assertTrue(row.get(i).getText().contains(payload.get(i - 1).get("gender").asText()));

		}

		driver.close();

	}

}
