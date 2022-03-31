import com.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheckUserRegistrationTest {
    private UserOperations userOperations;

    @Before
    public void setUp() {
        userOperations = new UserOperations();
    }

    @After
    public void tearDown() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        Response createUniqueUserResponse = userOperations.create(userForRegistration);
        String TokenUser = createUniqueUserResponse.jsonPath().get("accessToken");
        userOperations.delete();
    }

    @Test
    public void newUserWasSuccessfullyRegisteredtedTest() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        Response userRegistrationResponse = userOperations.create(userForRegistration);

        Assert.assertEquals("Two equal users can be created", 200, userRegistrationResponse.statusCode());
        Assert.assertEquals("Wrong error message", "true", userRegistrationResponse.jsonPath().getString("success"));
    }

    @Test
    public void checkUserRegistrationWithoutName() {
        UserHasEmailAndPassword userHasEmailAndPassword = UserHasEmailAndPassword.getRandom();
        Response createUserWithoutNameResponse = userOperations.createUserWithoutName(userHasEmailAndPassword);

        Assert.assertEquals("User can be created without password", 403, createUserWithoutNameResponse.statusCode());
        Assert.assertEquals("Wrong error message", "false", createUserWithoutNameResponse.jsonPath().getString("success"));
        Assert.assertEquals("Wrong error message", "Email, password and name are required fields", createUserWithoutNameResponse.jsonPath().getString("message"));
    }

    @Test
    public void checkUserRegistrationWithoutEmail() {
        UserHasPasswordAndName userHasPasswordAndName = UserHasPasswordAndName.getRandom();
        Response createUserWithoutEmailResponse = userOperations.createUserWithoutEmail(userHasPasswordAndName);

        Assert.assertEquals("User can be created without Email", 403, createUserWithoutEmailResponse.statusCode());
        Assert.assertEquals("Wrong error message", "false", createUserWithoutEmailResponse.jsonPath().getString("success"));
        Assert.assertEquals("Wrong error message", "Email, password and name are required fields", createUserWithoutEmailResponse.jsonPath().getString("message"));
    }

    @Test
    public void checkUserRegistrationWithoutPassword() {
        UserHasEmailAndName userHasEmailAndName = UserHasEmailAndName.getRandom();
        Response createUserWithoutPasswordResponse = userOperations.createUserWithoutPassword(userHasEmailAndName);

        Assert.assertEquals("User can be created without password", 403, createUserWithoutPasswordResponse.statusCode());
        Assert.assertEquals("Wrong error message", "false", createUserWithoutPasswordResponse.jsonPath().getString("success"));
        Assert.assertEquals("Wrong error message", "Email, password and name are required fields", createUserWithoutPasswordResponse.jsonPath().getString("message"));
    }

    @Test
    public void checkTwoEqualUsersCanNotBeCreated() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        Response createUniqueUserResponse = userOperations.create(userForRegistration);
        Response createSecondUserResponse = userOperations.create(userForRegistration);

        Assert.assertEquals("Two equal users can be created", 403, createSecondUserResponse.statusCode());
        Assert.assertEquals("Wrong error message", "false", createSecondUserResponse.jsonPath().getString("success"));
        Assert.assertEquals("Wrong error message", "User already exists", createSecondUserResponse.jsonPath().getString("message"));
    }
}
