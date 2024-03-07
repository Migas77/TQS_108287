package com.tqs108287.App;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@ExtendWith(SeleniumJupiter.class)
public class DepencyInjectionTest
{
    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    void test(ChromeDriver driver) {
        // Exercise
        String sutUrl = "https://pi-propertease.github.io/"; // site do projeto de PI:)
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        // Verify
        assertThat(title).isEqualTo("Hello from PropertEase | PropertEase");
    }
}
