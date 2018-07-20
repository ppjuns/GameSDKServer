package com.ppjun.game.base


class Constant {
    companion object {
        val SUCCESS_CODE = 200
        val ERROR_CODE = -200
        val SUCCESS_INIT = "初始化成功"
        val ERROR_INIT = "初始化失败"
        val SUCCESS_ADD = "新增成功"
        val ERROR_ADD = "新增失败"
        val ERROR_REPECT_ADD = "新增失败，游戏已存在"
        val ERROR_OPENID_LOGIN = "登录失败,openId 为空"
        val ERROR_NAME_LOGIN = "登录失败,name 为空"
        val ERROR_IMG_LOGIN = "登录失败,img 为空"
        val ERROR_SEX_LOGIN = "登录失败,sex 为空"
        val SUCCESS_LOGIN = "登录成功"

        val WECHAT_APP_ID = ""
        val WECHAT_MCH_ID = ""
        val WECHAT_MCH_KEY = ""

        val ALIPAY_APP_ID = ""
        val ALIPAY_PRIVATE_KEY = ""
        val ALIPAY_PUBLIC_KEY = ""

        val TRADE_TYPE = "trade_type"

        val PAGE_SIZE = 10
        val PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoyvn71uKsafoi7IlYqGkWD1BryA3JoVpRdnA7AqV4C2HwqMOjA/fVfqjLbMIxXn3JEnQXXSIjHqnOuwpafUX9UQL3U0Pdrl3dGI5aco+3cyZ2geElK73jm4WP/wnH5htR7cpqvWgSABhOMi0hhh1bAq3/NgH5CGY+7W7BIxdWM4cL+9+W/cA9LYl3f5PLn/iJZluE/dE9ey7lNWgxrlKjrGe63Ldh53gFC+Yd8Gx/If07THAhlPHAnDHA1yGlp/8VM/mvtWA5yl7feGjsDa8xmj7ktgz29zq58v/5hGX6fETGUZigl24zBQKSgG4flfHY2r+037ljXdlCDMlt+khgQIDAQAB"

        val PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCjK+fvW4qxp+iLsiVioaRYPUGvIDcmhWlF2cDsCpXgLYfCow6MD99V+qMtswjFefckSdBddIiMeqc67Clp9Rf1RAvdTQ92uXd0Yjlpyj7dzJnaB4SUrveObhY//CcfmG1Htymq9aBIAGE4yLSGGHVsCrf82AfkIZj7tbsEjF1Yzhwv735b9wD0tiXd/k8uf+IlmW4T90T17LuU1aDGuUqOsZ7rct2HneAUL5h3wbH8h/TtMcCGU8cCcMcDXIaWn/xUz+a+1YDnKXt94aOwNrzGaPuS2DPb3Orny//mEZfp8RMZRmKCXbjMFApKAbh+V8djav7TfuWNd2UIMyW36SGBAgMBAAECggEAX1pXDX83S55NvOT8B+lh+tbBPa/byr0HyfP4U3fUIc85mLpk0+CqqLnxBdGr/IrLq/8erp5c9YrvkvVTNkIuH/wF2usfcEft0Ktd1b6EgmUAO3NNPP4KWMdoZTfbPRF8Ov6EcQ1c/vaJ2f8fz4UXiW2yGyW7ZstjdSS8B2pnWm8VJw43nBL8q8RwmAL0ox/gSMhVIdADnG1jtMN5oEK/bCY4TS5tYAAiDMb+L8J5xb23ygI2AKsp7pTjkujxWpp6wvJ37XmQS4Ul9u7ByMxtZLn/IiM0cPG69AV9JwHRdaULI4eUoEqTaKYO0db4Y4PFSMsGHzZ40boKivlt6nFbiQKBgQD0/e1g+Dl47JITJrX3g1Fb6G1YJVig/oi8VFli2eqjBNh5PVoej79liNj51ON2nu5hH7sP+xCeFOZeLrGRu0gMdq3L48Vbsdawl695xQaD07fNOB1W2oi4kr7Ia2r9sisGEh/IXvt6Xn8nJ/M57wd1CTZicAQLQbWZ4NQurVCXWwKBgQCqgNJiMnV0d9fy0JsvUislYJUIG5W2xfxtgPSSf4OyjGQFDPvdlH0E/Gey2EkbBmKN6KR6U9PLG2dXi9FIfs4zt+q2QWsYWoTt3x53dgJQA9JZoOMqn6sYQ7/X5uRPVEXMX1OuSTI5l/26sWYV5FM+fvDnqr7HlAXyNfTglSRdUwKBgGvJtMDvNUoQu4wVggXuDsOjFUis1buvgTVq3xDw05z5qsxcw+OiVxEzDlOM6K7t6e9taH31djA2/cv+Oz3k6iQhqwGDqvdW3SBA/IFUwDML2Vg/Ehl+eJzMY4gGSsJnuyommzkrIPWL0eHGX+rjUaKadQUGR6E0PdP/6RrIQib1AoGAPaX2a6ry7PmoCxOCCMoc75bMS/ItX7iWXMGTrKSOabB5OZpINwXJblQU6oVpaWwVllnXnqNQoM93vh+/vAadZ/XH174vis6v4ZIa4fa5P1UiaWXSvUaeBlf4tYe2ZTiDRDHzf8DXeBuMkkQyNRgpQR+E2z7RXDgZLkp0D//c9iECgYEA0wHVnwOg0T2oINNeGIy6RikLLRtWDYWn3d03oGz88O/PYNWQX3HWAMKrqZ+gL4muJ3M+HSHzqjCMniZIMYfI4Ox4wj5uwc5nDNXWnHdXDpbD5DdR0f9mryLlCiO3uwyay74cU/8iNjUSfj8BiFjK66tAhqx7/JZ18E3Rra6ukDA="
    }
}