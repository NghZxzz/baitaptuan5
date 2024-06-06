package com.example.product.Services;

import com.example.product.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private List<Product> listProduct = new ArrayList<>();

    public ProductService(){

    }
    public List<Product> getAll(){
        return listProduct;
    }
    public List<Product> getByNameAll(String name){
        return listProduct.stream().filter(x->x.getName().toLowerCase().contains(name)).toList();
    }
    public Product get(int id){
        return listProduct.stream().filter(p->p.getId() == id).findFirst().orElse(null);
    }
    public void add(Product product){
        listProduct.add(product);
    }
    public void edit(Product editproduct){
        Product find = listProduct.get(editproduct.getId());
        if(find != null){
            find.setName(editproduct.getName());
            find.setPrice(editproduct.getPrice());
            find.setImage(editproduct.getImage());
        }
    }
    public void delete(int id) {
        listProduct.removeIf(x->x.getId() == id);
    }
    public Optional<Product> getProductById(int id)
    {
        Optional<Product> editProduct = listProduct.stream()
                .filter(x->x.getId()== id)
                .findFirst();
        return editProduct;
    }
}
