package com.sist.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sist.recipe.CatSubDAO;
import com.sist.recipe.RecipeDAO;
import com.sist.recipe.RecipeInsertDAO;
import com.sist.recipe.RecipeService;
import com.sist.recipe.RecipeUpdateDAO;
import com.sist.recipe.RecipeUpdateService;
import com.sist.users.UsersService;
import com.sist.recipe.RecipeInsertService;
import com.sist.util.FileManager;
import com.sist.util.PagingManager;
import com.sist.util.StringManager;
import com.sist.vo.CatSubVO;
import com.sist.vo.CatTopVO;
import com.sist.vo.IngrRecipeVO;
import com.sist.vo.IngredientVO;
import com.sist.vo.RecipeVO;
import com.sist.vo.UsersVO;
import com.sist.vo.RecipeContentVO;
import com.sist.vo.RecipeTagVO;

@Controller
public class RecipeController {
	
	@Autowired
	private CatSubDAO catSubDAO;	
	@Autowired
	private RecipeDAO recipeDAO;
	@Autowired
	private RecipeUpdateDAO recipeUpdateDAO;
	@Autowired
	private RecipeInsertDAO recipeInsertDAO;	
	@Autowired
	private FileManager fm;
	
	@Autowired
	private UsersService usersService;
	@Autowired 
	RecipeUpdateService recipeUpdateService;
	
	@Autowired
	private RecipeInsertService recipeInsertService;
	@Autowired
	private RecipeService recipeService;
	
	@RequestMapping("/recipe/recipe_insert")
	 public String recipe_insert(Model model){
	
		List<CatTopVO>list =catSubDAO.selectTopList();
		model.addAttribute("toplist", list);
		
		return "/recipe/recipe_insert";
	 }
	
	
	@RequestMapping("recipe/recipe_insertok")
	public String recipe_insertok(RecipeVO recipe,
								String tags,
								MultipartFile mainFile,HttpSession session,RedirectAttributes red){	
		
		
		

		UsersVO user = (UsersVO) session.getAttribute("user");
		System.out.println("??????????????????"+user.getId());
		
		int rid=recipeInsertService.recipeInsert(recipe, tags, mainFile,user.getId());
		red.addAttribute("id", rid);
		
		
		return "redirect:/recipe/recipe_detail";
	}
	@RequestMapping("recipe/recipe_update")
	public String recipe_update(Model model){
		
		int id=73141;
		RecipeVO vo=recipeUpdateDAO.selectRecipe(id);	
		List<CatTopVO>list =catSubDAO.selectTopList();
		List<RecipeContentVO> steps=recipeUpdateDAO.selectStesCon(id);
		CatSubVO cate=recipeUpdateDAO.selectCatsub(vo.getCat_sub_id());
		List<IngrRecipeVO> ingr=recipeUpdateDAO.selectIngRecipe(id);

		List<String> tags=recipeUpdateDAO.selectRTag(id);
		String tag=StringManager.listToString(tags);
		
		
		
		
		model.addAttribute("steps",steps);
		model.addAttribute("tag",tag);
		model.addAttribute("ingrlist",ingr);
		model.addAttribute("top",cate.getCat_top_id());
		model.addAttribute("sub",cate.getId());
		model.addAttribute("toplist", list);
		model.addAttribute("rvo",vo);
		
		
		
		
		return "/recipe/recipe_update";
	}
	@RequestMapping("recipe/recipe_updateok")
	public String recipe_updateok(RecipeVO recipe,
			String tags,MultipartFile mainFile,int rid){
				//1?????? recipe ??? ?????? ?????? ???????????? ????????? ?????? 
		
		
		
		recipeUpdateService.updateRecipe(recipe, tags, mainFile, rid);
		
		
		
		return "/recipe/?";
	}
		
	
	
	
	@RequestMapping("recipe/recipe_main")
	public String recipeList(Model model){
		//tag?????? ????????????
		List<RecipeTagVO> recipeTagRankList=recipeDAO.tagNameRankList();
		int total=recipeTagRankList.size();
		//3??? ???????????? ?????? ????????????
		int[] randomRanTag=new int[3];
		for (int a = 0; a < randomRanTag.length; a++) {
			randomRanTag[a]=(int)(Math.random()*total);

			for (int b = 0; b < a; b++) {
				if (randomRanTag[a]==randomRanTag[b]) {//???????????? ?????? ????????? ?????? ?????? ?????????
					a=a-1;//?????? 
					break;
				}
					
			}		
		}
		List<RecipeTagVO> randomRecipeTagRankList=new ArrayList<RecipeTagVO>();
		for (int i : randomRanTag) {
			randomRecipeTagRankList.add(recipeTagRankList.get(i));
		}
		String[] tagNameArr=new String[3];
		for(int i=0; i<tagNameArr.length; i++){
			tagNameArr[i]=randomRecipeTagRankList.get(i).getName();
			
		}
		for (String string : tagNameArr) {
			System.out.println(string);
		}
		

		List<CatSubVO> subList= catSubDAO.selectList(1);//????????? ????????? ????????????
		model.addAttribute("subList", subList);

		Map map=new HashMap();
		map.put("start", 1);
		map.put("end", 3);
		map.put("tagName", tagNameArr[0]);
		
		//List<RecipeVO> recipeList=new ArrayList<RecipeVO>();		
		List<RecipeVO> recipeList0=recipeDAO.recipeTagListByTagName(map);
		for (RecipeVO recipeVO : recipeList0) {
			recipeVO.setImgAuto();
			recipeVO.setNickname(usersService.selectNickName(recipeVO.getUser_id()));
		}
		
		map.put("tagName", tagNameArr[1]);
		List<RecipeVO> recipeList1=recipeDAO.recipeTagListByTagName(map);
		for (RecipeVO recipeVO : recipeList1) {
			recipeVO.setImgAuto();
			recipeVO.setNickname(usersService.selectNickName(recipeVO.getUser_id()));
		}
		
		map.put("tagName", tagNameArr[2]);
		List<RecipeVO> recipeList2=recipeDAO.recipeTagListByTagName(map);
		for (RecipeVO recipeVO : recipeList2) {
			recipeVO.setImgAuto();
			recipeVO.setNickname(usersService.selectNickName(recipeVO.getUser_id()));
		}
		
		
		/*for (int i=0; i<tagNameArr.length; i++) {
			String tagName=tagNameArr[i];
			List<RecipeTagVO> tagList=recipeDAO.recipeTagSelectList3ByName(tagName);
			for (RecipeTagVO recipeTag : tagList) {
				RecipeVO recipe=recipeDAO.recipeDetail(recipeTag.getRecipe_id());
				recipe.setImgAuto();
				recipe.setNickname(usersService.selectNickName(recipe.getUser_id()));
				recipeList.add(recipe);
			}
		}
		
		int count=3;
		for (int i = 0; i < recipeList.size(); i++) {
			if (i<3) {
				recipeList1.add(recipeList.get(i));				
			}else if (i<6) {
				recipeList2.add(recipeList.get(i));				
			}else{
				recipeList3.add(recipeList.get(i));				
			}
		}*/
		
		model.addAttribute("tagNameArr", tagNameArr);
		model.addAttribute("recipeList0", recipeList0);
		model.addAttribute("recipeList1", recipeList1);
		model.addAttribute("recipeList2", recipeList2);
		
		return "recipe/recipe_main";
	}
	
	
	
