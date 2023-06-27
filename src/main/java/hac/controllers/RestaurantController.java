package hac.controllers;

import hac.Services.IngredientServices;
import hac.Services.MenuItemServices;
import hac.Services.MenuServices;
import hac.repo.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    @Resource(name = "sessionBean")
    private ArrayList<CartItem> cartItems;

    @GetMapping("/login")
    public String login(Model model)
    {

        return "login";
    }
    @RequestMapping("/")
    public String index(Model model) {
        System.out.println("hello");
        Menu menu = new Menu();
        model.addAttribute("menuItems",menu.getMenuItems());
        model.addAttribute("greeting","domo arigato mr.robot!");
        model.addAttribute("menu", menu);
        ArrayList<Menu> menus = (ArrayList<Menu>) menuService.getAllMenus();
        model.addAttribute("menus",menus);
        return "index";
    }
    @GetMapping("/openMenu")
    public String getMenuItems(@RequestParam("menuId") Long menuId, Model model) {
        // Retrieve the menu items based on the menuId
        Menu menu = menuService.getMenu(menuId).get();
        List<MenuItem> menuItems = menuService.getMenuItemsByMenu(menu);
        System.out.println("we are openning menu");
        model.addAttribute("menuItems",menuItems);
        model.addAttribute("greeting","domo arigato mr.robot!");
        model.addAttribute("menu", menu);
        ArrayList<Menu> menus = (ArrayList<Menu>) menuService.getAllMenus();
        model.addAttribute("menus",menus);

        return "index"; // Thymeleaf template to display the menu items
    }
    @GetMapping("/shared/addToCart")
    public String addToCart(@RequestParam("itemId") Long itemId) {
        MenuItem newItem = menuItemService.getMenuItem(itemId).get();
        boolean itemFound = false;
        for (CartItem cartItem : cartItems) {
            if (Objects.equals(cartItem.getItem().getItem_id(), newItem.getItem_id()))
            {
                cartItem.increaseAmount();
                itemFound = true;
            }
        }
        if(!itemFound)
            cartItems.add(new CartItem(newItem,1));
        for (CartItem cartItem : cartItems)
            System.out.println("item name: "+ cartItem.getItem().getMenuItemName() + "item amount: " + cartItem.getAmount());
        return "redirect:/";
    }
    @PostMapping("/shared/cart")
    public String openCart(Model model){

        try {
            model.addAttribute("item",new MenuItem());
            model.addAttribute("amount",0);
            model.addAttribute("cartItems",cartItems);
            return "shared/cart";
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        model.addAttribute("cartItems",cartItems);
        return "shared/cart";
    }
    @PostMapping("/admin/decreaseQuantity")
    public String decreaseCartItem(@RequestParam("menuItem_id") Long menuItem_id,
                                   Model model){
        try{
            for (CartItem cartItem : cartItems)
                System.out.println("item id: " + cartItem.getItem().getItem_id());

            System.out.println("returned item id: " + menuItem_id);
            for(CartItem cartItem:cartItems)
            {
                if(cartItem.getItem().getItem_id() == menuItem_id)
                   cartItem.decreaseAmount();
            }

            model.addAttribute("item",new MenuItem());
            model.addAttribute("amount",0);
            model.addAttribute("cartItems",cartItems);
            return "shared/cart";
        }catch(Exception e){
            System.out.println("messge in remove from cart" + e.getMessage());
        }
        model.addAttribute("item",new MenuItem());
        model.addAttribute("amount",0);
        model.addAttribute("cartItems",cartItems);
        return "shared/cart";
    }
    @PostMapping("/admin/increaseQuantity")
    public String increaseCartItem(@RequestParam("menuItem_id") Long menuItem_id,
                                   Model model){
        try{
            for (CartItem cartItem : cartItems)
                System.out.println("item id: " + cartItem.getItem().getItem_id());

            System.out.println("returned item id: " + menuItem_id);
            for(CartItem cartItem:cartItems)
            {
                if(cartItem.getItem().getItem_id() == menuItem_id)
                    cartItem.increaseAmount();
            }

            model.addAttribute("item",new MenuItem());
            model.addAttribute("amount",0);
            model.addAttribute("cartItems",cartItems);
            return "shared/cart";
        }catch(Exception e){
            System.out.println("messge in remove from cart" + e.getMessage());
        }
        model.addAttribute("item",new MenuItem());
        model.addAttribute("amount",0);
        model.addAttribute("cartItems",cartItems);
        return "shared/cart";
    }
    @PostMapping("/admin/removeFromCart")
    public String removeFromCart(@RequestParam("menuItem_id") Long menuItem_id,
                                 Model model)
    {
        try{
            for (CartItem cartItem : cartItems)
                System.out.println("item id: " + cartItem.getItem().getItem_id());

            System.out.println("returned item id: " + menuItem_id);
            cartItems.removeIf(cartItem -> cartItem.getItem().getItem_id() == menuItem_id);

            model.addAttribute("item",new MenuItem());
            model.addAttribute("amount",0);
            model.addAttribute("cartItems",cartItems);
            return "shared/cart";
        }catch(Exception e){
            System.out.println("messge in remove from cart" + e.getMessage());
        }
        model.addAttribute("item",new MenuItem());
        model.addAttribute("amount",0);
        model.addAttribute("cartItems",cartItems);
        return "shared/cart";
    }
    @PostMapping("/admin/editMenu")
    public String editMenu( @Valid  Menu menu, BindingResult menuResult,
                            Model model){
        System.out.println("editmenu 1");
        if(menuResult.hasErrors())
        {
            System.out.println("validation error: " + menuResult.getAllErrors());
            model.addAttribute("menu", menu);
            model.addAttribute("menu_id", menu.getMenu_id());
            model.addAttribute("menuItem",new MenuItem());
            return "admin/menu-editor";
        }
        try{
            System.out.println("editmenu 2");
            if(menuService.getMenuByName(menu.getMenuName()).isPresent())
                menu = menuService.getMenuByName(menu.getMenuName()).get();

            System.out.println("validation error: " + menuResult.getAllErrors());
            model.addAttribute("menu", menu);
            model.addAttribute("menu_id", menu.getMenu_id());
            model.addAttribute("menuItem",new MenuItem());
            return "admin/menu-editor";
        }catch(Exception e){
            System.out.println("error in admin/editMenu" + e.getMessage());
        }
        System.out.println("editmenu 3");
        return "admin/menu-editor";
    }
    @PostMapping("/admin/deleteItem")
    public String deleteItem(@RequestParam("menuItem_id") Long menuItem_id,
                             @RequestParam("menu_id") Long menu_id,
                             Model model){
        try{
            Menu menu = menuService.getMenu((menu_id)).get();
            MenuItem item = menuItemService.getMenuItem(menuItem_id).get();
            menu.removeItem(item);

            model.addAttribute("menu", menu);
            model.addAttribute("menu_id", menu.getMenu_id());
            model.addAttribute("menuItem",new MenuItem());
            return "admin/menu-editor";
        }catch(Exception e){
            System.out.println("problem in delete item post: " + e.getMessage());
        }
        System.out.println("u need to add attributes");
        return "admin/menu-editor";
    }
    @PostMapping("/showItems")
    public String indexPost(@RequestParam("menu_id") Long menu_id,
                            Model model , Principal principal){

        try{
            Menu menu = menuService.getMenu((menu_id)).get();

            ArrayList<Menu> menus = (ArrayList<Menu>) menuService.getAllMenus();

            model.addAttribute("menuItems",menu.getMenuItems());
            model.addAttribute("menus",menus);
            model.addAttribute("menu", menu);
            model.addAttribute("menu_id",menu_id);
            model.addAttribute("greeting", "item successfully added");
            return "index";
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        model.addAttribute("menuItems",new ArrayList<>());
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

    @PostMapping("/admin/validateMenuItem")
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
        /*if(menuIdResult.hasErrors())
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
        }*/
        try{

            if(menuService.getMenuByName(menu.getMenuName()).isPresent())
                menu = menuService.getMenuByName(menu.getMenuName()).get();
            else if(menuService.getMenu((menu_id)).isEmpty()) {
                menu.setMenu_id(menu_id);
                menuService.saveMenu(menu);
            } else
                menu = menuService.getMenu((menu_id)).get();

            // Add the new item to the menu's item list and save the menu
            if(!menuService.isItemInMenu(menu,menuItem.getMenuItemName()) && menuItem.getMenuItemPrice() !=0) {
                menu.addItem(menuItem);
                menuItem.setMenu(menu);
                menuItemService.saveMenuItem(menuItem);
            }
            else
            {System.out.println("item found in menu");
                }

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
