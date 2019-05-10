define({ "api": [
  {
    "group": "Login_and_Register",
    "type": "get/post",
    "url": "/login/send/",
    "title": "login as customer",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "status",
            "description": "<p>status code</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "type",
            "description": "<p>login type</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>login user name</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>appended text message.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n\"status\": 200,\n\"type\": \"success\",\n\"username\": \"myname\",\n\"message\": \"Login success\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "{\n\"status\": 403,\n\"type\": \"fail\",\n\"username\": \"myname\",\n\"message\": \"Login fail\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/login_and_register/Login.java",
    "groupTitle": "Login_and_Register",
    "name": "Get_postLoginSend"
  },
  {
    "group": "Login_and_Register",
    "type": "get/post",
    "url": "/login/send_employee/",
    "title": "login as employee",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "status",
            "description": "<p>status code</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "type",
            "description": "<p>login type</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>login user name</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>appended text message.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n\"status\": 200,\n\"type\": \"success\",\n\"username\": \"myname\",\n\"message\": \"Login success\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "{\n\"status\": 403,\n\"type\": \"fail\",\n\"username\": \"myname\",\n\"message\": \"Login fail\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/login_and_register/Login.java",
    "groupTitle": "Login_and_Register",
    "name": "Get_postLoginSend_employee"
  },
  {
    "group": "Login_and_Register",
    "type": "post",
    "url": "/login/send/",
    "title": "customer register",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "gender",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "verified_code",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "passport_no",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "Response",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/login_and_register/Register.java",
    "groupTitle": "Login_and_Register",
    "name": "PostLoginSend"
  },
  {
    "group": "Login_and_Register",
    "type": "post",
    "url": "/verify_code/(email_employee/email_customer)",
    "title": "send verify code for reset password",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "address",
            "description": "<p>邮箱地址</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "Response",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/mail/Mail.java",
    "groupTitle": "Login_and_Register",
    "name": "PostVerify_codeEmail_employeeEmail_customer"
  },
  {
    "group": "Login_and_Register",
    "type": "post",
    "url": "/verify_code/new_account",
    "title": "send verify code for register",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "address",
            "description": "<p>邮箱地址</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "Response",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/mail/Mail.java",
    "groupTitle": "Login_and_Register",
    "name": "PostVerify_codeNew_account"
  },
  {
    "group": "Login_and_Register",
    "type": "post",
    "url": "/verify_code/(reset_employee/reset_customer)",
    "title": "reset password",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "verified_code",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "Response",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/mail/Mail.java",
    "groupTitle": "Login_and_Register",
    "name": "PostVerify_codeReset_employeeReset_customer"
  },
  {
    "group": "Manage",
    "type": "post",
    "url": "/manage/append",
    "title": "append more information to claim",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "appendage",
            "description": "<p>要附加的新信息</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "claimNo",
            "description": "<p>要修改的claimNo</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "Response",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/manage/Process.java",
    "groupTitle": "Manage",
    "name": "PostManageAppend"
  },
  {
    "group": "Manage",
    "type": "post",
    "url": "/manage/modify",
    "title": "modify claim information",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "modification",
            "description": "<p>要修改为的新信息</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "claimNo",
            "description": "<p>要修改的claimNo</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "Response",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/manage/Process.java",
    "groupTitle": "Manage",
    "name": "PostManageModify"
  },
  {
    "group": "Manage",
    "name": "process_claim",
    "type": "post",
    "url": "/manage/process",
    "title": "process claim",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "state",
            "description": "<p>要修改为的状态</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "feedback",
            "description": "<p>反馈</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "claimNo",
            "description": "<p>要修改的claimNo</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "Response",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/manage/Process.java",
    "groupTitle": "Manage"
  },
  {
    "group": "Policy_and_Claim",
    "permission": [
      {
        "name": "user or employee"
      }
    ],
    "type": "get",
    "url": "/claim/detail",
    "title": "get a claim detail",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Int",
            "optional": false,
            "field": "ClaimNo",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "(200) {json} Success-Response:",
          "content": "{\n\"ClaimNo\": \"34\",\n\"policyNo\": \"2\",\n\"detail\": \"detail blablabla\",\n\"real_name\": \"chen\",\n\"claim_date\": \"2019-04-02\",\n\"loss_date\": \"2019-04-01\",\n\"feedback\": null\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/policy_and_claim/Claim_user.java",
    "groupTitle": "Policy_and_Claim",
    "name": "GetClaimDetail"
  },
  {
    "group": "Policy_and_Claim",
    "permission": [
      {
        "name": "user"
      }
    ],
    "type": "get",
    "url": "/claim/my_claims",
    "title": "get user's claim list",
    "success": {
      "examples": [
        {
          "title": "(200) {json} Success-Response:",
          "content": "[\n{\n\"claimNo\": 1,\n\"policyNo\": 1,\n\"detail\": \"I lost myself\",\n\"state\": \"accept\",\n\"feedback\": null\n},\n{\n\"claimNo\": 2,\n\"policyNo\": 1,\n\"detail\": \"I lost! I lost!\",\n\"state\": \"waiting\",\n\"feedback\": null\n}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/policy_and_claim/Claim_user.java",
    "groupTitle": "Policy_and_Claim",
    "name": "GetClaimMy_claims"
  },
  {
    "group": "Policy_and_Claim",
    "permission": [
      {
        "name": "user"
      }
    ],
    "type": "post",
    "url": "/claim/new_claim",
    "title": "add a new claim",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Int",
            "optional": false,
            "field": "policyNo",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "detail",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "real_name",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "claim_date",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "loss_date",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "(200) {json} Success-Response:",
          "content": "{\n\"status\": 200,\n\"type\": \"success\",\n\"message\": \"Add cliam successfully\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/policy_and_claim/Claim_user.java",
    "groupTitle": "Policy_and_Claim",
    "name": "PostClaimNew_claim"
  },
  {
    "group": "Policy_and_Claim",
    "name": "get_waiting_processed_claims",
    "permission": [
      {
        "name": "employee"
      }
    ],
    "type": "get",
    "url": "/claim/(waiting_claims/processed_claims)",
    "title": "get waiting/processed claims",
    "success": {
      "examples": [
        {
          "title": "(200) {json} Success-Response:",
          "content": "[\n{\n\"claimNo\": 2,\n\"policyNo\": 1,\n\"detail\": \"I lost! I lost!\",\n\"state\": \"waiting\",\n\"feedback\": null\n},\n{\n\"claimNo\": 3,\n\"policyNo\": 2,\n\"detail\": \"ahhhhhh\",\n\"state\": \"waiting\",\n\"feedback\": null\n}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/policy_and_claim/Claim_employee.java",
    "groupTitle": "Policy_and_Claim"
  },
  {
    "group": "Policy_and_Claim",
    "name": "nameIsUseless",
    "permission": [
      {
        "name": "user"
      }
    ],
    "type": "get",
    "url": "/policy/my_policies",
    "title": "get your policy list",
    "success": {
      "examples": [
        {
          "title": "(200) {json} Success-Response:",
          "content": "[\n{\n\"policyNo\": 1,\n\"policyName\": \"testpolicy\",\n\"content\": \"insure everything!\"\n},\n{\n\"policyNo\": 2,\n\"policyName\": \"testpolicy2\",\n\"content\": \"insure nothing!\"\n}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/policy_and_claim/Policy.java",
    "groupTitle": "Policy_and_Claim"
  },
  {
    "group": "manage",
    "name": "get_claim_number",
    "type": "get",
    "url": "/manage/(number/my_number)",
    "title": "get claim number(user/employee)",
    "success": {
      "examples": [
        {
          "title": "(200) {json} Success-Response:",
          "content": "{\n\"processed\": 8,\n\"unprocessed\": 2\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/manage/Process.java",
    "groupTitle": "manage"
  },
  {
    "group": "transaction",
    "type": "get",
    "url": "/transaction/product_detail",
    "title": "get a product detail",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Int",
            "optional": false,
            "field": "productNo",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "(200) {json} Success-Response:",
          "content": "{\n\"productNo\": 1,\n\"productName\": \"Product1\",\n\"content\": \"This is a really good insurance\",\n\"price\": 100\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/transaction/Transaction.java",
    "groupTitle": "transaction",
    "name": "GetTransactionProduct_detail"
  },
  {
    "group": "transaction",
    "type": "get",
    "url": "/transaction/products_list",
    "title": "get product list",
    "success": {
      "examples": [
        {
          "title": "(200) {json} Success-Response:",
          "content": "[\n{\n\"productNo\": 1,\n\"productName\": \"Product1\",\n\"content\": \"This is a really good insurance\",\n\"price\": 100\n},\n{\n\"productNo\": 2,\n\"productName\": \"Product2\",\n\"content\": \"This is a really bad insurance\",\n\"price\": 200\n}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/transaction/Transaction.java",
    "groupTitle": "transaction",
    "name": "GetTransactionProducts_list"
  },
  {
    "group": "transaction",
    "permission": [
      {
        "name": "user"
      }
    ],
    "type": "post",
    "url": "/transaction/buy_product",
    "title": "buy a new product",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Int",
            "optional": false,
            "field": "productNo",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/transaction/Transaction.java",
    "groupTitle": "transaction",
    "name": "PostTransactionBuy_product"
  }
] });
