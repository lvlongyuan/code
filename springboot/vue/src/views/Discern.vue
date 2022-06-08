<template>
  <div>
    <el-card style="width:460px;margin:50px auto;">
      <div style="margin: 20px 0;text-align: center;"><b>上传图片开始识别</b>
        <div style="text-align: left; padding: 0px 10px;margin-top: 20px">
          <el-form label-width="80px">
            <el-upload
                class="image-uploader"
                action="http://localhost:9090/discern/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess">
              <img v-if="imageUrl" :src="imageUrl" class="upPlant">
              <i v-else class="el-icon-plus image-uploader-icon"></i>
            </el-upload>

            <el-form-item label="识别结果">
              <el-tooltip placement="top">
                <div slot="content">识别结果准确程度由高到低进行输出<br/>第一个结果最精确</div>
                <el-input v-model="itis" disabled autocomplete="off"style="width:250px;border: lavender solid 1px;"></el-input>
              </el-tooltip>
            </el-form-item>

          </el-form>
        </div>
      </div>

    </el-card>
  </div>
</template>



<script>

export default {
  name: "Discern",
  data(){
    return{
      itis:"",
      imageUrl:'',
    }
  },
  created() {
  },
  methods:{
    handleAvatarSuccess(res,file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.itis = res
    },
  },
}
</script>




<style>
.image-uploader{
  text-align: center;
  padding-bottom: 10px;
}
.image-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.image-uploader .el-upload:hover {
  border-color: #409EFF;
}
.image-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 188px;
  height: 188px;
  line-height: 188px;
  text-align: center;
}
.upPlant {
  width: 188px;
  height: 188px;
  display: block;
}

</style>