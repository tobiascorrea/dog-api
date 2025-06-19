package dataprovider;

import org.testng.annotations.DataProvider;

public class ImageCountDataProvider {

    // Usado para cenários que só precisam validar COUNT (sem se preocupar com breed)
    @DataProvider(name = "validCounts")
    public static Object[][] validCounts() {
        return new Object[][]{
                {1},
                {10},
                {25},
                {50}
        };
    }

    @DataProvider(name = "invalidCounts")
    public static Object[][] invalidCounts() {
        return new Object[][]{
                {-1},
                {0},
                {51},
                {100}
        };
    }

    // NOVO: Cenário com combinação de breed + count (para múltiplas imagens por raça)
    @DataProvider(name = "validBreedAndCountCombinations")
    public static Object[][] validBreedAndCountCombinations() {
        return new Object[][]{
                {"hound", 1},
                {"pug", 3},
                {"akita", 10},
                {"retriever", 25},
                {"boxer", 50}
        };
    }
}
