package dataprovider;

import org.testng.annotations.DataProvider;

public class SubBreedImageCountDataProvider {

    @DataProvider(name = "validSubBreedWithCounts")
    public static Object[][] validSubBreedWithCounts() {
        return new Object[][]{
                {"hound", "afghan", 1},
                {"hound", "afghan", 3},
                {"bulldog", "english", 5},
                {"mastiff", "bull", 10},
                {"retriever", "golden", 20},
                {"spaniel", "cocker", 50},
        };
    }

    @DataProvider(name = "invalidSubBreedWithCounts")
    public static Object[][] invalidSubBreedWithCounts() {
        return new Object[][]{
                {"hound", "invalidSub", 3},
                {"invalidBreed", "afghan", 5},
                {"retriever", "unknown", 10},
        };
    }

    @DataProvider(name = "extremeCounts")
    public static Object[][] extremeCounts() {
        return new Object[][]{
                {"hound", "afghan", 0},     // limite inferior
                {"hound", "afghan", 100},   // superior
                {"hound", "afghan", 999},   // estresse
        };
    }
}
