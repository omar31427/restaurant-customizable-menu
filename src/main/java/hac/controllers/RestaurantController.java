package hac.controllers;

import hac.Services.IngredientServices;
import hac.Services.MenuItemServices;
import hac.Services.MenuServices;
import hac.Services.PurchaseServices;
import hac.repo.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


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
    private PurchaseServices purchaseService;
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
        MenuItem menuItem = new MenuItem();
        model.addAttribute("menuItem",menuItem);
        model.addAttribute("menuItems",menu.getMenuItems());
        model.addAttribute("greeting","domo arigato mr.robot!");
        model.addAttribute("menu", menu);
        ArrayList<Menu> menus = (ArrayList<Menu>) menuService.getAllMenus();
        model.addAttribute("menus",menus);
        return "index";
    }
    @GetMapping("/openMenu")
    public String getMenuItems(@RequestParam("menuId") long menuId, Model model) {
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
  /*  @PostMapping("shared/checkout")
    public String openPurchaseModal ()
    {

    }*/


    @GetMapping("/searchByName")
    public String searchByName(@RequestParam("menuItemName") String menuItemName,
                               @RequestParam(value = "vegan", required = false) boolean vegan,
                               @RequestParam(value = "vegetarian", required = false) boolean vegetarian,
                               Model model)
    {

        try{

            List<MenuItem> searchResult = new ArrayList<>();
            if(!vegan&&!vegetarian)
                searchResult = menuItemService.getItemsContaining(menuItemName);
            else
                searchResult = menuItemService.getItemsByNameAndFilters(menuItemName,vegan,vegetarian);


            //model.addAttribute("ingredientName" , " ");
            model.addAttribute("menuItems",searchResult);
            ArrayList<Menu> menus = (ArrayList<Menu>) menuService.getAllMenus();
            model.addAttribute("menuItemName",menuItemName);
            model.addAttribute("menus",menus);
            return "index";
        }   catch(Exception e){
            System.out.println("problem in search by name: " + e.getMessage());
        }
        ArrayList<Menu> menus = (ArrayList<Menu>) menuService.getAllMenus();
        model.addAttribute("menus",menus);
        model.addAttribute("menuItems",new ArrayList<MenuItem>());
        return "index";
    }
    @PostMapping("/searchByIngredient")
    public String searchByIngredient(@RequestParam("ingredientName") String ingredientName,
                                     @RequestParam(value = "vegan", required = false) boolean vegan,
                                     @RequestParam(value = "vegetarian", required = false) boolean vegetarian,
                                     Model model)
    {

        try{
            List<Ingredient> ingredients = ingredientService.getIngredientsByName(ingredientName);

            List<MenuItem> searchResult = new ArrayList<>();
            if(!vegan&&!vegetarian)
                for (Ingredient ing: ingredients)
                    searchResult.addAll(ing.getMenuItems());
            else{
                for (Ingredient ing: ingredients)
                    if(ing.isVegan() == vegan && ing.isVegetarian() == vegetarian)
                        searchResult.addAll(ing.getMenuItems());
            }

            model.addAttribute("menuItems",searchResult);
            ArrayList<Menu> menus = (ArrayList<Menu>) menuService.getAllMenus();
            model.addAttribute("menus",menus);
            return "index";
        }   catch(Exception e){
            System.out.println("problem in search by ingredient: " + e.getMessage());
        }
        return "index";
    }
    @PostMapping("/shared/purchaseComplete")
    public String checkout(@Valid @ModelAttribute("purchase") Purchase purchase,
                           BindingResult purchaseResult,
                           Principal principal,
                           Model model)
    {
        if(purchaseResult.hasErrors()) {
            model.addAttribute("cartItems", cartItems);
            return "shared/cart";
        }
        try {
            System.out.println("purchase name: "+ purchase.getUserName());
            String username = principal.getName();
            purchase.setUserName(username);
            purchaseService.save(purchase);

            model.addAttribute("userName",username);
            model.addAttribute("purchase",new Purchase());
            model.addAttribute("cartItems",cartItems);
            return "shared/cart";
        }catch (Exception e){

            System.out.println("problem in checkout: " + e.getMessage());
        }
        model.addAttribute("cartItems", cartItems);
        return "shared/cart";
    }
    @PostMapping("/admin/globalPurchaseHistory")
    public String globaPurchaseHistory (Model model)
    {
        model.addAttribute("purchases",purchaseService.getAllPurchases());
        return "success";
    }
    @PostMapping("/shared/purchaseHistory")
    public String purchaseHistory(Principal principal, Model model)
    {
        List<Purchase> purchases = new ArrayList<>();
        String username = principal.getName(); // Retrieve the username directly

        for (Purchase purchase : purchaseService.getAllPurchases()) {
            if (purchase.getUserName().equals(username)) {
                purchases.add(purchase);
            }
        }
        model.addAttribute("purchases",purchases);
        return "success";
    }
    @GetMapping("/shared/addToCart")
    public String addToCart(@RequestParam("itemId") long itemId) {
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
    public String openCart(@Valid CartItem cartItem, BindingResult itemResult,
                           Model model){
        if(itemResult.hasErrors())
        {
            model.addAttribute("purchase",new Purchase());
            model.addAttribute("cartItems",cartItems);
            return "shared/cart";
        }
        try {
            model.addAttribute("purchase",new Purchase());
            model.addAttribute("cartItems",cartItems);
            return "shared/cart";
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        model.addAttribute("purchase",new Purchase());
        model.addAttribute("cartItems",cartItems);
        return "shared/cart";
    }
    @PostMapping("/shared/decreaseQuantity")
    public String decreaseCartItem(@RequestParam("menuItem_id") long menuItem_id,
                                   @Valid CartItem cartItem, BindingResult itemResult,
                                   Model model){
        if(itemResult.hasErrors())
        {
            System.out.println("error in amount");
            model.addAttribute("cartItem", cartItem);
            model.addAttribute("item",new MenuItem());
            model.addAttribute("amount",0);
            model.addAttribute("purchase",new Purchase());
            model.addAttribute("cartItems",cartItems);
            return "shared/cart";
        }
        try{
            for(CartItem item:cartItems)
                if(item.getItem().getItem_id() == menuItem_id) {
                    cartItem = item;
                    break;
                }
            if(cartItem.getAmount() < 1)
                cartItem.setAmount(0);
            else
                cartItem.decreaseAmount();

            model.addAttribute("purchase",new Purchase());
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
    @PostMapping("/shared/increaseQuantity")
    public String increaseCartItem(@RequestParam("menuItem_id") long menuItem_id,
                                   @Valid CartItem cartItem, BindingResult itemResult,
                                   Model model){
        if(itemResult.hasErrors())
        {
            System.out.println("error in amount");
            model.addAttribute("cartItem", cartItem);
            model.addAttribute("item",new MenuItem());
            model.addAttribute("amount",0);
            model.addAttribute("purchase",new Purchase());
            model.addAttribute("cartItems",cartItems);
            return "shared/cart";
        }
        try{
            for(CartItem item:cartItems)
                if(item.getItem().getItem_id() == menuItem_id) {
                    cartItem = item;
                    break;
                }
            if(cartItem.getAmount() >99)
                cartItem.setAmount(100);
            else
                cartItem.increaseAmount();

            model.addAttribute("purchase",new Purchase());
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
    @PostMapping("/shared/setQuantity")
    public String setCartItemAmt(@RequestParam("menuItem_id") long menuItem_id,
                                 @RequestParam("amount") Integer amount,
                                   Model model){
        try{

            for(CartItem item:cartItems)
                if(item.getItem().getItem_id() == menuItem_id) {
                    if(item.getAmount() > 99)
                        item.setAmount(100);
                    else if(item.getAmount() < 1)
                        item.setAmount(0);
                    else
                        item.setAmount(amount);

                    break;
                }
            model.addAttribute("purchase",new Purchase());
            model.addAttribute("cartItems",cartItems);
            return "shared/cart";
        }catch(Exception e){
            System.out.println("messge in remove from cart" + e.getMessage());
        }
        model.addAttribute("cartItems",cartItems);
        return "shared/cart";
    }
    @PostMapping("/shared/removeFromCart")
    public String removeFromCart(@RequestParam("menuItem_id") long menuItem_id,
                                 Model model)
    {
        try{
            for (CartItem cartItem : cartItems)
                System.out.println("item id: " + cartItem.getItem().getItem_id());

            System.out.println("returned item id: " + menuItem_id);
            cartItems.removeIf(cartItem -> cartItem.getItem().getItem_id() == menuItem_id);

            model.addAttribute("item",new MenuItem());
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
    @PostMapping("shared/checkout")
    public String checkout(@RequestParam("userName") String name,
                           @RequestParam("menuItem_id") long itemId,
                           @RequestParam("modalToggle") boolean modal,
                           Principal principal,
                           Model model)
    {
        System.out.println("nnnnnnnnnnnnnnnn");
        MenuItem menuItem = menuItemService.getMenuItem(itemId).get();
        CartItem item = new CartItem();
         for(CartItem cItem : cartItems)
             if(cItem.getItem().getItem_id() == itemId)
             {
                 item = cItem;
                 break;
             }

        System.out.println("user details: " + ((UserDetails) principal).getUsername());

        Purchase purchase = new Purchase(((UserDetails) principal).getUsername()
                ,menuItem.getMenuItemPrice()*item.getAmount(),menuItem );
        System.out.println("item price: "+ purchase.getPayment());
        model.addAttribute("purchase",purchase);
        model.addAttribute("userName",name);
        model.addAttribute("menuItem_id",itemId);
        model.addAttribute("modalToggle",true);
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
            model.addAttribute("newIngredient", new Ingredient());
            return "admin/menu-editor";
        }catch(Exception e){
            System.out.println("error in admin/editMenu" + e.getMessage());
        }
        System.out.println("editmenu 3");
        return "admin/menu-editor";
    }
    @PostMapping("/admin/deleteItem")
    public String deleteItem(@RequestParam("menuItem_id") long menuItem_id,
                             @RequestParam("menu_id") long menu_id,
                             Model model){
        try{
            Menu menu = menuService.getMenu((menu_id)).get();
            MenuItem item = menuItemService.getMenuItem(menuItem_id).get();
            menu.removeItem(item);

            model.addAttribute("menu", menu);
            model.addAttribute("menu_id", menu.getMenu_id());
            model.addAttribute("menuItem",new MenuItem());
            model.addAttribute("newIngredient", new Ingredient());
            return "admin/menu-editor";
        }catch(Exception e){
            System.out.println("problem in delete item post: " + e.getMessage());
        }
        System.out.println("u need to add attributes");
        return "admin/menu-editor";
    }
    @PostMapping("/showItems")
    public String indexPost(@RequestParam("menu_id") long menu_id,
                            Model model , Principal principal){

        try{
            Menu menu = menuService.getMenu((menu_id)).get();

            ArrayList<Menu> menus = (ArrayList<Menu>) menuService.getAllMenus();

            model.addAttribute("menuItems",menu.getMenuItems());
            model.addAttribute("menus",menus);
            model.addAttribute("menu", menu);
            model.addAttribute("menu_id",menu_id);
            model.addAttribute("menuItem", new MenuItem());
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
        model.addAttribute("newIngredient", new Ingredient());
        model.addAttribute("menu", menu);
        model.addAttribute("menuItem",item);
        model.addAttribute("menu_id", menu.getMenu_id());
        return "admin/menu-editor";
    }

    @PostMapping("/admin/addIngredient")
    public String addIngredient(@RequestParam("menuItem_id") long itemId,
                                @ModelAttribute("newIngredient") Ingredient newIngredient,
                                Model model) {

        MenuItem menuItem = menuItemService.getMenuItem(itemId).orElse(new MenuItem());
        if(!menuItem.hasIngredient(newIngredient)) {
            newIngredient.addMenuItem(menuItem);
            ingredientService.saveIngredient(newIngredient);
            menuItem.addIngredient(newIngredient);
            menuItem.setFilters();
            menuItemService.saveMenuItem(menuItem);
            for (Ingredient ing : menuItem.getIngredients())
                System.out.println("ingredient name: " + ing.getIngredientName());
            System.out.println("menuItem name: " + menuItem.getMenuItemName());
        }else {
            System.out.println("ingredient already found in item");
        }

        model.addAttribute("menu", menuItem.getMenu());
        model.addAttribute("menuItem", menuItem);
        model.addAttribute("ingredients", menuItem.getIngredients());
        model.addAttribute("menu_id", menuItem.getMenu().getMenu_id());

        return "admin/menu-editor";
    }

    @PostMapping("/admin/validateMenuItem")
    public String BuildMenuItem(@Valid MenuItem menuItem,BindingResult itemResult,
                                @Valid Menu menu, BindingResult menuResult,
                                @RequestParam("menu_id") long menu_id, BindingResult menuIdResult,
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
            model.addAttribute("newIngredient", new Ingredient());
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
