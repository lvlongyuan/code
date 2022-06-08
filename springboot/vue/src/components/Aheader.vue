<template>
  <div style="width: 600px;margin: auto;">
    <div>
      <el-row>
        <el-col :span="6">
          <el-link type="warning" @click="$router.push('/manger/user')" autocomplete="off" >用户管理</el-link>
        </el-col>
        <el-col :span="6">
          <el-link type="warning" @click="$router.push('/manger/file')" autocomplete="off" >文件管理</el-link>
        </el-col>
        <el-col :span="6">
          <el-link type="warning" @click="$router.push('/manger/userdata')" autocomplete="off" >用户数据</el-link>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "Aheader"
}
</script>

<style scoped>

</style>