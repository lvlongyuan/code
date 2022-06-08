<template>
  <div style="line-height: 60px; display: flex" >
    <div style="flex: 1">
      <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse"></span>
      <!--面包屑-->
        <el-breadcrumb separator-class="el-icon-arrow-right" style="display: inline-block ;margin-left:10px">
          <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
          <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
        </el-breadcrumb>
    </div>

    <el-dropdown style="width: 150px; cursor: pointer; text-align: right">
      <div style="display: inline-block">

        <img :src="user.avatar"
             alt=""
             style="width: 36px; border-radius: 50%;position: relative;top: 10px;right: 5px">
        <div style="display: inline-block">
          <span v-if="user.nickname">{{user.nickname}}</span>
          <span v-else @click="goLogin"><img src="../pic/登录.png" style="width: 50px; border-radius: 50%;position: relative;top: 8px;right: 6px"></span>
        </div>
        <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>
      <!--停浮在该标签上第一个标签控制展开-->
      <el-dropdown-menu slot="dropdown" style="width:100px; text-align:center">
        <el-dropdown-item style="font-size:14px; padding:5px 0">
          <router-link style="color: grey; text-decoration: none" to="/person"><i>个人信息</i></router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size:14px; padding:5px 0">
          <i style="color: grey;text-decoration: none" @click="logout">退出</i>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>



  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass:String,
    user:Object,
    collapse:Function
  },
  data(){
    return{

    }
  },
  methods:{
    goLogin(){
      this.$router.push("/login")
    },
    logout(){
      this.$store.commit("logout")
      this.$message.success("退出成功")
    }
  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName; //需要监听的数据
    }
  },
  watch:{
    currentPathName(newVal,oldVal){
      // console.log(newVal)
    }
  }
}
</script>

<style scoped>

</style>