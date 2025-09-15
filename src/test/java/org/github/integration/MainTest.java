package org.github.integration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainTest {

    @Test
    public void greetContainsNamaste() {
        assertEquals(true, Main.greet().contains("Namaste"));
    }

    @Test
    public void greetIsNotEmpty() {
        assertEquals(false, Main.greet().isEmpty());
    }

    @Test
    public void greetReturnsDifferentMessage_Failure() {
        String failEnv = System.getenv("FAIL_THIS_BUILD");
        if ("true".equalsIgnoreCase(failEnv)) {
            assertEquals("Hello", Main.greet()); // This will fail
        } else {
            assertEquals("Namaste from github-jenkins-integration", Main.greet()); // This will pass
        }
    }

    @Test
    public void greetReturnsExpectedMessage() {
        assertEquals("Namaste from github-jenkins-integration", Main.greet());
    }
}