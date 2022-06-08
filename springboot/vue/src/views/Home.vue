<template>
  <div class="wapper">
    <div>
      <el-button
          plain
          type="text"
          @click="open2">
        系统简介
      </el-button>
    </div>
    <img src="../pic/ppp.png" style="width: 200px;height: 100px;display: block; margin: 0 auto;margin-top: 150px" >
    <el-card>
      <el-row>
        <el-col :span="12"style="text-align: center">
          <img src="../pic/地点定位_o.png" style="height: 100px;width: 100px">
          <p style="margin: 5px 0px;font-size: 18px;">当前位置:{{city}}</p>
        </el-col>
        <el-col :span="12">
          <p style="margin: 12px 0px;">今日天气:{{wea}}</p>
          <p style="margin: 12px 0px;">今日温度:{{tem}}</p>
          <p style="margin: 12px 0px;">今日风况:{{wind}}</p>
          <p style="margin: 12px 0px;">空气质量:{{qua}}</p>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Home",
  data(){
    return{
      wea:"",
      tem:"",
      wind:"",
      qua:"",
      city:"",
      warnName:"",
      warnTime:""

    }
  },
  created() {
    this.position()
    this.getweather()
  },
  methods:{
    position(){
      this.request.get("/position").then(res =>{
        console.log(res[0].city)
        this.city = res[0].city
      })
    },
    getweather(){
      this.request.get("/weather").then(res =>{
        console.log(res[1])
        this.wea = res[1].wea
        this.wind = res[1].wind
        this.tem = res[1].tem
        this.qua = res[1].qua
      })
    },
    open2() {
      this.$notify({
        title: '提示',
        message: '这是一条不会自动关闭的消息',
        duration: 0
      });
    }
  }
}
</script>

<style scoped>
.wapper{
  background: url('https://img.zcool.cn/community/010deb5efc20f2a801215aa0154640.jpg?x-oss-process=image/auto-orient,1/resize,m_lfit,w_1280,limit_1/sharpen,100/format,webp/quality,Q_100') no-repeat;
  background-size: 100% 100% ;
  width: 100%;
  height: 100%;
}
.el-card{
  display: block;
  margin: 0px auto;
  background: rgba(0,0,0,.5);
  /*背景颜色为黑色，透明度为0.7*/
  width: 400px;
  padding: 10px 20px;
  border-radius: 13px;
  color: white;
}
</style>