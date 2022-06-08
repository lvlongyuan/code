<template>
  <diV>
    <el-card id="main" style="height: 600px;width: 1120px;margin:20px auto" ></el-card>
  </diV>
</template>

<script>
import * as echarts from 'echarts';
export default {
  name: "PricePic",
  mounted() {

    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var option = {
      title:{
        text:"农产品批发价格200指数"
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: []
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line',
          areaStyle: {}
        }
      ]
    };
    this.request.get("/priceindex").then(res => {
      console.log(res[0].datename)
      option.xAxis.data = [res[0].datename,res[1].datename,res[2].datename,res[3].datename,res[4].datename,res[5].datename,res[6].datename,res[7].datename,res[8].datename,res[9].datename]

      option.series[0].data = [res[0].value,res[1].value,res[2].value,res[3].value,res[4].value,res[5].value,res[6].value,res[7].value,res[8].value,res[9].value]

      myChart.setOption(option)
    })

  }
}
</script>

<style scoped>

</style>