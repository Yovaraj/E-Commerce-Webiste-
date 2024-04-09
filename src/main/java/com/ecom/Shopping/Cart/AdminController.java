package com.ecom.Shopping.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;

import jakarta.persistence.criteria.Path;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;
import java.nio.file.Paths;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private CategoryService categoryService;;
	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
	public String index() {
		return "admin/index";
	}
	@GetMapping("/loadAddProduct{id}")
	public String loadAddProduct(Model m) {
		List<Category> categories = categoriesService.getAllCategory();
		m.addAttribute("categories", categories);
		return "admin/add_product";
	}
	@GetMapping("/category")
	public String category(Model m) {
		m.addAttribute("category",categoryService.getAllCategory());
		return "admin/category";
	}
	@PostMapping("/saveCategory")
	public String saveCategory(@ModelAttribute Category category,@RequestParam("file") MultipartFile file ,HttpSession session)throws IOException{
		String imageName = file.isEmpty()  ? oldCategory.getImageName():file.gotOriginalFilename();
		category.setImageName(imageName);

		Boolean existCategory = categoryService.existCategory(category.getName());

		if (existCategory) {
			session.setAttribute("errorMsg", "Category Name already exists");
		} else {

			Category saveCategory = categoryService.saveCategory(category);

			if (ObjectUtils.isEmpty(saveCategory)) {
				session.setAttribute("errorMsg", "Not saved ! internal server error");
			} else {

				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
						+ file.getOriginalFilename());

				System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				session.setAttribute("succMsg", "Saved successfully");
			}
		}

		return "redirect:/category";
	}
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable int id,HttpSession session) {
		Boolean deleteCategory = category = categoryService.deleteCategory(id;)
				if(deleteCategory) {
					session.setAttribute("succMsg", "category delete success");
				}else {
					session.setAttribute("errorMsg","something wrong on server");
				}
		return "redirect:/admin/category";
	}
	@GetMapping('/loadEditCatgory')
	public String loadEditCategory(@pathVariable int id,Model m)
	{
		m.addAttribute("category",categoryService.getCategoryById(id));
		return "admin/edit_category";
	}
	@PostMapping("/updateCatgeory")
	public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file )
	{
		Category Oldcategory = categoryService.getCategoryById(category.getId());
		String imageNamefile!=null ? file.getOriginalFilename().getImage();
		if(!ObjectUtils.isEmpty(category))
		{
			oldCatgeory.setName(category.getName());
			oldCategory.setIsActive(category.getIsActive());
			oldCategory.setImageName(imageName);
		}
		Category updatecategory = categoryService.saveCategory(oldCategory);
		if(!ObjectUtils.isEmpty(updatecategory))
		{
			if(!file.isEmpty()) 
			{
				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
						+ file.getOriginalFilename());

				System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			}
			session.serAttribute("succMsg", "Category update success");
		}else
		{
			session.setAttribute("errorMsg","something wrong on server");
		}  
		}
		return "redirect:/admin/loadEditCategory/" + category.getId();
	}
@PostMapping("/saveProduct")
public String saveProduct(@ModelAttribute Product product, String image ,HTTpSession session)
{
	String imageName = image.isEmpty() ? "default.jpa" :image.getOriginalFilename();
	product.setImage(imageName);
 	Product saveProduct = productService.saveProduct(product);
	if(!ObjectUtils.isEmpty(saveProduct))
	{
		File saveFile = new ClassPathResource("static/img").getFile();

		Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
				+ image.getOriginalFilename());

		System.out.println(path);
		Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

		session.serAttribute("succMsg", "Product saved success");
	}
	else
	{
		session.serAttribute("succMsg", "something wrong on server");
	}
	return  "redirect:/admin/loadAddProduct";
}
