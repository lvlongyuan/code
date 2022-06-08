<template>
  <!--注册-->
  <div class="wrapper">
    <div class="loginbox">
      <div class="loginname"><b>用户注册</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" class="inputbox" prefix-icon="el-icon-user"  v-model="user.username" placeholder="请输入用户名/手机号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" class="inputbox" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password v-model="user.password" ></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input size="medium" class="inputbox" prefix-icon="el-icon-lock" placeholder="确认密码" show-password v-model="user.confirmPassword" ></el-input>
        </el-form-item>
        <div class="buttonbox">
          <div style="text-align: center;">
            <el-button class="buttonbox2" type="primary" size="medium" autocomplete="off" @click="register">注册</el-button>
            <el-button class="buttonbox2" type="warning" size="medium" autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
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
        confirmPassword:[
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { min: 6, max: 10, message: '长度在 6 到 10 个字符', trigger: 'blur'}
        ],
      }
    }
  },
  methods:{
    register(){
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          if(this.user.password !== this.user.confirmPassword){
            this.$message.warning("两次密码不一致")
            return false;

          }
          this.request.post("/user/register",this.user).then(res => {
            if (res.code === "200"){
              this.$message.success("注册成功")
            }else{
              this.$message.error(res.msg)
            }
          })
        } else {
          return false;
        }
      });
    },
  },

}
</script>


<style>
.wrapper{
  height: 100vh;
  background-image: url("https://img.zcool.cn/community/0168255e79b36fa801216518395a25.jpg?x-oss-process=image/auto-orient,1");
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
  margin: 150px auto;
  background: rgba(0,0,0,.3);
  /*背景颜色为黑色，透明度为0.5*/
  box-shadow: 3px 15px 18px rgba(0,0,0,.6);
  /*边框阴影  水平阴影3 垂直阴影15px 模糊18px 颜色黑色透明度0.6*/
  width: 350px;
  hight: 280px;
  padding: 18px 24px;
  border-radius: 13px;
}
.inputbox{
  margin: 3px 0;
}
.buttonbox{
  margin: 10px 0;
}
.buttonbox2{
  font-size: 15px;
  letter-spacing: 8px;
  width: 145px;
}
</style>