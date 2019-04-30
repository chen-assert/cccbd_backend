define({ "api": [
  {
    "group": "Login_Register",
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
    "groupTitle": "Login_Register",
    "name": "Get_postLoginSend"
  },
  {
    "group": "Login_Register",
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
    "groupTitle": "Login_Register",
    "name": "Get_postLoginSend_employee"
  },
  {
    "group": "Login_Register",
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
    "groupTitle": "Login_Register",
    "name": "PostLoginSend"
  },
  {
    "group": "Login_Register",
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
    "groupTitle": "Login_Register",
    "name": "PostVerify_codeNew_account"
  },
  {
    "group": "Policy",
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
    "groupTitle": "Policy"
  }
] });
