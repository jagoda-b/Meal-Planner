package mealplanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Meal {
    private final String type;
    private final String name;
    private final List<String> ingredients;

    public Meal(MealBuilder mealBuilder){
        this.type = mealBuilder.type;
        this.name = mealBuilder.name;
        this.ingredients = mealBuilder.ingredients;
    }

    public String getType() {
        return type;
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
        sb.append("Category: ").append(type).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Ingredients:").append("\n");

        for (String ingredient : ingredients) {
            sb.append(ingredient).append("\n");
        }

        return sb.toString();
    }

    public static class MealBuilder {
        private String type;
        private String name;
        private List<String> ingredients = new ArrayList<>();

        public Meal build() {
            return new Meal(this);
        }

        public MealBuilder addType(String type) {
            this.type = type;
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
