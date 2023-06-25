package hac.controllers;

import hac.Services.IngredientServices;
import hac.Services.MenuItemServices;
import hac.Services.MenuServices;
import hac.repo.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


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
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/")
    public String index(Model model) {
        System.out.println("hello");
        Menu menu = new Menu();
        model.addAttribute("greeting","domo arigato mr.robot!");
        model.addAttribute("menu", menu);

        return "index";
    }
    @PostMapping("/showItems")
    public String indexPost(@RequestParam("menu_id") Long menu_id,
                            Model model){

        try{
            System.out.println("we got here eeeeeeeeeee");
            Menu menu = menuService.getMenu((menu_id)).get();
            System.out.println("the menu has this many items in it: " + menu.getMenuItems().size());
            System.out.println("we got this menu id: " + menu_id + "and we got this menu: " + menu.getMenu_id());
            model.addAttribute("menu", menu);
            model.addAttribute("menu_id",menu_id);
            model.addAttribute("greeting", "item successfully added");
            return "index";
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        model.addAttribute("menu", new Menu());
        model.addAttribute("menu_id",menu_id);
        model.addAttribute("greeting", "item successfully added");
        return "index";
    }
    @GetMapping("/admin/menu-editor")
    public String openMenuEditor(Model model, Principal principal)
    {
        System.out.println("get menu editor");
        Menu menu = new Menu();
        MenuItem item = new MenuItem();
        menu.addItem(item);
        model.addAttribute("menu", menu);
        model.addAttribute("menuItem",item);
        model.addAttribute("menu_id", menu.getMenu_id());
        return "admin/menu-editor";
    }

    @PostMapping("/validateMenuItem")
    public String BuildMenuItem(@Valid MenuItem menuItem,BindingResult itemResult,
                                @Valid Menu menu, BindingResult menuResult,
                                @RequestParam("menu_id") Long menu_id, BindingResult menuIdResult,
                                Model model)
    {

        if(menuResult.hasErrors())
        {
            System.out.println("validation error: " + menuResult.getAllErrors());
            model.addAttribute("menu", menu);
            model.addAttribute("menu_id", menu.getMenu_id());
            model.addAttribute("menuItem",menuItem);
            return "admin/menu-editor";
        }
        if(menuIdResult.hasErrors())
        {
            System.out.println("validation error: " + menuResult.getAllErrors());
            model.addAttribute("menu", menu);
            model.addAttribute("menu_id", menu.getMenu_id());
            model.addAttribute("menuItem",menuItem);
            return "admin/menu-editor";
        }
        if(itemResult.hasErrors())
        {
            System.out.println("validation errors: " + itemResult.getAllErrors());
            model.addAttribute("menu", menu);
            model.addAttribute("menu_id", menu.getMenu_id());
            model.addAttribute("menuItem",menuItem);
            return "admin/menu-editor";
        }
        try{
            if(menuService.getMenu((menu_id)).isEmpty()) {
                menu.setMenu_id(menu_id);
                menuService.saveMenu(menu);
            } else
                menu = menuService.getMenu((menu_id)).get();

            // Add the new item to the menu's item list and save the menu
            menu.addItem(menuItem);
            menuItem.setMenu(menu);
            menuItemService.saveMenuItem(menuItem);
            menuService.saveMenu(menu);

            System.out.println("saved to menu: " + menu.getMenu_id());
            model.addAttribute("menu", menu);
            model.addAttribute("menu_id", menu.getMenu_id());
            model.addAttribute("menuItem", menuItem);
            return "admin/menu-editor";
        }catch(Exception e){
            System.out.println("error validating new items: " +e.getMessage());
        }
        //=========================================================================================
        for(MenuItem item : menu.getMenuItems())
            System.out.println("item name is: "+ item.getMenuItemName());
        //=========================================================================================
        System.out.println("2 "+menuItem.getMenuItemName());
        model.addAttribute("menu", menu);
        model.addAttribute("menu_id", menu.getMenu_id());
        model.addAttribute("menuItem",menuItem);
        return "admin/menu-editor";
    }

}
