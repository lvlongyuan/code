<template>
  <div style="margin-left: 20px">
    <div style="margin: 10px 0">
      <el-input class="mr-5" style="width: 200px" placeholder="请输入查询内容" suffix-icon="el-icon-search" v-model="name"@keyup.enter.native="load"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0 0 0">
      <el-upload
          :show-file-list="false"
          :on-success="handleFileUploadSuccess"
          action="http://localhost:9090/file/upload"
          style="display: inline-block">
        <el-button type="primary">上传文件<i class="el-icon-top"></i></el-button>
      </el-upload>
      <el-popconfirm class="ml-5"
                     confirm-button-text='确定'
                     cancel-button-text='再想想吧'
                     icon="el-icon-info"
                     icon-color="red"
                     title="您确定要删除选中的所有内容吗？"
                     @confirm="delBatch"
      >
        <el-button type="danger" slot="reference" style="margin-right: 5px">批量删除<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>

    <!--多选按钮-->
    <div><el-button type="text" @click="openChoice" style="color:darkgrey">多选</el-button></div>

    <el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" v-if="choiceShow" width="55"></el-table-column>
      <el-table-column prop="name" label="文件名称"></el-table-column>
      <el-table-column prop="type" label="文件类型"></el-table-column>
      <el-table-column prop="size" label="文件大小"></el-table-column>
      <el-table-column label="下载">
        <template slot-scope="scope">
          <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column  label="启用">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable" @change="changeEnable(scope.row)" active-color="#13ce66" inactive-color="#ccc"></el-switch>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-popconfirm class="ml-5"
                         confirm-button-text='确定'
                         cancel-button-text='再想想吧'
                         icon="el-icon-info"
                         icon-color="red"
                         title="您确定要删除吗？"
                         @confirm="handleDelete(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
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
  name: "File",
  data(){
    return{
      tableData:[],
      name:"",
      multipleSelection:[],
      total:0,
      pageNum:1,
      pageSize:5,
      choiceShow:false,//设置多选按钮默认关闭
    }
  },
  created() {
    this.load()
  },
  methods:{
    openChoice(){
      //多选按钮，点击展开
      this.choiceShow = !this.choiceShow
    },
    load(){ //请求数据：包括 页码，每页条数，总条数 用这些数据渲染到页面
      this.request.get("/file/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
        }
      }).then(res => {
        // fetch("http://localhost:9090/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username)
        //     .then(res =>res.json()).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
        //   this.tableData = res.data //后台得到的数据会自动放到tableData中
        //   this.total = res.total //在后台获取到数据总数也自动更新到total
      })
    },
    reset(){
      this.name=""
      this.load()
    },
    handleDelete(id){
      this.request.delete("/file/"+id).then(res =>{
        if(res.code === '200'){
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)  //扁平化处理 [{},{},{},{}] => [1,2,3]
      this.request.post("/file/del/batch",ids).then(res =>{
        if(res.code === '200'){
          this.$message.success("批量删除成功")
          this.load()
          this.choiceShow = false
        }else {
          this.$message.error("批量删除失败，请确认是否选择内容")
        }
      })
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
    },
    download(url){
      window.open(url)
    },
    changeEnable(row){
      this.request.post("/file/update",row).then(res => {
        if (res.code === '200'){
          this.$message.success("操作成功")
        }
      })
    }
  }
}
</script>

<style scoped>

</style>