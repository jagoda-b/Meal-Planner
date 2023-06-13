package mealplanner.Meal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Meal {
    private final String category;
    private final String name;
    private final List<String> ingredients;

    public Meal(MealBuilder mealBuilder){
        this.category = mealBuilder.category;
        this.name = mealBuilder.name;
        this.ingredients = mealBuilder.ingredients;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getIngredients() {
        return new ArrayList<>(ingredients);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category: ").append(category).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Ingredients:").append("\n");

        for (String ingredient : ingredients) {
            sb.append(ingredient.strip()).append("\n");
        }

        return sb.toString();
    }

    public static class MealBuilder {
        private String category;
        private String name;
        private List<String> ingredients = new ArrayList<>();

        public Meal build() {
            return new Meal(this);
        }

        public MealBuilder addCategory(String type) {
            this.category = type;
            return this;
        }

        public MealBuilder addName(String name) {
            this.name = name;
            return this;
        }

        public MealBuilder addIngredients(String ingredients) {
            this.ingredients = Arrays.stream(ingredients.split(",")).toList();
            return this;
        }

        
    }


}
