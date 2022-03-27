import com.UserForCheckAuthorization;
import com.UserForRegistration;
import com.UserOperations;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheckChangeListDataUserWithAuthorizationStepTest {
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
    public void checkChangeDataUserWithAuthorizationStepTest() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        Response userRegistrationResponse = userOperations.create(userForRegistration);
        String TokenUser = userRegistrationResponse.jsonPath().get("accessToken");
        Response userAuthorizationResponse = userOperations.userLogIn(UserForCheckAuthorization.from(userForRegistration));
        Response getDataRegisteredUser = userOperations.getDataRegistratedUser();
        Response getModifiedDataRegisteredUser = userOperations.changeDataRegistratedUser(TokenUser, userForRegistration);

        Assert.assertEquals("Data user have not been changed", 200, getModifiedDataRegisteredUser.statusCode());
        Assert.assertEquals("Wrong error message", "true", userRegistrationResponse.jsonPath().getString("success"));
    }
}