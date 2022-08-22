package com.bankapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

class BankAppBackEndApplicationTests {

    String jhonBankAccountId = "00d15864-022b-4b6d-bfc6-dae6195574fb";
    String incorrectBankAccount = "00d15864-022b-4b6d-bfc6-dae6195574f";
    String checkJhonAccounts = "Jhon@gmail.com";
    String getCheckJhonTransferRequest = "Jhon@gmail.comTRANSFER";
    String getCheckJhonCreditRequest = "Jhon@gmail.comCREDIT";
    String getCheckJhonDebitRequest = "Jhon@gmail.comDEBIT";
    String getCheckJhonNewAccRequest = "Jhon@gmail.comNEWACC";
    String xpath = "(//*[@id='listacc'])[1]";
    private WebDriver driver;
    public void waitALittleById(String foo){
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class);
        wait.until(driver -> driver.findElement(By.id(foo)));
    }
    public void waitALittleByXpath(String foo){
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class);
        wait.until(driver -> driver.findElement(By.xpath(foo)));
    }
    public void waitALittleByName(String foo){
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class);
        wait.until(driver -> driver.findElement(By.name(foo)));
    }
    public void waitALittleByClass(String foo){
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class);
        wait.until(driver -> driver.findElement(By.className(foo)));
    }
    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void setUp(){
        driver = new ChromeDriver();
        driver.get("http://localhost:4200/");
        driver.manage().window().maximize();
    }
    @AfterEach
    void tearDown(){driver.quit();}

    @Test
    void registerUser() {
        WebElement registerPage = driver.findElement(By.linkText("Register"));
        registerPage.click();
    }

    @Test
    void userExisted() {
        WebElement registerPage = driver.findElement(By.linkText("Register"));
        registerPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPass = driver.findElement(By.name("password"));
        WebElement inputConfPass = driver.findElement(By.name("cpassword"));
        inputEmail.sendKeys("Admin@gmail.com");
        inputPass.sendKeys("1234");
        inputConfPass.sendKeys("1234");
        WebElement loginButton = driver.findElement(By.id("register"));
        loginButton.click();
        waitALittleByClass("text-danger");
        WebElement errorMessage = driver.findElement(By.className("text-danger"));
        String actualMessage = errorMessage.getText();
        String expectedMessage = "User with email: Admin@gmail.com is already existing";
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void passwordDontMatch() {
        WebElement registerPage = driver.findElement(By.linkText("Register"));
        registerPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPass = driver.findElement(By.name("password"));
        WebElement inputConfPass = driver.findElement(By.name("cpassword"));
        inputEmail.sendKeys("Admin@gmail.com");
        inputPass.sendKeys("1234");
        inputConfPass.sendKeys("124");
        WebElement errorMessage = driver.findElement(By.id("samePass"));
        String actualMessage = errorMessage.getText();
        String expectedMessage = "Password & confirm password must be same";
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void requiredPassRegistration() {
        WebElement registerPage = driver.findElement(By.linkText("Register"));
        registerPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPass = driver.findElement(By.name("password"));
        WebElement inputConfPass = driver.findElement(By.name("cpassword"));
        inputEmail.sendKeys("Admin@gmail.com");
        inputPass.sendKeys("");
        inputConfPass.sendKeys("");
        WebElement errorMessage = driver.findElement(By.id("reqpass"));
        String actualMessage = errorMessage.getText();
        String expectedMessage = "Password is a required field";
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void EmailInvalidRegistration() {
        WebElement registerPage = driver.findElement(By.linkText("Register"));
        registerPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPass = driver.findElement(By.name("password"));
        inputEmail.sendKeys("Admin@gmail.c");
        inputPass.sendKeys("");
        WebElement errorMessage = driver.findElement(By.id("validateEmail"));
        String actualMessage = errorMessage.getText();
        String expectedMessage = "Enter a valid value of email";
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void reqEmailRegistration() {
        WebElement registerPage = driver.findElement(By.linkText("Register"));
        registerPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPass = driver.findElement(By.name("password"));
        WebElement inputConfPass = driver.findElement(By.name("cpassword"));
        inputEmail.sendKeys("");
        inputPass.sendKeys("123");
        inputConfPass.sendKeys("123");
        WebElement errorMessage = driver.findElement(By.id("reqEmail"));
        String actualMessage = errorMessage.getText();
        String expectedMessage = "Email Id is a required field";
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void loginLogOutAdmin() {
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputEmail.sendKeys("Admin@gmail.com");
        inputPassword.sendKeys("1234");
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
        waitALittleById("logout");
        WebElement logoutButton = driver.findElement(By.id("logout"));
        logoutButton.click();
    }

    @Test
    void loginLogoutUser() {
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputEmail.sendKeys("Jhon@gmail.com");
        inputPassword.sendKeys("1234");
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
        waitALittleById("logout");
        WebElement logoutButton = driver.findElement(By.id("logout"));
        logoutButton.click();

    }

    @Test
    void badCredentials() {
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputEmail.sendKeys("Admin@gmail.com");
        inputPassword.sendKeys("123");
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
        WebElement errorMessage = driver.findElement(By.className("text-danger"));
        String actualMessage = errorMessage.getText();
        String expectedMessage = "Bad Credentials, please enter valid email and password";
        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void emailIdRequired() {
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputEmail.sendKeys("");
        inputPassword.sendKeys("1234");
        WebElement errorMessage = driver.findElement(By.id("email_req"));
        String actualMessage = errorMessage.getText();
        String expectedMessage = "Email Id is a required field";
        assertEquals(actualMessage,expectedMessage);    }

    @Test
    void passwordRequired() {
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputPassword.sendKeys("");
        inputEmail.sendKeys("Admin@gmail.com");
        WebElement errorMessage = driver.findElement(By.id("pwd_req"));
        String actualMessage = errorMessage.getText();
        String expectedMessage = "Password is a required field";
        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void emailInvalid() {
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputEmail.sendKeys("Admin");
        inputPassword.sendKeys("sm");

        WebElement errorMessage = driver.findElement(By.id("email_invalid"));
        System.out.println("Actual message: " + errorMessage.getText());
        String actualMessage = errorMessage.getText();
        String expectedMessage = "Enter a valid value of email";
        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void tryToGetAWrongAccountId() {
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputEmail.sendKeys("Jhon@gmail.com");
        inputPassword.sendKeys("1234");
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
        waitALittleByName("accounts");
        WebElement accountsLink = driver.findElement(By.name("accounts"));
        accountsLink.click();
        WebElement inputSearch = driver.findElement(By.id("search"));
        WebElement searchButton = driver.findElement(By.id("searchButton"));
        inputSearch.sendKeys(incorrectBankAccount);
        searchButton.click();
        waitALittleByClass("text-danger");
        WebElement errorMessage = driver.findElement(By.className("text-danger"));
        String actualMessage = errorMessage.getText();
        String expectedMessage = "Please enter a valid id";
        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void makeATypeOfRequest() {
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputEmail.sendKeys("Jhon@gmail.com");
        inputPassword.sendKeys("1234");
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
        waitALittleByName("accounts");
        WebElement accountsLink = driver.findElement(By.name("accounts"));
        accountsLink.click();
        WebElement inputSearch = driver.findElement(By.id("search"));
        WebElement searchButton = driver.findElement(By.id("searchButton"));
        inputSearch.sendKeys(jhonBankAccountId);
        searchButton.click();
        waitALittleById("credit");
        WebElement creditRequest = driver.findElement(By.id("credit"));
        creditRequest.click();
        WebElement creditAmount = driver.findElement(By.id("amount"));
        creditAmount.sendKeys("100001");
        WebElement creditDescription = driver.findElement(By.id("description"));
        creditDescription.sendKeys("For something");
        WebElement creditSave = driver.findElement(By.id("save"));
        creditSave.click();
        WebElement debitRequest = driver.findElement(By.id("debit"));
        debitRequest.click();
        WebElement debitAmount = driver.findElement(By.id("amount"));
        debitAmount.sendKeys("100002");
        WebElement description = driver.findElement(By.id("description"));
        description.sendKeys("For something");
        WebElement debitSave = driver.findElement(By.id("save"));
        debitSave.click();
        WebElement transferRequest = driver.findElement(By.id("transfer"));
        transferRequest.click();
        WebElement transferAmount = driver.findElement(By.id("amount"));
        transferAmount.sendKeys("100003");
        WebElement transferDescription = driver.findElement(By.id("description"));
        transferDescription.sendKeys("For something");
        WebElement accountDestination = driver.findElement(By.id("accDestination"));
        accountDestination.sendKeys(jhonBankAccountId);
        WebElement save = driver.findElement(By.id("save"));
        save.click();
        WebElement newAccountButton = driver.findElement(By.id("newAccounts"));
        newAccountButton.click();
        waitALittleById("balance");
        WebElement balanceInput = driver.findElement(By.id("balance"));
        WebElement saveOperation = driver.findElement(By.id("saveOperation"));
        balanceInput.sendKeys("10000");
        saveOperation.click();
    }

    @Test
    void navigateToAccountsInAdmin(){
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputEmail.sendKeys("Admin@gmail.com");
        inputPassword.sendKeys("1234");
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
        waitALittleById("accounts");
        WebElement accountsButton = driver.findElement(By.id("accounts"));
        accountsButton.click();
        waitALittleById("search");
        WebElement inputSearch = driver.findElement(By.id("search"));
        WebElement searchButton = driver.findElement(By.id("searchButton"));
        inputSearch.sendKeys(jhonBankAccountId);
        searchButton.click();
        WebElement searchCustomersButton = driver.findElement(By.id("searchCustomer"));
        searchCustomersButton.click();
        waitALittleById(checkJhonAccounts);
        WebElement customerAccounts = driver.findElement(By.id(checkJhonAccounts));
        customerAccounts.click();
        WebElement secondInputSearch = driver.findElement(By.id("search"));
        secondInputSearch.sendKeys(jhonBankAccountId);
        WebElement secondSearchButton = driver.findElement(By.id("searchButton"));
        secondSearchButton.click();
    }

    @Test
    void acceptOrDeclineCreditRequest(){
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputEmail.sendKeys("Admin@gmail.com");
        inputPassword.sendKeys("1234");
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
        waitALittleById("requests");
        WebElement requestsButton = driver.findElement(By.id("requests"));
        requestsButton.click();
        waitALittleById(getCheckJhonCreditRequest);
        WebElement customerCreditButtonAccept = driver.findElement(By.id(getCheckJhonCreditRequest));
        customerCreditButtonAccept.click();
        try {
            waitALittleByXpath(xpath);
            WebElement accIdAccept = driver.findElement(By.xpath(xpath));
            WebElement acceptButtonAccept = driver.findElement(By.id(accIdAccept.getText()+"ACCEPT"));
            acceptButtonAccept.click();
            driver.switchTo().alert().accept();
            waitALittleByXpath(xpath);
            WebElement accIdDecline = driver.findElement(By.xpath(xpath));
            WebElement acceptButtonDecline = driver.findElement(By.id(accIdDecline.getText()+"DECLINE"));
            acceptButtonDecline.click();
            driver.switchTo().alert().accept();
        }catch (Exception exception){
            System.out.println("The Element doesn't exist. The list either is empty, either you chose an attribute to high for the xpath. ");
        }
    }

    @Test
    void acceptOrDeclineDebitRequest(){
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputEmail.sendKeys("Admin@gmail.com");
        inputPassword.sendKeys("1234");
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
        waitALittleById("requests");
        WebElement requestsButton = driver.findElement(By.id("requests"));
        requestsButton.click();
        waitALittleById(getCheckJhonCreditRequest);
        WebElement customerCreditButtonAccept = driver.findElement(By.id(getCheckJhonDebitRequest));
        customerCreditButtonAccept.click();
        try {
            waitALittleByXpath(xpath);
            WebElement accIdAccept = driver.findElement(By.xpath(xpath));
            WebElement acceptButtonAccept = driver.findElement(By.id(accIdAccept.getText()+"ACCEPT"));
            acceptButtonAccept.click();
            driver.switchTo().alert().accept();
            waitALittleByXpath(xpath);
            WebElement accIdDecline = driver.findElement(By.xpath(xpath));
            WebElement acceptButtonDecline = driver.findElement(By.id(accIdDecline.getText()+"DECLINE"));
            acceptButtonDecline.click();
            driver.switchTo().alert().accept();
        }catch (Exception exception){
            System.out.println("The Element doesn't exist. The list either is empty, either you chose an attribute to high for the xpath. ");
        }
    }

    @Test
    void acceptOrDeclineTransferRequest(){
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputEmail.sendKeys("Admin@gmail.com");
        inputPassword.sendKeys("1234");
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
        waitALittleById("requests");
        WebElement requestsButton = driver.findElement(By.id("requests"));
        requestsButton.click();
        waitALittleById(getCheckJhonCreditRequest);
        WebElement customerCreditButtonAccept = driver.findElement(By.id(getCheckJhonTransferRequest));
        customerCreditButtonAccept.click();
        try {
            waitALittleByXpath(xpath);
            WebElement accIdAccept = driver.findElement(By.xpath(xpath));
            WebElement acceptButtonAccept = driver.findElement(By.id(accIdAccept.getText()+"ACCEPT"));
            acceptButtonAccept.click();
            driver.switchTo().alert().accept();
            waitALittleByXpath(xpath);
            WebElement accIdDecline = driver.findElement(By.xpath(xpath));
            WebElement acceptButtonDecline = driver.findElement(By.id(accIdDecline.getText()+"DECLINE"));
            acceptButtonDecline.click();
            driver.switchTo().alert().accept();
        }catch (Exception exception){
            System.out.println("The Element doesn't exist. The list either is empty, either you chose an attribute to high for the xpath. ");
        }
    }

    @Test
    void acceptOrDeclineNewAccountRequest(){
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputEmail.sendKeys("Admin@gmail.com");
        inputPassword.sendKeys("1234");
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
        waitALittleById("requests");
        WebElement requestsButton = driver.findElement(By.id("requests"));
        requestsButton.click();
        waitALittleById(getCheckJhonCreditRequest);
        WebElement customerCreditButtonAccept = driver.findElement(By.id(getCheckJhonNewAccRequest));
        customerCreditButtonAccept.click();
        try {
            waitALittleByXpath(xpath);
            WebElement accIdAccept = driver.findElement(By.xpath(xpath));
            WebElement acceptButtonAccept = driver.findElement(By.id(accIdAccept.getText()+"ACTIVATE"));
            acceptButtonAccept.click();
            driver.switchTo().alert().accept();
            waitALittleByXpath(xpath);
            WebElement accIdDecline = driver.findElement(By.xpath(xpath));
            WebElement acceptButtonDecline = driver.findElement(By.id(accIdDecline.getText()+"SUSPEND"));
            acceptButtonDecline.click();
            driver.switchTo().alert().accept();
        }catch (Exception exception){
            System.out.println("The Element doesn't exist. The list either is empty, either you chose an attribute to high for the xpath. ");
        }
    }

    @Test
    void inputIncorrectId(){
        WebElement loginPage = driver.findElement(By.linkText("Login"));
        loginPage.click();
        WebElement inputEmail = driver.findElement(By.name("email"));
        WebElement inputPassword = driver.findElement(By.name("pwd"));
        inputEmail.sendKeys("Admin@gmail.com");
        inputPassword.sendKeys("1234");
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
        waitALittleById("accounts");
        WebElement accountsButton = driver.findElement(By.id("accounts"));
        accountsButton.click();
        waitALittleById("search");
        WebElement inputSearch = driver.findElement(By.id("search"));
        WebElement searchButton = driver.findElement(By.id("searchButton"));
        inputSearch.sendKeys(incorrectBankAccount);
        searchButton.click();
        waitALittleByClass("text-danger");
        WebElement errorMessage = driver.findElement(By.className("text-danger"));
        String actualMessage = errorMessage.getText();
        String expectedMessage = "Please provide a correct account id";
        assertEquals(actualMessage,expectedMessage);
    }

}
