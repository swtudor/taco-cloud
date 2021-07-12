package com.example.tacocloud.web;

import com.example.tacocloud.data.IngredientRepository;
import com.example.tacocloud.data.TacoRepository;
import com.example.tacocloud.domain.Ingredient;
import com.example.tacocloud.domain.Ingredient.Type;
import com.example.tacocloud.domain.Order;
import com.example.tacocloud.domain.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository){
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    @ModelAttribute(name="order")
    public Order order(){ return new Order();}

    @ModelAttribute(name="taco")
    public Taco taco(){return new Taco();}

    @GetMapping
    public String showDesignForm(Model model){
        getIngredients(model);
        model.addAttribute("design", new Taco());
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco taco, Errors error, @ModelAttribute Order order){
        if(error.hasErrors()){
            log.info("BOOOOOOOOO - your taco order failed" );
            return "design";
        }
        Taco saved = tacoRepository.save(taco);
        order.addTaco(saved);

        log.info("Processing taco design for " + taco.getName());
        return "redirect:/orders/current";
    }

    private Model getIngredients(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        Type[] types = Ingredient.Type.values();
        for(Type type :types){
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients, type));
        }
        return model;
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
        return ingredients.stream().filter(x-> x.getType().equals(type)).collect(Collectors.toList());
    }

}
