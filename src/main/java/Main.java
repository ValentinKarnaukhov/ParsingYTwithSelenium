import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
 * Created by Valentin on 22.10.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        Scanner scanner=new Scanner(System.in);

        System.out.println("Link:");
        String link=scanner.nextLine();


        WebDriver driver = new ChromeDriver();
        driver.get(link);
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

        WebElement inpoint=driver.findElement(By.tagName("ytd-video-secondary-info-renderer"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",inpoint);

        WebElement count=driver.findElement(By.cssSelector("yt-formatted-string.count-text.ytd-comments-header-renderer"));

        String[] qt=count.getText().split(" ");
        int qtint=Integer.parseInt(StringUtils.join(qt,"",0,qt.length-1));


        List<WebElement> elements = driver.findElements(By.cssSelector("yt-formatted-string#content-text.ytd-comment-renderer"));


        while (elements.size()!=qtint){
            elements = driver.findElements(By.cssSelector("yt-formatted-string#content-text.ytd-comment-renderer"));
            Thread.sleep(3000);
            System.out.println("-->"+elements.size()+"/"+qtint);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elements.get(elements.size()-1));
        }

        driver.quit();

        FileWriter fout=new FileWriter("src/main/resources/comments.txt");


        for(int i=0;i<elements.size();i++){
            fout.write(elements.get(i).toString());
        }
        fout.close();
    }
}
