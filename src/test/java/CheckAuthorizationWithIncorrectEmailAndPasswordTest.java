import com.CreateUserForAuthorization;
import com.UserOperations;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheckAuthorizationWithIncorrectEmailAndPasswordTest {
    private UserOperations userOperations;

    @Before
    public void setUp() {
        userOperations = new UserOperations();
    }

    @Test
    public void checkAuthorizationWithIncorrectEmailAndPasswordTest() {
        CreateUserForAuthorization createUserForAuthorization = new CreateUserForAuthorization("Alex@yandex.ru", "Petrov123");
        Response createUserForAuthorizationResponse = userOperations.userWithIncorrectDataLogin(createUserForAuthorization);

        Assert.assertEquals("User can log in", 401, createUserForAuthorizationResponse.statusCode());
        Assert.assertEquals("Wrong error message", "false", createUserForAuthorizationResponse.jsonPath().getString("success"));
        Assert.assertEquals("Wrong error message", "email or password are incorrect", createUserForAuthorizationResponse.jsonPath().getString("message"));
    }
}
