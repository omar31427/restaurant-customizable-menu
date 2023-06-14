package hac.controllers;

import hac.Services.IngredientServices;
import hac.Services.MenuItemServices;
import hac.Services.MenuServices;
import hac.Services.UserServices;
import hac.repo.Ingredient;
import hac.repo.Menu;
import hac.repo.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** this is a test controller, delete/replace it when you start working on your project */
@Controller
public class RestaurantController {

    @Autowired
    private IngredientServices ingredientService;
    @Autowired
    private MenuItemServices menuItemService;
    @Autowired
    private MenuServices menuService;
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("greeting", "Hello World");
        return "index";
    }
    @PostMapping("/Build-Menu")
    public String BuildMenu(@RequestParam Menu newMenu, @RequestParam MenuItem newMenuItem,
                            @RequestParam Ingredient newIngredient, Model model){

        try {
            menuService.saveMenu(newMenu);
            menuItemService.saveMenuItem(newMenuItem);
            ingredientService.saveIngredient(newIngredient);
            model.addAttribute("Menu", new Menu());
            model.addAttribute("MenuItem", new MenuItem());
            model.addAttribute("Ingredient", new Ingredient());
        } catch (Exception e) {
            //model.addAttribute("message", "Sorry we could not perform your request!");
        }

        model.addAttribute("Menu", new Menu());
        model.addAttribute("MenuItem", new MenuItem());
        model.addAttribute("Ingredient", new Ingredient());
        return "menu-editor";
    }

}
