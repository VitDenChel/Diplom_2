import com.UserHasPasswordAndName;
import com.UserOperations;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheckUserRegistrationWithoutEmailTest {
    private UserOperations userOperations;


    @Before
    public void setUp() {
        userOperations = new UserOperations();
    }

    @Test
    public void checkUserRegistrationWithoutEmail() {
        UserHasPasswordAndName userHasPasswordAndName = UserHasPasswordAndName.getRandom();
        Response createUserWithoutEmailResponse = userOperations.createUserWithoutEmail(userHasPasswordAndName);

        Assert.assertEquals("User can be created without Email", 403, createUserWithoutEmailResponse.statusCode());
        Assert.assertEquals("Wrong error message", "false", createUserWithoutEmailResponse.jsonPath().getString("success"));
        Assert.assertEquals("Wrong error message", "Email, password and name are required fields", createUserWithoutEmailResponse.jsonPath().getString("message"));
    }
}

