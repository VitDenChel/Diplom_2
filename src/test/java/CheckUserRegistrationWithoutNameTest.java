import com.UserHasEmailAndPassword;
import com.UserOperations;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheckUserRegistrationWithoutNameTest {
    private UserOperations userOperations;


    @Before
    public void setUp() {
        userOperations = new UserOperations();
    }

    @Test
    public void checkUserRegistrationWithoutName() {
        UserHasEmailAndPassword userHasEmailAndPassword = UserHasEmailAndPassword.getRandom();
        Response createUserWithoutNameResponse = userOperations.createUserWithoutName(userHasEmailAndPassword);

        Assert.assertEquals("User can be created without password", 403, createUserWithoutNameResponse.statusCode());
        Assert.assertEquals("Wrong error message", "false", createUserWithoutNameResponse.jsonPath().getString("success"));
        Assert.assertEquals("Wrong error message", "Email, password and name are required fields", createUserWithoutNameResponse.jsonPath().getString("message"));
    }
}
