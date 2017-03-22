###**nodejs**

####**Server_get**

	var http = require('http');

	// 1. 요청한 url을 객체로 만들기 위해 url 모듈사용
	var url = require('url');
	// 2. 요청한 url 중에 Query String 을 객체로 만들기 위해 querystring 모듈 사용
	var querystring = require('querystring'); 
	
	var server = http.createServer(function(request,response){
	    3. 콘솔화면에 로그 시작 부분을 출력
	    console.log('--- log start ---');
	    console.log(request.url);
	    // 4. 브라우저에서 요청한 주소를 parsing 하여 객체화 후 출력
	    var parsedUrl = url.parse(request.url);
	    console.log(parsedUrl);
	    // 5. 객체화된 url 중에 Query String 부분만 따로 객체화 후 출력
	    var parsedQuery = querystring.parse(parsedUrl.query,'&','=');
	    console.log(parsedQuery);
	    // 6. 콘솔화면에 로그 종료 부분을 출력
	    console.log('--- log end ---');
	
	    response.writeHead(200, {'Content-Type':'text/html'});
	    response.end('Hello node.js!!');
	});
	
	server.listen(8080, function(){
	    console.log('Server is running...');
	});


####**Server_get**
	
	var http = require('http');
	var querystring = require('querystring');
	
	var server = http.createServer(function(request,response){
	  // 1. post로 전달된 데이터를 담을 변수를 선언
	  var postdata = '';
	  // 2. request객체에 on( ) 함수로 'data' 이벤트를 연결
	  request.on('data', function (data) {
	    // 2.1 data 이벤트가 발생할 때마다 callback을 통해 postdata 변수에 값을 저장
	    console.log('찍히나?')
	    postdata = postdata + data;
	  });
	
	  // 3. request객체에 on( ) 함수로 'end' 이벤트를 연결
	  request.on('end', function () {
	    // 4. end 이벤트가 발생하면(end는 한버만 발생한다) 3번에서 저장해둔 postdata 를 querystring 으로 객체화
	    var parsedQuery = querystring.parse(postdata);
	    // 5. 객체화된 데이터를 로그로 출력
	    console.log(parsedQuery);
	    // 6. 성공 HEADER 와 데이터를 담아서 클라이언트에 응답처리
	    response.writeHead(200, {'Content-Type':'text/html'});
	    response.end('var1의 값 = ' + parsedQuery.aaa);
	  });
	
	});
	
	server.listen(8080, function(){
	    console.log('Server is running...');
	});


####**index.html**

	
	<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-u"/>
		<title></title>
	</head>
	<body>
	내용을 쓰고, <br/>
	<img src="temp.jpg"/>
	</body>
	</html>
	

####**server_file**

	
	var http = require('http');
	var url = require('url');
	var fs = require('fs');
	
	// 1. 서버생성
		var server = http.createServer((request,response)=>{
			var parsedUrl= url.parse(request.url);
			var res = parsedUrl.pathname;
		
		if(res == "/index.html") {
			//파일을 읽어서 전송한다.
			fs.readFile('index.html', 'utf-8', function(error, data) {
			    response.writeHead(200, {'Content-Type':'text/html'});
	    		response.end(data);
			});
		} else if(res =="/temp.jpg") {
			fs.readFile('temp.jpg', function(error, data) {
			    response.writeHead(200, {'Content-Type':'image/jpeg'});
	    		response.end(data);
			});
		}
		else {
				response.writeHead(404, {'Content-Type':'text/html'});
	    		response.end("<h1>404 Page Not Found!</h1>");
		}
	
	
	});
	
	server.listen(1004, ()=> {
		console.log('Server is running...');
	});