	/**
	 * @param page
	 * @param id
	 * @param name
	 * @param model
	 * @return
	 * 
	 * ????????? ????????? ?????????, ????????? ??????????????? ??????????????? 
	 * cat_sub_id??? ????????? ?????? id??? ?????? recipe????????? ????????????.
	 */
	@RequestMapping("recipe/recipe_sublist")
	public String recipeSubList(PagingManager page, int cat_sub_id, String name, Model model){
		page.setRowSize(9);
		Map<String, Integer> pageCal = page.calcPage(550);//block?????? ???????????? ?????? ???????????? ?????? ?????????.
		
		//mybatis mappter??? ????????? map
		Map map=new HashMap();
		map.put("cat_sub_id", cat_sub_id);		
		map.put("start", pageCal.get("start"));
		map.put("end", pageCal.get("end"));
		
		List<RecipeVO> list=recipeDAO.catSubRecipeListData(map);
		for (RecipeVO vo : list) {
			//???????????? ?????? ???????????? ????????? ????????? ????????? ???????????? oriname??? ????????????.
			vo.setImgAuto();
			vo.setNickname(usersService.selectNickName(vo.getUser_id()));
			System.out.println(usersService.selectNickName(vo.getUser_id()));
		}
		
		//totalpage ?????????
		int totalpage=recipeDAO.catSubRecipeListTotalPage(cat_sub_id);
		
		model.addAttribute("list", list);		
		model.addAttribute("page", page.getPage());
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("name", name);
		model.addAttribute("cat_sub_id", cat_sub_id);
		
		return "recipe/recipe_sublist";
	}
	
