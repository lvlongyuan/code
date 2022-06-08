<template>
  <div style="margin-left: 20px">
    <!--内 上下边距15px-->
    <div style="margin: 10px 0">
      <el-input class="mr-5" style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"@keyup.enter.native="load"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0"> <!--内 上左下右-->
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
    </div>

    <!--信息列表-->
    <el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" v-if="choiceShow" width="55"></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="flag" label="唯一标识"></el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>

      <el-table-column label="操作" width="280" align="center">
        <template slot-scope="scope">
          <el-button type="info" @click="selectMenu(scope.row)">分配菜单<i class="el-icon-menu"></i></el-button>
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
    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识">
          <el-input v-model="form.flag" autocomplete="off"></el-input>
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

    <!--新增表单-->
    <el-dialog title="菜单分配" :visible.sync="menuVisible" width="30%">
      <el-tree
          :data="menuData"
          :props="props"
          node-key="id"
          :default-expanded-keys="expends"
          :default-checked-keys="checks"
          ref="tree"
          show-checkbox>
        <span class="custom-tree-node"slot-scope="{node,data}">
          <span><i :class="data.icon"></i>{{ data.name }}</span>
        </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
        <el-button @click="menuVisible = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  data(){
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      dialogFormVisible: false,
      menuVisible: false,
      form: {},
      choiceShow: false,//设置多选按钮默认关闭
      multipleSelection: [],//数组接收要多选的数据
      menuData: [],
      props:{
        label:'name',
      },
      expends:[],
      checks:[],
      roleId: 0,
      roleFlag:''
    }
  },

  created(){
    //请求分页查询数据
    this.load()
  },

  methods:{
    load(){ //请求数据：包括 页码，每页条数，总条数 用这些数据渲染到页面
      this.request.get("/role/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset(){
      this.name=""
      this.load()
    },
    save(){
      this.request.post("/role",this.form).then(res => {//请求接口，request.js中设置api 简化url
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
      this.request.delete("/role/"+id).then(res =>{
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
    },
    selectMenu(role){
      this.menuVisible = true
      this.roleId = role.id
      this.roleFlag = role.flag

      // 请求数据
      this.request.get("/menu").then(res => {
        this.menuData = res.data

        //把后台返回的菜单数据处理成id数组
        this.expends = this.menuData.map(v => v.id)
      })
      this.request.get("/role/rolemenu/" + this.roleId).then(res => {
        //先渲染弹框内的元素，或者使用$nextTick对未来元素进行处理
        this.menuVisible = true

        this.checks = res.data
        this.request.get("/menu/ids").then(r => {
          const ids = r.data
          ids.forEach(id => {
            if (!this.checks.includes(id)){
              this.$refs.tree.setChecked(id,false)
            }
          })

        })
      })
    },
    saveRoleMenu(){
      this.request.post("/role/rolemenu/"+this.roleId,this.$refs.tree.getCheckedKeys()).then(res => {
        if(res.code === '200'){
          this.$message.success("绑定成功")
          this.menuVisible = false
          //操作管理员角色后需要重新登陆
          if(this.roleFlag === 'ROLE_ADMIN'){
            this.$store.commit("logout")
          }
        }else {
          this.$message.error(res.msg)
        }
      })
    }
  },
  name: "Role"
}
</script>

<style scoped>

</style>