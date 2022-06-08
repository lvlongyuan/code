<template>
    <el-menu :default-openeds="opens" style="min-height: 100%;overflow: hidden"
             background-color="rgb(48, 65, 86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             :collapse="isCollapse"
             router
             @select="handleSelect"
    >
      <div style="height: 60px;line-height: 60px;text-align: center">
        <img src="../assets/logo.png" alt="logo" style="width: 20px;position:relative;top:5px;right: 3px">
        <b style="color: white" v-show="logoTextShow">农业信息系统</b>
      </div>
      <div v-for="item in menus" :key="item.id">
        <div v-if="item.path">
          <el-menu-item :index="item.path">
            <i :class="item.icon"></i>
            <span slot="title">{{item.name}}</span>
          </el-menu-item>
        </div>
        <div v-else>
          <el-submenu :index="item.id + '' ">
            <template slot="title">
              <i :class="item.icon"></i>
              <span slot="title">{{item.name}}</span>
            </template>
            <div v-for="subItem in item.children" :key="subItem.id">
              <el-menu-item :index="subItem.path">
                <i :class="subItem.icon"></i>
                <span slot="title">{{subItem.name}}</span>
              </el-menu-item>
            </div>
          </el-submenu>
        </div>
      </div>

<!--      <el-menu-item index="/">-->
<!--          <i class="el-icon-s-home"></i>-->
<!--          <span slot="title">主页</span>-->
<!--      </el-menu-item>-->

<!--      <el-submenu index="2">-->
<!--        <template slot="title">-->
<!--          <i class="el-icon-menu"></i>-->
<!--          <span slot="title">农业信息</span>-->
<!--        </template>-->
<!--        <el-menu-item index="/discern">-->
<!--          <i class="el-icon-full-screen"></i>-->
<!--          <span slot="title">植物识别</span>-->
<!--        </el-menu-item>-->
<!--        <el-menu-item index="/skill">-->
<!--          <i class="el-icon-full-screen"></i>-->
<!--          <span slot="title">种植技术</span>-->
<!--        </el-menu-item>-->
<!--        <el-menu-item index="/market">-->
<!--          <i class="el-icon-folder-opened"></i>-->
<!--          <span slot="title">最新农作物市场价格</span>-->
<!--        </el-menu-item>-->
<!--        <el-menu-item index="/priceindex">-->
<!--          <i class="el-icon-folder-opened"></i>-->
<!--          <span slot="title">农产品价格指数</span>-->
<!--        </el-menu-item>-->
<!--      </el-submenu>-->

<!--      <el-submenu index="3">-->
<!--        <template slot="title">-->
<!--          <i class="el-icon-wind-power"></i>-->
<!--          <span slot="title">气象</span>-->
<!--        </template>-->
<!--        <el-menu-item index="/weather">-->
<!--          <i class="el-icon-sunny"></i>-->
<!--          <span slot="title">天气情况</span>-->
<!--        </el-menu-item>-->
<!--        <el-menu-item index="/warn">-->
<!--          <i class="el-icon-warning"></i>-->
<!--          <span slot="title">气象灾害预警信号</span>-->
<!--        </el-menu-item>-->
<!--      </el-submenu>-->

<!--      <el-submenu index="3">-->
<!--        <template slot="title">-->
<!--          <i class="el-icon-wind-power"></i>-->
<!--          <span slot="title">系统管理</span>-->
<!--        </template>-->
<!--        <el-menu-item index="/user">-->
<!--          <i class="el-icon-sunny"></i>-->
<!--          <span slot="title">用户管理</span>-->
<!--        </el-menu-item>-->
<!--        <el-menu-item index="/role">-->
<!--          <i class="el-icon-warning"></i>-->
<!--          <span slot="title">角色管理</span>-->
<!--        </el-menu-item>-->
<!--        <el-menu-item index="/menu">-->
<!--          <i class="el-icon-warning"></i>-->
<!--          <span slot="title">菜单管理</span>-->
<!--        </el-menu-item>-->
<!--      </el-submenu>-->


    </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse:Boolean,
    logoTextShow:Boolean
  },
  data(){
    return{
      menus :localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
      opens :localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : []
    }
  },
  methods:{
    handleSelect(){
      this.$route.fullPath.split("/")
    }
  }
}
</script>

<style scoped>

</style>