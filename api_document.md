Seems need to write a API document...<br>
I also not sure how to write that, so just a experiment version<br>

http://cccbd.top:8080/RESTHello
#### testapi
    @apiName testapi
    @api {get} /hello/{id}
    @apiParam {String} id id 
    @Produces("text/plain")
    @apiSuccess (200) {PlainText}
    @apiSuccessExample {PlainText} 
    hello id

#### login
    @apiName login
    @api {get/post} /login/send/
    @apiParam {String} username 
    @apiParam {String} password 
    @Produces("application/json")
    @apiSuccess (200) {int} status  status code
    @apiSuccess (200) {String} type  login type
    @apiSuccess (200) {String} username  login user name
    @apiSuccess (200) {String} message  appended text message.
    @apiSuccessExample {json} Success-Response:
    {
        "status": 200,
        "type": "success",
        "username": "myname",
        "message": "Login success"
    }
    @apiErrorExample {json} Error-Response:
    {
        "status": 403,
        "type": "fail",
        "username": "myname",
        "message": "Login fail"
    }

#### register
    @apiName register
    @api {get/post} /login/send/
    @apiParam {String} username 
    @apiParam {String} password 
    @apiParam {String} gender 
    @Produces("plain/text")
    @apiSuccess (200) {list} account list
    @apiSuccessExample {PlainText} 
    Register success, your username is: username

#### get account list
    @apiName get account list
    @api {get} /account
    @apiParam {int} limit number limit 
    @Produces("application/json")
    @apiSuccess (200) {PlainText} "register success"
    @apiSuccess (403) {PlainText} "register fail"
    @apiSuccessExample {json} Success-Response:
    [
        {
            "name": "caoziyi",
            "pass": "xixihaha_",
            "id": "1",
            "gender": null
        },
        {
            "name": "hhhh",
            "pass": "345678",
            "id": "2",
            "gender": null
        },
        {
            "name": "myname",
            "pass": "mypass",
            "id": "3",
            "gender": null
        }
    ]

<br><br><br>

###### todo
* change return value to json format
* test auto generate api document X
* change framework to spring boot...?