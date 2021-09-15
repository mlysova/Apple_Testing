package com.apple.pages.testing.browser;

import com.apple.pages.testing.ApplePageTests;
import com.apple.pages.testing.utils.TestUtils;
import com.browserup.bup.BrowserUpProxy;
import com.browserup.bup.proxy.CaptureType;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.commons.lang.NotImplementedException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

//@EnableConfigurationProperties
@SpringBootTest
@SpringBootConfiguration
@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"chrome", "edge"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BrowserApplePageBaseTests implements ApplePageTests {

    protected static BrowserUpProxy bmp;

    @BeforeAll
    public void setUp() {
        closeWebDriver();
        Configuration.baseUrl = "https://apple.com";
        Configuration.proxyEnabled = true;
        Configuration.proxyHost = "0.0.0.0";
        Configuration.fileDownload = FileDownloadMode.PROXY;

        Configuration.browserCapabilities.setCapability("platformName", getPlatformName());
        Configuration.browser = getBrowserName();

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
    }

    protected String getBrowserName() {
        return "chrome";
    }

    protected String getPlatformName() {
        return "mac";
    }

    @AfterAll
    public static void close() {
        closeWebDriver();
    }

    @BeforeEach
    public void openMainPage() {
        open("/");

        bmp = WebDriverRunner.getSelenideProxy().getProxy();
        bmp.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
        // remember both requests and responses
        bmp.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        // start recording!
        bmp.newHar("pofig");
    }

    @Test
    @Override
    public void testCheckoutButtonClick() {
        $(By.id("ac-gn-bag")).shouldBe(visible);
        $(By.id("ac-gn-bag")).click();

        $(By.id("ac-gn-bagview-content")).shouldBe(visible);
        assert TestUtils.is200CodeInResponseByQueryParam("link", "bag - /us/shop/goto/bag", bmp);
    }

    @Test
    @Override
    public void testAppleClick() {
        $(By.xpath("//a[@id='ac-gn-firstfocus']")).shouldBe(visible);
        $(By.xpath("//a[@id='ac-gn-firstfocus']")).click();

        assert TestUtils.is200CodeInResponse("https://www.apple.com/", true, bmp);
    }

    @Test
    @Override
    public void testStoreButtonClick() {
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-store']")).click();

        assert TestUtils.is200CodeInResponse("https://www.apple.com/store", true, bmp);
        assert $(By.className("rs-storehome")).isDisplayed();
        $(By.xpath("//div[@class='rs-shop-subheader']")).shouldHave(exactText("The best way to buy the products you love."));
    }

    @Test
    @Override
    public void testMacButtonClick() {
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-mac']")).click();

        assert TestUtils.is200CodeInResponse("https://www.apple.com/mac/", true, bmp);
        assert $(By.id("main")).isDisplayed();
        $(By.xpath("//h2[@class='typography-hero-product-headline']")).shouldHave(exactText("New iMac"));
    }

    @Test
    @Override
    public void testIpadButtonClick() {
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-ipad']")).click();

        assert TestUtils.is200CodeInResponse("https://www.apple.com/ipad/", true, bmp);
        $(By.xpath("//h2[@class='typography-hero-headline']")).shouldHave(Condition.text("New iPad"));
    }

    @Test
    @Override
    public void testIphoneButtonClick() {
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-iphone']")).click();

        assert TestUtils.is200CodeInResponse("https://www.apple.com/iphone/", true, bmp);
        $(By.xpath("//p[@class='typography-hero-headline hero-headline']")).shouldHave(exactText("Oh. So. Pro."));
    }

    @Test
    @Override
    public void testWatchButtonClick() {
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-watch']")).click();

        assert TestUtils.is200CodeInResponse("https://www.apple.com/watch/", true, bmp);
        $(By.xpath("//p[@class='tile-headline  typography-custom-headline']"))
                .shouldHave(exactText("Full screen ahead."));
    }

    @Test
    @Override
    public void testTvButtonClick() {
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-tv']")).click();

        assert TestUtils.is200CodeInResponse("https://www.apple.com/tv/", true, bmp);
        $(By.xpath("//h3[@class='tv-app-headline typography-section-headline']"))
                .shouldHave(exactText("All your TV. All in one app."));
    }

    @Test
    @Override
    public void testMusicButtonClick() {
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-music']")).shouldBe(visible);
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-music']")).click();

        $(By.xpath("//li[@class='chapternav-item chapternav-item-apple-music']")).shouldBe(visible);
        assert TestUtils.is200CodeInResponse("https://www.apple.com/music/", true, bmp);
    }

    @Test
    @Override
    public void testSupportButtonClick() {
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-support']")).click();

        assert TestUtils.is200CodeInResponse("https://support.apple.com/", true, bmp);
        $(By.xpath("//h1[@class='pageTitle-heading']"))
                .shouldHave(exactText("Welcome to Apple Support"));
    }

    @Test
    @Override
    public void testSearchButtonClick() {
        throw new NotImplementedException();
    }
}
