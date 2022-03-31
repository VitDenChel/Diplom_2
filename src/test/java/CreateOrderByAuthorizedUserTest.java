import com.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateOrderByAuthorizedUserTest {
    private OrderOperations orderOperations;
    private UserOperations userOperations;
    private IngredientsForOrder ingredientsForOrder;

    @Before
    public void setUp() {
        orderOperations = new OrderOperations();
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
    public void newOrderWasCreatedByAuthorizedUserTest() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        IngredientsForOrder ingredientsForOrder = IngredientsForOrder.IngredientsForOrder();
        Response userRegistrationResponse = userOperations.create(userForRegistration);
        String TokenUser = userRegistrationResponse.jsonPath().get("accessToken");
        Response userAuthorizationResponse = userOperations.userLogIn(UserForCheckAuthorization.from(userForRegistration));
        Response checkCreateOrderResponse = orderOperations.createOrderWithIngredients(ingredientsForOrder);

        Assert.assertEquals("Order was not created", 200, checkCreateOrderResponse.statusCode());
        Assert.assertEquals("Wrong error message", "true", checkCreateOrderResponse.jsonPath().getString("success"));
    }

    @Test
    public void newOrderWasCreatedByAuthorizedUserWithoutListIngredientsTest() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        Response userRegistrationResponse = userOperations.create(userForRegistration);
        String TokenUser = userRegistrationResponse.jsonPath().get("accessToken");
        Response userAuthorizationResponse = userOperations.userLogIn(UserForCheckAuthorization.from(userForRegistration));
        Response orderAuthorizedUserWithoutIngredientsResponse = orderOperations.createOrder();

        Assert.assertEquals("Order without ingredients was created", 400, orderAuthorizedUserWithoutIngredientsResponse.statusCode());
        Assert.assertEquals("Wrong error message", "false", orderAuthorizedUserWithoutIngredientsResponse.jsonPath().getString("success"));
        Assert.assertEquals("Wrong error message", "Ingredient ids must be provided", orderAuthorizedUserWithoutIngredientsResponse.jsonPath().getString("message"));
    }

    @Test
    public void newOrderWasCreatedByAuthorizedUserAndIncorrectHashIngredientTest() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        IngredientsForOrderIncorrectHash ingredientsForOrderIncorrectHash = IngredientsForOrderIncorrectHash.IngredientsForOrderIncorrectHash();
        Response userRegistrationResponse = userOperations.create(userForRegistration);
        String TokenUser = userRegistrationResponse.jsonPath().get("accessToken");
        Response userAuthorizationResponse = userOperations.userLogIn(UserForCheckAuthorization.from(userForRegistration));
        Response checkCreateOrderResponse = orderOperations.createOrderWithInccorectHashIngredients(ingredientsForOrderIncorrectHash);

        Assert.assertEquals("Order was created", 500, checkCreateOrderResponse.statusCode());
    }
}
