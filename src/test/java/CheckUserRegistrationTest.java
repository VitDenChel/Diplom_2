import com.UserForRegistration;
import com.UserOperations;
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
}
