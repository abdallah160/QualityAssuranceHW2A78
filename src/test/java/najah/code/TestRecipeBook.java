package najah.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TestRecipeBook {

    @Test
    @DisplayName("recipe array starts with 4 spots")
    void testGetRecipes() {
        RecipeBook recipeBook = new RecipeBook();

        assertEquals(4, recipeBook.getRecipes().length);
        assertNull(recipeBook.getRecipes()[0]);
    }

    @Test
    @DisplayName("addRecipe should add a new recipe")
    void testAddRecipeValid() {
        RecipeBook recipeBook = new RecipeBook();
        Recipe recipe = makeRecipe("Latte");

        assertTrue(recipeBook.addRecipe(recipe));
        assertEquals("Latte", recipeBook.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("addRecipe should not add duplicate recipe")
    void testAddRecipeInvalid() {
        RecipeBook recipeBook = new RecipeBook();
        Recipe recipe1 = makeRecipe("Mocha");
        Recipe recipe2 = makeRecipe("Mocha");

        assertTrue(recipeBook.addRecipe(recipe1));
        assertFalse(recipeBook.addRecipe(recipe2));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @DisplayName("deleteRecipe should return deleted recipe name")
    void testDeleteRecipeValid(int index) {
        RecipeBook recipeBook = new RecipeBook();
        for (int i = 0; i < index; i++) {
            recipeBook.addRecipe(makeRecipe("Fill" + i));
        }
        Recipe recipe = makeRecipe("Tea" + index);
        recipeBook.addRecipe(recipe);

        assertEquals("Tea" + index, recipeBook.deleteRecipe(index));
        assertEquals("", recipeBook.getRecipes()[index].getName());
    }

    @Test
    @DisplayName("deleteRecipe should return null when place is empty")
    void testDeleteRecipeInvalid() {
        RecipeBook recipeBook = new RecipeBook();

        assertNull(recipeBook.deleteRecipe(0));
        assertEquals(4, recipeBook.getRecipes().length);
    }

    @Test
    @DisplayName("editRecipe should return old name and store new recipe")
    void testEditRecipeValid() {
        RecipeBook recipeBook = new RecipeBook();
        Recipe oldRecipe = makeRecipe("Espresso");
        Recipe newRecipe = makeRecipe("Cappuccino");
        recipeBook.addRecipe(oldRecipe);

        assertEquals("Espresso", recipeBook.editRecipe(0, newRecipe));
        assertEquals("", recipeBook.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("editRecipe should return null when place is empty")
    void testEditRecipeInvalid() {
        RecipeBook recipeBook = new RecipeBook();
        Recipe newRecipe = makeRecipe("Hot Chocolate");

        assertNull(recipeBook.editRecipe(1, newRecipe));
        assertEquals(0, recipeBook.getRecipes()[1] == null ? 0 : 1);
    }

    @Test
    @DisplayName("recipe book timeout test")
    void testTimeout() {
        RecipeBook recipeBook = new RecipeBook();
        Recipe recipe = makeRecipe("Fast");

        assertTimeout(Duration.ofMillis(100), () -> recipeBook.addRecipe(recipe));
        assertTimeout(Duration.ofMillis(100), recipeBook::getRecipes);
    }

    private Recipe makeRecipe(String name) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        return recipe;
    }
}
