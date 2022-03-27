import com.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateOrderUnauthorizedUserTest {
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
    public void newOrderWasCreatedUnAuthorizedUserTest() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        IngredientsForOrder ingredientsForOrder = IngredientsForOrder.IngredientsForOrder();
        Response userRegistrationResponse = userOperations.create(userForRegistration);
        Response checkCreateOrderResponse = userOperations.createOrderWithIngredients(ingredientsForOrder);

        Assert.assertEquals("Order was created without authorization", 404, checkCreateOrderResponse.statusCode());
        Assert.assertEquals("Wrong error message", "false", checkCreateOrderResponse.jsonPath().getString("success"));

    }

    @Test
    public void newOrderWasCreatedByUnauthorizedUserWithoutIngredientsTest() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        Response userRegistrationResponse = userOperations.create(userForRegistration);
        Response orderByUnauthorizedUserWithoutIngredients =  userOperations.createOrder();

        Assert.assertEquals("Two equal users can be created", 400, orderByUnauthorizedUserWithoutIngredients.statusCode());
        Assert.assertEquals("Wrong error message", "false", orderByUnauthorizedUserWithoutIngredients.jsonPath().getString("success"));
        Assert.assertEquals("Wrong error message", "Ingredient ids must be provided", orderByUnauthorizedUserWithoutIngredients.jsonPath().getString("message"));
    }

    @Test
    public void newOrderWasCreatedByUnauthorizedAndIncorrectHashIngredientTest() {
        UserForRegistration userForRegistration = UserForRegistration.getRandom();
        IngredientsForOrderIncorrectHash ingredientsForOrderIncorrectHash = IngredientsForOrderIncorrectHash.IngredientsForOrderIncorrectHash();
        Response userRegistrationResponse = userOperations.create(userForRegistration);
        Response checkCreateOrderResponse = userOperations.createOrderWithInccorectHashIngredients(ingredientsForOrderIncorrectHash);

        Assert.assertEquals("Order was created", 500, checkCreateOrderResponse.statusCode());
    }
}
