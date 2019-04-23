Seems need to write a API document...<br>

http://cccbd.top:8080/RESTHello
[TOC]
## Register & Login
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
    @api {post}   /login/send/
    @apiParam {String} username 
    @apiParam {String} password 
    @apiParam {String} gender
    @apiParam {String} email
    @apiParam {String} verified_code
    @Produces("text/plain")
    @apiSuccess (200) {PlainText}
    @apiSuccessExample {PlainText} 
    Register success, your username is: username

## Policy

#### get policies
    @apiName get your policy list
    @apiPermission  need user_token
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

## Claim 

#### add a new claim
    @apiName add a new claim
    @apiPermission  need user_token
    @api {post}   /claim/new_claim 
    @apiParam {Int} policyNo 
    @apiParam {String} detail 
    @apiParam {String} real_name
    @apiParam {Date} claim_date
    @apiParam {Date} loss_date
    @Produces("application/json")
    @apiSuccessExample (200) {json} Success-Response:
    {
        "status": 200,
        "type": "success",
        "message": "Add cliam successfully"
    }

#### get claims
    @apiName get your claim list
    @apiPermission  need user_token
    @api {get}   /claim/my_claims 
    @Produces("application/json")
    @apiSuccessExample (200) {json} Success-Response:
    [
        {
            "claimNo": 1,
            "policyNo": 1,
            "detail": "I lost myself",
            "state": "accept",
            "feedback": null
        },
        {
            "claimNo": 2,
            "policyNo": 1,
            "detail": "I lost! I lost!",
            "state": "waiting",
            "feedback": null
        }
    ]

#### get a claim detail
    @apiName get a claim detail
    @apiPermission  need user_token or employee_token
    @api {get}   /claim/detail 
    @apiParam {Int} ClaimNo 
    @Produces("application/json")
    @apiSuccessExample (200) {json} Success-Response:
    {
        "ClaimNo": "34",
        "policyNo": "2",
        "detail": "detail blablabla",
        "real_name": "chen",
        "claim_date": "2019-04-02",
        "loss_date": "2019-04-01",
        "feedback": null
    }

#### waiting/processed claims
    @apiName get waiting/processed claims
    @apiPermission  need employee_token
    @api {get}  /claim/waiting_claims           waiting(unprocessed) claims
    @api {get}  /claim/processed_claims         processed claim
    @Produces("application/json")
    @apiSuccessExample (200) {json} Success-Response:
    [
        {
            "claimNo": 2,
            "policyNo": 1,
            "detail": "I lost! I lost!",
            "state": "waiting",
            "feedback": null
        },
        {
            "claimNo": 3,
            "policyNo": 2,
            "detail": "ahhhhhh",
            "state": "waiting",
            "feedback": null
        }
    ]

#### waiting/processed claims(for single user)
    @apiName get waiting/processed claims
    @apiPermission  need user_token
    @api {get}  /claim/waiting_claims_user          waiting(unprocessed) claims
    @api {get}  /claim/processed_claims_user        processed claim
    @Produces("application/json")
    @apiSuccessExample (200) {json} Success-Response:
    [
        {
            "claimNo": 2,
            "policyNo": 1,
            "detail": "I lost! I lost!",
            "state": "waiting",
            "feedback": null
        },
        {
            "claimNo": 3,
            "policyNo": 2,
            "detail": "ahhhhhh",
            "state": "waiting",
            "feedback": null
        }
    ]

   

#### process claim(employee)
    @apiName process claim
    @api {post}     /manage/process
    @Produces("text/plain")
    @apiParam {String} state        要修改为的状态
    @apiParam {String} feedback     反馈
    @apiParam {String} claimNo      要修改的claimNo
    @apiSuccessExample (200) {PlainText} "success"

#### append more information(user)(传过去的信息会附加到数据库字段中的末尾)
    @apiName append more information
    @api {post}     /manage/append
    @Produces("text/plain")
    @apiParam {String} appendage    要附加的新信息
    @apiParam {String} claimNo      要修改的claimNo
    @apiSuccessExample (200) {PlainText} "success"

#### modify information(user)(会删除原先detail并且用新信息覆盖)
    @apiName modify information
    @api {post}     /manage/modify
    @Produces("text/plain")
    @apiParam {String} modification     修改为的新信息
    @apiParam {String} claimNo          要修改的claimNo
    @apiSuccessExample (200) {PlainText} "success"    


#### get rest claim quantity
    @apiName get claim number
    @api {get}  /manage/number      to employee
    @api {get}  /manage/my_number   to user
    @Produces("application/json")
    @apiSuccessExample (200) {json} Success-Response:
    {
        "processed": 8,
        "unprocessed": 2
    }

#### send verify code
    @apiName send verify code for register
    @api {post}   /verify_code/new_account  sended email address
    @apiParam {String} address  邮箱地址 
    @Produces("text/plain")
    @apiSuccess (200) {PlainText}

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
## transaction
#### products list
    @apiName get product list
    @apiPermission  all
    @api {get}   /transaction/products_list 
    @Produces("application/json")
    @apiSuccessExample (200) {json} Success-Response:
    [
        {
            "productNo": 1,
            "productName": "Product1",
            "content": "This is a really good insurance",
            "price": 100
        },
        {
            "productNo": 2,
            "productName": "Product2",
            "content": "This is a really bad insurance",
            "price": 200
        }
    ]
#### buy product
    @apiName add a new claim
    @apiPermission  need user_token
    @api {post}   /transaction/buy
    @apiParam {Int} productNo
    @Produces("text/plain")
<br><br><br>
## test user
name:testuser   
pass:123456 

## test employee
name:testemployee   
pass:123456 


## todo
* test auto generate api document (fail)
* change framework to spring boot (give up)
* 邮件发送 (success)
* 改写web.xml为autoscan模式 (fail)
* 显示旧的信息+新的content
* 对所有网页添加token验证 (...好麻烦啊,spring真香)
* 单元测试...咋写啊!
* 参考resteasy最佳实践

presentation-flow
ppt:
边展示(注册与登陆)边介绍前端:3min
what:界面，bootstrap，下载数据到表格
how:
benefit:use bootstrap, easy to cross platform/browser, and adapt mobile platform
后端:2min
what:database, 域名，服务器，RESTful API, micro server
how:Digital Ocean cloud server with tomcat
benefit:High Scalability and Flexibility
前后端连接:详细1min
how to pack(打包) to mobile:1min
展示2:1min
展望未来计划1min
ask:question 1 min

Customer：
登录—> 用ajax发送登录请求 —> 返回状态码 （set userCookie）—> customer主页面（个人信息还没完成） —>
丢失行李可以点 —> Claim界面 —> 输入信息，以post形式上传到链接 —> 成功
•process界面 —> 显示所有丢失行李的claim
•policy界面 —> 显示所有已购买的policy

employee：
登录 —> 用ajax发送登录请求 —> 返回状态码 （set userCookie）—> dashboard （get未完成及完成的表单有多少个）—>
Unprocessed get所有state为waiting的claim，点击链接得到policyNo，跳转到processing界面，
发送policyNo到后端，后端把该保单所有信息发送到processing界面，点击按钮设置state（后端更新state） —>
跳转到Unprocessed界面，claim-1



