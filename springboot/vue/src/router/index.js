import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manage.vue'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/404',
    name: '404',
    component: () => import('../views/404.vue')
  }
]

// const routes = [
//   {
//     path: '/',
//     component: () => import('../views/Manage.vue'),
//     redirect:"/home",
//     //基于主框架的子路由，中间区域的变动
//     children:[
//       {path: 'home', name: '', component: () => import('../views/Home.vue')},
//       {path: 'user', name: '用户管理', component: () => import('../views/User.vue')},
//       {path: 'role', name: '角色管理', component: () => import('../views/Role.vue')},
//       {path: 'menu', name: '菜单管理', component: () => import('../views/Menu.vue')},
//       {path: 'discern', name: '植物识别', component: () => import('../views/Discern.vue')},
//       {path: 'weather', name: '天气', component: () => import('../views/Weather.vue')},
//       {path: 'warn', name: '预警信号', component: () => import('../views/Warn.vue')},
//       {path: 'skill', name: '种植技术', component: () => import('../views/Skill.vue')},
//       {path: 'market', name: '农产品市场价格', component: () => import('../views/Market.vue')},
//       {path: 'priceindex', name: '农产品批发价格200指数表', component: () => import('../views/PriceIndex.vue')},
//       {path: 'priceindexpic', name: '农产品批发价格200指数表', component: () => import('../views/PricePic.vue')},
//     ],
//
//   },
//   {
//     path: '/manger',
//     component: () => import('../views/Amanger.vue'),
//     //基于主框架的子路由，中间区域的变动
//     children:[
//       {path: 'home', name: '后台管理系统主页', component: () => import('../views/Ahome.vue')},
//       {path: 'userdata', name: '用户数据', component: () => import('../views/UserData.vue')},
//       {path: 'file', name: '文件管理', component: () => import('../views/File.vue')},
//     ],
//
//   },
//   {
//     path: '/login',
//     name: 'Login',
//     component: () => import('../views/Login.vue')
//   },
//   {
//     path: '/alogin',
//     name: 'ALogin',
//     component: () => import('../views/ALogin.vue')
//   },
//   {
//     path: '/register',
//     name: 'Register',
//     component: () => import('../views/Register.vue')
//   },
//   {
//     path: '/ahome',
//     name: 'Ahome',
//     component: () => import('../views/Amanger.vue')
//   },
//
// ]



const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//提供一个重置路由的方法
export const resetRouter = () => {
  router.matcher = new VueRouter({
    mode:"history",
    base:process.env.BASE_URL,
    routes
  })
}

export const setRoutes = () =>{
  const storeMenus = localStorage.getItem("menus");
  if(storeMenus){


    //获取当前路由对象名称数组
    const currentRouteNames = router.getRoutes().map(v => v.name)
    if(!currentRouteNames.includes('Manage')){
      //拼装动态路由
      const manageRoute = {path:'/', name:'Manage',component:() => import('../views/Manage.vue'),redirect:"/home",children:[
          {path: 'person', name: '个人信息', component: () => import('../views/PersonInformation.vue')},
        ]}
      const menus = JSON.parse(storeMenus)
      menus.forEach(item => {
        if(item.path){//当且仅当path不为空的时候才去设置路由
          let itemMenu = {path:item.path.replace("/",""),name:item.name,component:() => import('../views/' + item.pagePath + '.vue')}
          manageRoute.children.push(itemMenu)
        }else if(item.children.length){
          item.children.forEach(item => {
            if(item.path){
              let itemMenu = {path:item.path.replace("/",""),name:item.name,component:() => import('../views/' + item.pagePath + '.vue')}
              manageRoute.children.push(itemMenu)
            }
          })
        }
      })
      //动态添加到现在的路由对象中去
      router.addRoute(manageRoute)
    }

  }
}

//重置再set一次路由
setRoutes()

//路由守卫
router.beforeEach((to,from,next)=>{
  localStorage.setItem("currentPathName",to.name) //设置当前路由名称 为了在Header组件中去使用
  store.commit("setPath") //触发store的数据更新

  //未找到路由的情况
  if(!to.matched.length){
    const storeMenus = localStorage.getItem("menus")
    if(storeMenus){
      next("/404")
    }else{
      //跳回到登录界面
      next("/login")
    }
  }
  next()//放行路由
})

export default router
