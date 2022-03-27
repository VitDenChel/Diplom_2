import com.UserHasEmailAndName;
import com.UserOperations;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheckUserRegistrarionWithoutPasswordTest {
    private UserOperations userOperations;


    @Before
    public void setUp() {
        userOperations = new UserOperations();
    }

    @Test
    public void checkUserRegistrationWithoutPassword() {
        UserHasEmailAndName userHasEmailAndName = UserHasEmailAndName.getRandom();
        Response createUserWithoutPasswordResponse = userOperations.createUserWithoutPassword(userHasEmailAndName);

        Assert.assertEquals("User can be created without password", 403, createUserWithoutPasswordResponse.statusCode());
        Assert.assertEquals("Wrong error message", "false", createUserWithoutPasswordResponse.jsonPath().getString("success"));
        Assert.assertEquals("Wrong error message", "Email, password and name are required fields", createUserWithoutPasswordResponse.jsonPath().getString("message"));
    }
}
