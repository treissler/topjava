package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService service;

    public Collection<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public MealTo get(int id) {
        log.info("get {}", id);
        Meal meal = service.get(id, SecurityUtil.authUserId());
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), false);
    }

    public MealTo create(MealTo mealTo) {
        log.info("create {}", mealTo);
        Meal meal = new Meal(mealTo.getId(), SecurityUtil.authUserId(), mealTo.getDateTime(), mealTo.getDescription(), mealTo.getCalories());
        checkNew(meal);
        service.create(meal);
        return mealTo;
    }

    public void delete(int id) {
        log.info("delete {}", id, " ");
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(MealTo mealTo, int id) {
        log.info("update {} with id={}", id);
        Meal meal = new Meal(mealTo.getId(), SecurityUtil.authUserId(), mealTo.getDateTime(), mealTo.getDescription(), mealTo.getCalories());
        assureIdConsistent(meal, id);
        service.update(meal);
    }

}