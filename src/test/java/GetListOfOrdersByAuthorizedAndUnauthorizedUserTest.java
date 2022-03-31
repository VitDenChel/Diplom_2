import com.OrderOperations;
import com.UserForCheckAuthorization;
import com.UserForRegistration;
import com.UserOperations;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetListOfOrdersByAuthorizedAndUnauthorizedUserTest {
    private UserOperations userOperations;
    private UserForCheckAuthorization userForCheckAuthorization;
    private OrderOperations orderOperations;

    @Before
    public void setUp() {
        userOperations = new UserOperations();
        orderOperations = new OrderOperations();
    }

    @After
    public void tearDown() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        Response createUniqueUserResponse = userOperations.create(userForRegistration);
        String TokenUser = createUniqueUserResponse.jsonPath().get("accessToken");
        userOperations.delete();
    }

    @Test
    public void getListOfOrdersAuthorizedUserTest() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        Response userRegistrationResponse = userOperations.create(userForRegistration);
        String TokenUser = userRegistrationResponse.jsonPath().get("accessToken");
        Response userAuthorizationResponse = userOperations.userLogIn(UserForCheckAuthorization.from(userForRegistration));
        Response userListOfOrdersResponse = orderOperations.getListOrdersAuthorizedUser(TokenUser);

        Assert.assertEquals("List of orders can not be received", 200, userListOfOrdersResponse.statusCode());
        Assert.assertEquals("Wrong error message", "true", userListOfOrdersResponse.jsonPath().getString("success"));
    }

    @Test
    public void checkAuthorizationUserWhoWasNotAuthorizedTest() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        Response userRegistrationResponse = userOperations.create(userForRegistration);
        Response getListOrdersUserResponse = orderOperations.getListOrdersRegistratedUser();

        Assert.assertEquals("List of orders can be received", 401, getListOrdersUserResponse.statusCode());
        Assert.assertEquals("Wrong error message", "false", getListOrdersUserResponse.jsonPath().getString("success"));
    }
}
