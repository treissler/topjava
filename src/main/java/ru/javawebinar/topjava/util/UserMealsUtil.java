package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();
        meals.forEach(meal -> caloriesPerDayMap.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));

        List<UserMealWithExcess> userMealsFiltered = new ArrayList<>();
        meals.forEach(meal -> {
            if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                UserMealWithExcess mealFiltered = new UserMealWithExcess(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesPerDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay);
                userMealsFiltered.add(mealFiltered);
            }
        });
        return userMealsFiltered;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayMap = meals.stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        List<UserMealWithExcess> userMealsFiltered = meals.stream()
                .filter(meal -> isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesPerDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
        return userMealsFiltered;
    }

    public static List<UserMealWithExcess> filteredByStreamsAndCollectors(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .collect(collectMealsWithExcess(startTime, endTime, caloriesPerDay));
    }

    public static Collector<UserMeal, ?, List<UserMealWithExcess>> collectMealsWithExcess(LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        class CollectorState {
            public final List<UserMeal> userMeals = new ArrayList<>();
            public final HashMap<LocalDate, Integer> caloriesTotalPerDay = new HashMap<>();
        }
        return Collector.of(
                CollectorState::new,
                (state, userMeal) -> {
                    state.userMeals.add(userMeal);
                    state.caloriesTotalPerDay.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);
                },
                (state1, state2) -> {
                    state2.caloriesTotalPerDay.forEach((k, v) -> state1.caloriesTotalPerDay.merge(k, v, Integer::sum));
                    state1.userMeals.addAll(state2.userMeals);
                    return state1;
                },
                state -> state.userMeals.stream()
                        .filter(meal -> isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                        .map(meal -> new UserMealWithExcess(meal.getDateTime(),
                                meal.getDescription(),
                                meal.getCalories(),
                                state.caloriesTotalPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay
                        )).collect(Collectors.toList()));
    }

}
