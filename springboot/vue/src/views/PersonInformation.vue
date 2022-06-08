<template>
  <div>
    <el-card style="width:500px;margin:50px auto;">
      <div style="margin: 20px 0;text-align: center;"><b>个人信息</b></div>
      <div style="text-align: left; padding: 0px 10px">
        <el-form label-width="80px">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/file/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
            <img v-if="form.avatar" :src="form.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <el-form-item label="用户名">
            <el-input v-model="form.username" disabled autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="form.nickname" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="form.email" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="电话">
            <el-input v-model="form.phone" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="地址">
            <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width:300px;height: 40px;font-size: medium;margin-left: 20px" type="primary" @click="save">确 定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "PersonInformation",
  data(){
    return{
      form:{},
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.getUser().then(res => {
      this.form = res
    })
  },
  methods:{
    async getUser(){
      return (await this.request.get("/user/username/" + this.user.username)).data
    },
    save(){
      this.request.post("/user",this.form).then(res => {//请求接口，request.js中设置api 简化url
        if(res.code === '200'){
          this.$message.success("保存成功")

          //触发父级获取User的方法
          this.$emit("refreshUser")

        }else {
          this.$message.error("保存失败")
        }
      })
    },

    handleAvatarSuccess(res) {
      this.form.avatar = res
    }

  }
}
</script>

<style>
.avatar-uploader{
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}

</style>