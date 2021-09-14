package com.apple.pages.testing.mobile;

import com.apple.pages.testing.ApplePageTests;
import com.browserup.bup.BrowserUpProxy;
import com.browserup.bup.proxy.CaptureType;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@SpringBootTest
@SpringBootConfiguration
@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "iOS")
public class IosApplePageTests implements ApplePageTests {

    private static BrowserUpProxy bmp;

    @BeforeAll
    public static void setUp() {

        Configuration.startMaximized = false;
        Configuration.browserSize = null;

        Configuration.baseUrl = "https://apple.com";
        Configuration.proxyEnabled = true;
        Configuration.proxyHost = "0.0.0.0";
        Configuration.fileDownload = FileDownloadMode.PROXY;

        Configuration.browser = IOSSelenideDriver.class.getName();

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false));
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
    public void testStoreButtonClick() {
        $(By.xpath("//a[@class='ac-gn-menuanchor ac-gn-menuanchor-open']")).click();
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-store']")).click();

        $(By.className("rs-shop-subheader")).shouldBe(visible);
        $(By.xpath("//div[@class='rs-shop-subheader']")).shouldHave(exactText("The best way to buy the products you love."));
    }

    @Test
    @Override
    public void testAppleClickClick() {
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-apple']")).click();

        $(By.xpath("//span[@class='shop-ribbon-copy variant']"))
                .shouldHave(text(" We look forward to welcoming you to our stores. Whether you "));
    }

    @Test
    @Override
    public void testMacButtonClick() {
        $(By.xpath("//a[@class='ac-gn-menuanchor ac-gn-menuanchor-open']")).click();
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-mac']")).click();

        $(By.id("main")).shouldBe(visible);
        $(By.xpath("//h2[@class='typography-hero-product-headline']")).shouldHave(exactText("New iMac"));
    }

    @Test
    @Override
    public void testIpadButtonClick() {
        $(By.xpath("//a[@class='ac-gn-menuanchor ac-gn-menuanchor-open']")).click();
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-ipad']")).click();

        $(By.xpath("//h2[@class='typography-hero-headline']")).shouldHave(exactText("New iPad Pro"));
    }

    @Test
    @Override
    public void testIphoneButtonClick() {
        $(By.xpath("//a[@class='ac-gn-menuanchor ac-gn-menuanchor-open']")).click();
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-iphone']")).click();

        $(By.xpath("//p[@class='typography-hero-headline hero-headline']")).shouldHave(exactText("Blast Past Fast."));
    }

    @Test
    @Override
    public void testWatchButtonClick() {
        $(By.xpath("//a[@class='ac-gn-menuanchor ac-gn-menuanchor-open']")).click();
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-watch']")).click();

        $(By.xpath("//p[@class='tile-headline typography-custom-headline']"))
                .shouldHave(exactText("The future of health is on your wrist."));
    }

    @Test
    @Override
    public void testTvButtonClick() {
        $(By.xpath("//a[@class='ac-gn-menuanchor ac-gn-menuanchor-open']")).click();
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-tv']")).click();

        $(By.xpath("//h3[@class='tv-app-headline typography-section-headline']"))
                .shouldHave(exactText("All your TV. All in one app."));
    }

    @Test
    @Override
    public void testMusicButtonClick() {
        $(By.xpath("//a[@class='ac-gn-menuanchor ac-gn-menuanchor-open']")).click();
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-music']")).click();

        $(By.xpath("//li[@class='chapternav-item chapternav-item-apple-music']"))
                .shouldBe(visible);
    }

    @Test
    @Override
    public void testSupportButtonClick() {
        $(By.xpath("//a[@class='ac-gn-menuanchor ac-gn-menuanchor-open']")).click();
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-support']")).click();

        $(By.xpath("//h1[@class='pageTitle-heading']"))
                .shouldHave(exactText("Welcome to Apple Support"));
    }

    @Test
    @Override
    public void testSearchButtonClick() {
        $(By.xpath("//a[@class='ac-gn-menuanchor ac-gn-menuanchor-open']")).click();
        $(By.xpath("//a[@id='ac-gn-link-search-small']")).click();

        $(By.id("ac-gn-searchform-input")).shouldBe(visible);
        $(By.id("ac-gn-searchview-close-small")).shouldHave(text("Cancel"));
    }

    @Test
    @Override
    public void testCheckoutButtonClick() {
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-bag']")).click();

        $(By.id("ac-gn-bag-small")).click();
        $(By.id("ac-gn-bagview-content")).shouldBe(visible);
    }
}
