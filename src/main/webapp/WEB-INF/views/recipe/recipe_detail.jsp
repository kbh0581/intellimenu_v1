<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <link href="/css/style.css" rel="stylesheet" type="text/css">
<link href="/css/responsive.css" rel="stylesheet" type="text/css">
<link href="/css/animate.css" rel="stylesheet" type="text/css"> -->
<script>
	$(function(){
		$('#foodimg').height($('#foodimg').width()*0.6);
		
	})
</script>
<div class="container">
	<hr>
	<h2>${recipe.title }</h2>
	<h4 align="right">by 글쓴이</h4>
	<h5 align="center">${recipe.summary }</h5>
	<hr />
	<div class="row">
		<div class="col-sm-4 wow fadeInLeft delay-05s">
			<div class="service-list">
				<div class="service-list-col1">
					<i class="fa fa-file-text-o"></i>
				</div>
				<div class="service-list-col2">
					<h3>카테고리</h3>
					<p>${recipe.cat_sub_id}</p>
				</div>
			</div>
			<div class="service-list">
				<div class="service-list-col1">
					<i class="fa fa-users"></i>
				</div>
				<div class="service-list-col2">
					<h3>인원</h3>
					<p>${recipe.reqmember }</p>
				</div>
			</div>
			<div class="service-list">
				<div class="service-list-col1">
					<i class="fa fa-hand-lizard-o"></i>
				</div>
				<div class="service-list-col2">
					<h3>난이도</h3>
					<p>${recipe.lvl }</p>
				</div>
			</div>
			<div class="service-list">
				<div class="service-list-col1">
					<i class="fa fa-clock-o"></i>
				</div>
				<div class="service-list-col2">
					<h3>시간</h3>
					<p>${recipe.time }</p>
				</div>
			</div>
		</div>
		<figure class="col-sm-8  text-right wow fadeInUp delay-02s">
			<img src="${recipe.img}" id="foodimg" width="100%" alt="">
		</figure>

	</div>

	<hr />

	<div class="portfolioFilter">
		<center>
			<h3>재료</h3><br />
			<table width="200px">				
			<c:forEach var="ingredient" items="${recipe.ingredientList}">
				<tr style="border-bottom: 1px solid lightgrey">
					<th align="left">
						<a href="/recipe/recipe_ingr_list?ingrName=${ingredient.name }">${ingredient.name}</a>
					</th>
					<td align="right">${ingredient.quantity }</td>
				</tr>
			</c:forEach>
			</table>
		</center>
	</div>

	<hr />

	<c:forEach var="vo" items="${recipe.contentList }">
		<div class="container">
			<div class="row">
				<figure class="col-lg-3 col-sm-2 wow fadeInLeft">
					<img src="${vo.img }" alt="">
				</figure>
				<div class="col-lg-9 col-sm-10 featured-work">
					<P class="padding-b">${vo.step+1 }.${vo.content }</P>
				</div>
			</div>
		</div>

		<hr />
	</c:forEach>


	<div class="recipeTag">
		<div class="row">
			<div class="col-lg-12 featured-work">
				<li ><a href="#"style="color:#fff; background:#7cc576">#TAG</a></li>
				<c:forEach var="tag" items="${recipe.tagList}">
					<li><a href="/recipe/recipe_tag_list?tagName=${tag.name }">${tag.name }</a></li>
				</c:forEach>
			</div>
		</div>
		<hr />
	</div>


</div>
