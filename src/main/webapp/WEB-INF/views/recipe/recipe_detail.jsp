<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, maximum-scale=1">
<title>Insert title here</title>

<link href="/css/style.css" rel="stylesheet" type="text/css">
<link href="/css/responsive.css" rel="stylesheet" type="text/css">
<link href="/css/animate.css" rel="stylesheet" type="text/css">
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="https://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/transition.js"></script>
<script type="text/javascript" src="/js/jquery-scrolltofixed.js"></script>

<script>
	
</script>
</head>
<body >
	<div class="container">
    	<h2>${recipe.title }</h2>
    	<h6>${recipe.summary } by 글쓴이  </h6>
        <div class="row">
        	<div class="col-sm-4 wow fadeInLeft delay-05s">
                <div class="service-list">
                	<div class="service-list-col1">
                    	<i class="fa-medkit"></i>
                    </div>
                	<div class="service-list-col2">
                        <h3>카테고리</h3>
                        <p>${recipe.cat_sub_id}</p>
                    </div>
                </div>
                <div class="service-list">
                	<div class="service-list-col1">
                    	<i class="fa-gear"></i>
                    </div>
                	<div class="service-list-col2">
                        <h3>인원</h3>
                        <p>${recipe.reqmember }</p>
                    </div>
                </div>
                <div class="service-list">
                	<div class="service-list-col1">
                    	<i class="fa-apple"></i>
                    </div>
                	<div class="service-list-col2">
                        <h3>난이도</h3>
                        <p>${recipe.lvl }</p>
                    </div>
                </div>
                <div class="service-list">
                	<div class="service-list-col1">
                    	<i class="fa-medkit"></i>
                    </div>
                	<div class="service-list-col2">
                        <h3>시간</h3>
                        <p>${recipe.time }</p>
                    </div>
                </div>
            </div>
            <figure class="col-sm-8  text-right wow fadeInUp delay-02s">
            	<img src="${recipe.img}" width="100%" alt="">
            </figure>
        
        </div>
	</div>
</body>
</html>