	@RequestMapping("recipe/recipe_detail")
	public String recipeDetail(int id, Model model,HttpSession session){
		int user_id=0;
		boolean userCk=false;
		UsersVO user=null;
		if((user=(UsersVO) session.getAttribute("user"))!=null){
		
		 user_id=user.getId();
		 model.addAttribute("ID", user_id);
		}
		
		/*RecipeVO recipe=recipeDAO.recipeDetail(id);
		
		//???????????? ?????? ???????????? ????????? ????????? ????????? ???????????? oriname??? ????????????.
		recipe.setImgAuto();
		
		
		//???????????? ????????????
		List<RecipeContentVO> contentList=recipeDAO.recipeDetailContent(id);
		System.out.println("contentlistsize??? "+contentList.size());
		for (RecipeContentVO vo : contentList) {
			//System.out.println(vo.getImg_ori());
			
			//???????????? ?????? ???????????? ????????? ????????? ????????? ???????????? oriname??? ????????????.
			if (vo.getImg_new().equals("imgfromweb")) {
				vo.setImg(vo.getImg_ori());
			}else{
				vo.setImg(vo.getImg_new());				
			}
			vo.setImgAuto();
			
		}
		
		//ingr_recipe???????????? ingredient????????? ??????
		List<IngredientVO> ingrList=recipeDAO.IngrRecipeJoin(id);
		
		List<RecipeTagVO> tagList=recipeDAO.recipeTagSelectListByRecipeId(id);*/
		
		//?????? ????????? service??? ??????.
		RecipeVO recipe=recipeService.recipeDetail(id);
		if(user_id==recipe.getUser_id()){
			userCk=true;
		}
		System.out.println(userCk);
		model.addAttribute("userCk",userCk);
		model.addAttribute("id", id);
		model.addAttribute("recipe", recipe);
		/*model.addAttribute("contentList", contentList);
		model.addAttribute("ingrList", ingrList);
		model.addAttribute("tagList", tagList);*/
		return "recipe/recipe_detail";
	}
	@RequestMapping("recipe/recipe_main_test")
	public String recipeMainTest(){
		
		return "recipe/recipe_main_test";
	}
	
	@RequestMapping("recipe/recipe_tag_list")
	public String recipeTagListByTagName(String tagName, PagingManager page, Model model){
		int totalPage=recipeDAO.recipeTagListTotalPage(tagName);
		
		page.setRowSize(9);
		Map pageCal=page.calcPage(100);//block?????? ???????????? ?????? ???????????? ?????? ?????????.
		
		Map map=new HashMap();
		map.put("start", pageCal.get("start"));
		map.put("end", pageCal.get("end"));
		map.put("tagName", tagName);
		
		List<RecipeVO> recipeList=recipeDAO.recipeTagListByTagName(map);
		for (RecipeVO vo : recipeList) {
			
			//???????????? ?????? ???????????? ????????? ????????? ????????? ???????????? oriname??? ????????????.
			vo.setImgAuto();
			vo.setNickname(usersService.selectNickName(vo.getUser_id()));

		}
		
		model.addAttribute("list", recipeList);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("page", page.getPage());
		model.addAttribute("tagName", tagName);
		
		return "recipe/recipe_tag_list";
	}
	
	@RequestMapping("recipe/recipe_ingr_list")
	public String recipeIngrListByIngrName(PagingManager page, String ingrName, Model model){
		int total=recipeDAO.recipeIngrListTotal(ingrName);
				
		page.setRowSize(9);
		Map pageCal=page.calcPage(total);
		
		Map map=new HashMap();
		map.put("start", pageCal.get("start"));
		map.put("end", pageCal.get("end"));
		map.put("ingrName", ingrName);
		
		List<RecipeVO> recipeList=recipeDAO.recipeIngrListByIngrName(map);
		for (RecipeVO vo : recipeList) {
			vo.setImgAuto();
			vo.setNickname(usersService.selectNickName(vo.getUser_id()));
		}
		System.out.println("recipeList?????????"+recipeList.size());
		
		model.addAttribute("recipeList", recipeList);
		model.addAttribute("totalPage", page.getTotalPage());
		model.addAttribute("page", page.getPage());
		model.addAttribute("ingrName", ingrName);		
		
		return "recipe/recipe_ingr_list";
	}
	
	@RequestMapping("recipe/recipe_user_list")
	public String recipeUserListByNicknanme(PagingManager page, String nickname, Model model){
		int total=recipeDAO.getRecipeListTotalByNick(nickname);
				
		page.setRowSize(9);
		Map pageCal=page.calcPage(total);
		
		Map map=new HashMap();
		map.put("start", pageCal.get("start"));
		map.put("end", pageCal.get("end"));
		map.put("nickname", nickname);
		
		List<RecipeVO> recipeList=recipeDAO.getRecipeListByNick(map);
		for (RecipeVO vo : recipeList) {
			vo.setImgAuto();
			vo.setNickname(usersService.selectNickName(vo.getUser_id()));

		}
		System.out.println("recipeList?????????"+recipeList.size());
		
		model.addAttribute("recipeList", recipeList);
		model.addAttribute("totalPage", page.getTotalPage());
		model.addAttribute("page", page.getPage());
		model.addAttribute("nickname", nickname);		
		
		return "recipe/recipe_user_list";
	}
	
}
