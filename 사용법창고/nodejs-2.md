### **nodejs-2**

#### **Server_beta2**

		var http = require('http');
	var url = require('url');
	var fs = require('fs');

	// 몽고 모듈 추가
	var client = require('mongodb').MongoClient;

	// 1. mime 모듈 추가. 서비스하려는 파일의 타입을 알아내기 위해서 필요
	var mime = require('mime'); // npm install mime

	var querystring = require('querystring');


	// 1. 서버생성
	var server = http.createServer((request,response)=>{
		var parsedUrl= url.parse(request.url);
		var res = parsedUrl.pathname;


		if(res == "/") {
			res = "/index.html";
		}

		// 제일 앞에 /를 제거하면 fs.readfile에서 실제 경로상의 파일을 접근할 수 있다.
		var res = res.substring(1);

		if(res == "post") {

				console.log(request.method);
			if(request.method == "POST") {
				// 요청이 넘어온 post의 body를 읽어서 postdata에 담는다.
				var postdata = '';
				  request.on('data', function (data) {
				    postdata = postdata + data;
				  });
				  // post 데이타를 다 읽고 나면 end 이벤트가 발생해서 아래로직이 실행
				  request.on('end', function () {
				    // 4. end 이벤트가 발생하면(end는 한버만 발생한다) 3번에서 저장해둔 postdata 를 querystring 으로 객체화
				    var parsedQuery = querystring.parse(postdata);
				    var Client = require('mongodb').MongoClient;
					client.connect('mongodb://localhost:27017/bbs', function(error, db){
					    if(error) {
					        response.writeHead(200, {'Content-Type':'text/html'});
				    		response.end('<h1>404 page not found</h1>');
					    } else {
					        var post = {title: parsedQuery.title, name:parsedQuery.name, content:parsedQuery.content };
					        db.collection('qna').insert(post);
					        db.close();
					    }
					});
				    // 6. 성공 HEADER 와 데이터를 담아서 클라이언트에 응답처리
				    response.writeHead(200, {'Content-Type':'text/html'});
				    response.end('등록되었습니다.');
				  });
			} else {

			}
		}else {
			var resMime = mime.lookup(res); // 파일의 mimeType을 가져온다.
		console.log("mime="+resMime);
		// 요청된 파일의 mime type이 text/html 일 경우만 처리
		if(resMime == "text/html") {
			//파일을 읽어서 전송한다.
			fs.readFile(res, 'utf-8', function(error, data) {
			    response.writeHead(200, {'Content-Type':'text/html'});
	    		response.end(data);
			});
			// 그 이외의 mime type은 모두 여기서 처리
		} else {
			fs.readFile(res, function(error, data) {
			    if(error) {
			    	response.writeHead(200, {'Content-Type':'text/html'});
	    			response.end('<h1>404 page not found</h1>');
			    }else {
			    	response.writeHead(200, {'Content-Type':resMime});
	    			response.end(data);
			    }
			});
		}
		}
	});

	server.listen(1004, ()=> {
		console.log('Server is running...');
	});

#### **hello**
	var Client = require('mongodb').MongoClient;
	
	Client.connect('mongodb://localhost:27017/bbs', function(error, db){
	    if(error) {
	        console.log(error);
	    } else {
	        // 1. 입력할 document 생성
	        var post = {title:'제목2', name:'Micheal2', content:'내용이라네2' };
	        // 2. qna 컬렉션의 insert( ) 함수에 입력
	        db.collection('qna').insert(post);
	
	        db.close();
	    }
	});

#### **helloread**
	
		console.log('start');
	var Client = require('mongodb').MongoClient;
	
	Client.connect('mongodb://localhost:27017/bbs', function(error, db){
	    if(error) {
	        console.log(error);
	    } else {
	        // 2. find( ) 함수에 아무런 입력값이 없으면 커렉션의 전체 다큐먼트를 일겅온다.
	        var cursor = db.collection('qna').find();
	        // 2. 읽어온 다큐먼트를 커서에 넣고 반복처리
	        cursor.each(function(err,doc){ // 다큐먼트(mysql과 같은 관계 db에서의 record)가 예약어
	            if(err){
	                console.log(err);
	            }else{
	                if(doc != null){
	                	// 3. document가 정상적으로 있으면 뽑는당
	                    console.log(doc);
	                }
	            }
	        });
	        db.close();
	    }
	});

#### **index.html**

	
		<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-u"/>
		<title></title>
	</head>
	<body>
	<h1>여기는 index.html</h1> <br/>
	<ol>
		<li><a href="post/post.html">쓰기</a></li>
		<li><a href="post">목록</a></li>
	</ol>
	</body>
	</html>
		

#### **server_file**

	
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


### **post**

	<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-u"/>
		<title></title>
	</head>
	<body>
	<h1>쓰기</h1> <br/>
	<form action="/post" method="POST">
	<ol>
		<li>제목 : <input type="text" name="title" id="title"/></li>
		<li>작성자 : <input type="text" name="name" id="name"/></li>
		<li><textarea name="content" id="content" rows="5" cols="120"></textarea></li>
	</ol>
	<input type="submit" value="전송"/>
	</form>
	</body>
	</html>