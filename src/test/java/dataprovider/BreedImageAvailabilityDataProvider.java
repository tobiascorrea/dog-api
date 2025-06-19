package dataprovider;

import org.testng.annotations.DataProvider;

public class BreedImageAvailabilityDataProvider {

    @DataProvider(name = "breedAndLargeCount")
    public static Object[][] breedAndLargeCount() {
        return new Object[][] {
                {"affenpinscher", 50},
                {"bluetick", 50},
                {"clumber", 50},
                {"eskimo", 50},
                {"otterhound", 50}
        };
    }
}
