<template>
  <div class="wapper">
    <div>
      <el-button type="primary" @click="table = true">撒点种子！！！</el-button>
      <el-drawer
          title="科学种植"
          :visible.sync="table"
          direction="rtl"
          size="40%">
        <el-table :data="gridData" style="margin-left: 50px;">
          <el-table-column property="plantName" label="植物名称" width="200"></el-table-column>
          <el-table-column property="plantUrl" label="植物图" width="300">
            <template slot-scope="scope">
              <a :href="scope.row.plantUrl" target="_blank" style="text-decoration: none;"><img :src="scope.row.imgUrl"></a>
            </template>
          </el-table-column>
        </el-table>
      </el-drawer>
    </div>
  </div>
</template>

<script>
export default {
  name: "Skill",
  data() {
    return {
      table: false,
      dialog: false,
      loading: false,
      gridData:[],
      plantName:"",
      plantUrl:"",
      imgUrl:""
    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      this.request.get("/skill",{
        params:{
          plantName:this.plantName,
          plantUrl:this.plantUrl,
          imgUrl:this.imgUrl
        }
      }).then(res => {
        console.log(res)
        this.gridData = res
      })

    }
  }
}
</script>

<style scoped>

.wapper{
  background: url('https://img.zcool.cn/community/01yktetpphqg4lnjnp8qvr3536.jpg?x-oss-process=image/auto-orient,1/resize,m_lfit,w_1280,limit_1/sharpen,100/format,webp/quality,Q_100') no-repeat;
  background-size: 100% 100% ;
  background-attachment: fixed;
  width: 100%;
  height: 100%;
  /*position: absolute;*/ /*保持图片原尺寸*/
  /*overflow: hidden;*/
}

</style>