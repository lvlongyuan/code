<template>
  <div style="margin-left: 20px">
    <!--内 上下边距15px-->
    <div style="margin: 10px 0">
      <el-input class="mr-5" style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"@keyup.enter.native="load"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0"> <!--内 上左下右-->
        <el-button type="primary" @click="handleAdd(form.pid)">新增<i class="el-icon-circle-plus-outline"></i></el-button>
    </div>

    <!--信息列表-->
    <el-table
        :data="tableData"
        border stripe
        @selection-change="handleSelectionChange"
        row-key="id"
        default-expand-all>
      <el-table-column prop="id" label="ID"></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="path" label="路径"></el-table-column>
      <el-table-column prop="pagePath" label="页面路径"></el-table-column>
      <el-table-column label="图标">
        <template slot-scope="scope">
          <span :class="scope.row.icon"/>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>

      <el-table-column label="操作" width="280" align="center">
        <template slot-scope="scope">
          <el-button type="primary" @click="handleAdd(scope.row.id)" v-if="!scope.row.pid && !scope.row.path">新增子菜单<i class="el-icon-plus"></i></el-button>
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


    <!--新增表单-->
    <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="页面路径">
          <el-input v-model="form.pagePath" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
         <el-select clearable v-model="form.icon" placeholder="请选择图标" style="width: 100%">
           <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
             <i :class="item.value" /> {{ item.name }}
           </el-option>

         </el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
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
      name:"",
      dialogFormVisible:false,
      form:{},
      choiceShow:false,//设置多选按钮默认关闭
      multipleSelection:[],//数组接收要多选的数据
      options:[]
    }
  },
  created() {
    //请求分页查询数据
    this.load()
  },
  methods:{
    load(){ //请求数据：包括 页码，每页条数，总条数 用这些数据渲染到页面
      this.request.get("/menu",{
        params:{
          name:this.name
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
      })
    },
    reset(){
      this.name=""
      this.load()
    },
    save(){
      this.request.post("/menu",this.form).then(res => {//请求接口，request.js中设置api 简化url
        if(res.code == '200'){
          this.$message.success("操作成功")
          this.dialogFormVisible = false
          this.load()
        }else {
          this.$message.error("操作失败")
        }
      })
    },
    handleAdd(pid){
      this.dialogFormVisible=true
      this.form={}
      if (pid){
        this.form.pid = pid
      }
    },
    handleEdit(row){
      this.form = row //获取选中信息放入form中
      this.dialogFormVisible = true //打开表单

      //请求icon数据
      this.request.get("/menu/icon").then(res => {
        this.options = res.data
      })
    },
    handleDelete(id){
      this.request.delete("/menu/"+id).then(res =>{
        if(res.code == '200'){
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.error("删除失败")
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
    }
  },
  name: "Menu",
  props:{

  }
}
</script>

<style scoped>

</style>