import com.UserForCheckAuthorization;
import com.UserForRegistration;
import com.UserOperations;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheckAuthorizationUserWhoWasRegisteredTest {
    private UserOperations userOperations;
    private UserForCheckAuthorization userForCheckAuthorization;

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
    public void checkAuthorizationUserWhoWasRegisteredTest() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        Response userRegistrationResponse = userOperations.create(userForRegistration);
        Response userAuthorizationResponse = userOperations.userLogIn(UserForCheckAuthorization.from(userForRegistration));

        Assert.assertEquals("User can not log in", 200, userAuthorizationResponse.statusCode());
        Assert.assertEquals("Wrong error message", "true", userAuthorizationResponse.jsonPath().getString("success"));
    }
}
