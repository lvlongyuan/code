import vue from 'vue'
import vuex from 'vuex'
import router, {resetRouter} from "@/router";
//vuex
vue.use(vuex)

const store = new vuex.Store({
    state: {
        currentPathName : ''
    },
    mutations: {
        setPath (state) {
            state.currentPathName = localStorage.getItem("currentPathName")
        },
        logout(){
            //清空缓存
            localStorage.removeItem("user")
            localStorage.removeItem("menus")
            router.push("/login")

            //重置路由
            resetRouter()
        }
    }
})
export default store
