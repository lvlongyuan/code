<template>
  <div style="margin-left: 10px">
    <div style="display: block; margin:20px auto;color: red;">
      <p>最新预警信息: {{warnt}}{{warnn}}</p>
    </div>
    <div style="margin: 10px 0">
      <el-input class="mr-5" style="width: 200px" placeholder="请输入要查询的信号内容" suffix-icon="el-icon-search" v-model="warnName"@keyup.enter.native="load"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <!--表格-->
    <el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
      <el-table-column prop="warnName" label="预警信号"></el-table-column>
      <el-table-column prop="warnTime" label="时间"></el-table-column>
      <el-table-column prop="warnUrl"  label="详细查询" width="100px">
        <template slot-scope="scope">
          <a :href="scope.row.warnUrl" target="_blank" class="el-icon-link" style="text-decoration: none;"></a>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <div class="pd-10">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>
export default {
  name: "Warn",
  data(){
    return{
      tableData:[],
      warnName:"",
      warnTime:"",
      warnUrl:"",
      multipleSelection:[],
      total:0,
      pageNum:1,
      pageSize:10,
      warnn:"",
      warnt:"",
    }
  },
  created() {
    // //请求分页查询数据
    // fetch("http://localhost:9090/warn/page?pageNum=1&pageSize=2").then(res => res.json()).then(res => {
    //   console.log(res)
    // })
    this.warn()
    this.load()
  },
  methods:{
    warn(){
      this.request.get("/warn").then(res =>{
        console.log(res[0])
        this.warnt = res[0].warnTime
        this.warnn = res[0].warnName
      })
    },
    load(){ //请求数据：包括 页码，每页条数，总条数 用这些数据渲染到页面
      this.request.get("/warn/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          warnName:this.warnName,
          warnTime:this.warnTime,
          warnUrl:this.warnUrl
        }
      }).then(res => {
        // fetch("http://localhost:9090/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username)
        //     .then(res =>res.json()).then(res => {
        console.log(res)
        this.tableData = res.data
        this.total = res.total
        //   this.tableData = res.data //后台得到的数据会自动放到tableData中
        //   this.total = res.total //在后台获取到数据总数也自动更新到total
      })
    },
    reset(){
      this.warnName=""
      this.warnTime=""
      this.load()
    },

    handleSizeChange(pageSize){
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()//重新在前台获得 每页条数 数据并渲染
    },
    handleCurrentChange(pageNum){
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()//重新在前台获得 页码 数据并渲染
    },
    handleSelectionChange(val){
      this.multipleSelection = val
    },
    handleFileUploadSuccess(){
      this.load()
    }
  }
}
</script>

<style scoped>

</style>