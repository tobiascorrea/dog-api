package dataprovider;

import org.testng.annotations.DataProvider;

public class SubBreedImageDataProvider {

    @DataProvider(name = "validSubBreedCombinations")
    public static Object[][] validSubBreedCombinations() {
        return new Object[][] {
                {"hound", "afghan"},
                {"hound", "basset"},
                {"bulldog", "english"},
                {"bulldog", "french"},
                {"bulldog", "boston"},
                {"mastiff", "bull"},
                {"mastiff", "english"},
                {"retriever", "golden"},
                {"terrier", "american"},
                {"spaniel", "blenheim"}
        };
    }

    @DataProvider(name = "invalidSubBreedCombinations")
    public static Object[][] invalidSubBreedCombinations() {
        return new Object[][] {
                {"pug", "golden"},     // pug não tem sub-raça
                {"hound", "fake"},
                {"xyz", "abc"}
        };
    }
}
