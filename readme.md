Restful back-end for a insurance application
API document:https://cccbd.top:8443/RESTHello/apidoc/index.html


<br><br><br>

#### test user account
username:testuser   
password:123456 

#### test employee account
username:testemployee   
password:123456 


## todo
* 改写web.xml为autoscan模式 (fail)
* 对所有网页添加token验证 (...好麻烦啊,spring真香)
* 单元测试...咋写啊!

apidoc -i src/main/java -o src/main/webapp/apidoc/
electron-packager . "Hibernia-Sino Insurance" --platform=win32 --overwrite
