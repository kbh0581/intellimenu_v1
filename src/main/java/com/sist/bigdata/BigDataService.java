package com.sist.bigdata;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import com.sist.users.UsersService;
import com.sist.vo.RecipeVO;
import com.sist.weather.WeatherManager;

@Service
public class BigDataService {
	@Autowired
	private WeatherManager weatherManager;
	
	@Autowired
	private MartMapper martMapper;
	
	@Autowired
	private MartDAO martDAO;
	
	@Autowired
	private UsersService usersService;
	
	public void helloExample(Model model){
		
		model.addAttribute("hello", "안녕");
	}
	
	public void getWeather(Model model){	
		String weather=weatherManager.getWeather();
		model.addAttribute("weather", weather);
	}
	
	public void getMart(Model model){

		//몽고db에서 불러오기
		int today=21;
		List<MartVO> todaylist_ori = martDAO.selectDay(today);
		//채소 인기재료만 가져오기
		List<MartVO> todaylist=new ArrayList<MartVO>();
		for(int i=0;i<todaylist_ori.size();i++){
			if(todaylist_ori.get(i).getCate()==0 ){
				if(todaylist.size()<=10){
					todaylist.add(todaylist_ori.get(i));
				}
			}
		}	
 		String todayHitItem=todaylist.get(0).getItem();
		
 		//line chart를 위한 json만들기		
		StringBuffer line = new StringBuffer();
		for(int j = 0; j < todaylist.size(); j++) {
			MartVO vo = todaylist.get(j);// todayitem
			//List<MartVO> thisItemList = martDAO.selectHitItem(vo.getItem(),2);
			List<MartVO> thisItemList = martDAO.selectItem(vo.getItem());
			line.append("{");
			line.append("\"name\": \"" + vo.getItem() + "\",");
			line.append("\"WeeklyData\": [");
			for (int i = 0; i < thisItemList.size(); i++) {
				if (i != thisItemList.size() - 1) {
					line.append("{\"week\": \"" + thisItemList.get(i).getDay() + " Jul 2017\", \"value\": "
							+ thisItemList.get(i).getHit()+ " },");
				} else {
					line.append("{\"week\": \"" + thisItemList.get(i).getDay() + " Jul 2017\", \"value\": "
							+ thisItemList.get(i).getHit()+ " }");
				}
			}
			line.append("]");
			if (j != todaylist.size() - 1) {
				line.append("},");
			} else {
				line.append("}");
			}
		}
		String lineData="["+line.toString()+"]";
		
		//워드클라우드를 위한 json만들기
		StringBuffer word=new StringBuffer();
		for(int j=0;j<todaylist.size();j++){
			word.append("{");
			word.append("\"word\":\""+todaylist.get(j).getItem());
			word.append("\",\"freq\":"+todaylist.get(j).getHit()*5);
			if(j!=todaylist.size()-1){
				word.append("},");
			}else{
				word.append("}");
			}
		};
		String wordData="["+word.toString()+"]";
		System.out.println(wordData);
		
		//오늘 최고 인기재료 이름으로 레시피 목록을 구하고 랜덤함수로 만들기
		List<RecipeVO> recipeList=martMapper.RecipeBymartHitIngr(todayHitItem); //오늘 최고 인기재료 이름 구하기
		for (RecipeVO vo : recipeList) {
			vo.setImgAuto();
			vo.setNickname(usersService.selectNickName(vo.getUser_id()));
		}
	
		//레시피를 위한 랜덤값 얻어오기
		int[] random=new int[5];
		for (int a = 0; a < random.length; a++) {
			int x=(int)(Math.random()*recipeList.size());			
			random[a]=x;	
			for (int b = 0; b < a; b++) {
				if (random[a]==random[b]) {//새로얻은 값이 이전에 얻은 값과 같다면
					/*x=(int)(Math.random()*total);			
					random[a]=x;무한루프걸리네?*/
					a=a-1;//다시 
					break;
				}
			}			
		}
		List<RecipeVO> randomMartList=new ArrayList<RecipeVO>();
		for (int i = 0; i < 3; i++) {
			randomMartList.add(recipeList.get(random[i]));
		}

		model.addAttribute("todayHitItem",todayHitItem);
		model.addAttribute("lineData", lineData);
		model.addAttribute("wordData", wordData);
		model.addAttribute("randomMartList", randomMartList);
	}

}
