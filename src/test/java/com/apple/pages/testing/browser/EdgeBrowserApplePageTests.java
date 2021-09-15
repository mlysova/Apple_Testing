package com.apple.pages.testing.browser;

import com.apple.pages.testing.utils.TestUtils;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.codeborne.selenide.Selenide.*;

@SpringBootTest
@ActiveProfiles(profiles = "edge")
public class EdgeBrowserApplePageTests extends BrowserApplePageBaseTests {

    @Test
    @Override
    public void testSearchButtonClick() {
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-search']")).click();

        $(By.className("ac-gn-searchform-input")).shouldBe(Condition.visible);
        assert TestUtils.is200CodeInResponseByQueryParam("link", "search - /us/search", bmp);
    }

    @Override
    protected String getBrowserName() {
        return "edge";
    }

    @Override
    protected String getPlatformName() {
       return "win10";
//        return "mac";
    }
}
