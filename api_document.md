Seems need to write a API document...<br>

http://cccbd.top:8080/RESTHello
#### login
    @apiName login
    @api {get/post}   /login/send/              user login
    @api {get/post}   /login/send_employee/     employee login
    @comment user和employee登陆的其余部分一样
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
    then would auto set a user/empolyee token  
    @apiErrorExample {json} Error-Response:
    {
        "status": 403,
        "type": "fail",
        "username": "myname",
        "message": "Login fail"
    }

#### register
    @apiName register
    @api {get/post}   /login/send/
    @apiParam {String} username 
    @apiParam {String} password 
    @apiParam {String} gender
    @apiParam {String} email  
    @Produces("text/plain")
    @apiSuccess (200) {PlainText}
    @apiSuccessExample {PlainText} 
    Register success, your username is: username

#### get account list
    @apiName get account list
    @api {get}   /account
    @apiParam {int} limit number limit 
    @Produces("application/json")
    @apiSuccessExample (200) {json} Success-Response:
    [
        {
            "name": "testname",
            "pass": "testpass",
            "id": "1",
            "gender": "male"
        },
        {
            "name": "chen",
            "pass": "123456",
            "id": "2",
            "gender": "female"
        }
    ]

#### get policies
    @apiName get your policy list
    @api {get}   /policy/my_policies 
    @Produces("application/json")
    @apiSuccessExample (200) {json} Success-Response:
    [
        {
            "policyNo": 1,
            "policyName": "testpolicy",
            "content": "insure everything!"
        },
        {
            "policyNo": 2,
            "policyName": "testpolicy2",
            "content": "insure nothing!"
        }
    ]
   
  
#### add claim
    @apiName add a new claim
    @api {get/post}   /claim/new_claim 
    @apiParam {Int} policyNo 
    @apiParam {String} detail 
    @Produces("application/json")
    @apiSuccessExample (200) {json} Success-Response:
    {
        "status": 200,
        "type": "success",
        "message": "add cliam success"
    }

#### get claims
    @apiName get your claim list
    @api {get}   /claim/my_claims 
    @Produces("application/json")
    @apiSuccessExample (200) {json} Success-Response:
    [
        {
            "claimNo": 1,
            "policyNo": 1,
            "detail": "I lost myself",
            "state": null,
            "feedback": null
        },
        {
            "claimNo": 2,
            "policyNo": 1,
            "detail": "I lost! I lost!",
            "state": null,
            "feedback": null
        }
    ]

#### send verify code
    @apiName send verify code
    @api {get}   /verify_code 
    
#### process claim
    @apiName process claim

#### get claim number
    @apiName get claim number
    @api {get}  /manage/number
    @Produces("application/json")
    @apiSuccessExample (200) {json} Success-Response:
    {
        "processed": 9,
        "unprocessed": 1
    }

<br><br><br>
####test user
name:testuser   
pass:123456 

####test employee
name:testemployee   
pass:123456 


###### todo
* test auto generate api document (fail)
* change framework to spring boot (give up)
* 邮件发送 (success)
* 改写web.xml为autoscan模式 (fail)
* 显示旧的信息+新的content
* 对所有网页添加token验证 (...好麻烦啊,spring真香)
* 单元测试...咋写啊!

ppt：
10min 边说边点 -1
9min 做过：.lostluggage 完成：
后端：
1. what：database, 域名，服务器，API接口，
2. how：前端：what：界面s，bootstrap，下载数据到表格
前后端连接：详细。

Customer：
登录—> 用ajax发送登录请求 —> 返回状态码 （set userCookie）—> customer主页面（个人信息还没完成） —>
丢失行李可以点 —> Claim界面 —> 输入信息，以post形式上传到链接 —> 成功
•process界面 —> 显示所有丢失行李的claim
•policy界面 —> 显示所有已购买的policy


employee：
登录 —> 用ajax发送登录请求 —> 返回状态码 （set userCookie）—> dashboard （get未完成及完成的表单有多少个）—>
Unprocessed get所有state为null的claim，点击链接得到policyNo，跳转到processing界面，
发送policyNo到后端，后端把该保单所有信息发送到processing界面，点击按钮设置state（后端更新state） —>
跳转到Unprocessed界面，claim-1



