package com.lzw.blog.web;

import com.lzw.blog.service.BlogService;
import com.lzw.blog.service.TagService;
import com.lzw.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: lzw
 * @Date: 2020/04/19/18:36
 * @Description:
 */
@Controller
public class indexController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private TypeService typeService;

	@Autowired
	private TagService tagService;

	@GetMapping("/")
	public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
	                    Model model) {
		model.addAttribute("page", this.blogService.listBlog(pageable));
		model.addAttribute("types", this.typeService.listType(6));
		model.addAttribute("tags", this.tagService.listTagTop(10));
		model.addAttribute("recommendBlogs", this.blogService.listRecommendBlogTop(8));
		return "index";
	}

	@PostMapping("/search")
	public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
	                     @RequestParam String query, Model model) {
		model.addAttribute("page", this.blogService.listBlog(pageable, "%" + query + "%"));
		model.addAttribute("query", query);
		return "search";
	}

	@GetMapping("/blog/{id}")
	public String blog(@PathVariable Long id, Model model) {
		model.addAttribute("blog", this.blogService.getBlogContent(id));
		return "blog";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/footer/newblog")
	public String newBlogList(Model model) {
		model.addAttribute("newBlogList", this.blogService.listRecommendBlogTop(3));
		return "_fragments::newBlogList";
	}
}
