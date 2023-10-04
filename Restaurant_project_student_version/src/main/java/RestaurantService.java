import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant findRestaurantByName(String restaurantName)throws restaurantNotFoundException {

        //UPDATED
        for (Restaurant restaurant : restaurants) {
            if (restaurantName.equalsIgnoreCase(restaurant.getName())) {
                return restaurant;
            }
        }
        throw new restaurantNotFoundException("Restaurant NOT Found");
    }


    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }


    public void addItemWithPrice(Restaurant restaurant, String itemName, int price) {
        //code
        List<Item> menu = restaurant.getMenu();
        Item newItem = new Item(itemName, price);
        menu.add(newItem);
    }

    public int calculateOrderValue(String[] itemNames) {
        //code
        int orderValue = 0;

        for (String itemName : itemNames) {
            int itemTotalPrice = restaurants.stream()
                    .flatMap(restaurant -> restaurant.getMenu().stream())
                    .filter(item -> item.getName().equals(itemName))
                    .mapToInt(Item::getPrice)
                    .sum();

            orderValue += itemTotalPrice;
        }

        return orderValue;
    }


}
