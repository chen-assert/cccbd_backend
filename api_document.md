below documents are deprecated, new document now change to:
https://cccbd.top:8443/RESTHello/apidoc/index.html

[TOC]
## Register & Login
#### login
    @apiName login
    @api {get/post}   /login/send/              user login
    @api {get/post}   /login/send_employee/     employee login
    @apiParam {String} username 
    @apiParam {String} password 
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

#### Register
    @apiName register
    @api {post}   /login/send/
    @apiParam {String} username 
    @apiParam {String} password 
    @apiParam {String} gender
    @apiParam {String} email
    @apiParam {String} verified_code
    @apiSuccess (200) {PlainText}
    @apiSuccessExample {PlainText} 
    Register success, your username is: username

## Policy & Claim

#### get policies
    @apiName get your policy list
    @apiPermission  need user_token
    @api {get}   /policy/my_policies 
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


#### add a new claim
    @apiName add a new claim
    @apiPermission  need user_token
    @api {post}   /claim/new_claim 
    @apiParam {Int} policyNo 
    @apiParam {String} detail 
    @apiParam {String} real_name
    @apiParam {Date} claim_date
    @apiParam {Date} loss_date
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
    @apiParam {String} state        要修改为的状态
    @apiParam {String} feedback     反馈
    @apiParam {String} claimNo      要修改的claimNo
    @apiSuccessExample (200) {PlainText} "success"

#### append more information(user)(传过去的信息会附加到数据库字段中的末尾)
    @apiName append more information
    @api {post}     /manage/append
    @apiParam {String} appendage    要附加的新信息
    @apiParam {String} claimNo      要修改的claimNo
    @apiSuccessExample (200) {PlainText} "success"

#### modify information(user)(会删除原先detail并且用新信息覆盖)
    @apiName modify information
    @api {post}     /manage/modify
    @apiParam {String} modification     修改为的新信息
    @apiParam {String} claimNo          要修改的claimNo
    @apiSuccessExample (200) {PlainText} "success"    


#### get rest claim quantity
    @apiName get claim number
    @api {get}  /manage/number      to employee
    @api {get}  /manage/my_number   to user
    @apiSuccessExample (200) {json} Success-Response:
    {
        "processed": 8,
        "unprocessed": 2
    }

#### send verify code
    @apiName send verify code for register
    @api {post}   /verify_code/new_account  sended email address
    @apiParam {String} address  邮箱地址 
    @apiSuccess (200) {PlainText}



## transaction
#### products list
    @apiName get product list
    @apiPermission  all
    @api {get}   /transaction/products_list 
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

#### get product detail
    @apiName get a product detail
    @apiPermission  all
    @api {get}   /transaction/product_detail
    @apiParam {Int} productNo
    @apiSuccessExample (200) {json} Success-Response:
    {
        "productNo": 1,
        "productName": "Product1",
        "content": "This is a really good insurance",
        "price": 100
    }

#### buy product
    @apiName buy a new product
    @apiPermission  need user_token
    @api {post}   /transaction/buy_product
    @apiParam {Int} productNo

## Reset Password

#### Send verify code for reset password 
    @apiName send verify code for reset password
    @api {post}   /verify_code/email_employee
    @api {post}   /verify_code/email_customer
    @apiParam {String} address  邮箱地址 
    @apiSuccess (200) {PlainText}


#### Reset
    @apiName reset
    @api {post}   /verify_code/reset_employee
    @api {post}   /verify_code/reset_customer
    @apiParam {String} username
    @apiParam {String} password
    @apiParam {String} email
    @apiParam {String} verified_code
    @apiSuccess (200) {PlainText}


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
