import com.UserForCheckAuthorization;
import com.UserForRegistration;
import com.UserOperations;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetListOfOrdersUnauthorizedUserTest {
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
    public void checkAuthorizationUserWhoWasNotAuthorizedTest() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        Response userRegistrationResponse = userOperations.create(userForRegistration);
        Response getListOrdersUserResponse = userOperations.getListOrdersRegistratedUser();

        Assert.assertEquals("List of orders can be received", 401, getListOrdersUserResponse.statusCode());
        Assert.assertEquals("Wrong error message", "false", getListOrdersUserResponse.jsonPath().getString("success"));
    }
}
