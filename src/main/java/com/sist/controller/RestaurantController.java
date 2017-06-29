package com.sist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.restaurant.RestaurantDAO;
import com.sist.util.PagingManager;
import com.sist.vo.RestaurantVO;

@Controller
public class RestaurantController {
	@Autowired
	private RestaurantDAO restaurantDAO;

	@RequestMapping("restaurant/restaurant_list")
	public String restaurantList(Model model) {
		List<RestaurantVO> list = restaurantDAO.restaurantListData();
		model.addAttribute("list", list);
		return "restaurant/restaurant_list";
	}

}
