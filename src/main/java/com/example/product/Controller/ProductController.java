package com.example.product.Controller;

import com.example.product.Services.ProductService;
import com.example.product.models.Product;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("product",new Product());
        return "products/create";
    }
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("product") Product newProduct,
                         @RequestParam("imageProduct") MultipartFile imageProduct,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "products/create";
        }
        if (imageProduct != null && imageProduct.getSize() > 0) {
            try {
                File saveFile = new ClassPathResource("static/images").getFile();
                String newImageFile = UUID.randomUUID() + ".png";
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + newImageFile);
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                newProduct.setImage(newImageFile);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        var listproducts = productService.getAll();
        Product productcuoi = listproducts.stream().reduce((f,l)->l).orElse(null);
        if(productcuoi != null)
            newProduct.setId(productcuoi.getId()+1);
        else
            newProduct.setId(1);
        productService.add(newProduct);
        return "redirect:/products";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,Model model){

        var editproduct = productService.getProductById(id);
        if(editproduct.isPresent())
        {
            model.addAttribute("product",editproduct.get());
            return "products/edit";
        }else{
            return "not-found";
        }
    }
    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("product") Product editProduct,
                         @RequestParam("imageProduct") MultipartFile imageProduct,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "products/edit";
        }
        if (imageProduct != null && imageProduct.getSize() > 0) {
            try {
                File saveFile = new ClassPathResource("static/images").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + editProduct.getImage());
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        productService.edit(editProduct);
        return "redirect:/products";
    }
    @GetMapping("")
    public String index(Model model)
    {
        model.addAttribute("listproduct", productService.getAll());
        return "products/index";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id)
    {
        productService.delete(id);
        return "redirect:/products";
    }
    @GetMapping("/search")
    public String search(@RequestParam("nameproduct") String  nameproduct,Model model)
    {
        List<Product> listproductsearch = productService.getByNameAll(nameproduct);
        model.addAttribute("listproduct", listproductsearch);
        return "products/index";
    }
}
