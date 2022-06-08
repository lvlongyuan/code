<template>
  <div style="height: 100%">
    <el-container style="min-height: 100vh;">
      <el-aside :width="sideWidth +'px'" style="background-color: rgb(238, 241, 246);min-height:100%;box-shadow:2px 0 6px rgb(0 21 41 /38%)">
        <Aside :isCollapse="isCollapse" :logoTextShow = "logoTextShow"/>
      </el-aside>

      <el-container>
        <el-header style="border-bottom: 1px solid #ccc">
          <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user"/>
        </el-header>

        <el-main style="padding: 0px;margin-top:0px">
          <!--当前页面的 子路由 会在这里 <router-view/> 展示-->
          <router-view @refreshUser="getUser" />
        </el-main>
      </el-container>

    </el-container>
  </div>
</template>

<script>

//@ is an alias to /src

import Aside from '@/components/Aside.vue';
import Header from '@/components/Header.vue';

export default {
    data() {
      return {

        collapseBtnClass:'el-icon-s-fold',//设置‘收缩图标’，提醒点击可以收缩侧边栏
        isCollapse:false,//开始设置展开
        sideWidth:200,
        logoTextShow:true,
        user:{}
      }
    },
  components:{
      Aside,
      Header
  },
  created() {
      //一开始就在后台获取最新的User数据
      this.getUser()
  },
  methods:{
    collapse(){
        //点击收缩按钮触发
        this.isCollapse = !this.isCollapse//点击可以触发收缩和展开侧边栏互相变换
        if (this.isCollapse){//------收缩
          this.sideWidth = 64//点击收缩按钮将侧边栏设为64px实现收缩效果
          this.collapseBtnClass = "el-icon-s-unfold"//收缩效果实现后将‘收缩图标’变换为‘展开图标’
          this.logoTextShow = false
        }else{//------展开
          this.sideWidth = 200
          this.collapseBtnClass = "el-icon-s-fold"
          this.logoTextShow = true
        }
      },
    getUser(){
      let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
      //从后台获取user数据
      this.request.get("/user/username/" + username).then(res => {
        //重新赋值后台最新的User数据
        this.user = res.data
      })
    }
    }
  };
</script>