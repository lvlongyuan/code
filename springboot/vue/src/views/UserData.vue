<template>
  <div>
    <el-row>
      <el-col :span="12"><div id="main" style="width:500px; height: 400px; margin-top: 20px"></div></el-col>
      <el-col :span="12"><div id="pie" style="width:500px; height: 400px; margin-top: 20px"></div></el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "Home",
  data(){
    return{

    }
  },
  mounted() { //页面元素渲染之后再触发
    //折线图

    //折线图
    var option = {
      title: {
        text: '各季度用户数量统计',
        subtext: '折线图',
        left: 'center'
      },
      xAxis: {
        type: 'category',
        data: ["第一季度","第二季度","第三季度","第四季度"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        {
          data: [],
          type: 'bar'
        }
      ]
    };
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);

    //饼图
    var pieOption = {
      title: {
        text: '各季度用户数量统计',
        subtext: '比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          type: 'pie',//图表类型
          radius: '50%',//饼图大小比例
          data: [],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);


    this.request.get("/echarts/members").then(res => {
      option.series[0].data = res.data
      option.series[1].data = res.data
      myChart.setOption(option);


      pieOption.series[0].data = [
        {name:'第一季度' ,value: res.data[0]},
        {name:'第二季度' ,value: res.data[1]},
        {name:'第三季度' ,value: res.data[2]},
        {name:'第四季度' ,value: res.data[3]}
      ]
      pieChart.setOption(pieOption);

    })
  }
}

</script>

<style scoped>

</style>