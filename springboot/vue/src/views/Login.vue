<template>
  <div class="wrapper">
    <div class="loginbox">
      <div class="loginname"><b>用户登录</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" class="inputbox" prefix-icon="el-icon-user"  v-model="user.username" placeholder="请输入用户名/手机号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" class="inputbox" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password v-model="user.password" @keyup.enter.native="login" ></el-input>
        </el-form-item>
        <div class="buttonbox">
          <div style="text-align: left;">
            <el-button class="loginbutton" type="primary" size="medium" autocomplete="off" @click="login">登录</el-button>
          </div>
          <div style="text-align: right;">
            <el-link type="warning" class="registerbutton" @click="$router.push('/register')" autocomplete="off" >没有账号？去注册</el-link>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {setRoutes} from "@/router"
export default {
  name: "Login",
  data(){
    return{
      user:{},
      rules:{
        username:[
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 7, message: '长度在 2 到 7 个字符', trigger: 'blur'}
        ],
        password:[
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 10, message: '长度在 6 到 10 个字符', trigger: 'blur'}
        ],
      },
    }
  },
  methods:{
    login(){
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          this.request.post("/user/login",this.user).then(res => {
            if (res.code === "200"){
              localStorage.setItem("user",JSON.stringify(res.data)) //存储用户信息到浏览器
              localStorage.setItem("menus",JSON.stringify(res.data.menus)) //存储用户信息到浏览器

              setRoutes()
              this.$router.push("/home")
              this.$message.success("登录成功")
            }else{
              this.$message.error(res.msg)
            }
          })
        } else {
          return false;
        }
      });

    },
  }

}
</script>


<style>
  .wrapper{
    height: 100vh;
    /*线性梯度渐变色 linear-gradient*/
    /*background-image: linear-gradient(to bottom right, #FC466B,#3F5EFB);*/
    background-image: url("https://img.zcool.cn/community/0191bc5e79b39ca80120a8954ce258.jpg?x-oss-process=image/auto-orient,1/resize,m_lfit,w_1280,limit_1/sharpen,100/format,webp/quality,Q_100");
    overflow: hidden;
  }
  .loginname{
    margin: 10px 0;
    text-align: center;
    font-size: 25px;
    color: #fff;
    letter-spacing: 1px;
    /*字符间的间距1px*/
  }
  .loginbox{
    margin: 200px auto;
    background: rgba(0,0,0,.7);
    /*背景颜色为黑色，透明度为0.7*/
    box-shadow: 3px 15px 18px rgba(0,0,0,.6);
    /*边框阴影  水平阴影3 垂直阴影15px 模糊18px 颜色黑色透明度0.6*/
    width: 400px;
    hight: 300px;
    padding: 18px 24px;
    border-radius: 13px;
  }
  .inputbox{
    margin: 5px 0;
  }
  .buttonbox{
    margin: 8px 0;
  }
  .loginbutton{
    font-size: 16px;
    letter-spacing: 8px;
    width: 120px;
  }
  .registerbutton{
    font-size: 12px;
    letter-spacing: 2px;
  }
</style>