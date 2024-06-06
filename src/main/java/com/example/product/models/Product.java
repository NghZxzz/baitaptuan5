package com.example.product.models;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {


    private int id;
    @NotBlank(message = "tên sản phẩm không được để trống")
    private String name;
    @Length(min=0,max=50,message = "tên hình ảnh không quá 50 ký tự")
    private String image;
    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 1,message = "Giá sản phẩm không được nhỏ hơn 1")
    @Max(value = 99999999,message = "Giá sản phẩm không được lớn hơn 99999999")
    private long price;

}
