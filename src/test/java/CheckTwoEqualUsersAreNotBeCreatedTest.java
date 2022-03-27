import com.UserForRegistration;
import com.UserOperations;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheckTwoEqualUsersAreNotBeCreatedTest {
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
    public void checkTwoEqualUsersCanNotBeCreated() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        Response createUniqueUserResponse = userOperations.create(userForRegistration);
        Response createSecondUserResponse = userOperations.create(userForRegistration);

        Assert.assertEquals("Two equal users can be created", 403, createSecondUserResponse.statusCode());
        Assert.assertEquals("Wrong error message", "false", createSecondUserResponse.jsonPath().getString("success"));
        Assert.assertEquals("Wrong error message", "User already exists", createSecondUserResponse.jsonPath().getString("message"));
    }
}
