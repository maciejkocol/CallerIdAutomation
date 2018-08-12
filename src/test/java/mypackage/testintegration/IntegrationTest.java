package mypackage.testintegration;

import org.springframework.test.annotation.IfProfileValue;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@IfProfileValue(name="testprofile", value="integrationtest")
public @interface IntegrationTest {
}