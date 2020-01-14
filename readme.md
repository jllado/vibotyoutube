### Install
- Create file .youtube_client_secrent.json. Get data from your youtube credentials page (OAuth 2.0 IDs -> download json)
- Get auth token from here: https://accounts.google.com/o/oauth2/auth?client_id=any_id&redirect_uri=urn:ietf:wg:oauth:2.0:oob&scope=https://www.googleapis.com/auth/youtube.upload&response_type=code
- gradlew build -x test
- docker-compose up --build -d

#### Use
- Create video meta data: 
curl --header "Content-Type: application/json" --request POST --data '{"title": "any title","description": "any description","category": "NEWS","keywords": ["cool","video","other"]}' http://localhost:20000/create
- Upload video: 
curl -F video=@video.mp4  http://localhost:20000/upload
