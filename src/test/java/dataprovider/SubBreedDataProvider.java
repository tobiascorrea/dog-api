package dataprovider;

import org.testng.annotations.DataProvider;

public class SubBreedDataProvider {

    @DataProvider(name = "breedsForSubBreedValidation")
    public static Object[][] breedsForSubBreedValidation() {
        return new Object[][] {
                {"hound"},         // tem várias sub-raças
                {"bulldog"},       // tem sub-raças: english, french, boston
                {"terrier"},       // tem muitas sub-raças
                {"pug"},           // sem sub-raça
                {"beagle"},        // sem sub-raça
                {"retriever"},     // golden, curly, etc.
                {"spaniel"},       // várias sub-raças
                {"labrador"},      // sem sub-raça
                {"mastiff"},       // english, bull, tibetan...
                {"dane"}           // deve ter 'great'
        };
    }
}
