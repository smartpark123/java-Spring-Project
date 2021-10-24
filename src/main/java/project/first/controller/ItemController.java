package project.first.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.first.Manager.SessionConst;
import project.first.domain.Item;
import project.first.domain.Member;

import project.first.file.FileStore;
import project.first.service.ItemService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final FileStore fileStore;





    @GetMapping("/items/new")
    public String createForm(Model model){

        model.addAttribute("form", new ItemFormDTO());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String registerItem(@SessionAttribute(SessionConst.LOGIN_MEMBER) Member member, @ModelAttribute ItemFormDTO form) throws IOException {

        MultipartFile imageFile = form.getImageFiles();
        Map<String, String> uploadFile = fileStore.storeFile(imageFile);


        Item item = new Item();
        item.setName(form.getItemName());
        item.setPrice(form.getPrice());
        item.setStockQuantity(form.getStockQuantity());
        item.setCategory(form.getCategory());
        item.setTitle(form.getTitle());
        item.setSellerComment(form.getSellerComment());
        item.setSellerHP(form.getSellerHP());
        item.setMember(member);
        item.setUploadFileName(uploadFile.get("originalFilename"));
        item.setStoreFileName(uploadFile.get("storeFileName"));


        itemService.saveItem(item);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "/items/itemList";
    }

    @GetMapping("items/detail/{itemId}/edit")
        public String itemDetail(@PathVariable("itemId") long itemId, Model model){
            Item item = itemService.findOneUser(itemId);

            model.addAttribute("items", item);
            return "/items/detail";
        }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws
            MalformedURLException {
        return new UrlResource("file:///" + fileStore.getFullPath(filename));
    }




    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") long itemId, Model model){
        Item item = itemService.findOne(itemId);

        ItemFormDTO form = new ItemFormDTO();
        form.setId(item.getId());
        form.setItemName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setCategory(item.getCategory());
        form.setTitle(item.getTitle());
        form.setSellerComment(item.getSellerComment());
        form.setSellerHP(item.getSellerHP());


        model.addAttribute("form", form);
        return "/items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") ItemFormDTO form){



        itemService.updateItem(itemId, form.getItemName(), form.getPrice(), form.getStockQuantity(), form.getTitle(), form.getCategory(), form.getSellerComment(), form.getSellerHP());

        return "redirect:/items";
    }




}


