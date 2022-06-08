<template>
  <div style="margin-left: 20px">
    <!--内 上下边距15px-->
    <div style="margin: 10px 0">
      <el-input class="mr-5" style="width: 200px" placeholder="请输入搜索名称" suffix-icon="el-icon-search" v-model="username"@keyup.enter.native="load"></el-input>
      <el-input class="mr-5" style="width: 200px" placeholder="请输入搜索地区" suffix-icon="el-icon-search" v-model="address"@keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" placeholder="请输入昵称" suffix-icon="el-icon-search" v-model="nickname"@keyup.enter.native="load"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0 0 0"> <!--内 上左下右-->
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
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
      <el-upload
          :show-file-list="false"
          :accept="'xlsx'"
          :on-success="handleExcelImportSuccess"
          action="http://localhost:9090/user/import"
          style="display: inline-block">
          <el-button type="primary">导入<i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出<i class="el-icon-top"></i></el-button>
    </div>

    <!--多选按钮-->
    <div><el-button type="text" @click="openChoice" style="color:darkgrey">多选</el-button></div>
    <!--信息列表-->
    <el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" v-if="choiceShow" width="55"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="role" label="角色"></el-table-column>
      <el-table-column prop="nickname" label="昵称"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>

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

    <!--新增表单-->
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-select clearable v-model="form.role" placeholder="请选择角色" style="width: 100%">
            <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.flag"></el-option>
          </el-select>
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
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">确 定</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  data(){
    return{
      tableData:[],
      total:0,
      pageNum:1,
      pageSize:5,
      username:"",
      address:"",
      nickname:"",
      dialogFormVisible:false,
      form:{},
      choiceShow:false,//设置多选按钮默认关闭
      multipleSelection:[],//数组接收要多选的数据
      roles:""
    }
  },
  created() {
    //请求分页查询数据
    this.load()
  },
  methods:{
    openChoice(){
      //多选按钮，点击展开
      this.choiceShow = !this.choiceShow
    },
    load(){ //请求数据：包括 页码，每页条数，总条数 用这些数据渲染到页面
      this.request.get("/user/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
          nickname:this.nickname,
          address:this.address
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

      this.request.get("/role").then(res => {
        this.roles = res.data
      })

    },
    reset(){
      this.username=""
      this.nickname=""
      this.address=""
      this.load()
    },
    save(){
      this.request.post("/user",this.form).then(res => {//请求接口，request.js中设置api 简化url
        if(res.code == '200'){
          this.$message.success("操作成功")
          this.dialogFormVisible = false
          this.load()
        }else {
          this.$message.error("操作失败")
        }
      })
    },
    handleAdd(){
      this.dialogFormVisible=true
      this.form={}
    },
    handleEdit(row){
      this.form = row //获取选中信息放入form中
      this.dialogFormVisible = true //打开表单
    },
    handleDelete(id){
      this.request.delete("/user/"+id).then(res =>{
        if(res.code == '200'){
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)  //扁平化处理 [{},{},{},{}] => [1,2,3]
      this.request.post("/user/del/batch",ids).then(res =>{
        if(res.code == '200'){
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
    exp(){
      window.open("http://localhost:9090/user/export")
    },
    handleExcelImportSuccess(){
      this.$message.success("导入成功")
      this.load()
    }
  },
  name: "User",
  props:{

  }
}
</script>

<style scoped>

</style>