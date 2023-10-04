import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initialize Mockito
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }


    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {

        Restaurant foundRestaurant = service.findRestaurantByName("Amelie's cafe");
        // Assert that the expected restaurant is found
        assertEquals("Amelie's cafe", foundRestaurant.getName());
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {

        assertThrows(restaurantNotFoundException.class, () -> {
            service.findRestaurantByName("Pantry d'or");
        });
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants - 1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {

        assertThrows(restaurantNotFoundException.class, () -> service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1() {

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales", "Chennai", LocalTime.parse("12:00:00"), LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1, service.getRestaurants().size());
    }


    //TDD Approach
    @Test
    public void testAddItemWithPriceToRestaurant() {
        // Add an item with a price to the restaurant
        service.addItemWithPrice(restaurant, "sizzling brownie", 319);

        // Retrieve the restaurant's menu and check if the item and price are included
        List<Item> menu = restaurant.getMenu();
        boolean foundItem = false;

        for (Item item : menu) {
            String itemName = item.getName();
            int itemPrice = item.getPrice();

            if (itemName.equals("sizzling brownie") && itemPrice == 319) {
                foundItem = true;
                break; // Exit the loop once the item is found
            }
        }
        assertTrue(foundItem);
    }

    @Test
    public void testCalculateOrderValue() {
        // Add items with prices to the restaurant
        service.addItemWithPrice(restaurant, "sweet corn soup", 119);
        service.addItemWithPrice(restaurant, "vegetable lasagne", 269);


        // Calculate the order value for a list of selected items
         int orderValue = service.calculateOrderValue(new String[]{"sweet corn soup", "vegetable lasagne"});

        // Verify that the order value is as expected
        // (sweet corn soup: 119 + vegetable lasagne: 269 = 388)
         assertEquals(388, orderValue);
    }


    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>
}