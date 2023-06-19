package hac.controllers;

import hac.Services.IngredientServices;
import hac.Services.MenuItemServices;
import hac.Services.MenuServices;
import hac.Services.UserServices;
import hac.repo.*;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
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
    @Autowired
    private MenuRepository menuRepo;

    @GetMapping("/")
    public String index(Model model) {

        return "index";
    }
    @GetMapping("/menuEditor")
    public String openMenuEditor( Model model)
    {
        model.addAttribute("formWrapper", new FormWrapper());
       //model.addAttribute("menuItems",menuItemService.getAllItems());
        return "menu-editor";
    }
    @PostMapping("/validateMenu")
    public String BuildMenu(@Valid FormWrapper formWrapper,BindingResult formResult
                            , Model model)
    {
        if(formResult.hasErrors())
        {
            System.out.println("validation error: " + formResult.getAllErrors());
        }
        try {
            model.addAttribute("menu", formWrapper);
            return "menu-editor";
        } catch (Exception e) {
            model.addAttribute("message", "Sorry we could not perform your request!");
        }
        System.out.println("we got to this point 2");
        return "menu-editor";
    }

    @PostMapping("/validateMenuItem")
    public String BuildMenuItem(@Valid FormWrapper formWrapper,BindingResult formResult ,
                                Model model)
    {
        if(formResult.hasErrors())
        {
            System.out.println("validation errors: " + formResult.getAllErrors());
            return "menu-editor";
        }

        try{

            formWrapper.getMenu().addItem(formWrapper.getMenuItem());
            formWrapper.getMenuItem().setMenu(formWrapper.getMenu());
            model.addAttribute("formWrapper",formWrapper);
            return "menu-editor";
        }catch(Exception e){
            ;
        }
        return "menu-editor";
    }

}
