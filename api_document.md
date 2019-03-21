Seems need to write a API document...<br>
I also not sure how to write that, so just a experiment version<br>
#### testapi
    @apiName testapi
    @api {get} /hello/{id}
    @apiParam {String} id id 
    @Produces("text/plain")
    @apiSuccess (200) {PlainText}
    @apiSuccessExample {PlainText} 
    HTTP/1.1 200 OK 
    hello id

#### login
    @apiName login
    @api {get/post} /login/send/
    @apiParam {String} username 
    @apiParam {String} password 
    @Produces("text/plain")
    @apiSuccess (200) {PlainText} "login success"
    @apiSuccess (403) {PlainText} "login fail"
    @apiSuccessExample {PlainText} 
    HTTP/1.1 200 OK 
    login success

#### register
    @apiName login
    @api {get/post} /login/send/
    @apiParam {String} username 
    @apiParam {String} password 
    @apiParam {String} id 
    @apiParam {String} gender 
    @Produces("text/plain")
    @apiSuccess (200) {PlainText} "register success"
    @apiSuccess (403) {PlainText} "register fail"
    @apiSuccessExample {PlainText} 
    HTTP/1.1 200 OK 
    Register success, your username is: username

##todo
* change return value to json format
* test auto generage api document X
* change framework to spring boot......